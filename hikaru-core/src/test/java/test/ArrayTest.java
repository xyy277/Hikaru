package test;

import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.pattern.factory.LogFactory;
import savvy.wit.framework.core.structure.logical.LogicalLoop;

import java.util.regex.Pattern;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ArrayTest
 * Author : zhoujiajun
 * Date : 2019/2/19 14:39
 * Version : 1.0
 * Description : 
 ******************************/
public class ArrayTest {

    private static Log log = LogFactory.getLog();

    public static void main(String[] args) {
        String regex1 = "[0-9]*(\\*){1}(\\W)*";
        String regex2 = "[0-9]*(\\*){1}[0-9]*";
        String regex3 = "(\\W)*(\\*){1}[0-9]*";
        Pattern pattern1 = Pattern.compile(regex1);
        Pattern pattern2 = Pattern.compile(regex2);
        Pattern pattern3 = Pattern.compile(regex3);
        log.log(        pattern1.matcher("6*=").matches());
        log.log(        pattern2.matcher("6*6").matches());
        log.log(        pattern3.matcher("-*23").matches());
        log.print("100*a");
        log.print("6*6");
        log.print("=*1");
//
//        LogicalLoop loop = LogicalLoop.create();
//        loop.push(1, "zhou", 'x', "hello");
//        loop.restore();
//        for (int i = 0; i < loop.size(); i++) {
//            log.log(loop.next());
//        }
//        log.print("=*10");
//        loop.push(loop.remove(2));
//        for (int i = 0; i < loop.size(); i++) {
//            log.log(loop.next());
//        }
//        log.print("=*10");
//        for (int i = 0; i < loop.size(); i++) {
//            loop.excursion(1);
//            log.log(        loop.fetch());
//        }
//
//        loop.excursion(11);
//        log.log(loop.fetch());
    }
}
