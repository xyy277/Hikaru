package savvy.wit.framework.core.pattern.proxy;

import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.pattern.factory.DbFactory;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.util.HashMap;
import java.util.Map;

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

    private Runtime runtime;

    private Map<String, Process> processMap;

    private RuntimeProxy () {
        this.runtime = Runtime.getRuntime();
        this.processMap = new HashMap<>();
    }

    public static RuntimeProxy get() {
        return LazyInit.INITIALIZATION;
    }

    private static class LazyInit {
        private static final RuntimeProxy  INITIALIZATION = new RuntimeProxy();
    }

    public Process execute(String processKey, String command) {
        Process process = null;
//        log.log(command);
        try {
            process = processMap.get(processKey);
            if (process != null && process.isAlive()) {
                log.print("100*-").print("["+ processKey +" ] ").print("<< process already alive >>").println("100*-");
                return process;
            }
            process = runtime.exec(command);
            log.print("100*-").print("["+ processKey +" ] ").print("<< command      executed >>").println("100*-");
//            if (process.isAlive()) {
//                processMap.put(processKey, process);
//            }
            log.print("100*-").print("["+ processKey +" ] ").print("<< process isAlive: " + process.isAlive() + " >>").println("100*-");
        } catch (Exception e) {
            log.error(e);
        }
        return process;
    }


    public Runtime getRuntime() {
        return runtime;
    }

    public Map<String, Process> getProcessMap() {
        return processMap;
    }


}
