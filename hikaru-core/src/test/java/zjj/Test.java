package zjj;

import savvy.wit.framework.core.base.interfaces.Log;
import savvy.wit.framework.core.pattern.decorate.Counter;
import savvy.wit.framework.core.pattern.factory.LogFactory;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Test
 * Author : zhoujiajun
 * Date : 2019/1/7 12:54
 * Version : 1.0
 * Description : 
 ******************************/
public class Test {

    private static Log log = LogFactory.getLog();
    private static char[] chars = null; // 测试


    public static void test(String s) {
        initPool();
        Counter counters = Counter.create();
        for (char t : chars) {
            Counter counter = Counter.create();
            for (char c : s.toCharArray()) {
                if (c == t) {
                    counter.counting();
                }
            }
            if (counter.getCount() == 1 ){
                log.log(counters.getIndex(1) + " 字符：" + t + " 出现（次）：" + counter.getCount());
            }
        }
    }

    private static void initPool() {
        chars = new char[26 * 2 + 10];
        int index = 0;
        for (int var = 65; var < 65 + 26; var ++) {
            if (index < 10) {
                chars[index] = ("" + index).charAt(0);
            }
            chars[index + 10] = (char) var;
            chars[index + 10 + 26] = (char) (var + 32);
            index++;
        }
    }
}
