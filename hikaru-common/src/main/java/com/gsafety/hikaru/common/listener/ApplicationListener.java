package com.gsafety.hikaru.common.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import savvy.wit.framework.core.pattern.factory.ConfigFactory;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : ApplicationListener
 * File name : ApplicationListener
 * Author : zhoujiajun
 * Date : 2019/1/7 17:34
 * Version : 1.0
 * Description : 
 ******************************/
@WebListener
public class ApplicationListener implements ServletContextListener {

    private Logger log = LoggerFactory.getLogger(ApplicationListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.info("ApplicationListener init");
        // 很low的方式去配置参数
        // 设置log打印格式
        LogFactory.front("").behind("\t\t:");
        ConfigFactory dbFactory = ConfigFactory.me();
        dbFactory.setSource("./db.properties", "db.properties")                 // 设置dao数据源
                .setEnumClassList("com.gsafety.hikaru.model.enumerate")         // 设置泛型package
                .setProperty("vacancy", "true")                                 // 设置参数vacancy - 插入数据为null时，补“”
                .setProperty("intervalMark", "@$");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.info("ApplicationListener destroyed");
    }
}
