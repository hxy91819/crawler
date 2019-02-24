package bwjava.service.impl;

import bwjava.dao.write.BeautyModelWriterDao;
import bwjava.entity.BeautyModel;
import bwjava.service.BeautyService;
import bwjava.util.SnowFlake;
import com.bwjava.entity.CrawlMeta;
import com.bwjava.service.SimpleCrawlJob;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author chenjing
 * @date 2019-02-23 12:07
 */
@Service
@Log4j2
public class BeautyServiceImpl implements BeautyService {
    @Resource
    private BeautyModelWriterDao beautyModelWriterDao;

    @Autowired
    private SnowFlake snowFlake;

    @Override
    public int saveSingleBeautyModel(String url) {
        String title = this.getTitle(url);
        BeautyModel beautyModel = new BeautyModel();
        beautyModel.setTitle(title);
        beautyModelWriterDao.insertSelective(beautyModel);

        Set<List<String>> selectRule = new HashSet<>();
        selectRule.add(Collections.singletonList("img[class=content_img]"));
        selectRule.add(Collections.singletonList("h1[text]"));

        Set<List<String>> selectHrefs = new HashSet<>();
        selectHrefs.add(Arrays.asList("div[id=pages]", "a[href]", "a[class!=a1]"));

        CrawlMeta crawlMeta = new CrawlMeta(url, selectRule);
        crawlMeta.setSelectorHrefs(selectHrefs);

        SimpleCrawlJob job = new SimpleCrawlJob();
        job.setCrawlMeta(crawlMeta);
        job.setDepth(1);
        return 0;
    }

    /**
     * 获取标题，需要是第一页
     *
     * @param url
     * @return
     */
    public String getTitle(String url) {
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
        if (!strings.isEmpty()) {
            return strings.get(0);
        }
        return "";
    }

    @Override
    public int testSnow() {
        log.info("next id is :{}", snowFlake.nextId());
        return 0;
    }
}
