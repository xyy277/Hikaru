package com.gsafety.hikaru.api.test;

import com.gsafety.hikaru.api.BaseController;
import com.gsafety.hikaru.common.global.Result;
import com.gsafety.hikaru.common.global.ValidList;
import com.gsafety.hikaru.model.system.User;
import com.gsafety.hikaru.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import savvy.wit.framework.core.base.service.dao.Order;
import savvy.wit.framework.core.base.service.dao.Pagination;
import savvy.wit.framework.core.pattern.factory.CDT;

import java.util.List;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : MondyController
 * Author : zhoujiajun
 * Date : 2019/1/23 11:17
 * Version : 1.0
 * Description : 
 ******************************/
@RestController
@RequestMapping("/user")
public class UserController extends BaseController<User> {

    private Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

//    @RequestMapping(value = "", method = RequestMethod.POST)
//    public Result<List<User>> index(@RequestBody Pagination pagination) {
//        return Result.success(userService.query(CDT.page(pagination).order("name", Order.DESC)));
//    }

//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    public Result<User> add(@RequestBody @Validated User user) {
//        return Result.success(userService.insert(user));
//    }

    @RequestMapping(value = "/addBatch", method = RequestMethod.POST)
    public Result<List<User>> addBatch(@RequestBody @Validated ValidList<User> users) {
        userService.insertBath(users);
        return Result.success((userService.query()));
    }


}
