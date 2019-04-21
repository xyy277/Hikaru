package com.gsafety.hikaru.api.test;

import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.pattern.factory.LogFactory;
import savvy.wit.framework.core.pattern.proxy.RuntimeProxy;

import java.io.FileInputStream;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : RuntimeTest
 * Author : zhoujiajun
 * Date : 2019/4/1 15:38
 * Version : 1.0
 * Description : 
 ******************************/
public class RuntimeTest {

    private static final Log log = LogFactory.getLog();
    private static class Path {

    }
    public static void main(String[] args) {
        log.log(        ((FileInputStream)new RuntimeTest().getClass().getClassLoader().getResourceAsStream("/db.properties")));
//        RuntimeProxy.get().execute("java -jar C:\\Users\\Administrator\\Desktop\\hikaru\\server\\hikaru-server.jar", true);
    }
}
