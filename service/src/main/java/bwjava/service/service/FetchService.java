package bwjava.service.service;

import bwjava.service.dao.read.BeautyModelReaderDao;
import bwjava.service.dao.write.BeautyModelPicWriterDao;
import bwjava.service.dao.write.BeautyModelWriterDao;
import bwjava.service.entity.BeautyModel;
import bwjava.service.entity.BeautyModelPic;
import bwjava.service.util.BeautyLegUtil;
import bwjava.util.SnowFlake;
import com.bwjava.client.BeautyListClient;
import com.bwjava.client.BeautyPicClient;
import com.bwjava.dto.BeautyListInfo;
import com.bwjava.dto.ModelInfo;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

/**
 * 模特数据爬取服务
 *
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
     * 爬取并保存模特列表
     *
     * @param entrancesUrl 入口地址
     */
    public void fetchAndSaveBeautyList(String entrancesUrl) {
        BeautyListClient client = new BeautyListClient(entrancesUrl);
        BeautyListInfo modelPicUrls = client.getModelPicUrls();
        String org = client.getOrg();
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
            beautyModel.setOrg(org);
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
    public void fetchAndSaveBeautyPics(String org) {
        Page<BeautyModel> beautyModels;
        int pageNum = 1;
        long pageTotal;
        do {
            beautyModels = PageHelper.startPage(pageNum++, 100).doSelectPage(() -> beautyModelReaderDao.selectIdEntranceurl(org));
            pageTotal = beautyModels.getPages();

            fetchAndSaveByModels(beautyModels);
        } while (pageTotal >= pageNum);
    }

    /**
     * 指定需要跑批的数据
     *
     * @param pageNum
     * @param pageSize
     */
    public void fetchAndSaveBeautyPicsByPage(String org, int pageNum, int pageSize) {
        Page<BeautyModel> beautyModels = PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> beautyModelReaderDao.selectIdEntranceurl(org));
        fetchAndSaveByModels(beautyModels);
    }

    /**
     * 根据BeautyModels进行爬取并保存数据库
     *
     * @param beautyModels beautyModel列表
     */
    private void fetchAndSaveByModels(List<BeautyModel> beautyModels) {
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
                beautyModelPic.setSortNo(BeautyLegUtil.getPicSortNumber(x));
                return beautyModelPic;
            }).collect(Collectors.toList());
            Runnable insertRunnable = () -> {
                int count = beautyModelPicWriterDao.insertBatch(beautyModelPics);
                log.info("insert batch count:{}", count);
            };
            ExecutorServiceUtil.getInstance().execute(insertRunnable);
        }
    }
}

@Getter
@Setter
class ModalData {
    private String title;

    private String picCount;
}
