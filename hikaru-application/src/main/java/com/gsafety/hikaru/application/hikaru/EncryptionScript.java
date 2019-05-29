package com.gsafety.hikaru.application.hikaru;

import savvy.wit.framework.core.algorithm.model.key.KeyStore;
import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.base.util.RSAUtil;
import savvy.wit.framework.core.base.util.Strings;
import savvy.wit.framework.core.pattern.adapter.FileAdapter;
import savvy.wit.framework.core.pattern.decorate.Counter;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.io.*;
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
 * Description : 给db.properties密码加密
 ******************************/
public class EncryptionScript {

    private static Log log = LogFactory.getLog();

    private final static String PUBLIC_KEY = KeyStore.RSA_PUBLIC_KEY;

    public static void main(String[] args) {
        encipherment();
    }


    /**
     * 自动修改db
     */
    public static void encryption(String... fileNames) {
        for (String fileName : fileNames) {
//            log.println("当前修改文件为：\t"+ fileName);
            String path = "";
            if (new File("./" + fileName).exists()) {
                path ="./" + fileName;
            }
            else {
                path = Thread.currentThread().getContextClassLoader().getResource(fileName).getFile();
            }
            path = path.substring(0, 1).equals("/") ? path.substring(1, path.length()) : path;
//        path = Strings.path2Backslash(path);
            Counter counter = Counter.create();
            counter.setValue("append", true);
            counter.setValue("check", true);
            if (new File(path).exists()) {
                FileAdapter.me().readLine(path, string -> {
                    if ((Boolean)counter.getValue("check")) {
                        if (string.indexOf("#password=") != -1) {
                            counter.setValue("append", true);
                        } else if ( string.indexOf("password=") != -1) {
                            counter.setValue("append", false);
                            counter.setValue("check", false);
                        }
                    }
                });
            }

            if ((Boolean) counter.getValue("append")) {
                String password = "";
                log.println("100*--");
                log.print("请输入要连接的数据库密码，系统将对其加密：");
                Scanner scanner = new Scanner(System.in);
                password = scanner.nextLine();
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

                if (new File(System.getProperty("user.dir") + "\\hikaru-application\\src\\main\\resources\\"+ fileName).exists()) {
                    // IDE 写入配置文件
                    FileAdapter.me().lazyWriter(new File(System.getProperty("user.dir") + "\\hikaru-application\\src\\main\\resources\\" + fileName),
                            "\npassword=" + cryptograph);
                }
                // 需要运行jar与配置文件同级目录存放
                if (new File(System.getProperty("user.dir") + "\\" + fileName).exists()) {
                    // 打包jar运行 写入配置文件
                    FileAdapter.me().lazyWriter(new File(System.getProperty("user.dir") +"\\" + fileName),
                            "\npassword="+cryptograph);
                }
                log.println("100*--");
                log.warn("请查看"+ fileName +"中密码是否添加成功，如未添加请手动添加↓");
                log.println("请将下方↓↓↓生成的字符串复制到" + fileName);
                log.println("password=" + cryptograph);
                log.println("100*==");
            }
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
