package com.gsafety.hikaru.api.test;

import com.gsafety.hikaru.common.global.Result;
import com.gsafety.hikaru.common.global.ValidList;
import com.gsafety.hikaru.model.test.Monday;
import com.gsafety.hikaru.service.MondayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import savvy.wit.framework.core.base.service.dao.Order;
import savvy.wit.framework.core.base.service.dao.Pagination;
import savvy.wit.framework.core.base.util.JsonUtil;
import savvy.wit.framework.core.pattern.factory.CDT;
import savvy.wit.framework.core.pattern.factory.Daos;
import savvy.wit.framework.core.service.BaseService;
import savvy.wit.framework.core.service.impl.BaseServiceImpl;

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
@RequestMapping("/monday")
public class MondayController {

    private Logger log = LoggerFactory.getLogger(MondayController.class);

    @Autowired
    private MondayService mondayService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result<List<Monday>> index(@RequestParam String monday, @RequestParam String pagination) {
        Pagination pagination1 = JsonUtil.fromJson(pagination, Pagination.class);
        Monday monday1 = JsonUtil.fromJson(monday, Monday.class);
        return Result.success(mondayService.query(CDT.page(pagination1, "name", "like", monday1.getName()).order("name", Order.DESC)));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<Monday> add(@RequestBody @Validated Monday monday) {
        return Result.success(mondayService.insert(monday));
    }

    @RequestMapping(value = "/addBatch", method = RequestMethod.POST)
    public Result<List<Monday>> addBatch(@RequestBody @Validated ValidList<Monday> mondays) {
        mondayService.insertBath(mondays);
        return Result.success((mondayService.query()));
    }


}
