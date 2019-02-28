package savvy.wit.framework.core.pattern.proxy;

import java.util.Map;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ProcessProxy
 * Author : zhoujiajun
 * Date : 2019/2/28 10:42
 * Version : 1.0
 * Description : 
 ******************************/
public class ProcessProxy {


    private Map<String, Process> processMap;

    private ProcessProxy () {

    }

    public static ProcessProxy get() {
        return LazyInit.INITIALIZATION;
    }

    private static class LazyInit {
        private static final ProcessProxy  INITIALIZATION = new ProcessProxy();
    }

    public Map<String, Process> getProcessMap() {
        return processMap;
    }

    public ProcessProxy setProcessMap(Map<String, Process> processMap) {
        this.processMap = processMap;
        return get();
    }

}
