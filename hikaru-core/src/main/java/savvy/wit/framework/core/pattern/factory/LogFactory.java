package savvy.wit.framework.core.pattern.factory;

import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.base.service.log.impl.LogExcutor;

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
    private static Log log = LogExcutor.me();

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

    public static void print(String... strings) {
        String string = "";
        if (strings != null && strings.length > 0) {
            for (String s : strings) {
                string += s;
            }
        }
        int length = string.length();
        log.println().print("100*--").println()
                .print("|").print("198* ").println("|")
                .print("|").print((198-length)/2 +"* ").print(string).print((198-length)/2 +"* ").print(length%2 != 0 ? " " : "").println("|")
                .print("|").print("198* ").println("|")
                .print("100*--").println();
    }
}
