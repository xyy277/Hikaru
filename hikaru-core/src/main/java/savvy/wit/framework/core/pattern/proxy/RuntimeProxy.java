package savvy.wit.framework.core.pattern.proxy;

import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.pattern.adapter.FileAdapter;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.io.IOException;
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

    private String encoding = "GBK";

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

    public synchronized void execute(String processKey, String command, boolean monitoring) {
        Process process = null;
        log.log(command);
        try {
//            process = processMap.get(processKey);
            if (process != null && process.isAlive()) {
                log.print("80*=").print("["+ processKey +" ] ").print("<< process already alive >>").println("80*=");
            }
            process = runtime.exec(commandAdapter(command));
//            processMap.put(processKey, process);
            while (process.isAlive()) {
                if (!monitoring) {
                    break;
                }
                FileAdapter.me().readLine(process.getInputStream(), encoding, string -> log.println(string).toString());
            }
            if (monitoring) {
                FileAdapter.me().readLine(process.getErrorStream(), encoding, string -> log.println(string).toString());
            }
            log.print("80*=").print("["+ processKey +" ] ").print("<< process isAlive: " + process.isAlive() + " >>").println("80*=");
        } catch (Exception e) {
            log.error(e);
        }
    }

    public synchronized void execute(String command, boolean monitoring) {
        Process process = null;
        log.log(command);
        try {
            process = runtime.exec(commandAdapter(command));
            while (process != null && process.isAlive()) {
                if (!monitoring) {
                    break;
                }
                FileAdapter.me().readLine(process.getInputStream(), encoding, string -> log.println(string).toString());
            }
            if (monitoring) {
                FileAdapter.me().readLine(process.getErrorStream(), encoding, string -> log.println(string).toString());
            }
            log.print("80*=").print("<< process isAlive: " + process.isAlive() + " >>").println("80*=");
        } catch (Exception e) {
            log.error(e);
        }
    }

    public synchronized void execute(String... commands) {
        for (String command : commands) {
            log.println(command);
            try {
                Process process = runtime.exec(commandAdapter(command));
                if (process != null && process.isAlive()) {
                    FileAdapter.me().readLine(process.getInputStream(), encoding, line -> log.println(line).toString());
                }
            } catch (IOException e) {
                log.error(e);
            }
        }
    }

    public RuntimeProxy encoding(String encoding) {
        this.encoding = encoding;
        return LazyInit.INITIALIZATION;
    }

    public Runtime getRuntime() {
        return runtime;
    }

    public Map<String, Process> getProcessMap() {
        return processMap;
    }

    private String commandAdapter(String command) {
        String system = System.getProperty("os.name");
        if (system.toUpperCase().indexOf("WINDOWS") != -1) {
            command = "cmd /c " + command;
        } else if (system.toUpperCase().indexOf("LINUX") != -1) {
            command = "sc -c " + command;
        }
        return command;
    }

}
