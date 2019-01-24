package com.gsafety.hikaru.api.test;


import com.gsafety.hikaru.model.system.User;
import com.gsafety.hikaru.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import savvy.wit.framework.core.base.interfaces.Log;
import savvy.wit.framework.core.base.util.DateUtil;
import savvy.wit.framework.core.base.util.StringUtil;
import savvy.wit.framework.core.pattern.factory.CDT;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.util.Arrays;
import java.util.List;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : TestController
 * Author : zhoujiajun
 * Date : 2018/12/17 10:49
 * Version : 1.0
 * Description : 
 ******************************/
@RestController
@RequestMapping("/test")
public class TestController {

    private Log log = LogFactory.getLog();

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/{num}", method = RequestMethod.GET)
    public ResponseEntity<String> test(@PathVariable String num) {
        return new ResponseEntity(StringUtil.createCode(num.length(), true), HttpStatus.OK);
    }

    @RequestMapping(value = "/feign/{num}", method = RequestMethod.GET)
    public ResponseEntity<String> testFeign(@PathVariable String num) {
        return userService.testFeign(num);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<User>> test() {
        List<User> users = null;
//        users = userService.query(User.class, CDT.where("name", "like", "%zhou%"));
        users = userService.query(CDT.NEW());
        return new ResponseEntity(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<User> add(@RequestBody @Validated User user) {
        user.setOptTime(DateUtil.getNow());
        user.setOptUser(StringUtil.createCode());
        User user1 = userService.save(user);
        return new ResponseEntity(user1, HttpStatus.OK);
    }

    @RequestMapping(value = "/addBatch", method = RequestMethod.POST)
    public ResponseEntity<Integer> add(@RequestBody @Validated(value = User.class) User[] users) {
        int count = userService.insertBath(Arrays.asList(users));
        return new ResponseEntity(count, HttpStatus.OK);
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> remove(@PathVariable String id) {
        userService.remove(id);
        return new ResponseEntity(id, HttpStatus.OK);
    }

    @RequestMapping(value = "/fetch/{id}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> fetch(@PathVariable String id) {
        User user = new User();
        user.setId(id);
        return new ResponseEntity(userService.findOne(user), HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public ResponseEntity<Boolean> update() {
        User user = new User();
        user.setName("ZJJ");
        user.setAge(0);
        return new ResponseEntity(userService.update(user, CDT.where("name", "=", "zhoujiajun")), HttpStatus.OK);
    }
}
