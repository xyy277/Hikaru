package com.gsafety.hikaru.model.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import savvy.wit.framework.core.base.service.dao.annotation.*;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : SysRoleUser
 * Author : zhoujiajun
 * Date : 2019/3/21 11:26
 * Version : 1.0
 * Description : 
 ******************************/
@Table
@Data
@NoArgsConstructor
public class SysRoleUser {

    @Column
    @Type(type = CType.VARCHAR, width = 32)
    @Comment("角色id")
    @ApiModelProperty(value = "角色id", required = true)
    private String roleId;

    @Column
    @Type(type = CType.VARCHAR, width = 32)
    @Comment("人员id")
    @ApiModelProperty(value = "人员id", required = true)
    private String userId;
}
