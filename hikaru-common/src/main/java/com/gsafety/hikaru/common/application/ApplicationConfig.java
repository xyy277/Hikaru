package com.gsafety.hikaru.common.application;

import com.gsafety.hikaru.common.application.init.ApplicationInitialization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import savvy.wit.framework.core.base.util.StringUtil;
import savvy.wit.framework.core.pattern.factory.Daos;
import savvy.wit.framework.core.pattern.factory.LogFactory;
import savvy.wit.framework.core.pattern.proxy.RuntimeProxy;

import java.util.Map;

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

    @Value("${hikaru.datasource.init}")
    private boolean init;

    @Value("${server.address}")
    private String address;
    @Value("${server.port}")
    private String port;
    @Value("${server.servlet.context-path}")
    private String contextPath;

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
        // 初始化数据源dao
        if (init) {
            String automation = env.getProperty("hikaru.table.automation");
            String refactor = env.getProperty("hikaru.table.refactor");
            String pack = env.getProperty("hikaru.table.pack");
            if(StringUtil.isNotBlank(automation))
                this.automation = Boolean.parseBoolean(automation);
            if(StringUtil.isNotBlank(refactor))
                this.refactor = Boolean.parseBoolean(refactor);
            if(StringUtil.isNotBlank(pack))
                this.pack = pack;
            // 库我也帮你建吧
            Daos.get().execute("CREATE DATABASE IF NOT EXISTS "
                    + env.getProperty("hikaru.database.name")
                    + " DEFAULT CHARSET utf8 COLLATE utf8_general_ci");
            ApplicationInitialization.me().initialization(this.automation, this.refactor, this.pack);
        }
        log.info("ApplicationConfig init completed");
        String url = address + ":" + port + contextPath;
        LogFactory.open(200)
                .printL("Hello Hikaru !", "Welcome")
                .printL("visit address: http://" + url)
                .close();

        // 初始化完成打开浏览器
        Browser.open(true, url);
    }





    private static class Browser {

        /**
         * 打开浏览器
         * @param open 是否打开
         * @param url 打开之后跳转网页地址
         */
        private static void open(Boolean open, String url) {
            if (!open) {
                return;
            }
            Map<String, String> map = System.getenv();
            String userName = map.get("USERNAME");// 获取用户名
            String computerName = map.get("COMPUTERNAME");// 获取计算机名
            String userDomain = map.get("USERDOMAIN");// 获取计算机域名
            // command 谷歌浏览器路径
            RuntimeProxy.get().execute(
                    "chrome",
                    "C:\\Users\\" + userName + "\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe " + url,
                    false);
            RuntimeProxy.get().execute(
                    "chrome",
                    "C:\\Users\\" + "Administrator" + "\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe " + url,
                    false);
        }
    }


}
