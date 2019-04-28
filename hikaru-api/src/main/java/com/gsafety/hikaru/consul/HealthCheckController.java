package com.gsafety.hikaru.consul;

import com.gsafety.hikaru.model.extend.Hikaru;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import savvy.wit.framework.core.base.util.DateUtil;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : consul健康检查
 * File name : HealthCheckController
 * Author : zhoujiajun
 * Date : 2019/1/16 14:41
 * Version : 1.0
 * Description : 
 ******************************/
@RestController
@Service("consulHealthIndicator")
public class HealthCheckController {

    private Logger log = LoggerFactory.getLogger(HealthCheckController.class);

    /**
     * 心跳
     * @return
     */
    @RequestMapping(value = "/heartbeat", method = RequestMethod.GET)
    public ResponseEntity<Hikaru> heartbeat() {
        Hikaru hikaru = new Hikaru();
        hikaru.setDescription("OK");
        hikaru.setTime(DateUtil.getNow("yyyy-MM-dd HH:mm:ss,S"));
        return new ResponseEntity<>(hikaru, HttpStatus.OK);
    }
}
