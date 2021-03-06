package com.gsafety.hikaru.common.filter;

import com.gsafety.hikaru.common.global.SystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import savvy.wit.framework.core.pattern.factory.Daos;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 路由过滤器
 * File name : CharacterEncodingFilter
 * Author : zhoujiajun
 * Date : 2018/12/21 11:29
 * Version : 1.0
 * Description : 
 ******************************/
@Order(1)
@WebFilter(urlPatterns = "/*", filterName = "RouterFilter")
public class RouterFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(RouterFilter.class);

    @Value("${server.servlet.context-path}")
    private String path;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("RouterFilter init ");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        String uri = request.getRequestURI();
//        if ((path + "/").equals(uri) ){
//            response.sendRedirect(uri + SystemConfig.SWAGGER_URI);
//        }else filterChain.doFilter(request, response);
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        logger.info("RouterFilter destroy ");
    }
}
