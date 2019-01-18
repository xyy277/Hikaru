package com.gsafety.hikaru.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : TestFeign
 * Author : zhoujiajun
 * Date : 2019/1/17 14:41
 * Version : 1.0
 * Description : 
 ******************************/
@FeignClient( name = "hikaru", url = "192.168.67.241:8889/hikaru")
public interface TestFeign {

    /**
     * feign test
     * @param num PathVariable中路径参数需要指定value
     * @return String
     */
    @RequestMapping(value = "/test/{num}", method = RequestMethod.GET)
    ResponseEntity<String> test(@PathVariable(value = "num") String num);

    @RequestMapping(value = "/test/{num}", method = RequestMethod.GET)
    ResponseEntity<String> check(@PathVariable("num") String num);
}
