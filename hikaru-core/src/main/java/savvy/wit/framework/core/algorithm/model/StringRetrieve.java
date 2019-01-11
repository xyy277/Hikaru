package savvy.wit.framework.core.algorithm.model;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : [0-9A-Za-z]{5,}
 * File name : StringRetrieve
 * Author : zhoujiajun
 * Date : 2019/1/11 14:40
 * Version : 1.0
 * Description : 
 ******************************/
public class StringRetrieve {

    /**
     * 0 - 9 -> 48 - 57
     * A - Z -> 65 - 90
     * a - z -> 97 - 122
     */
    private static int[][] pool = null; // 0 1 2 3 4 5 6 7 8 9 A B C D E F G H I J K L M N O P Q R S T U V W X Y Z a b c d e f g h i j k l m n o p q r s t u v w x y z
    private static final int FIGURE_EXCURSION = 48; // 0-9 偏移量
    private static final int UPPER_ALPHABET_EXCURSION = 55; // A-Z 偏移量
    private static final int LOWER_ALPHABET_EXCURSION = 61; // z-z 偏移量



    public static String getBoOnlyOnceCode(String paramString) {
        pool = new int[26 * 2 + 10][2]; // 存放 0-9 A-Z a-z
        calculateString(paramString);
        return calculateIndex(calculatePool());
    }

    /**
     * template:  0 1 2 3 4 5 6 7 8 9 | A  B C D E F G H I J K L M N O P Q R S T U V W X Y  Z | a b c d e f g h i j k l m n o p q r s t u v w x y  z
     *      int: 48 ...            57| 65 ...                                             90 | 97 ...                                            122
     *    index: 0 1 2 ...          | 10                  ...                            35 | 36                     ...                         61
     *    count: 0 1 0 ...                  9  1 ... 0 11 ...           22 0 1                      0 0 3 1 0 1 ...
     * 数组pool为模板，顺序0-9A-Za-z
     * template 模板
     * int 模板中对应的值
     * index 为一次出现时的下标，下标对应paramString
     * count 为出现的次数，累加，默认0
     */
    private static void calculateString(String paramString) {
        char[] chars = paramString.toCharArray();
        for (int i = 1; i <= chars.length; i++) {
            int var = chars[i-1];
            if (var >= 48 && var <= 57) { // 0 - 9
                pool[var - FIGURE_EXCURSION][0] = pool[var - FIGURE_EXCURSION][0] == 0 ?
                        i : pool[var - FIGURE_EXCURSION][0];
                pool[var - FIGURE_EXCURSION][1] += 1;
            }
            if (var >= 65 && var <= 90) { // A - Z
                pool[var - UPPER_ALPHABET_EXCURSION][0] = pool[var - UPPER_ALPHABET_EXCURSION][0] == 0 ?
                        i : pool[var - UPPER_ALPHABET_EXCURSION][0];
                pool[var - UPPER_ALPHABET_EXCURSION][1] += 1;
            }
            if (var >= 97 && var <= 122) { // a - z
                pool[var - LOWER_ALPHABET_EXCURSION][0] = pool[var - LOWER_ALPHABET_EXCURSION][0] == 0 ?
                        i : pool[var - LOWER_ALPHABET_EXCURSION][0];
                pool[var - LOWER_ALPHABET_EXCURSION][1] += 1;
            }
        }
    }

    /**
     * template:  0 1 2 3 4 5 6 7 8 9 | A  B C D E F G H I J K L M N O P Q R S T U V W X Y  Z | a b c d e f g h i j k l m n o p q r s t u v w x y  z
     *    index: 0 1 2 ...          | 10                  ...                            35 | 36                     ...                         61
     * initCache，存储pool中只出现一次的字符的第一次出现下标
     * 求出比较下标最小及第一次出现的字符所在下标对应模板的字符
     */
    private static int calculatePool() {
        int [] intCache = new int[pool.length]; //
        for (int var = 0 ; var < pool.length ; var++) {
            if (pool[var][1] == 1) {
                intCache[var] = pool[var][0]; // 只出现一次的字符的顺序存入cache
            }
        }
        int index = -1;
        int min = -1;
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

    /**
     * 根据下标查找对应模板的值
     * @param index
     * @return
     */
    private static String calculateIndex(int index) {
        String value = "空";
        // 计算下标
        if (index >= 0 && index <= 9) {
            value = String.valueOf((char)(index + FIGURE_EXCURSION));
        }
        if (index >= 10 && index <= 35) {
            value = String.valueOf((char)(index + UPPER_ALPHABET_EXCURSION));
        }
        if (index >= 36 && index <= 61) {
            value = String.valueOf((char)(index + LOWER_ALPHABET_EXCURSION));
        }
        return value;
    }

}
