package com.gsafety.hikaru.common.middleware.shedule.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 系统计划配置
 * File name : SheduleConfig
 * Author : zhoujiajun
 * Date : 2019/1/4 10:06
 * Version : 1.0
 * Description :
 ******************************/
@Configuration
@EnableScheduling // 启用定时任务
public class ScheduleConfig implements SchedulingConfigurer {

    private Logger log = LoggerFactory.getLogger(ScheduleConfig.class);

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        log.info("Scheduler configureTasks init");
        scheduledTaskRegistrar.setScheduler(taskExecutor());
    }

    /**
     * 并发任务
     * @return
     */
    @Bean(destroyMethod = "shutdown")
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(100);
    }
}
