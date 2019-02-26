package com.gsafety.hikaru.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import savvy.wit.framework.core.pattern.proxy.RuntimeProxy;

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

    public static void main(String[] args) {

        // -------------------------------------------------------------------------------------------------------------
        // |1、开发环境时：动态设置数据库密码，一次执行加密后添加到db.properties中,永久有效，重新编译后失效                  |
        // |如不需可进行注释，那么在启动前请前往EncryptionScript执行main函数，将生成的数据库密码添加到db.properties中，并重新编译
        // |2、上线部署时，如若在db.properties中添加有password会按正常方式执行，如若不存在，会在启动之后在控制台要求输入明文密码，这种方式会影响部署美感
        // |建议在上线部署时，手动给配置文件添加加密后的password，加密方式执行EncryptionScript.main()                      |
        EncryptionScript.encryption();
        // -------------------------------------------------------------------------------------------------------------

        // -------------------------------------------------------------------------------------------------------------
        // |启动redis redis安装路径根据实际修改                                                                          |
        RuntimeProxy.execute("cmd /k  I:\\Work\\service\\redis64\\redis-server.exe");
        // |启动consul,windows开发环境下运行时自动启动本地consul，缺点看不到consul运行日志(需本地安装consul)                |
        RuntimeProxy.execute("cmd /k consul agent -dev -ui -node=node1");
        // |以上本地开发环境下可以这么做，测试及生产环境务必删除                                                           |
        // -------------------------------------------------------------------------------------------------------------
        // 启动jmeter，shell 根据实际安装目录修改
//        RuntimeProxy.execute("cmd /k G:\\server\\apache-jmeter-5.0\\bin\\jmeter.bat");

        // 启动服务
        SpringApplication.run(Application.class, args);

    }

}
