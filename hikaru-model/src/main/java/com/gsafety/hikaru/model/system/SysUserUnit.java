package com.gsafety.hikaru.model.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import savvy.wit.framework.core.base.service.dao.annotation.*;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : SysUserUnit
 * Author : zhoujiajun
 * Date : 2019/3/21 14:25
 * Version : 1.0
 * Description : 
 ******************************/
@Table
@Data
@NoArgsConstructor
public class SysUserUnit {

    @Column
    @Type(type = CType.VARCHAR, width = 32)
    @Comment("部门id")
    @ApiModelProperty(value = "部门id", required = true)
    private String unitId;

    @Column
    @Type(type = CType.VARCHAR, width = 32)
    @Comment("人员id")
    @ApiModelProperty(value = "人员id", required = true)
    private String userId;

}
