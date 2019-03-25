package com.gsafety.hikaru.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import savvy.wit.framework.core.base.util.DateUtil;
import savvy.wit.framework.core.pattern.decorate.Counter;
import savvy.wit.framework.core.pattern.factory.LogFactory;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : boot启动类
 * File name : Application
 * Author : zhoujiajun
 * Date : 2018/12/17 10:07
 * Version : 1.0
 * Description : 
 ******************************/
@SpringBootApplication
@EnableDiscoveryClient // 启用consul注册
// Enable默认都只默认扫描当前及以下的包，需要指定
@EnableFeignClients(basePackages = "com.gsafety.hikaru.feign")
public class Application {

    private Surrounding in = new Surrounding();

    public static void main(String[] args) {
        Counter counter = Counter.create();

        Application running = new Application();
        // -------------------------------------------------------------------------------------------------------------
        // |1、开发环境时：动态设置数据库密码，一次执行加密后添加到db.properties中,永久有效，重新编译后失效                  |
        // |如不需可进行注释，那么在启动前请前往EncryptionScript执行main函数，将生成的数据库密码添加到db.properties中，并重新编译
        // |2、上线部署时，如若在db.properties中添加有password会按正常方式执行，如若不存在，会在启动之后在控制台要求输入明文密码，这种方式会影响部署美感
        // |建议在上线部署时，手动给配置文件添加加密后的password，加密方式执行EncryptionScript.main()                      |
        EncryptionScript.encryption();
        // -------------------------------------------------------------------------------------------------------------

        // 本地开发环境一键启动，适合入门开发者，减少繁琐步骤
        running.in.OS();

        // 启动服务
        SpringApplication.run(Application.class, args);

        LogFactory.print("Application Startup takes: " + DateUtil.formatDateTime(counter.close()));

    }

}
