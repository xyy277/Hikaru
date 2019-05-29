package com.gsafety.hikaru.application.hikaru;

import com.gsafety.hikaru.application.Application;
import org.springframework.boot.SpringApplication;
import savvy.wit.framework.core.base.util.DateUtil;
import savvy.wit.framework.core.pattern.decorate.Counter;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : HikaruApplication
 * Author : zhoujiajun
 * Date : 2019/5/29 15:31
 * Version : 1.0
 * Description : 
 ******************************/
public class HikaruApplication {

    private static Surrounding surrounding = Surrounding.deploy();

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void run(String[] args) {
        main(args);
    }

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        Counter counter = Counter.create();

        surrounding.OS();
        if (!surrounding.isRunning()) {
            // 你需要手动执行Surrounding的main方法 开启相关环境，如果你对系统依赖的环境熟并且已经确保配置正确可以注释此行
            return;
        }

        // -------------------------------------------------------------------------------------------------------------
        // |1、开发环境时：动态设置数据库密码，一次执行加密后添加到db.properties中,永久有效，重新编译后失效             |
        // |如不需可进行注释，那么在启动前请前往EncryptionScript执行main函数，将生成的数据库密码添加到db.properties中，并重新编译
        // |2、上线部署时，如若在db.properties中添加有password会按正常方式执行，如若不存在，会在启动之后在控制台要求输入明文密码，这种方式会影响部署美感
        // |建议在上线部署时，手动给配置文件添加加密后的password，加密方式执行EncryptionScript.main()                   |
        EncryptionScript.encryption("db.properties");
        // -------------------------------------------------------------------------------------------------------------

        SpringApplication.run(Application.class, args);
        LogFactory.print("Application Startup takes: " + DateUtil.formatDateTime(counter.close()));
    }
}
