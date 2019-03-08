package com.gsafety.hikaru.common.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import savvy.wit.framework.core.base.util.StringUtil;
import savvy.wit.framework.core.pattern.factory.DbFactory;
import savvy.wit.framework.core.pattern.factory.LogFactory;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 自定义应用框架初始化
 * File name : ApplicationConfig
 * Author : zhoujiajun
 * Date : 2019/1/15 10:42
 * Version : 1.0
 * Description : springBoot加载完所有bean之后执行的初始化方法
 ******************************/
@Component
@Order(2)
// 自定义读取配置文件，将bootstrap加入到environment中
@PropertySource(value = {"classpath:bootstrap.properties", "file:./bootstrap.properties"},
        ignoreResourceNotFound = true) // 忽略找不到文件的报错
public class ApplicationConfig implements CommandLineRunner {

    private Logger log = LoggerFactory.getLogger(ApplicationConfig.class);


    /**
     * 推荐第一种方式 配合environment获取，可以判断为空时不获取
     */
    @Autowired
    private Environment env;

    private boolean automation;
    private boolean refactor;
    private String pack;

    /**
     * 第二种方式获取，boolean若没有配置会报错
     * 使用第二种配置，需要确保配置文件中有如下配置
     */
//    @Value("${hikaru.table.automation}")
//    private boolean automation;
//
//    @Value("${hikaru.table.refactor}")
//    private boolean refactor;
//
//    @Value("${hikaru.table.pack}")
//    private String pack;

    /**
     * 第三种方式通过appconfig获取，命名只可一个单位
     * ${} 与 #{} 区别
     * #{ obj.property? : default_value } 注入的是外部参数对应的property
     * #{ obj.property? : default_value } SpEL表达式对应的内容
     * appconfig 对象内参数值是只能是xxx,如果是*.*格式会报错
     */
//    @Value("#{appconfig[automation]}")
//    private boolean automation;
//
//    @Value("#{appconfig[refactor]}")
//    private boolean refactor;
//
//    @Value("#{appconfig[pack]}")
//    private String pack;

    @Override
    public void run(String... strings) throws Exception {
        log.info("ApplicationConfig start init");
        String automation = env.getProperty("hikaru.table.automation");
        String refactor = env.getProperty("hikaru.table.refactor");
        String pack = env.getProperty("hikaru.table.pack");
        if(StringUtil.isNotBlank(automation))
            this.automation = Boolean.parseBoolean(automation);
        if(StringUtil.isNotBlank(refactor))
            this.refactor = Boolean.parseBoolean(refactor);
        if(StringUtil.isNotBlank(pack))
            this.pack = pack;
        ApplicationInitialization.me().initialization(this.automation, this.refactor, this.pack);
        log.info("ApplicationConfig init completed");
        LogFactory.print("Hello Hikaru !");
    }
}
