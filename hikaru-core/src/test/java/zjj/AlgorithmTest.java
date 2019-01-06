package zjj;

import com.alibaba.druid.sql.visitor.functions.Char;
import org.junit.Before;
import org.junit.Test;
import savvy.wit.framework.core.base.interfaces.Log;
import savvy.wit.framework.core.base.util.DateUtil;
import savvy.wit.framework.core.base.util.StringUtil;
import savvy.wit.framework.core.pattern.decorate.Counter;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.util.ArrayList;
import java.util.Arrays;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : AlgorithmTest
 * Author : zhoujiajun
 * Date : 2019/1/6 17:57
 * Version : 1.0
 * Description : 
 ******************************/
public class AlgorithmTest {

    /**
     * 0 - 9 -> 48 - 57
     * a - z -> 97 - 122
     * A - Z -> 65 - 90
     */
    private static int[] pool = null; // 0 1 2 3 4 5 6 7 8 9 a b c d e f g h i j k l m n o p q r s t u v w x y z A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
    private static String paramString = null;
    private static final Log log = LogFactory.getLog();
    private static final int FIGURE_INTERVAL = 48;
    private static final int UPPER_ALPHABET_INTERVAL = 55;
    private static final int LOWER_ALPHABET_INTERVAL = 61;
    /**
     * 为测试提供前提条件
     */
    @Before
    public void initEnvironment() {
        initParamString();
//        initPool();
    }

    /**
     * 初始化两个基本数据
     * 生成一个满足条件的字符串
     * 和一个需要协助计算的数组空间
     */
    private static void initParamString() {
//        log.log((1<<-1)-1);
//        log.log(~(1<<31));
        int length = DateUtil.random(~(1<<31) / 1000) / 1000 / 10; // 字符串长度随机
        paramString = StringUtil.createCode( length <= 5 ? 5 : length); // 生成a-zA-Z0-9的字符串
        log.log("随机字符串：" + paramString);
        log.log("随机字符串长度：" + length);
        pool = new int[26 * 2 + 10];
    }

    private static void initPool() {
        int index = 0;
        for (int var = 65; var < 65 + 26; var ++) {
            if (index < 10) {
                pool[index] = ("" + index).charAt(0);
            }
            pool[index + 10] = (char) var;
            pool[index + 10 + 26] = (char) (var + 32);
            index++;
        }
        for (int c : pool) {
            System.out.println(c);
        }
    }

//    @Test
    public void testAlgorithm() {
        log.log("第一个出现次数唯一的字符：" + getBoOnlyOnceCode());
//        for (int c : pool) {
//            System.out.println(c);
//        }
    }

    private static String getBoOnlyOnceCode() {
        calculateString();
        return calculatePool();
    }
    private static void calculateString() {
        for (char c : paramString.toCharArray()) {
            int var = c;
            if (var > 48 && var < 57) { // 0 - 9
                pool[var - FIGURE_INTERVAL] += 1;
            }
            if (var > 65 && var < 90) { // A - Z
                pool[var - UPPER_ALPHABET_INTERVAL] += 1;
            }
            if (var > 97 && var < 122) { // a - z
                pool[var - LOWER_ALPHABET_INTERVAL] += 1;
            }
        }
    }

    private static String calculatePool() {
        String value = "";
        int index = -1;
        for (int var = 0 ; var < pool.length ; var++) {
            if (pool[var] == 1) {
                index = var;
                break;
            }
        }
        log.log(index);
        if (index > 0 && index < 9) {
           value = String.valueOf((char)(index + FIGURE_INTERVAL));
        }
        if (index > 10 && index < 35) {
            value = String.valueOf((char)index + UPPER_ALPHABET_INTERVAL);
        }
        if (index > 36 && index < 61) {
            value = String.valueOf((char)index + LOWER_ALPHABET_INTERVAL);
        }
        return value;
    }

    public static void main(String[] args) {
        Counter counter = Counter.create();
        String s = "9IrDVW9YDz9OCyLDLuwQ9f5GWY0WZ7Oghh473OXHOmPLAk0zFXnrsBN2yrEMDSsnSRD2ptJOEDmBDNXMjzKiL96nNlm";
        for (char c : s.toCharArray()) {
            if (c == '3') {
                counter.counting();
            }
        }
        log.log(counter.getCount());
    }
}
