package com.bwjava.client;

import bwjava.util.CommonUtil;
import com.bwjava.dto.BeautyListInfo;
import com.bwjava.entity.CrawlMeta;
import com.bwjava.entity.CrawlResult;
import com.bwjava.service.SimpleCrawlJob;
import com.bwjava.util.ExecutorServiceUtil;
import com.google.common.base.Preconditions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

/**
 * 模特列表客户端
 *
 * @author chenjing
 * @date 2019-02-24 15:56
 */
public class BeautyListClient {

    private static final Logger log = LogManager.getLogger(BeautyListClient.class);
    /**
     * 入口Url
     */
    private String entransUrl;

    /**
     * 单页套图数量
     */
    private static final int PAGE_SUITE_COUNT = 40;

    /**
     * 套图数量
     */
    private Integer suiteCount;

    /**
     * 机构名称
     */
    private String org;

    public BeautyListClient(String entransUrl) {
        this.entransUrl = entransUrl;
    }

    /**
     * 获取套图数量
     *
     * @return
     */
    public int getSuiteCount() {
        try {
            Set<List<String>> selectRule = new HashSet<>();
            // 地址规则
            List<String> suiteCount = Arrays.asList("div[class=shoulushuliang]", "span");
            selectRule.add(suiteCount);

            List<String> org = Arrays.asList("div[class=fenlei]", "h1");
            selectRule.add(org);

            CrawlMeta crawlMeta = new CrawlMeta(entransUrl, selectRule);
            SimpleCrawlJob job = new SimpleCrawlJob();
            job.setCrawlMeta(crawlMeta);
            job.setDepth(0);

            Future<?> submit = ExecutorServiceUtil.getInstance().submit(job);

            submit.get();
            List<String> strings = job.getCrawlResult().getResult().get(suiteCount);
            Preconditions.checkState(!strings.isEmpty());
            this.suiteCount = Integer.valueOf(strings.get(0));

            List<String> orgs = job.getCrawlResult().getResult().get(org);
            this.setOrg(orgs.get(0));
            return this.suiteCount;
        } catch (Exception e) {
            log.error("getSuiteCount exception:{}", CommonUtil.getStackTrace(e));
            return 0;
        }
    }

    /**
     * 获取模特的详情页入口url
     *
     * @return
     */
    public BeautyListInfo getModelPicUrls() {
        if (suiteCount == null) {
            this.getSuiteCount();
        }

        int pageCount = (int) Math.ceil((double) this.suiteCount / (double) PAGE_SUITE_COUNT);

        List<String> entransUrls = new ArrayList<>();
        entransUrls.add(entransUrl);
        for (int i = 1; i <= pageCount - 1; i++) {
            entransUrls.add(entransUrl + "index_" + i + ".html");
        }

        Set<List<String>> selectRule = new HashSet<>();
        // 地址规则
        List<String> urlRule = Arrays.asList("div[class=hezi]", "a:has(img)");
        selectRule.add(urlRule);
        // 封面图规则
        List<String> thumbPicRule = Arrays.asList("div[class=hezi]", "a", "img");
        selectRule.add(thumbPicRule);


        CountDownLatch countDownLatch = new CountDownLatch(entransUrls.size());
        List<SimpleCrawlJob> jobs = new ArrayList<>();
        for (String url : entransUrls) {
            CrawlMeta crawlMeta = new CrawlMeta(url, selectRule);

            SimpleCrawlJob job = new SimpleCrawlJob();
            job.setCrawlMeta(crawlMeta);
            job.setDepth(0);
            job.setCountDownLatch(countDownLatch);
            jobs.add(job);

            ExecutorServiceUtil.getInstance().submit(job);
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<String> urls = new ArrayList<>();
        List<String> thumbPics = new ArrayList<>();
        for (SimpleCrawlJob job : jobs) {
            List<CrawlResult> crawlResults = job.getCrawlResults();
            for (CrawlResult crawlResult : crawlResults) {
                List<String> urlTemp = crawlResult.getResult().get(urlRule);
                urls.addAll(urlTemp);
                List<String> thumbPicTemp = crawlResult.getResult().get(thumbPicRule);
                thumbPics.addAll(thumbPicTemp);
            }
        }
        BeautyListInfo beautyListInfo = new BeautyListInfo();
        beautyListInfo.setUrl(urls);
        beautyListInfo.setThumbPic(thumbPics);
        return beautyListInfo;
    }

    public String getOrg() {
        return this.org;
    }

    public void setOrg(String org) {
        this.org = org;
    }
}
