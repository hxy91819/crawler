package com.bwjava.service;

import com.bwjava.entity.CrawlMeta;
import com.bwjava.entity.CrawlResult;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 爬虫使用范例
 *
 * @author chenjing
 * @date 2019-02-21 15:43
 */
public class SimpleCrawlJobTest {
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
}
