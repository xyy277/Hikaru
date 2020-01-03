package com.gsafety.hikaru.application.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import savvy.wit.framework.core.base.callback.LogCallBack;
import savvy.wit.framework.core.base.callback.LogThrowableCallBack;
import savvy.wit.framework.core.base.enums.LogType;
import savvy.wit.framework.core.base.service.log.AbstractLogAspectJ;
import savvy.wit.framework.core.base.service.log.LogMsgBuilder;
import savvy.wit.framework.core.base.util.DateUtil;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : LoggerAspectJ
 * Author : zhoujiajun
 * Date : 2019/12/26 16:53
 * Version : 1.0
 * Description : 
 ******************************/
@Aspect
@Component
@Lazy(false)
public class LoggerAspectJ extends AbstractLogAspectJ {

    @Value("${topic}")
    private String topic;

    @Autowired
    private KafkaTemplate kafkaTemplate;


    @Override
    public LogThrowableCallBack throwableBack() {
        return (joinPoint, throwable) -> {
            System.out.println(throwable.toString());
        };
    }

    @Override
    public LogCallBack logback() {
        return (joinPoint, log, result) -> {
            String id = log.id();
            for (LogType type : log.type()) {
                switch (type) {
                    case SYSTEM:
                        break;
                }

                kafkaTemplate.send(topic, LogMsgBuilder
                        .create()
                        .type(type)
                        .time(DateUtil.getNow())
                        .prefix()
                        .param()
                        .result(result)
                        .suffix()
                        .build());
            }
        };
    }
}