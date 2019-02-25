package com.gsafety.hikaru.api.test;

import com.gsafety.hikaru.common.enumerate.business.Ultraviolet;
import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.pattern.factory.LogFactory;

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
        log.log(Ultraviolet.Powerful.getParamType());

    }
}
