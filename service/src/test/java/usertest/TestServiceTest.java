package usertest;

import bwjava.service.ServiceApplication;
import bwjava.service.dao.read.BeautyModelPicReaderDao;
import bwjava.service.entity.BeautyModelPic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用于手动修复数据等操作
 *
 * @author chenjing
 * @date 2019-03-13 18:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceApplication.class)
public class TestServiceTest {

    @Resource
    private BeautyModelPicReaderDao beautyModelPicReaderDao;

    @Test
    public void test(){
        List<BeautyModelPic> beautyModelPics = beautyModelPicReaderDao.selectIdPicurl();

        for (BeautyModelPic beautyModelPic : beautyModelPics) {
            String format = "modelId is %s, picurl is %s, sortNo is %s";
            String msg = String.format(format, beautyModelPic.getModelId(), beautyModelPic.getPicUrl(),
                    PicUrlHandler.getNumber(beautyModelPic.getPicUrl()));
            System.out.println(msg);
        }
    }
}


class PicUrlHandler {

    public static int getNumber(String url) {
        int last = url.lastIndexOf('/');
        int endfixLength = ".jpg".length();
        String substring = url.substring(last + 1, url.length() - endfixLength);
        return Integer.valueOf(substring);
    }

    public static void main(String[] args) {
        String url = "https://ii.hywly.com/a/1/25661/25.jpg";

        System.out.println(PicUrlHandler.getNumber(url));
    }
}