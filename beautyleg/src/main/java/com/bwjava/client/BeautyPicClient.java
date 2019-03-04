package com.bwjava.client;

import com.bwjava.dto.ModelInfo;
import com.bwjava.entity.CrawlMeta;
import com.bwjava.entity.CrawlResult;
import com.bwjava.service.SimpleCrawlJob;
import com.bwjava.util.ExecutorServiceUtil;
import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.concurrent.*;


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

    /**
     * 获取模特基本信息
     *
     * @return 模特基本信息
     */
    private ModelInfo getModelInfo(String entranceUrl) {
        // 从第一页抓取标题和基本信息
        Set<List<String>> selectRule = new HashSet<>();
        List<String> titleRule = Collections.singletonList("h1");
        selectRule.add(titleRule);
        List<String> picCountRule = Arrays.asList("div[class=tuji]", "p:contains(图片数量)");
        selectRule.add(picCountRule);

        CrawlMeta crawlMeta = new CrawlMeta(entranceUrl, selectRule);

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
                    modelInfo.setPicCount(Integer.valueOf(s.substring(6, s.length() - 1)));
                }
                break;
            }
        }
        return modelInfo;
    }

    /**
     * 构建爬取模特每张照片的Job
     *
     * @param entranceUrl
     * @param picCount
     * @return
     */
    public SimpleCrawlJob buildGetPicUrlsJob(String entranceUrl, Integer picCount) {
        Set<List<String>> selectRule = new HashSet<>();
        List<String> imgRule = Arrays.asList("div[class=content]", "img[class=tupian_img]");
        selectRule.add(imgRule);

        Set<List<String>> selectHrefs = new HashSet<>();
        selectHrefs.add(Arrays.asList("div[id=pages]", "a[class=a1]", ":contains(下一页)"));

        CrawlMeta crawlMeta = new CrawlMeta(entranceUrl, selectRule);
        crawlMeta.setSelectorHrefs(selectHrefs); // 可选项

        SimpleCrawlJob job = new SimpleCrawlJob();
        job.setCrawlMeta(crawlMeta);
        job.setDepth(picCount / picCountPerPage);
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

    public List<ModelInfo> doFetchAllUrls() {
        if (modelInfos.isEmpty()) {
            return Collections.emptyList();
        }
        CountDownLatch countDownLatch = new CountDownLatch(modelInfos.size());
        ThreadPoolExecutor tpe = ((ThreadPoolExecutor) service);

        List<SimpleCrawlJob> jobs = new ArrayList<>();
        Map<String, ModelInfo> modelInfoMap = new HashMap<>(modelInfos.size());
        for (ModelInfo modelInfo : modelInfos) {
            String entranceUrl = modelInfo.getEntranceUrl();
            modelInfoMap.put(entranceUrl, modelInfo); // 保存入口url和模特信息对应关系，方便后续进行匹配

            ModelInfo modelInfoCrawled = getModelInfo(entranceUrl);
            Integer picCount = modelInfoCrawled.getPicCount();
            modelInfo.setPicCount(picCount);
            modelInfo.setTitle(modelInfoCrawled.getTitle());

            SimpleCrawlJob simpleCrawlJob = buildGetPicUrlsJob(entranceUrl, picCount);
            simpleCrawlJob.setCountDownLatch(countDownLatch); // 设置定时器
            jobs.add(simpleCrawlJob);
            service.submit(simpleCrawlJob); // 提交跑批任务
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 爬取完毕之后，匹配信息到ModelInfos当中
        for (SimpleCrawlJob job : jobs) {
            String url = job.getCrawlMeta().getUrl();
//            job.getCrawlMeta().getSelectorRules().
            ModelInfo modelInfo = modelInfoMap.get(url);
            if (modelInfo == null) {
                log.warn("crawl url:{} don't get match model", url);
                continue;
            }
            List<String> picUrls = new ArrayList<>();
            List<CrawlResult> crawlResults = job.getCrawlResults();
            for (CrawlResult crawlResult : crawlResults) {
//                List<String> strings = crawlResult.getResult().get(imgRule);
//                picUrls.addAll(strings);
            }
        }
        return modelInfos;
    }

}

