package com.gsafety.hikaru.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : LocaleConfig
 * Author : zhoujiajun
 * Date : 2019/1/3 13:03
 * Version : 1.0
 * Description : 
 ******************************/
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class LocaleConfig extends WebMvcConfigurerAdapter {

    @Value("#{appconfig[language]}")
    private String language;

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        switch (language) {
            case "Zh_CN":
                sessionLocaleResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
                break;
            case "en_US":
                sessionLocaleResolver.setDefaultLocale(Locale.US);
                break;
            default:
                sessionLocaleResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
                break;
        }
        return sessionLocaleResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        // 参数名
        interceptor.setParamName("lang");
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
