package com.gsafety.hikaru.model.test;

import com.gsafety.hikaru.common.enumerate.business.Ultraviolet;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import savvy.wit.framework.application.model.BaseModel;
import savvy.wit.framework.core.base.service.dao.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Sunday
 * Author : zhoujiajun
 * Date : 2019/2/19 9:16
 * Version : 1.0
 * Description : 
 ******************************/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Sunday extends BaseModel {

    @Id
    @Column
    @Type(type = CType.VARCHAR, width = 32)
    @Comment("编号")
    @ApiModelProperty(value = "编号")
    private String id;

    @Column
    @Comment("气温")
    @Type(type = CType.FLOAT, width = 10)
    @NotNull
    @ApiModelProperty(value = "气温")
    private double temperature;

    @Column
    @Comment("紫外线强度")
    @Type(type = CType.INT, width = 2, vacancy = true)
    @ApiModelProperty(value = "紫外线强度")
    private Ultraviolet ultraviolet;


}
