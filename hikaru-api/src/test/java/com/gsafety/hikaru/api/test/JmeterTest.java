package com.gsafety.hikaru.api.test;

import savvy.wit.framework.core.pattern.proxy.RuntimeProxy;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : JmeterTest
 * Author : zhoujiajun
 * Date : 2019/2/13 14:28
 * Version : 1.0
 * Description : 
 ******************************/
public class JmeterTest {

    public static void main(String[] args) {
        RuntimeProxy.execute("cmd /k G:\\server\\apache-jmeter-5.0\\bin\\jmeter.bat");
    }
}
