package com.gsafety.hikaru.application.aspectj;

import com.gsafety.hikaru.common.global.Error;
import com.gsafety.hikaru.common.global.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ExtendExceptionHandler
 * Author : zhoujiajun
 * Date : 2018/12/28 9:38
 * Version : 1.0
 * Description : 
 ******************************/
@ControllerAdvice // 作用于@RequestMapping method
public class ExtendExceptionHandler {

    private Logger log = LoggerFactory.getLogger(ExtendExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public Result modelNotFoundExceptionHandler(Exception exception) {
        log.error("exception happened",exception);
        return Result.error(new Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage()));
    }
}
