package com.gsafety.hikaru.application.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : JpaConfiguration
 * Author : zhoujiajun
 * Date : 2019/2/19 10:43
 * Version : 1.0
 * Description : 
 ******************************/
@SpringBootConfiguration
@EntityScan(basePackages = "com.gsafety.hikaru.entity")
//@EnableJpaRepositories(basePackages = "com.gsafety.hikaru.repository")
public class JpaConfiguration {
}
