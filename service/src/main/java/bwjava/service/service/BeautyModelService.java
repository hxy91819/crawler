package bwjava.service.service;

import bwjava.service.dao.write.BeautyModelWriterDao;
import bwjava.service.entity.BeautyModel;
import bwjava.util.SnowFlake;
import com.bwjava.client.BeautyListClient;
import com.bwjava.dto.BeautyListInfo;
import com.bwjava.entity.CrawlMeta;
import com.bwjava.entity.CrawlResult;
import com.bwjava.service.SimpleCrawlJob;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author chenjing
 * @date 2019-02-24 11:57
 */
@Service
public class BeautyModelService {

    @Resource
    private BeautyModelWriterDao beautyModelWriterDao;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private ExecutorService executorService;

    public int save() {
        BeautyModel beautyModel = new BeautyModel();
        beautyModel.setId(snowFlake.nextId());
        beautyModel.setModelName("test");
        return beautyModelWriterDao.insertSelective(beautyModel);
    }

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
     * @param entranceUrls
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
        int count = beautyModelWriterDao.insertBatch(beautyModels);
        System.out.println("batch count:" + count);
    }

//    private List<String> crawlPics(String url) {
//
//    }
}

class ModalData {
    private String title;

    private String picCount;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicCount() {
        return picCount;
    }

    public void setPicCount(String picCount) {
        this.picCount = picCount;
    }
}
