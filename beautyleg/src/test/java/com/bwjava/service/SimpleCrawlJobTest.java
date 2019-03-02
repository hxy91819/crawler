package com.bwjava.service;

import com.bwjava.entity.CrawlMeta;
import com.bwjava.entity.CrawlResult;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 爬虫使用范例
 *
 * @author chenjing
 * @date 2019-02-21 15:43
 */
@Log4j2
public class SimpleCrawlJobTest {
    @Test
    public void testlog() {
        log.info("hahaha:{}", "dick");
    }

    @Test
    public void testCrawl() throws InterruptedException {
        String url = "https://www.meitulu.com/item/15665.html";

        Set<List<String>> selectRule = new HashSet<>();
        selectRule.add(Collections.singletonList("img[class=content_img]"));
        selectRule.add(Collections.singletonList("h1[text]"));

        Set<List<String>> selectHrefs = new HashSet<>();
        selectHrefs.add(Arrays.asList("div[id=pages]", "a[href]", "a[class!=a1]"));

        CrawlMeta crawlMeta = new CrawlMeta(url, selectRule);
        crawlMeta.setSelectorHrefs(selectHrefs); // 可选项

        SimpleCrawlJob job = new SimpleCrawlJob();
        job.setCrawlMeta(crawlMeta);
        job.setDepth(1);

        Thread thread = new Thread(job, "crawler-test");
        thread.start();

        thread.join();

        List<CrawlResult> crawlResults = job.getCrawlResults();
        List<Map<List<String>, List<String>>> collect = crawlResults.stream().map(CrawlResult::getResult).collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test
    public void testFirstPage() {
        String url = "https://www.meitulu.com/item/16720.html";
        // 从第一页抓取标题和基本信息并入库
        Set<List<String>> selectRule = new HashSet<>();
        List<String> titleRule = Collections.singletonList("h1");
        selectRule.add(titleRule);

        CrawlMeta crawlMeta = new CrawlMeta(url, selectRule);

        SimpleCrawlJob job = new SimpleCrawlJob();
        job.setCrawlMeta(crawlMeta);
        job.setDepth(0);

        Thread thread = new Thread(job);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<String> strings = job.getCrawlResults().get(0).getResult().get(titleRule);
//        if (!strings.isEmpty()) {
//            return strings.get(0);
//        }
    }
}
