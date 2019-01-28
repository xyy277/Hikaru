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
    private static int[][] pool = null;                     // 0 1 2 3 4 5 6 7 8 9 A B C D E F G H I J K L M N O P Q R S T U V W X Y Z a b c d e f g h i j k l m n o p q r s t u v w x y z
    private static final int INDEX_VALUE = 0;               // 字符
    private static final int INDEX_COUNT = 1;               // 次数
    private static final int DISORDER_INDEX = 0;            // 乱序下标
    private static final int ZERO = 0;                      // 0下标对应的字符0
    private static final int NINE = 9;                      // 9下标对应的字符9
    private static final int UPPER_A = 10;                  // 10下标对应的字符A
    private static final int UPPER_Z = 35;                  // 35下标对应的字符Z
    private static final int LOWER_A = 36;                  // 36下标对应的字符a
    private static final int LOWER_Z = 61;                  // 61下标对应的字符z
    private static final int FIGURE_EXCURSION = '0' - ZERO;                         // 0-9 偏移量
    private static final int UPPER_ALPHABET_EXCURSION = 'A' - UPPER_A;              // A-Z 偏移量
    private static final int LOWER_ALPHABET_EXCURSION = 'a' - LOWER_A;              // z-z 偏移量



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
            if (var >= '0' && var <= '9') { // 0 - 9
                pool[var - FIGURE_EXCURSION][INDEX_VALUE] = pool[var - FIGURE_EXCURSION][INDEX_VALUE] == 0 ?
                        i : pool[var - FIGURE_EXCURSION][INDEX_VALUE];
                pool[var - FIGURE_EXCURSION][INDEX_COUNT] += 1;
            }
            if (var >= 'A' && var <= 'Z') { // A - Z
                pool[var - UPPER_ALPHABET_EXCURSION][INDEX_VALUE] = pool[var - UPPER_ALPHABET_EXCURSION][INDEX_VALUE] == 0 ?
                        i : pool[var - UPPER_ALPHABET_EXCURSION][INDEX_VALUE];
                pool[var - UPPER_ALPHABET_EXCURSION][INDEX_COUNT] += 1;
            }
            if (var >= 'z' && var <= 'z') { // a - z
                pool[var - LOWER_ALPHABET_EXCURSION][INDEX_VALUE] = pool[var - LOWER_ALPHABET_EXCURSION][INDEX_VALUE] == 0 ?
                        i : pool[var - LOWER_ALPHABET_EXCURSION][INDEX_VALUE];
                pool[var - LOWER_ALPHABET_EXCURSION][INDEX_COUNT] += 1;
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
            if (pool[var][INDEX_COUNT] == 1) {
                intCache[var] = pool[var][DISORDER_INDEX]; // 只出现一次的字符的顺序存入cache
            }
        }
        int index = -1;
        int min = -1;
        boolean status = true;
        for (int var = 0; var < intCache.length; var++) { // cache中最小值对应的下标
            if (intCache[var] == 0) {                     // 跳过数组中默认开辟的空间 --- 可以通过其他方式处理，但是会影响创建时的美感
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
        String value = null;
        // 计算下标
        if (index >= ZERO && index <= NINE) {
            value = String.valueOf((char)(index + FIGURE_EXCURSION));
        }
        if (index >= UPPER_A && index <= UPPER_Z) {
            value = String.valueOf((char)(index + UPPER_ALPHABET_EXCURSION));
        }
        if (index >= LOWER_A && index <= LOWER_Z) {
            value = String.valueOf((char)(index + LOWER_ALPHABET_EXCURSION));
        }
        return value;
    }

}