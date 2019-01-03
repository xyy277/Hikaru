package com.gsafety.hikaru.application.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsafety.hikaru.common.interceptor.LegalVerifyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import savvy.wit.framework.core.base.util.JsonUtil;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.List;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : WebConfig
 * Author : zhoujiajun
 * Date : 2018/12/18 10:26
 * Version : 1.0
 * Description : 
 ******************************/
@Configuration
@EnableWebMvc
@ComponentScan({"com.gsafety.hikaru","savvy.wit.framework.core.base.interfaces.dao"})
@ServletComponentScan("com.gsafety.hikaru.common.filter") // 扫描自定义过滤器
@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootConfiguration
public class WebConfig  extends WebMvcConfigurerAdapter{

    @Autowired
    private LegalVerifyInterceptor legalVerifyInterceptor;

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(legalVerifyInterceptor).addPathPatterns("/**");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter=new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper=jackson2HttpMessageConverter.getObjectMapper();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(sdf);
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //允许key没有使用双引号的json
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        //注册枚举反序列化时候忽略大小写
        mapper.registerModule(JsonUtil.getEnumSimpleModule());
        converters.add(jackson2HttpMessageConverter);
        converters.add(stringHttpMessageConverter);
        super.configureMessageConverters(converters);
    }
}
