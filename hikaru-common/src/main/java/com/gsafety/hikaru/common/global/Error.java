package com.gsafety.hikaru.common.global;

import com.gsafety.hikaru.common.helper.ApplicationBeanFactory;
import io.swagger.annotations.ApiModelProperty;

import javax.servlet.http.HttpServletRequest;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Error
 * Author : zhoujiajun
 * Date : 2019/2/20 11:50
 * Version : 1.0
 * Description : 
 ******************************/
public class Error {

    @ApiModelProperty(value = "错误状态码", required = true)
    private final int code;

    @ApiModelProperty(value = "错误信息", required = true)
    private final String message;

    @ApiModelProperty(value = "发生错误的请求路径", required = true)
    private String path;

    public Error() {
        this.code = 0;
        this.message = "unknow error";
        this.path = ApplicationBeanFactory.getBean(HttpServletRequest.class).getRequestURI();
    }

    public Error(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
