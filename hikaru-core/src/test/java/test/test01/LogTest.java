package test.test01;

import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.base.util.Scanner;
import savvy.wit.framework.core.pattern.decorate.Counter;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : LogTest
 * Author : zhoujiajun
 * Date : 2019/6/3 16:15
 * Version : 1.0
 * Description : 
 ******************************/
public class LogTest {

    private static final Log log = LogFactory.getLog();

    public static void main(String[] args) {

        Set<Class<?>> classList = Scanner.scanning("test");
        /**
         *  前置条件传入一个字符数组
         *  打印一个数组 ||会截取数组中的最大下标值index，将不足index的字符串填补空字符串
         *  在打印一个拼接字符串时 可以用{}将要处理的符号包裹起来
         *  {5*-} 等同于打印5个-
         *  在单独打印时可以不用{}包裹
         *  单独使用||时，需要由{}包裹 如{||} 或者打印字符串类似 {A} || ， A可以是任意字符包括空字符串
         *  如：
         *  test.ArrayTest{ }||class java.lang.Object
         *  test.ArrayTest         class java.lang.Object
         *  test.PrintTest         class java.lang.Object
         *  test.test01.LogTest    class java.lang.Object
         *  test.test02.ObjectTest class java.lang.Object
         *  其次建议打印时的拼接方式用 print(A).print(B).printLn(C)这种方式
         *  package test||{5*-}> ArrayTest
         *  如：
         *  package test        -----> ArrayTest
         *  package test        -----> PrintTest
         *  package test.test01 -----> LogTest
         *  package test.test02 -----> ObjectTest
         */
        log.println();

        log.println("10*=");
        log.println("{10*=}{5*-}{100*5}");
        log.println("111111111111","5*-", "50*=");
        log.print("5*10").print("5*=").println("10*~");
        log.println(new Thread());
        log.println(classList.stream().map(aClass -> aClass.getPackage() + " || {5*-}> " + aClass.getSimpleName()).collect(Collectors.toList()).toArray());
        log.println(classList.stream().map(aClass -> aClass.getName() + "{ }||" + aClass.getSuperclass()).collect(Collectors.toList()).toArray());
        // open(length) length会一直有效，直到重新设置
        LogFactory.open(100)
                .printL("Hikaru")
                .close();

        LogFactory.open()
                .printL("Hikaru")
                .close();

        LogFactory.open(150)
                .printL("Hikaru")
                .close();

        LogFactory.print("Hikaru");


//        int i = Arrays.asList(Scanner.scanning("test")
//                .stream().map(aClass -> aClass.getPackage() + "|| {5*-}>> " + aClass.getSimpleName()).collect(Collectors.toList()).toArray())
//                .stream().map(o -> o.toString().substring(0, o.toString().lastIndexOf("||")))
//                .max((o1, o2) -> o1.toString().length()-o2.toString().length()).get().toString().length();
//        System.out.println(i);
//        System.out.println("package com.gsafety.hikaru.model.system.spider".length());

    }
}
