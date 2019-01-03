package com.gsafety.hikaru.application.aspectj;

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
@ControllerAdvice
public class ExtendExceptionHandler {

    private Logger log = LoggerFactory.getLogger(ExtendExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity modelNotFoundExceptionHandler(Exception exception) {
        log.error("exception happened",exception);
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
