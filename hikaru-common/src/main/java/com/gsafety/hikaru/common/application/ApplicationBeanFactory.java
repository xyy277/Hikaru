package com.gsafety.hikaru.common.application;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import savvy.wit.framework.core.base.interfaces.Log;
import savvy.wit.framework.core.pattern.factory.LogFactory;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ApplicationBeanUtil
 * Author : zhoujiajun
 * Date : 2019/1/3 16:55
 * Version : 1.0
 * Description : 
 ******************************/
@Component
public class ApplicationBeanFactory implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private Log log = LogFactory.getLog();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(ApplicationBeanFactory.applicationContext == null) {
            ApplicationBeanFactory.applicationContext = applicationContext;
        }
        log.log("---------------------------------------------------------------------");

        log.log("---------------------------------------------------------------------");

        log.log("------------------------application init-----------------------------");

        log.log("========ApplicationContext配置成功,在普通类可以通过调用SpringUtils.getAppContext()获取applicationContext对象,applicationContext="+ ApplicationBeanFactory.applicationContext+"========");

        log.log("---------------------------------------------------------------------");
    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name获取 Bean.
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }
}
