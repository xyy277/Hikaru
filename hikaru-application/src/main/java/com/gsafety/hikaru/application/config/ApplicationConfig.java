package com.gsafety.hikaru.application.config;

import com.gsafety.hikaru.common.application.ApplicationInitialization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import savvy.wit.framework.core.base.util.StringUtil;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 自定义应用框架初始化
 * File name : ApplicationConfig
 * Author : zhoujiajun
 * Date : 2019/1/15 10:42
 * Version : 1.0
 * Description : springBoot加载完所有bean之后执行的初始化方法
 ******************************/
@Component
@Order(2)
public class ApplicationConfig implements CommandLineRunner {

    private Logger log = LoggerFactory.getLogger(ApplicationConfig.class);

    @Value("${application.table.refactor}")
    private boolean refactor = false; // 默认false

    @Value("${application.table.pack}")
    private String pack;

//    @Autowired
//    private Environment env;

    @Override
    public void run(String... strings) throws Exception {
        log.info("ApplicationConfig start init");
//        String property = env.getProperty("application.table.refactor");
//        String pack = env.getProperty("application.table.pack");
//        refactor = StringUtil.isBlank(property) ? refactor : Boolean.parseBoolean(property);
        ApplicationInitialization.me().initialization(refactor, pack);
        log.info("ApplicationConfig init completed");
    }
}
