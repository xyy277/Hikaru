package com.gsafety.hikaru.application.hikaru;

import savvy.wit.framework.core.pattern.factory.LogFactory;
import savvy.wit.framework.core.pattern.proxy.RuntimeProxy;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Surrounding
 * Author : zhoujiajun
 * Date : 2019/3/7 14:44
 * Version : 1.0
 * Description : 环境运行前提配置路径
 ******************************/
public class Surrounding {

    private boolean running = false;

    private String system;

    private static boolean monitoring = false;

    private RuntimeProxy runtimeProxy = RuntimeProxy.get();

    // redis安装路径 指定exe
    private static final String redisPath = "I:\\Work\\service\\redis64\\redis-server.exe";

    // 配置consul环境变量，不会请百度
    private static final String consulRun = "consul agent -dev -ui -node=node1";

    public static Surrounding deploy() {
        return LazyInit.INITIALIZATION;
    }

    private static class LazyInit {
        private static Surrounding INITIALIZATION = new Surrounding();
    }

    public boolean isRunning() {
        if (!this.running) {
            LogFactory.print(" Surrounding deploy is not running, please check Surrounding status");
        }
        return this.running;
    }

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
        this.running = true;
    }

    /**
     * 适用于本地开发环境。
     * 环境配置 路径需要根据实际进行路径修改
     */
    protected void windowsOS() {
        // -------------------------------------------------------------------------------------------------------------
        // |启动redis redis安装路径根据实际修改                                                                          |
        runtimeProxy.execute("redis", redisPath, false);
        // |启动consul,windows开发环境下运行时自动启动本地consul，缺点看不到consul运行日志(需本地安装consul)                |
        runtimeProxy.execute("consul",consulRun, monitoring);
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
        runtimeProxy.execute("consul","./consul agent -dev -ui -node=consul-dev -client=0.0.0.0", monitoring);
    }

    public static void main(String[] args) {
        Surrounding run = new Surrounding();
        monitoring = true;
        run.OS();
    }
}
