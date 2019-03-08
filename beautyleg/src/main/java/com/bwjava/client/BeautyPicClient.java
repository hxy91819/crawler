package com.bwjava.client;

import com.bwjava.dto.ModelInfo;
import com.bwjava.entity.CrawlMeta;
import com.bwjava.entity.CrawlResult;
import com.bwjava.service.SimpleCrawlJob;
import com.bwjava.util.ExecutorServiceUtil;
import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * 单个模特的页面爬虫
 *
 * @author chenjing
 * @date 2019-02-24 15:04
 */
@Log4j2
public class BeautyPicClient {

    /**
     * 每页图片数量
     */
    private int picCountPerPage = 4;

    private List<ModelInfo> modelInfos;

    public BeautyPicClient(int picCountPerPage, List<ModelInfo> modelInfos) {
        this.picCountPerPage = picCountPerPage;
        this.modelInfos = modelInfos;
    }

    private static final List<String> TITLE_RULE = Collections.singletonList("h1");
    private static final List<String> PIC_COUNT_RULE = Arrays.asList("div[class=tuji]", "p:contains(图片数量)");

    /**
     * 获取模特基本信息
     *
     * @return 模特基本信息
     */
    private SimpleCrawlJob getModelInfo(String entranceUrl) {
        // 从第一页抓取标题和基本信息
        Set<List<String>> selectRule = new HashSet<>();
        selectRule.add(TITLE_RULE);
        selectRule.add(PIC_COUNT_RULE);
        selectRule.add(IMG_RULE);

        CrawlMeta crawlMeta = new CrawlMeta(entranceUrl, selectRule);

        SimpleCrawlJob job = new SimpleCrawlJob();
        job.setCrawlMeta(crawlMeta);
        job.setDepth(0);
        return job;
    }

    /**
     * 图片选择器规则
     */
    private static final List<String> IMG_RULE = Arrays.asList("div[class=content]", "img[class=tupian_img]");

    /**
     * 构建爬取模特每张照片的Job
     *
     * @param entranceUrl
     * @param picCount
     * @return
     */
    public SimpleCrawlJob buildGetPicUrlsJob(String entranceUrl, Integer picCount) {
        Set<List<String>> selectRule = new HashSet<>();
        selectRule.add(IMG_RULE);

//        Set<List<String>> selectHrefs = new HashSet<>();
//        selectHrefs.add(Arrays.asList("div[id=pages]", "a[class=a1]", ":contains(下一页)"));

        CrawlMeta crawlMeta = new CrawlMeta(entranceUrl, selectRule);
//        crawlMeta.setSelectorHrefs(selectHrefs); // 可选项

        SimpleCrawlJob job = new SimpleCrawlJob();
        job.setCrawlMeta(crawlMeta);
        job.setDepth(0);
        return job;
    }


    /**
     * 获取每一页的图片url
     *
     * @param picCount 图片总数
     * @return 所有图片的url list
     */
//    public List<String> getPicUrls(String entranceUrl, Integer picCount) {
//        Future<?> submit = service.submit(job);
//        try {
//            submit.get();
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
//
//        List<String> picUrls = new ArrayList<>();
//        List<CrawlResult> crawlResults = job.getCrawlResults();
//        for (CrawlResult crawlResult : crawlResults) {
//            List<String> strings = crawlResult.getResult().get(imgRule);
//            picUrls.addAll(strings);
//        }
//        return picUrls;
//    }

    private ExecutorService service = ExecutorServiceUtil.getInstance();

    /**
     * 爬取url所有模特的图片信息
     *
     * @return
     */
    public List<ModelInfo> doFetchAllUrls() {
        if (modelInfos.isEmpty()) {
            return Collections.emptyList();
        }
        CountDownLatch countDownLatch = new CountDownLatch(modelInfos.size());

        Map<String, ModelInfo> modelInfoMap = new HashMap<>(modelInfos.size());
        List<SimpleCrawlJob> jobs = new ArrayList<>();
        for (ModelInfo modelInfo : modelInfos) {
            String entranceUrl = modelInfo.getEntranceUrl();
            modelInfoMap.put(entranceUrl, modelInfo);
            SimpleCrawlJob job = getModelInfo(entranceUrl);
            jobs.add(job);
            job.setCountDownLatch(countDownLatch);
            service.execute(job);
        }

        try {
            countDownLatch.await(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // 爬取完毕之后，匹配信息到ModelInfos当中
        for (SimpleCrawlJob job : jobs) {
            ModelInfo modelInfo = modelInfoMap.get(job.getCrawlMeta().getUrl());
            if (modelInfo == null) {
                log.warn("url:{} can't get model", job.getCrawlMeta().getUrl());
                continue;
            }
            List<CrawlResult> crawlResults = job.getCrawlResults();
            if (crawlResults == null || crawlResults.isEmpty()) {
                log.warn("modelInfo:{}, don't get results", modelInfo);
                continue;
            }
            Map<List<String>, List<String>> result = crawlResults.get(0).getResult();
            List<String> title = result.get(TITLE_RULE);
            if (title != null && !title.isEmpty()) {
                modelInfo.setTitle(title.get(0));
            }
            List<String> picCount = result.get(PIC_COUNT_RULE);
            if (picCount != null && !picCount.isEmpty()) {
                for (String s : picCount) {
                    if (s.contains("图片数量")) {
                        if (s.length() > 8) {
                            modelInfo.setPicCount(Integer.valueOf(s.substring(6, s.length() - 1)));
                        }
                        break;
                    } else {
                        log.warn("modelInfo:{} don't has pic count", modelInfo);
                        modelInfo.setPicCount(0);
                    }
                }

                List<String> picUrls = new ArrayList<>();
                List<String> strings = result.get(IMG_RULE);
                if (strings == null || strings.isEmpty()) {
                    log.warn("modelInfo:{} don't get any pics", modelInfo);
                    continue;
                }
                // 使用规则计算出图片的url
                String picUrl = strings.get(0);
                if (picUrl.length() < 6) {
                    log.warn("invalid picUrl:{}", picUrl);
                    continue;
                }
                String headPicUrl = picUrl.substring(0, picUrl.length() - 5);
                Integer modelInfoPicCount = modelInfo.getPicCount();
                if (modelInfoPicCount == null) {
                    modelInfoPicCount = 0;
                }
                for (int i = 1; i <= modelInfoPicCount; i++) {
                    picUrls.add(headPicUrl + i + ".jpg");
                }
                modelInfo.setPicUrls(picUrls);
            }
        }
        return modelInfos;
    }

}

