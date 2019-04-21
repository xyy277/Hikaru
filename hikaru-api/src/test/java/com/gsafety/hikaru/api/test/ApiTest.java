package com.gsafety.hikaru.api.test;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ApiTest
 * Author : zhoujiajun
 * Date : 2019/1/4 13:03
 * Version : 1.0
 * Description : 
 ******************************/

import com.gsafety.hikaru.common.helper.ApplicationBeanFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.pattern.factory.ConfigFactory;
import savvy.wit.framework.core.pattern.factory.LogFactory;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApiTest.class})
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({"com.gsafety.hikaru", "savvy.wit.framework.core.base"})
public class ApiTest {

    private static final String PROJECT_PATH = System.getProperty("user.dir");

    private Log log = LogFactory.getLog();

    @Before
    public void init() {
        ConfigFactory.me().setSource(PROJECT_PATH.substring(0, PROJECT_PATH.lastIndexOf("\\")) + "\\hikaru-application\\src\\main\\resources\\db.properties")
                .setEnumClassList("com.gsafety.hikaru.model.enumerate")// 设置泛型package
                .setProperty("vacancy", "true")
                .setProperty("intervalMark", "@$");
    }
    @Test
    public void test() {
        log.log(ApplicationBeanFactory.getBean(TestController.class).fetch("c986b1b3be0c453ab1a35f260e34f011").getBody());
    }
}
