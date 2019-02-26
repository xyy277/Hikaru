package com.gsafety.hikaru.api.test;

import com.gsafety.hikaru.common.enumerate.Test;
import com.gsafety.hikaru.common.enumerate.Ultraviolet;
import com.gsafety.hikaru.model.test.Sunday;
import savvy.wit.framework.core.base.service.enumerate.EnumValueContract;
import savvy.wit.framework.core.base.service.enumerate.impl.EnumConvertBase;
import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.base.util.Scanner;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : EnumTest
 * Author : zhoujiajun
 * Date : 2019/2/25 11:38
 * Version : 1.0
 * Description : 
 ******************************/
public class EnumTest {

    private static final Log log = LogFactory.getLog();

    public static void main(String[] args) {
        Sunday sunday = new Sunday();
        sunday.setUltraviolet(Ultraviolet.Powerful);
        sunday.setTest(Test.OK);
        log.log(        EnumConvertBase.convert().getEnumClass());
        int i = EnumConvertBase.convert().enum2Value((EnumValueContract) sunday.getUltraviolet());
        log.log(i);
        log.println("100*xx");
        Class c = Ultraviolet.class;
        log.log(c.getSimpleName());
        log.println("100*==");
        Set<Class<?>> set = Scanner.scanning("com.gsafety.hikaru.common.enumerate");
        for (Field field : sunday.getClass().getDeclaredFields()) {
//            log.log(field.getName());
//            log.log(field.getClass());
//            log.log(field.getType().getSimpleName());
            try {
//                Class clazz = Class.forName(field.getType().getSimpleName(), false, ClassLoader.getSystemClassLoader());
//                log.log(clazz);
                String name = field.getType().getSimpleName();
                List<Class> list = set.parallelStream()
                        .filter(aClass -> aClass.getSimpleName().equals(name))
                        .collect(Collectors.toList());
                if (list.size() > 0) {
                    log.println("100*--");
                    log.log(list);
                }
            } catch (Exception e) {
                log.error(e);
            }
//            log.log(EnumValueContract.class.isAssignableFrom(field.getType().getClass()));
        }
    }
}
