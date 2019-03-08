package com.gsafety.hikaru.application;

import savvy.wit.framework.core.pattern.factory.LogFactory;
import savvy.wit.framework.core.pattern.proxy.RuntimeProxy;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Surrounding
 * Author : zhoujiajun
 * Date : 2019/3/7 14:44
 * Version : 1.0
 * Description : 
 ******************************/
public class Surrounding {

    private RuntimeProxy runtimeProxy = RuntimeProxy.get();

    protected Surrounding() {

    }

    /**
     * 环境配置 路径需要根据实际进行路径修改
     */
    protected void windowsOS() {
        // -------------------------------------------------------------------------------------------------------------
        // |启动redis redis安装路径根据实际修改                                                                          |
        runtimeProxy.execute("redis", "cmd /k  I:\\Work\\service\\redis64\\redis-server.exe");
        // |启动consul,windows开发环境下运行时自动启动本地consul，缺点看不到consul运行日志(需本地安装consul)                |
        runtimeProxy.execute("consul","cmd /k consul agent -dev -ui -node=node1");
        // |以上本地开发环境下可以这么做，测试及生产环境务必删除                                                           |
        // -------------------------------------------------------------------------------------------------------------
        // 启动jmeter，shell 根据实际安装目录修改
//        RuntimeProxy.execute("jmeter", "cmd /k G:\\server\\apache-jmeter-5.0\\bin\\jmeter.bat");
    }

    protected void linuxOS() {

    }
}
