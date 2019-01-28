package com.gsafety.hikaru.api.test;

import com.gsafety.hikaru.common.global.ValidList;
import com.gsafety.hikaru.model.test.Monday;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import savvy.wit.framework.core.base.interfaces.dao.Pagination;
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

    private BaseService<Monday> baseService = new BaseServiceImpl<>(Daos.get());

    @RequestMapping(value = "/{page}/{pageSize}", method = RequestMethod.GET)
    public ResponseEntity<List<Monday>> index(Monday monday, Pagination pagination) {
        return new ResponseEntity<>(baseService.query(Monday.class, CDT.page(pagination, "name", "like", monday.getName())), HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Monday> add(@RequestBody @Validated Monday monday) {
        return new ResponseEntity<>(baseService.insert(monday), HttpStatus.OK);
    }

    @RequestMapping(value = "/addBatch", method = RequestMethod.POST)
    public ResponseEntity<List<Monday>> addBatch(@RequestBody @Validated ValidList<Monday> mondays) {
        baseService.insertBath(mondays);
        return new ResponseEntity<>(baseService.query(Monday.class), HttpStatus.OK);
    }


}
