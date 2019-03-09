package bwjava.service.service;

import bwjava.service.entity.BeautyModel;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenjing
 * @date 2019-02-24 11:59
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FetchServiceTest {
    private static List<String> urls = new ArrayList<>();

    static {
        urls.add("https://www.meituri.com/x/57/");
        for (int i = 1; i <= 37; i++) {
            urls.add("https://www.meituri.com/x/57/index_" + i + ".html");
        }
    }

    @Resource
    private FetchService fetchService;

    @Resource
    private BeautyLegService beautyLegService;

    @Test
    public void test() {
    }

    @Test
    public void testGetBasic() {
        fetchService.crawlModalData("https://www.meitulu.com/item/15665.html");
    }

    @Test
    public void testSaveAllModelEntrance() {
        fetchService.fetchAndSaveBeautyList(urls, urls.size());
    }

    @Test
    public void testListAll() {
        List<BeautyModel> beautyModels = beautyLegService.listPage(1, 100);
        System.out.println(JSON.toJSONString(beautyModels));
    }

    @Test
    public void testFetchAndSavePic() {
        fetchService.fetchAndSaveBeautyPics();
    }
}