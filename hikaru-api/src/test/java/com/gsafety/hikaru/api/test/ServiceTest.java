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
import savvy.wit.framework.core.service.Configuration;
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
        Configuration configuration = ConfigFactory.me();
        String url = System.getProperty("user.dir");
        url = url.substring(0, url.lastIndexOf("\\"));
        configuration.setSource(url + "\\hikaru-application\\src\\main\\resources\\db.properties")  // 设置dao数据源
                .setProperty("vacancy", "true")                                                                                     // 设置参数vacancy - 插入数据为null时，补“”
                .setProperty("intervalMark", "@$")
                .setEnumClassList("com.gsafety.hikaru.model.enumerate");                                                             // 设置泛型package
        userService = new UserServiceImpl(Daos.get());

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
            user.setGender(DateUtil.random(2));
            user.setOnline(DateUtil.random(2));
            user.setDisable(DateUtil.random(2) > 0 ? true : false);
            user.setOptTime(DateUtil.getNow());
            user.setOptUser(StringUtil.createCode());
            userList.add(user);
        }
//        userService.insertBath(userList);

        List<User> users = userService.query(CDT.where("name", "like", "z"));
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
