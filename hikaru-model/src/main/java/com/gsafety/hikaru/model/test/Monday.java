package com.gsafety.hikaru.model.test;

import savvy.wit.framework.application.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import savvy.wit.framework.core.base.service.dao.annotation.*;

import javax.validation.constraints.NotNull;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Monday
 * Author : zhoujiajun
 * Date : 2019/1/23 11:14
 * Version : 1.0
 * Description : 
 ******************************/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Monday extends BaseModel {

    @Id
    @Column
    @Type(type = CType.VARCHAR)
    @Comment("id")
    @ApiModelProperty(value = "编号")
    private String id;

    @Column
    @Type(type = CType.VARCHAR)
    @Comment("姓名")
    @NotNull(message = "${property.null}")
    @ApiModelProperty(value = "姓名", required = true)
    private String name;
}
