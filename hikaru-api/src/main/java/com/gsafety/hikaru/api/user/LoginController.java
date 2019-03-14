package com.gsafety.hikaru.api.user;

import com.gsafety.hikaru.common.global.Result;
import com.gsafety.hikaru.model.system.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.util.UUID;


/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : LoginController
 * Author : zhoujiajun
 * Date : 2019/3/13 9:47
 * Version : 1.0
 * Description : 
 ******************************/
@RestController
public class LoginController {

    private Log log = LogFactory.getLog();

    @PostMapping("/login")
    public Result<User> login(@RequestBody User user) {
        log.log(user);
        user.setUapToken(UUID.randomUUID().toString());
        return Result.success(user);
    }
}
