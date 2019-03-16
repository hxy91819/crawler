package bwjava.service.service;

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
        String entranceUrl = "https://www.meituri.com/x/63/";
        urls.add(entranceUrl);
        for (int i = 1; i <= 11; i++) {
            urls.add(entranceUrl + "index_" + i + ".html");
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
    public void testSaveAllModelEntrance() {
        fetchService.fetchAndSaveBeautyList("https://www.meituri.com/x/74/");
    }

    @Test
    public void testListAll() {
        System.out.println(JSON.toJSONString(beautyLegService.listPage(1, 100, "Abby")));
    }

    @Test
    public void testFetchAndSavePic() {
        fetchService.fetchAndSaveBeautyPics("秀人网MyGirl美媛馆写真集大全");
    }

    @Test
    public void testFetchByPage() {
        fetchService.fetchAndSaveBeautyPicsByPage("秀人网MyGirl美媛馆写真集大全", 1, 8);
    }
}