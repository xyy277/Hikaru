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

    private int extent = 200;

    private LogFactory() {

    }

    public static LogFactory init() {
        return LazyInit.INITIALIZATION;
    }

    private static class LazyInit {
        private static LogFactory INITIALIZATION = new LogFactory();
    }

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

    public static void print(String string) {
        int length = string.length();
        log.println().print("100*--").println()
                .print("|").print("198* ").println("|")
                .print("|").print((198-length)/2 +"* ").print(string).print((198-length)/2 +"* ").print(length%2 != 0 ? " " : "").println("|")
                .print("|").print("198* ").println("|")
                .print("100*--").println();
    }

    /**
     * 开启一个格式打印
     * @param extents 格式串长度
     * @return
     */
    public static LogFactory open(int... extents) {
        if (extents != null && extents.length > 0)
            init().extent = 0;
        for (int extent : extents) {
            init().extent += extent;
        }
        log.println().print(init().extent / 2 + "*--").println()
                .print("|").print((init().extent-2) + "* ").println("|");
        return init();
    }

    /**
     * 格式化输出
     * @param strings 数组，循环每行打印
     * @return
     */
    public LogFactory printL(String... strings) {
        for (String string : strings) {
            int length = string.length();
            log.print("|").print((init().extent-2-length)/2 +"* ").print(string).print((init().extent-2-length)/2 +"* ").print(length%2 != 0 ? " " : "").println("|");
        }
        return init();
    }

    /**
     * 关闭格式化打印输出
     */
    public void close() {
        log.print("|").print((init().extent-2) + "* ").println("|")
                .print(init().extent / 2 + "*--").println();
    }

    public static void print(int line, String... strings) {
        int var = line / 2;
        log.println().print("100*--").println();
        for (int i = 0; i < var; i++) {
            log.print("|").print("198* ").println("|");
        }
        for (String string : strings) {
            int length = string.length();
            log.print("|").print((198-length)/2 +"* ").print(string).print((198-length)/2 +"* ").print(length%2 != 0 ? " " : "").println("|");
        }
        for (int i = 0; i < (line%2 != 0 ? var+1 : var); i++) {
            log.print("|").print("198* ").println("|");
        }
        log.print("100*--").println();
    }
}
