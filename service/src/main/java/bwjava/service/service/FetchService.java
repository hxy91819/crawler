package bwjava.service.service;

import bwjava.service.dao.read.BeautyModelReaderDao;
import bwjava.service.dao.write.BeautyModelPicWriterDao;
import bwjava.service.dao.write.BeautyModelWriterDao;
import bwjava.service.entity.BeautyModel;
import bwjava.service.entity.BeautyModelPic;
import bwjava.util.SnowFlake;
import com.bwjava.client.BeautyListClient;
import com.bwjava.client.BeautyPicClient;
import com.bwjava.dto.BeautyListInfo;
import com.bwjava.dto.ModelInfo;
import com.bwjava.entity.CrawlMeta;
import com.bwjava.entity.CrawlResult;
import com.bwjava.service.SimpleCrawlJob;
import com.bwjava.util.ExecutorServiceUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * 模特数据爬取服务
 * @author chenjing
 * @date 2019-02-24 11:57
 */
@Log4j2
@Service
public class FetchService {
    @Resource
    private BeautyModelWriterDao beautyModelWriterDao;

    @Resource
    private BeautyModelReaderDao beautyModelReaderDao;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private ExecutorService executorService;

    /**
     * 获取模特信息
     *
     * @param url 首页url
     * @return 模特信息
     */
    public ModalData crawlModalData(String url) {
        Set<List<String>> selectRule = new HashSet<>();
        List<String> titleRule = Collections.singletonList("h1"); // 标题
        selectRule.add(titleRule);
        List<String> picCountRule = Arrays.asList("div[class=width]", "div[class=c_1]", "p");
        selectRule.add(picCountRule);

        CrawlMeta crawlMeta = new CrawlMeta(url, selectRule);

        SimpleCrawlJob job = new SimpleCrawlJob();
        job.setCrawlMeta(crawlMeta);
        job.setDepth(0);

        Future<?> submit = executorService.submit(job);
        try {
            submit.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        CrawlResult result = job.getCrawlResults().get(0);
        List<String> strings = result.getResult().get(titleRule);
        Preconditions.checkState(!strings.isEmpty());
        String title = strings.get(0);
        List<String> picCount = result.getResult().get(picCountRule);
        return null;
    }

    /**
     * 爬取并保存模特列表
     *
     * @param entranceUrls 入口地址列表
     * @param pageCount
     */
    public void fetchAndSaveBeautyList(List<String> entranceUrls, Integer pageCount) {
        BeautyListClient client = new BeautyListClient(entranceUrls, pageCount);
        BeautyListInfo modelPicUrls = client.getModelPicUrls();
        List<String> urls = modelPicUrls.getUrl();
        List<String> thumbPics = modelPicUrls.getThumbPic();
        Preconditions.checkState(!urls.isEmpty());
        Preconditions.checkState(urls.size() == thumbPics.size());
        List<BeautyModel> beautyModels = new ArrayList<>();
        for (int i = 0; i < urls.size(); i++) {
            long id = snowFlake.nextId();
            BeautyModel beautyModel = new BeautyModel();
            beautyModel.setId(id);
            beautyModel.setEntranceUrl(urls.get(i));
            beautyModel.setThumbPic(thumbPics.get(i));
            beautyModels.add(beautyModel);
        }
        // 从数据库查询当前已经保存的，防止多次跑的时候进入重复的数据
        log.info("current crawl pics :{}", beautyModels.size());
        List<BeautyModel> beautyModelDB = beautyModelReaderDao.selectEntranceurl();
        log.info("db pics:{}", beautyModelDB.size());
        beautyModels.removeAll(beautyModelDB);
        if (beautyModels.isEmpty()) {
            return;
        }
        int count = beautyModelWriterDao.insertBatch(beautyModels);
        log.info("insert batch count:{}", count);
    }

    @Resource
    private BeautyModelPicWriterDao beautyModelPicWriterDao;

    /**
     * 根据数据库保存的入口地址，跑批所有模特的图片
     */
    public void fetchAndSaveBeautyPics() {
        Page<BeautyModel> beautyModels;
        int pageNum = 1;
        long pageTotal;
        do {
            beautyModels = PageHelper.startPage(pageNum++, 100).doSelectPage(() -> beautyModelReaderDao.selectIdEntranceurl());
            pageTotal = beautyModels.getPages();

            List<ModelInfo> modelInfos = beautyModels.stream().map(
                    x -> {
                        ModelInfo modelInfo = new ModelInfo();
                        modelInfo.setId(x.getId());
                        modelInfo.setEntranceUrl(x.getEntranceUrl());
                        return modelInfo;
                    }
            ).collect(Collectors.toList());
            BeautyPicClient client = new BeautyPicClient(5, modelInfos);
            List<ModelInfo> modelInfoWithPics = client.doFetchAllUrls();
            for (ModelInfo modelInfoWithPic : modelInfoWithPics) {
                // 更新基本信息
                BeautyModel beautyModel = new BeautyModel();
                BeanUtils.copyProperties(modelInfoWithPic, beautyModel);
                beautyModelWriterDao.updateByPrimaryKeySelective(beautyModel);
                // 删除所有原图
                beautyModelPicWriterDao.deleteByModelId(modelInfoWithPic.getId());
                List<String> picUrls = modelInfoWithPic.getPicUrls();
                // 批量插入新图
                if (picUrls == null || picUrls.isEmpty()) {
                    log.warn("modelInfo:{}, don't have pic", modelInfoWithPic);
                    continue;
                }
                List<BeautyModelPic> beautyModelPics = picUrls.parallelStream().map(x -> {
                    long l = snowFlake.nextId();

                    BeautyModelPic beautyModelPic = new BeautyModelPic();
                    beautyModelPic.setId(l);
                    beautyModelPic.setModelId(modelInfoWithPic.getId());
                    beautyModelPic.setPicUrl(x);
                    return beautyModelPic;
                }).collect(Collectors.toList());
                Runnable insertRunnable = () -> {
                    int count = beautyModelPicWriterDao.insertBatch(beautyModelPics);
                    log.info("insert batch count:{}", count);
                };
                ExecutorServiceUtil.getInstance().execute(insertRunnable);
            }
        } while (pageTotal >= pageNum);
    }
}

@Getter
@Setter
class ModalData {
    private String title;

    private String picCount;
}
