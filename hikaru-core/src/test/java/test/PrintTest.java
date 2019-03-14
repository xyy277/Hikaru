package test;

import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.pattern.adapter.FileAdapter;
import savvy.wit.framework.core.pattern.factory.Files;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.io.File;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : PrintTest
 * Author : zhoujiajun
 * Date : 2019/3/7 14:58
 * Version : 1.0
 * Description : 
 ******************************/
public class PrintTest {

    private static Log log = LogFactory.getLog();
    public static void main(String[] args) {
        LogFactory.open(240)
                .printL("hello world!")
                .printL("mother fucker!")
                .printL("son of bitch")
                .close();

//        LogFactory.print("Hello Hikaru !");
//        for (File file : Files.getFiles("C:\\Users\\Administrator\\Desktop\\hikaru\\server")) {
////            String encoding = Files.getEncoding(file);
////            log.log(encoding);
//            LogFactory.print(file.getName());
//            if (file.getName().indexOf("properties") != -1)
//                FileAdapter.me().readLine(file, string -> log.println(string));
//        }
    }
}
