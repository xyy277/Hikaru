package com.gsafety.hikaru.common.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import savvy.wit.framework.core.base.util.Scanner;
import savvy.wit.framework.core.pattern.factory.Daos;
import savvy.wit.framework.core.pattern.factory.DbFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ApplicationInitialization
 * Author : zhoujiajun
 * Date : 2019/1/15 10:38
 * Version : 1.0
 * Description : 
 ******************************/
public class ApplicationInitialization {

    private Logger log = LoggerFactory.getLogger(ApplicationInitialization.class);

    public static ApplicationInitialization me() {
        return LazyInit.INITIALIZATION;
    }

    private static class LazyInit {
        private static ApplicationInitialization INITIALIZATION = new ApplicationInitialization();
    }

    private ApplicationInitialization () {
    }

    public void initialization(boolean automation, boolean refactor, String... pack) {
        // 创建表
        if (automation) {
            Daos.get().createAtPackage(refactor, pack);
            log.info("Table Initialization complete");
        }

        // 系统数据

        // 启动流

        //
    }
}
