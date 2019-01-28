package com.gsafety.hikaru.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : CommonExceptionHandler
 * Author : zhoujiajun
 * Date : 2019/1/7 17:00
 * Version : 1.0
 * Description : 
 ******************************/
public class CommonExceptionHandler {

    private Logger log = LoggerFactory.getLogger(CommonExceptionHandler.class);

    // exception
    @ExceptionHandler({})
    public ResponseEntity modelNotFoundExceptionHandler(Exception exception) {
        log.error("redis happened",exception);
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
