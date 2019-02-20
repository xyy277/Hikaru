package com.gsafety.hikaru.common.global;

import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.Page;
import savvy.wit.framework.core.base.util.DateUtil;

import java.util.*;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 返回对象包装类
 * File name : Result
 * Author : zhoujiajun
 * Date : 2019/2/20 11:42
 * Version : 1.0
 * Description : 
 ******************************/
public class Result<T> {

    @ApiModelProperty(value = "返回对象", required = true)
    private T data;

    @ApiModelProperty(value = "错误信息", required = true)
    private Error error;

    @ApiModelProperty(value = "返回对象数量", required = true)
    private long dataCount;

    @ApiModelProperty(value = "请求是否成功", required = true)
    private boolean success;

    @ApiModelProperty(value = "系统时间", required = true)
    private String date;

    private static Logger log = LoggerFactory.getLogger(Result.class);

    public Result() {
        this.date = DateUtil.getNow();
    }

    public static <K> Result success(K k) {
        Result<K> result = new Result();
        result.setSuccess(true);
        result.setData(k);
        result.setDataCount(getCount(k));
        return result;
    }

    public static <N> Result<List<N>> success(Page<N> page) {
        Result<List<N>> result = new Result<>();
        result.setSuccess(true);
        result.setData(page.getContent());
        result.setDataCount(page.getTotalElements());
        return  result;
    }

    public static Result success() {
        Result result = new Result();
        result.setSuccess(true);
        return result;
    }

    public static Result error() {
        Error error = new Error();
        return buildError(error);
    }

    public static Result error(Error error) {
        return buildError(error);
    }

    private static Result error(int errorCode, String message) {
        Error error = new Error(errorCode, message);
        return buildError(error);
    }

    public static Result error(int errorCode, Object... args) {
        Error error = new Error(errorCode, getErrorMessage(errorCode, args));
        return buildError(error);
    }

    public static Result error(int errorCode, MessageSource messageSource, Object... args) {
        Error error;
        if (messageSource == null) {
            error = new Error(errorCode, getErrorMessage(errorCode, args));
        } else {
            String key = "errorCode." + errorCode;
            String message = getKeyMessage(key, messageSource, args);
            error = new Error(errorCode, message);
        }
        return buildError(error);
    }

    private static String getErrorMessage(int errorCode, Object... args) {
        String key = "errorCode." + errorCode;
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("message");
        messageSource.setDefaultEncoding("UTF-8");
        return getKeyMessage(key, messageSource, args);
    }

    private static String getKeyMessage(String key, MessageSource messageSource, Object... args) {
        try {
            return messageSource.getMessage(key, args, Locale.getDefault());
        } catch (NoSuchMessageException e) {
            log.error(e.getMessage(), e);
        }
        return "";
    }

    private static Result buildError(Error error) {
        Result result = new Result();
        result.setSuccess(false);
        result.setError(error);
        return result;
    }

    private static <K> int getCount(K k) {
        int count = 0;
        if (k == null) {
            return count;
        }
        if (k instanceof Collection) {
            count = ((Collection) k).size();
        } else if (k instanceof Object[]){
            count = ((Object[]) k).length;
        } else if (k instanceof Map) {
            count = ((Map) k).size();
        } else {
            count = 1;
        }
        return count;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public long getDataCount() {
        return dataCount;
    }

    public void setDataCount(long dataCount) {
        this.dataCount = dataCount;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
