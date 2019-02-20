package savvy.wit.framework.core.pattern.proxy;

import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.pattern.factory.LogFactory;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : RuntimeProxy
 * Author : zhoujiajun
 * Date : 2019/1/29 10:59
 * Version : 1.0
 * Description : 
 ******************************/
public class RuntimeProxy {

    private static Log log = LogFactory.getLog();

    public static void execute(String shell) {
        Runtime runtime = Runtime.getRuntime();
        log.log(shell);
        try {
            Process process = runtime.exec(shell);
            log.log("shell executed");
            log.log("isAlive: " + process.isAlive());
        } catch (Exception e) {
            log.error(e);
        }
    }

    public static void main(String[] args) {
        execute("cmd /c calc");
    }

}
