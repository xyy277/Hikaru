package com.gsafety.hikaru.common.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

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

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("filter init ");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        logger.info(request.getRequestURI());
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        logger.info("filter destroy ");
    }
}
