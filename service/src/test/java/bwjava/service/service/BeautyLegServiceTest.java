package bwjava.service.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author chenjing
 * @date 2019-03-17 13:30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BeautyLegServiceTest {
    @Resource
    BeautyLegService beautyLegService;
    @Test
    public void listGroupByOrg() {
        beautyLegService.listGroupByOrg();
    }
}