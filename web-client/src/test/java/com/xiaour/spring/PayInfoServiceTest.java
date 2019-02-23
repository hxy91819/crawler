package com.xiaour.spring;

import bwjava.Application;
import bwjava.entity.PayInfo;
import bwjava.service.PayInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * test elasticsearch
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {Application.class},
        initializers = {ConfigFileApplicationContextInitializer.class})
public class PayInfoServiceTest {

    @Autowired
    private PayInfoService payInfoService;

    @Test
    public void insert() {
        PayInfo payInfo = new PayInfo();
        payInfo.setCorpId("wx123");
        payInfo.setPlatformType("wx");
        payInfo.setMemo("test");
        payInfo.setPayer("hxy");
        payInfo.setPayAmount(BigDecimal.valueOf(12.35));
        int i = payInfoService.insertSelective(payInfo);
        System.out.println(i);
    }
}
