package com.gsafety.hikaru.application.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsafety.hikaru.common.global.SystemConfig;
import com.gsafety.hikaru.common.interceptor.LegalVerifyInterceptor;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.*;
import savvy.wit.framework.core.base.util.JsonUtil;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.List;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : web模块配置
 * File name : WebConfig
 * Author : zhoujiajun
 * Date : 2018/12/18 10:26
 * Version : 1.0
 * Description : 
 ******************************/
@EnableWebMvc // SpringWebMvc
@ComponentScan({"com.gsafety.hikaru", "savvy.wit.framework.core.base.service"}) // 扫描注册bean
@ServletComponentScan("com.gsafety.hikaru.common") // 扫描自定义 监听器 过滤器
@EnableAspectJAutoProxy(proxyTargetClass = true) // 启用AOP自动配置
@SpringBootConfiguration
public class WebConfig  implements WebMvcConfigurer {

    @Autowired
    private LegalVerifyInterceptor legalVerifyInterceptor; // 自定义拦截器

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("messages", "classpath:org/hibernate/validator/ValidationMessages");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");

        return messageSource;
    }

    /**
     * Validate配置
     * @return
     */
    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setProviderClass(HibernateValidator.class);
        localValidatorFactoryBean.setValidationMessageSource(messageSource());
        return localValidatorFactoryBean;
    }

    /**
     * 拦截器配置
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(legalVerifyInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/**.*");
    }


    /**
     * Swagger资源配置
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }

    /**
     * JSON格式转换
     * @param converters
     */
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
    }

    /**
     * 默认跳转swagger 同 router filter
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("", SystemConfig.SWAGGER_URI);
        registry.addRedirectViewController("/", SystemConfig.SWAGGER_URI);
    }

    /**
     * odata跨域
     * @return
     */
    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", corsConfiguration);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }

}
