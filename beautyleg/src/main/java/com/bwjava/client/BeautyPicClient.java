package com.bwjava.client;

import com.bwjava.dto.ModelInfo;
import com.bwjava.entity.CrawlMeta;
import com.bwjava.entity.CrawlResult;
import com.bwjava.service.SimpleCrawlJob;
import com.bwjava.util.ExecutorServiceUtil;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 单个模特的页面爬虫
 *
 * @author chenjing
 * @date 2019-02-24 15:04
 */
@AllArgsConstructor
public class BeautyPicClient {

    /**
     * 每页图片数量
     */
    private int picCountPerPage = 4;

    /**
     * 入口地址
     */
    private String url;

    /**
     * 获取模特基本信息
     *
     * @return 模特基本信息
     */
    public ModelInfo getModelInfo() {
        // 从第一页抓取标题和基本信息并入库
        Set<List<String>> selectRule = new HashSet<>();
        List<String> titleRule = Collections.singletonList("h1");
        selectRule.add(titleRule);
        List<String> picCountRule = Arrays.asList("div[class=width]", "div[class=c_l]", "p");
        selectRule.add(picCountRule);

        CrawlMeta crawlMeta = new CrawlMeta(url, selectRule);

        SimpleCrawlJob job = new SimpleCrawlJob();
        job.setCrawlMeta(crawlMeta);
        job.setDepth(0);

        Future<?> submit = ExecutorServiceUtil.getInstance().submit(job);
        try {
            submit.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        ModelInfo modelInfo = new ModelInfo();
        Map<List<String>, List<String>> result = job.getCrawlResults().get(0).getResult();
        List<String> title = result.get(titleRule);
        modelInfo.setTitle(title.get(0));
        List<String> picCount = result.get(picCountRule);
        for (String s : picCount) {
            if (s.contains("图片数量")) {
                if (s.length() > 8) {
                    modelInfo.setPicCount(Integer.valueOf(s.substring(6, s.length() - 2)));
                }
                break;
            }
        }
        return modelInfo;
    }


    /**
     * 获取每一页的图片url
     *
     * @param picCount 图片总数
     * @return 所有图片的url list
     */
    public List<String> getPicUrls(Integer picCount) {
        Set<List<String>> selectRule = new HashSet<>();
        List<String> imgRule = Collections.singletonList("img[class=content_img]");
        selectRule.add(imgRule);

        Set<List<String>> selectHrefs = new HashSet<>();
        selectHrefs.add(Arrays.asList("div[id=pages]", "a[class=a1]", ":contains(下一页)"));

        CrawlMeta crawlMeta = new CrawlMeta(url, selectRule);
        crawlMeta.setSelectorHrefs(selectHrefs); // 可选项

        SimpleCrawlJob job = new SimpleCrawlJob();
        job.setCrawlMeta(crawlMeta);
        job.setDepth(picCount / picCountPerPage - 1);

        Future<?> submit = ExecutorServiceUtil.getInstance().submit(job);
        try {
            submit.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        List<String> picUrls = new ArrayList<>();
        List<CrawlResult> crawlResults = job.getCrawlResults();
        for (CrawlResult crawlResult : crawlResults) {
            List<String> strings = crawlResult.getResult().get(imgRule);
            picUrls.addAll(strings);
        }
        return picUrls;
    }
}
