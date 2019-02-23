package com.xiaour.spring;

import com.alibaba.fastjson.JSON;
import bwjava.Application;
import bwjava.entity.UserInfo;
import bwjava.service.UserInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * test elasticsearch
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {Application.class},
        initializers = {ConfigFileApplicationContextInitializer.class})
public class UserInfoServiceTest {

    @Autowired
    private UserInfoService userInfoService;

    @Test
    public void test() {
        UserInfo userInfo = userInfoService.selectByPrimaryKey(1);
        System.out.println(JSON.toJSONString(userInfo));
    }
}
