package com.gsafety.hikaru.api.test;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import savvy.wit.framework.core.pattern.adapter.FileAdapter;
import savvy.wit.framework.core.pattern.factory.ExcelBuilderFactory;
import savvy.wit.framework.core.pattern.proxy.ExcelProxy;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : ExcelController
 * File name : ExcelController
 * Author : zhoujj
 * Date : 2020/3/17 10:21
 * Version : 1.0
 * Description : 
 ******************************/
@RestController
@RequestMapping("/excel")
@Api(value = "Excel控制器")
public class ExcelController {


    @ApiOperation(value = "配置文件转Excel", notes = "配置文件转Excel", produces="application/octet-stream")
    @RequestMapping(value = "/p2e/{fileName}", method = RequestMethod.POST)
    public void p2e(@ApiParam(value = "配置文件")@RequestBody MultipartFile file, @PathVariable("fileName") String fileName, HttpServletResponse response) {
        Map<String, Object> data = ExcelBuilderFactory.properties2Excel(FileAdapter.multipartFileToFile(file));
        ExcelProxy.proxy(ExcelBuilderFactory.construct(fileName).simple("sheet 1", data, "key", "zh-CN").builder()).http(response).produce();
    }


}
