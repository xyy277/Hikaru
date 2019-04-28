package com.gsafety.hikaru.api.test;

import com.gsafety.hikaru.model.business.User;
import com.gsafety.hikaru.service.UserService;
import com.gsafety.hikaru.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.base.util.DateUtil;
import savvy.wit.framework.core.base.util.JsonUtil;
import savvy.wit.framework.core.base.util.StringUtil;
import savvy.wit.framework.core.pattern.factory.CDT;
import savvy.wit.framework.core.pattern.factory.ConfigFactory;
import savvy.wit.framework.core.pattern.factory.Daos;
import savvy.wit.framework.core.pattern.factory.LogFactory;
import savvy.wit.framework.core.service.BaseService;
import savvy.wit.framework.core.service.impl.BaseServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ServiceTest
 * Author : zhoujiajun
 * Date : 2018/7/7 23:30
 * Version : 1.0
 * Description : 
 ******************************/
public class ServiceTest {
    private static Log log = LogFactory.getLog();
    private static final String PROJECT_PATH = System.getProperty("user.dir");

    UserService userService;

    @Before
    public void init() {
        ConfigFactory.me().setSource(PROJECT_PATH.substring(0, PROJECT_PATH.lastIndexOf("\\")) + "\\hikaru-application\\src\\main\\resources\\db.properties")
                .setSource(PROJECT_PATH.substring(0, PROJECT_PATH.lastIndexOf("\\")) + "\\hikaru-application\\src\\main\\resources\\test.properties")
                .setEnumClassList("com.gsafety.hikaru.model.enumerate")// 设置泛型package
                .setProperty("vacancy", "true")
                .setProperty("intervalMark", "@$");
        userService = new UserServiceImpl(Daos.get());

        log.log( () -> {
//            Daos.get().create(User.class);
        });
    }


    @Test
    public void test() {
        List<User> userList = new ArrayList<>();
        for (int var = 0; var < 100; var++) {
            User user = new User();
            user.setName(StringUtil.createCode());
            user.setUsername(StringUtil.createCode());
            user.setPassword(StringUtil.createCode());
            user.setAge(DateUtil.random(120));
            user.setOnline(DateUtil.random(2));
            user.setDisable(DateUtil.random(2) > 0 ? true : false);
            user.setOptTime(DateUtil.getNow());
            user.setOptUser(StringUtil.createCode());
            userList.add(user);
        }
        userService.insertBath(userList);

        List<User> users = userService.query(CDT.where("name", "=", "zhoujiajun"));
        log.log(users);
    }


    public static void main(String[] args) {
        User user = new User();
        user.setName(StringUtil.createCode());
        user.setUsername(StringUtil.createCode());
        user.setPassword(StringUtil.createCode());
        user.setAge(DateUtil.random(120));
        user.setOnline(DateUtil.random(2));
        user.setDisable(DateUtil.random(2) > 0 ? true : false);
        user.setOptTime(DateUtil.getNow());
        user.setOptUser(StringUtil.createCode());
        log.println(JsonUtil.toJson(user));
    }
}
