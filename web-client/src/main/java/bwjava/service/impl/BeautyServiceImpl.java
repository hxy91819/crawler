package bwjava.service.impl;

import bwjava.dao.write.BeautyModelWriterDao;
import bwjava.service.BeautyService;
import com.bwjava.entity.CrawlMeta;
import com.bwjava.service.SimpleCrawlJob;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author chenjing
 * @date 2019-02-23 12:07
 */
public class BeautyServiceImpl implements BeautyService {
    @Resource
    private BeautyModelWriterDao beautyModelWriterDao;

    @Override
    public int saveSingleBeautyModel(String url) {
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

    private
}

/**
 * 模特基本信息
 */
class ModelInfo {

}
