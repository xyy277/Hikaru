package com.gsafety.hikaru.common.shedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : SystemShedule
 * Author : zhoujiajun
 * Date : 2018/12/28 14:17
 * Version : 1.0
 * Description : 
 ******************************/
@Component
public class SystemSchedule {

    private Logger log = LoggerFactory.getLogger(SystemSchedule.class);

    @Scheduled(cron = "*/1 * * * * ?")
    public void checkLog() {
        log.info("checkLog");
    }
}
