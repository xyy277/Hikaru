package com.gsafety.hikaru.common.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
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
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.info("ApplicationListener destroyed");
    }
}