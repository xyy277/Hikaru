package com.gsafety.hikaru.application.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : SwaggerConfig
 * Author : zhoujiajun
 * Date : 2019/1/10 9:52
 * Version : 1.0
 * Description : 
 ******************************/
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                // 带Api注解
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                // 包路径
//                .apis(RequestHandlerSelectors.basePackage("com.gsafety.hikaru.api"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 页面标题
                .title("接口说明文档")
                // 创建人
                .contact(new Contact("zhoujiajun", "https://www.jjzhou.cn", "zhoujiajun@gsafety.com"))
                // 版本
                .version("1.0")
                // 描述
                .description("关注Hikaru, https://github.com/xyy277/Hikaru")
                .termsOfServiceUrl("https://github.com/xyy277/Hikaru")
                .build();
    }
}
