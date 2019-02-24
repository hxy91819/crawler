package bwjava.service.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author chenjing
 * @date 2019-02-24 11:59
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BeautyModelServiceTest {

    @Resource
    private BeautyModelService beautyModelService;

    @Test
    public void test(){
        System.out.println(beautyModelService.save());
    }

}