package com.gsafety.hikaru.common.middleware.shedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 系统计划
 * File name : SystemSchedule
 * Author : zhoujiajun
 * Date : 2018/12/28 14:17
 * Version : 1.0
 * Description : 模板
 ******************************/
@Component
public class SystemSchedule {

    private Logger log = LoggerFactory.getLogger(SystemSchedule.class);

    @Scheduled(cron = "0 0/5 * * * ?")
    public void checkLog() {
        log.info("check SystemSchedule Log->>>>>>>");
    }


    @Scheduled(cron = "0 0/5 * * * ?")
    public void auto() {
        log.info("<<<<<<<-auto ");
    }
}
