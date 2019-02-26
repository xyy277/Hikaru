package com.gsafety.hikaru.api.test;

import com.gsafety.hikaru.common.global.Result;
import com.gsafety.hikaru.model.test.Sunday;
import com.gsafety.hikaru.service.SundayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import savvy.wit.framework.core.pattern.factory.CDT;

import java.util.List;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : SundayController
 * Author : zhoujiajun
 * Date : 2019/2/25 9:18
 * Version : 1.0
 * Description : 
 ******************************/
@RestController
@Api(value = "晴天控制器", description = "用于测试晴天的参数")
@RequestMapping("/sunday")
public class SundayController {

    @Autowired
    private SundayService sundayService;

    @ApiOperation(value = "查询所有", notes = "获取晴天列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result<List<Sunday>> test() {
        return Result.success(sundayService.query());
    }

    @ApiOperation(value = "添加", notes = "添加晴天")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<Sunday> add(@ApiParam(value = "晴天对象") @RequestBody @Validated Sunday sunday) {
        return Result.success(sundayService.insert(sunday));
    }


    @ApiOperation(value = "删除", notes = "删除晴天")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Result<Boolean> delete(@ApiParam(value = "晴天id") @PathVariable String id) {
        return Result.success(sundayService.delete(CDT.NEW().where("id", "=", id)));
    }


    @ApiOperation(value = "修改", notes = "修改晴天")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<Boolean> put(@ApiParam(value = "晴天对象") @RequestBody @Validated Sunday sunday) {
        return Result.success(sundayService.update(sunday));
    }

    @ApiOperation(value = "查询", notes = "根据id查询晴天")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<Boolean> fetch(@ApiParam(value = "晴天id") @PathVariable String id) {
        return Result.success(sundayService.fetch(CDT.NEW().where("id", "=", id)));
    }

}
