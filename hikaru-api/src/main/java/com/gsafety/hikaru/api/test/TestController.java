package com.gsafety.hikaru.api.test;


import com.gsafety.hikaru.common.global.Result;
import savvy.wit.framework.core.base.service.message.LocaleMessage;
import com.gsafety.hikaru.model.business.User;
import com.gsafety.hikaru.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import savvy.wit.framework.core.base.annotations.Log;
import savvy.wit.framework.core.base.util.DateUtil;
import savvy.wit.framework.core.base.util.StringUtil;
import savvy.wit.framework.core.pattern.decorate.Counter;
import savvy.wit.framework.core.pattern.factory.CDT;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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
@Api(value = "测试控制器", description = "api开发标准")
public class TestController {


    @Autowired
    LocaleMessage localeMessage;

    @Autowired
    private UserService userService;

    @Log
    @RequestMapping(value = "/ok", method = RequestMethod.GET)
    public ResponseEntity<String> ok() {
        int[] ints = new int[0];
        System.out.println(ints[2]);
        System.out.println(localeMessage.getMessage("heartbeat.ok"));
        return new ResponseEntity(localeMessage.getMessage("heartbeat.ok"), HttpStatus.OK);
    }


    @RequestMapping(value = "/{num}", method = RequestMethod.GET)
    public ResponseEntity<String> test(@PathVariable String num) {
        return new ResponseEntity(StringUtil.createCode(num.length(), true), HttpStatus.OK);
    }

    @RequestMapping(value = "/feign/{num}", method = RequestMethod.GET)
    public ResponseEntity<String> testFeign(@PathVariable String num) {
        return userService.testFeign(num);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result<List<User>> test() {
        List<User> users = null;
//        users = userService.query(User.class, CDT.where("name", "like", "%zhou%"));
        users = userService.query(CDT.NEW());
        return Result.success(users);
    }

    @ApiOperation(value = "获取用户列表", notes = "测试集合转数组")
    @RequestMapping(value = "/array", method = RequestMethod.GET)
    public Result<User[]> testArray() {
        List<User> users = null;
        users = userService.query(CDT.NEW());
        return Result.success(users.toArray());
    }

    @ApiOperation(value = "获取用户列表", notes = "测试数组")
    @RequestMapping(value = "/array1", method = RequestMethod.GET)
    public Result<User[]> array() {
        List<User> users = null;
        users = userService.query(CDT.NEW());
        User[] users1 = new User[users.size()];
        Counter counter = Counter.create();
        users.forEach(user -> {
            users1[counter.getIndex()] = user;
        });
        return Result.success(users1);
    }

    @ApiOperation(value = "获取用户列表", notes = "测试map集合")
    @RequestMapping(value = "/array2", method = RequestMethod.GET)
    public Result<Map<String, User>> map() {
        return Result.success(userService.query(CDT.NEW()).parallelStream().collect(Collectors.toMap(User::getId, Function.identity())));
    }

    @ApiOperation(value = "获取用户列表", notes = "测试set集合")
    @RequestMapping(value = "/array3", method = RequestMethod.GET)
    public Result<Set<User>> set() {
        return Result.success(userService.query(CDT.NEW()).parallelStream().collect(Collectors.toSet()));
    }

    @ApiOperation(value = "添加用户", notes = "保存用户对象")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<User> add(@ApiParam(value = "用户对象") @RequestBody @Validated User user) {
        user.setOptTime(DateUtil.getNow());
        user.setOptUser(StringUtil.createCode());
        User user1 = userService.save(user);
        return Result.success(user1);
    }

    @RequestMapping(value = "/addBatch", method = RequestMethod.POST)
    public Result<Integer> add(@RequestBody @Validated(value = User.class) User[] users) {
        int count = userService.insertBath(Arrays.asList(users));
        return Result.success(count);
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
