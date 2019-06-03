package com.gsafety.hikaru.application;

import com.gsafety.hikaru.application.hikaru.HikaruApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.base.util.Scanner;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
@EnableDiscoveryClient // 启用consul注册
// Enable默认都只默认扫描当前及以下的包，需要指定
@EnableFeignClients(basePackages = "com.gsafety.hikaru.feign")
public class Application {

    public static void main(String[] args) {

        HikaruApplication.run(args);

    }

}
