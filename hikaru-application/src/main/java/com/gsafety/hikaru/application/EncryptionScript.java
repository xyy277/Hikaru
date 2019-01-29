package com.gsafety.hikaru.application;

import savvy.wit.framework.core.base.service.Log;
import savvy.wit.framework.core.base.util.FileUtil;
import savvy.wit.framework.core.base.util.RSAUtil;
import savvy.wit.framework.core.base.util.Strings;
import savvy.wit.framework.core.pattern.adapter.FileAdapter;
import savvy.wit.framework.core.pattern.decorate.Counter;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
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

    private final static String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDYHBOJWY8FyXfwG9f8yShp4UGqSYSx1mc" +
            "q6UxwzslQu9Mdl2nExEVYK-NYhc0-TRHwXRFVBbjgqxLykp2YMXN_24CNOYoQ1UBjZoYx24zdAp21o0SI-tVj1l94eJiGVG1WeXP1ERu" +
            "9WQgGAe0L8nn1IguYOEia7RymsG3w3e6c0QIDAQAB";

    public static void main(String[] args) {
        encipherment();
    }


    public static void encryption() {
        String path = "";
        if (new File("./db.properties").exists())
            path = "./db.properties";
        else
            path = Thread.currentThread().getContextClassLoader().getResource("db.properties").getFile();
        path = path.substring(0, 1).equals("/") ? path.substring(1, path.length()) : path;
        path = Strings.path2Backslash(path);

        Counter counter = Counter.create();
        counter.setValue("append", true);
        FileAdapter.me().readLine(path, string -> {
            if (string.indexOf("password=") != -1) {
                counter.setValue("append", false);
            }
        });

        if ((Boolean) counter.getValue("append")) {
            System.out.print("请输入要加密的数据库密码：");
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
            FileAdapter.me().lazyWriter(new File(path),
                    "password=" + cryptograph);
            log.warn("请查看db.properties中密码是否添加成功，如未添加请手动添加↓");
            System.out.println("请将下方↓↓↓生成的字符串复制到db.properties");
            System.out.println("password=" + cryptograph);
        }
    }

    public static void encipherment() {
            System.out.print("请输入要加密的数据库密码：");
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
            System.out.println("请将下方↓↓↓生成的字符串复制到db.properties");
            System.out.println("password=" + cryptograph);
        }
}
