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

    private String system;

    private RuntimeProxy runtimeProxy = RuntimeProxy.get();

    protected Surrounding() {
        this.system = System.getProperty("os.name");
    }

    /**
     * 根据系统运行环境选择不同环境配置
     */
    protected void OS() {
        LogFactory.open(200).printL("Current OS: " + system).close();
        if (this.system.toUpperCase().indexOf("WINDOWS") != -1) {
            windowsOS();
        } else if (this.system.toUpperCase().indexOf("LINUX") != -1) {
            linuxOS();
        }
    }

    /**
     * 适用于本地开发环境。
     * 环境配置 路径需要根据实际进行路径修改
     */
    protected void windowsOS() {
        // -------------------------------------------------------------------------------------------------------------
        // |启动redis redis安装路径根据实际修改                                                                          |
        runtimeProxy.execute("redis", "I:\\Work\\service\\redis64\\redis-server.exe", false);
        // |启动consul,windows开发环境下运行时自动启动本地consul，缺点看不到consul运行日志(需本地安装consul)                |
        runtimeProxy.execute("consul","consul agent -dev -ui -node=node1", true);
        // |以上本地开发环境下可以这么做，测试及生产环境务必删除                                                           |
        // -------------------------------------------------------------------------------------------------------------
        // 启动jmeter，shell 根据实际安装目录修改
//        RuntimeProxy.execute("jmeter", "cmd /k G:\\server\\apache-jmeter-5.0\\bin\\jmeter.bat");
    }

    /**
     * linux 生产环境不建议用这种方式启动注册中心登配置
     */
    protected void linuxOS() {
        // 略
    }

    public static void main(String[] args) {
        Surrounding run = new Surrounding();
        run.OS();
    }
}
