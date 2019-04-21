package com.gsafety.hikaru.model.system;

import com.gsafety.hikaru.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import savvy.wit.framework.core.base.service.dao.annotation.*;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 系统配置模型
 * File name : SysConfig
 * Author : zhoujiajun
 * Date : 2019/3/21 11:55
 * Version : 1.0
 * Description : 
 ******************************/
@Table
@Data
@NoArgsConstructor
public class SysConfig extends BaseModel {

    @Column(unique = true)
    @Type(type = CType.VARCHAR)
    @Comment("系统配置key")
    @ApiModelProperty(value = "系统配置key", required = true)
    private String key;

    @Column
    @Type(type = CType.VARCHAR)
    @Comment("系统配置value")
    @ApiModelProperty(value = "系统配置value", required = true)
    private String value;

    @Column
    @Type(type = CType.VARCHAR, vacancy = true)
    @Comment("系统配置说明")
    @ApiModelProperty(value = "系统配置说明", required = true)
    private String description;

    @Column
    @Type(type = CType.VARCHAR)
    @Comment("是否禁用")
    @ApiModelProperty(value = "是否禁用", required = true)
    private Boolean disable;


}
