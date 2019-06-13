package com.gsafety.hikaru.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 测试FeignClient
 * File name : TestFeign
 * Author : zhoujiajun
 * Date : 2019/1/17 14:41
 * Version : 1.0
 * Description : FeignClient
 * value()和name()一样，是被调用的服务的ServiceId。
 * url()直接填写硬编码URL地址。decode404（）即404是被解码，还是抛异常。
 * configuration（）指明FeignClient的配置类，默认的配置类为FeignClientsConfiguration类，在缺省情况下，这个类注入了默认的Decoder、Encoder和Constant等配置的bean。
 * fallback()为配置熔断器的处理类
 ******************************/
@FeignClient(value = "${hikaruServerName}", url = "${hikaruServerUrl}")
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
