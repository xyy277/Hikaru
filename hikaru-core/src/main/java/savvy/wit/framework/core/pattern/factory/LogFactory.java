package savvy.wit.framework.core.pattern.factory;

import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.base.service.log.impl.LogImpl;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Test
 * Author : zhoujiajun
 * Date : 2018/6/27 14:41
 * Version : 1.0
 * Description : 
 ******************************/
public class LogFactory {

    private static LogFactory factory = new LogFactory();

    // private
    private static Log log = LogImpl.me();

    public static Log getLog() {
        return log;
    }

    public static LogFactory front(String front) {
        log.front(front);
        return factory;
    }

    public static LogFactory behind(String behind) {
        log.behind(behind);
        return factory;
    }
}
