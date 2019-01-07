package zjj;

import org.junit.Before;
import org.junit.Test;
import savvy.wit.framework.core.base.interfaces.Log;
import savvy.wit.framework.core.base.util.DateUtil;
import savvy.wit.framework.core.base.util.StringUtil;
import savvy.wit.framework.core.pattern.decorate.Counter;
import savvy.wit.framework.core.pattern.factory.LogFactory;

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
     * A - Z -> 65 - 90
     * a - z -> 97 - 122
     */
    private static final Log log = LogFactory.getLog();
    private static int[][] pool = null; // 0 1 2 3 4 5 6 7 8 9 A B C D E F G H I J K L M N O P Q R S T U V W X Y Z a b c d e f g h i j k l m n o p q r s t u v w x y z
//    private static char[] chars = null; // 测试
    private static String paramString = null; // 符合条件的字符串
    private static final int FIGURE_EXCURSION = 48; // 0-9 偏移量
    private static final int UPPER_ALPHABET_EXCURSION = 55; // A-Z 偏移量
    private static final int LOWER_ALPHABET_EXCURSION = 61; // z-z 偏移量

    /**
     * 为测试提供前提条件
     */
    @Before
    public void initEnvironment() {
        initParamString();
    }

    @Test
    public void testAlgorithm() {
        log.log("第一个出现次数唯一的字符：" + getBoOnlyOnceCode());
    }

    /**
     * 初始化两个基本数据
     * 生成一个满足条件的字符串
     * 和一个需要协助计算的数组空间
     */
    private static void initParamString() {
        int length = DateUtil.random(~(1<<31) / 1000) / 1000 / 10; // 字符串长度随机
        paramString = StringUtil.createCode( length <= 5 ? 5 : length); // 生成a-zA-Z0-9的字符串
        log.log("随机字符串：" + paramString);
        log.log("随机字符串长度：" + length);
        pool = new int[26 * 2 + 10][2];
    }

    private static String getBoOnlyOnceCode() {
        calculateString();
        return calculateIndex(calculatePool());
    }
    private static void calculateString() {
        char[] chars = paramString.toCharArray();
        for (int i = 1; i <= chars.length; i++) {
            int var = chars[i-1];
            if (var > 48 && var < 57) { // 0 - 9
                pool[var - FIGURE_EXCURSION][0] = i;
                pool[var - FIGURE_EXCURSION][1] += 1;
            }
            if (var > 65 && var < 90) { // A - Z
                pool[var - UPPER_ALPHABET_EXCURSION][0] = i;
                pool[var - UPPER_ALPHABET_EXCURSION][1] += 1;
            }
            if (var > 97 && var < 122) { // a - z
                pool[var - LOWER_ALPHABET_EXCURSION][0] = i;
                pool[var - LOWER_ALPHABET_EXCURSION][1] += 1;
            }
        }
    }

    private static int calculatePool() {
        int [] intCache = new int[pool.length]; //
        for (int var = 0 ; var < pool.length ; var++) {
            if (pool[var][1] == 1) {
                intCache[var] = pool[var][0]; // 只出现一次的字符的顺序存入cache
            }
        }
        int index = 0;
        int min = 0;
        boolean status = true;
        for (int var = 0; var < intCache.length; var++) { // cache中最小值对应的下标
            if (intCache[var] == 0) {
                continue;
            }else if (status) {
                min = intCache[var];
                index = var;
                status = false;
                continue;
            }
            if (min > intCache[var]) {
                index = var;
                min = intCache[var];
            }
        }
        return index;
    }
    private static String calculateIndex(int index) {
        String value = "空";
        // 计算下标
        if (index > 0 && index < 9) {
            value = String.valueOf((char)(index + FIGURE_EXCURSION));
        }
        if (index > 10 && index < 35) {
            value = String.valueOf((char)(index + UPPER_ALPHABET_EXCURSION));
        }
        if (index > 36 && index < 61) {
            value = String.valueOf((char)(index + LOWER_ALPHABET_EXCURSION));
        }
        return value;
    }

//    public static void main(String[] args) {
//        initPool();
//        String s = "5jA8tyVDgs1Vkn";
//        for (char t : chars) {
//            Counter counter = Counter.create();
//            for (char c : s.toCharArray()) {
//                if (c == t) {
//                    counter.counting();
//                }
//            }
//            if (counter.getCount() == 1 ){
//                log.log(t + " " + counter.getCount());
//            }
//        }
//    }
//    private static void initPool() {
//        chars = new char[26 * 2 + 10];
//        int index = 0;
//        for (int var = 65; var < 65 + 26; var ++) {
//            if (index < 10) {
//                chars[index] = ("" + index).charAt(0);
//            }
//            chars[index + 10] = (char) var;
//            chars[index + 10 + 26] = (char) (var + 32);
//            index++;
//        }
//    }
}
