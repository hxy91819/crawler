package com.bwjava.client;

import com.bwjava.entity.CrawlMeta;
import com.bwjava.entity.CrawlResult;
import com.bwjava.service.SimpleCrawlJob;
import com.bwjava.util.ExecutorServiceUtil;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 模特列表客户端
 *
 * @author chenjing
 * @date 2019-02-24 15:56
 */
@AllArgsConstructor
public class BeautyListClient {

    /**
     * 入口url
     */
    private String url;

    /**
     * 抓取的页面总数
     */
    private Integer pageCount;

    public List<String> getModelPicUrls() {
        Set<List<String>> selectRule = new HashSet<>();
        List<String> urlRule = Arrays.asList("div[class=main]", "div[class=boxs]", "a:has(img)");
        selectRule.add(urlRule);

        Set<List<String>> selectHrefs = new HashSet<>();
        selectHrefs.add(Arrays.asList("div[id=pages]", "a[class=a1]", ":contains(下一页)"));

        CrawlMeta crawlMeta = new CrawlMeta(url, selectRule);
//        crawlMeta.setSelectorHrefs(selectHrefs); // 可选项

        SimpleCrawlJob job = new SimpleCrawlJob();
        job.setCrawlMeta(crawlMeta);
        job.setDepth(0);

        Future<?> submit = ExecutorServiceUtil.getInstance().submit(job);
        try {
            submit.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        List<String> urls = new ArrayList<>();
        List<CrawlResult> crawlResults = job.getCrawlResults();
        for (CrawlResult crawlResult : crawlResults) {
            List<String> strings = crawlResult.getResult().get(urlRule);
            urls.addAll(strings);
        }
        return urls;
    }
}
