package com.gsafety.hikaru.application;

import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.base.util.ArraysUtil;
import savvy.wit.framework.core.base.util.RSAUtil;
import savvy.wit.framework.core.base.util.Strings;
import savvy.wit.framework.core.pattern.adapter.FileAdapter;
import savvy.wit.framework.core.pattern.decorate.Counter;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Scanner;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 数据库密码加密
 * File name : EncryptionScript
 * Author : zhoujiajun
 * Date : 2019/1/29 16:03
 * Version : 1.0
 * Description : 给文件中的密码加密
 * 数据库加密路径会直接将加密后的密码添加到编译后的target目录下的db.properties
 * 一次修改永久有效，缺点当你需要修改密码时，需要手动修改，或者清除target，重新编译
 ******************************/
public class EncryptionScript {

    private static Log log = LogFactory.getLog();

    private final static String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCn6dre5CkrpxmUzh_UcPEKxJawwh1hDaR5pQS0W5VskSYC-Aq6SMYz7KImLet4uIkqVUVfX_03JMUZ6YDNvEHc6ZL3ssKez08LHKzQbPNtAvccDCz_0cpqp6dLpSYCd0Evr-D2ow5HxsDm70kX-qszG8E_L3M1xO25-1uvJs-wowIDAQAB";

    public static void main(String[] args) {
        encipherment();
    }


    /**
     * 自动修改db
     */
    public static void encryption(String... passwords) {
        String path = "";
        if (new File("./db.properties").exists())
            path ="./db.properties";
        else
            path = Thread.currentThread().getContextClassLoader().getResource("db.properties").getFile();
        path = path.substring(0, 1).equals("/") ? path.substring(1, path.length()) : path;
        path = Strings.path2Backslash(path);
        Counter counter = Counter.create();
        counter.setValue("append", true);
        if (new File(path).exists())
            FileAdapter.me().readLine(path, string -> {
                if (string.indexOf("#password=") == -1 && string.indexOf("password=") != -1) {
                    counter.setValue("append", false);
                } else if (string.indexOf("#password=") != -1 && string.indexOf("password=") == -1) {
                    counter.setValue("append", true);
                }
            });


        if ((Boolean) counter.getValue("append")) {
            String password = "";
            if (passwords.length <= 0) {
                log.println("100*--");
                log.print("请输入要加密的数据库密码：");
                Scanner scanner = new Scanner(System.in);
                password = scanner.nextLine();
            } else
                password = passwords[passwords.length - 1];
            RSAPublicKey publicKey = null;
            try {
                publicKey = RSAUtil.getPublicKey(PUBLIC_KEY);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
            String cryptograph = RSAUtil.publicEncrypt(password, publicKey);

            // 编译后配置文件写入
            FileAdapter.me().lazyWriter(new File(path),
                    "\npassword=" + cryptograph);

            if (new File(System.getProperty("user.dir") + "\\hikaru-application\\src\\main\\resources\\db.properties").exists()) {
                // IDE 写入配置文件
                FileAdapter.me().lazyWriter(new File(System.getProperty("user.dir") + "\\hikaru-application\\src\\main\\resources\\db.properties"),
                        "\npassword=" + cryptograph);
            }
//            if (new File(System.getProperty("user.dir") + "\\db.properties").exists()) {
//                // 打包jar运行 写入配置文件
//                 FileAdapter.me().lazyWriter(new File(System.getProperty("user.dir") +"\\db.properties"),
//                        "\npassword="+cryptograph);
//            }
            log.println("100*--");
            log.warn("请查看db.properties中密码是否添加成功，如未添加请手动添加↓");
            log.println("请将下方↓↓↓生成的字符串复制到db.properties");
            log.println("password=" + cryptograph);
            log.println("100*==");
        }
    }

    /**
     * 手动运行修改db
     */
    public static void encipherment() {
        log.println("100*--");
        log.print("请输入要加密的数据库密码：");
            Scanner scanner = new Scanner(System.in);
            String password = scanner.nextLine();
            RSAPublicKey publicKey = null;
            try {
                publicKey = RSAUtil.getPublicKey(PUBLIC_KEY);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
            String cryptograph = RSAUtil.publicEncrypt(password, publicKey);
            log.println("请将下方↓↓↓生成的字符串复制到db.properties");
            log.println("password=" + cryptograph);
        log.println("100*==");
    }
}
