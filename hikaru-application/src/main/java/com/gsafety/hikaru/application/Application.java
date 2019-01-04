package com.gsafety.hikaru.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : boot启动类
 * File name : Application
 * Author : zhoujiajun
 * Date : 2018/12/17 10:07
 * Version : 1.0
 * Description : 
 ******************************/
@SpringBootApplication
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
