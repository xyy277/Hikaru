package com.gsafety.hikaru.application.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 错误页面配置
 * File name : ErrorPageConfig
 * Author : zhoujiajun
 * Date : 2018/12/17 18:11
 * Version : 1.0
 * Description : 
 ******************************/
@Configuration
public class ErrorPageConfig {

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
                ErrorPage error400Page = new ErrorPage(HttpStatus.BAD_REQUEST, "/public/error/400.html");
                ErrorPage error404Page = new ErrorPage(HttpStatus.BAD_REQUEST, "/public/error/404.html");
                ErrorPage error401Page = new ErrorPage(HttpStatus.BAD_REQUEST, "/public/error/401.html");
                ErrorPage error500Page = new ErrorPage(HttpStatus.BAD_REQUEST, "/public/error/500.html");
                configurableEmbeddedServletContainer.addErrorPages(error400Page,error401Page,error404Page,error500Page);
            }
        };
    }
}
