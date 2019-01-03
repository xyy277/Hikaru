package com.gsafety.hikaru.common.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : LegalVerifyInterceptor
 * Author : zhoujiajun
 * Date : 2018/12/21 14:55
 * Version : 1.0
 * Description : 
 ******************************/
@Component
public class LegalVerifyInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(LegalVerifyInterceptor.class);

    /**
     * 请求处理之前拦截
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    /**
     * 处理请求完成之后，视图渲染之前
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 视图渲染完成之后
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
