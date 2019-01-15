package com.gsafety.hikaru.common.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : bean工厂
 * File name : ApplicationBeanUtil
 * Author : zhoujiajun
 * Date : 2019/1/3 16:55
 * Version : 1.0
 * Description : 
 ******************************/
@Component
public class ApplicationBeanFactory implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private Logger log = LoggerFactory.getLogger(ApplicationBeanFactory.class);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(ApplicationBeanFactory.applicationContext == null) {
            ApplicationBeanFactory.applicationContext = applicationContext;
        }
        log.info("---------------------------------------------------------------------------");

        log.info("---------------------------------------------------------------------------");

        log.info("------------------------ApplicationBeanFactory init------------------------");

        log.info("========ApplicationContext配置成功,在普通类可以通过调用SpringUtils.getAppContext()获取applicationContext对象,applicationContext="+ ApplicationBeanFactory.applicationContext+"========");

        log.info("----------------------------------------------------------------------------");
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
