package com.gsafety.hikaru.application.aspectj;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import savvy.wit.framework.core.base.callback.LogCallBack;
import savvy.wit.framework.core.base.service.log.AbstractLogAspectJ;

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

    @Override
    public LogCallBack logback() {
        return (joinPoint, log, result) -> {

        };
    }
}