package com.gsafety.hikaru.application.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
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
@SpringBootConfiguration
public class ErrorPageConfig implements ErrorPageRegistrar {

    /**
     * spring boot 2.x
     * @param errorPageRegistry
     */
    @Override
    public void registerErrorPages(ErrorPageRegistry errorPageRegistry) {
        ErrorPage error400Page = new ErrorPage(HttpStatus.BAD_REQUEST, "/public/error/400.html");
        ErrorPage error404Page = new ErrorPage(HttpStatus.BAD_REQUEST, "/public/error/404.html");
        ErrorPage error401Page = new ErrorPage(HttpStatus.BAD_REQUEST, "/public/error/401.html");
        ErrorPage error500Page = new ErrorPage(HttpStatus.BAD_REQUEST, "/public/error/500.html");
        errorPageRegistry.addErrorPages(error400Page, error401Page, error404Page, error500Page);
    }

//    spring boot 1.x
//    @Bean
//    public EmbeddedServletContainerCustomizer containerCustomizer() {
//        return (configurableEmbeddedServletContainer) -> {
//            ErrorPage error400Page = new ErrorPage(HttpStatus.BAD_REQUEST, "/public/error/400.html");
//            ErrorPage error404Page = new ErrorPage(HttpStatus.BAD_REQUEST, "/public/error/404.html");
//            ErrorPage error401Page = new ErrorPage(HttpStatus.BAD_REQUEST, "/public/error/401.html");
//            ErrorPage error500Page = new ErrorPage(HttpStatus.BAD_REQUEST, "/public/error/500.html");
//            configurableEmbeddedServletContainer.addErrorPages(error400Page,error401Page,error404Page,error500Page);
//        };
//    }
}
