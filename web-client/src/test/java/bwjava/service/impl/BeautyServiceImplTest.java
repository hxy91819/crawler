package bwjava.service.impl;

import bwjava.Application;
import bwjava.service.BeautyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author chenjing
 * @date 2019-02-23 20:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {Application.class},
        initializers = {ConfigFileApplicationContextInitializer.class})
public class BeautyServiceImplTest {
    @Autowired
    BeautyService beautyService;

    @Test
    public void testgetid() {
        for (int i = 0; i < 100; i++) {
            beautyService.testSnow();
        }
    }

}