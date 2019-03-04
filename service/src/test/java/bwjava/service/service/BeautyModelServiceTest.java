package bwjava.service.service;

import bwjava.service.entity.BeautyModel;
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
public class BeautyModelServiceTest {
    private static List<String> urls = new ArrayList<>();

    static {
        urls.add("https://www.meituri.com/x/57/");
        for (int i = 1; i <= 37; i++) {
            urls.add("https://www.meituri.com/x/57/index_" + i + ".html");
        }
    }

    @Resource
    private BeautyModelService beautyModelService;

    @Test
    public void test() {
        System.out.println(beautyModelService.save());
    }

    @Test
    public void testGetBasic() {
        beautyModelService.crawlModalData("https://www.meitulu.com/item/15665.html");
    }

    @Test
    public void testSaveAllModelEntrance() {
        beautyModelService.fetchAndSaveBeautyList(urls, urls.size());
    }

    @Test
    public void testListAll() {
        List<BeautyModel> beautyModels = beautyModelService.listAll(1, 2);
        System.out.println(beautyModels);
    }

    @Test
    public void testFetchAndSavePic() {
        beautyModelService.fetchAndSaveBeautyPics();
    }
}