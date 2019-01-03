package com.gsafety.hikaru.application.config;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ApplicationBeanConfig
 * Author : zhoujiajun
 * Date : 2019/1/3 13:27
 * Version : 1.0
 * Description : 
 ******************************/
@Configuration
@ComponentScan
public class ApplicationBeanConfig {

    @Bean(name = "appconfig")
    public PropertiesFactoryBean propertiesFactoryBeanConfigurer() {
        PropertiesFactoryBean bean = new PropertiesFactoryBean();
        bean.setLocations(getCustomResources());
        return bean;
    }

    public Resource[] getCustomResources() {
        ClassPathResource resourceA = new ClassPathResource("file:./application.properties");
        if (!resourceA.exists()) {
            resourceA = new ClassPathResource("application.properties");
        }

        ClassPathResource resourceB = new ClassPathResource("file:./bootstrap.properties");
        if (!resourceB.exists()) {
            resourceB = new ClassPathResource("bootstrap.properties");
        }

        return new ClassPathResource[] {resourceA, resourceB};

    }
}
