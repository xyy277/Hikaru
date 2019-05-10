package com.gsafety.hikaru.common.middleware.shedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import savvy.wit.framework.core.pattern.proxy.RuntimeProxy;

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

    @Scheduled(cron = "0 20 18 * * ?")
    public void shutdown() {
        RuntimeProxy.get().execute("shutdown", "shutdown -s -t 600", false);
        log.info("The machine will shut down at 18:30 in ten minutes");
    }

    @Scheduled(cron = "0 30 18 * * ?")
    public void shutdown2() {
        RuntimeProxy.get().execute("shutdown", "shutdown now", false);
        log.info("The machine will shut down at 18:30 in ten minutes");
    }
}
