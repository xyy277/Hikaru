package savvy.wit.framework.core.base.util;

import savvy.wit.framework.core.base.service.Log;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Strings
 * Author : zhoujiajun
 * Date : 2018/6/28 9:35
 * Version : 1.0
 * Description : 
 ******************************/
public class Strings {

    private static Log log = LogFactory.getLog();

    public static String pack2Path (String pack) {
        return pack.replaceAll("\\.","\\/");
    }

    public static String path2Pack (String path) {
        return path.replaceAll("/","\\.");
    }

    /**
     * 驼峰转下划线
     * abcDefGhi   =  abc_def_ghi
     * @param value
     * @return
     */
    public static String hump2Line (String value) {
        StringBuilder o = new StringBuilder();
        char[] chars = value.toCharArray();
        char c;
        for (int var = 0; var < chars.length; var++) {
            c = chars[var];
            if (c >= 65 && c <=90) {
                if (var > 0) {
                    o.append('_');
                }
                c = (char)(c + 32);
            }
            o.append(c);
        }
        return o.toString();
    }

    public static String line2Hump (String value) {
        StringBuilder o = new StringBuilder();
        char[] chars = value.toCharArray();
        char c;
        for (int var = 0; var < chars.length; var++) {
            c = chars[var];
            if (var > 0 && c == '_' && ++var <chars.length) {
                while (chars[var] == '_') {
                    var++;
                }
                c = (char)(chars[var] - 32);
            }
            if (var == 0 && c >= 97 && c <=122) {
                c = (char)(c - 32);
            }
            o.append(c);
        }
        return o.toString();
    }

    /**
     * \5FAE\8F6F\96C5\9ED1
     * 5FAE 8F6F 96C5 9ED1
     * @param unicode
     * @return
     */
    public static String unicode2Chinese (String unicode) {
        StringBuilder sb = new StringBuilder();
        String[] strings = unicode.trim().indexOf("\\") != -1 ? unicode.split("\\\\") : null;
        Arrays.asList(strings).forEach(s -> {
            if (StringUtil.isNotBlank(s)) {
                sb.append((char) Integer.parseInt(s, 16));
            }
        });
        return sb.toString();
    }

    /**
     *
     * @param value 内容
     * @param present 当前编码
     * @param target 目标转码
     * @return
     */
    public static String transformEncoding (String value, String present, String target) {
        try {
            return StringUtil.isNotBlank(value) ? new String (value.getBytes(present), target) : "";
        } catch (UnsupportedEncodingException e) {
            log.error(e);
        }
        return "";
    }

    public static String encoding(String value, String target) {
        try {
            return StringUtil.isNotBlank(value) ? new String (value.getBytes() , target) : "";
        } catch (UnsupportedEncodingException e) {
            log.error(e);
        }
        return "";
    }

    /**
     * 比较字符串的前length位是否相等
     * @param var1
     * @param var2
     * @param length
     * @return
     */
    public static boolean equals(String var1, String var2, int length) {
        boolean status = false;
        if (var1.length() >= length && var2.length() >= length) {
            for (int i = 0; i < length; i++) {
                if (var1.toCharArray()[i] != var2.toCharArray()[i]) {
                    return status;
                }
            }
            status = true;
        }
        return status;
    }
}
