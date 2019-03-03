package com.bwjava.client;

import com.bwjava.dto.BeautyListInfo;
import com.bwjava.entity.CrawlMeta;
import com.bwjava.entity.CrawlResult;
import com.bwjava.service.SimpleCrawlJob;
import com.bwjava.util.ExecutorServiceUtil;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * 模特列表客户端
 *
 * @author chenjing
 * @date 2019-02-24 15:56
 */
@AllArgsConstructor
public class BeautyListClient {

    /**
     * 入口url（复数）
     */
    private List<String> entransUrls;

    /**
     * 抓取的页面总数
     */
    private Integer pageCount;

    /**
     * 获取模特的详情页入口url
     *
     * @return
     */
    public BeautyListInfo getModelPicUrls() {
        Set<List<String>> selectRule = new HashSet<>();
        // 地址规则
        List<String> urlRule = Arrays.asList("div[class=hezi]", "a:has(img)");
        selectRule.add(urlRule);
        // 封面图规则
        List<String> thumbPicRule = Arrays.asList("div[class=hezi]", "a", "img");
        selectRule.add(thumbPicRule);

//        Set<List<String>> selectHrefs = new HashSet<>();
//        selectHrefs.add(Arrays.asList("div[id=pages]", "a[class=a1]", ":contains(下一页)"));

        CountDownLatch countDownLatch = new CountDownLatch(entransUrls.size());
        List<SimpleCrawlJob> jobs = new ArrayList<>();
        for (String url : entransUrls) {
            CrawlMeta crawlMeta = new CrawlMeta(url, selectRule);
//            crawlMeta.setSelectorHrefs(selectHrefs); // 可选项

            SimpleCrawlJob job = new SimpleCrawlJob();
            job.setCrawlMeta(crawlMeta);
            job.setDepth(this.pageCount);
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
}
