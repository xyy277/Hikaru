package com.gsafety.hikaru.model.system;

import com.gsafety.hikaru.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import savvy.wit.framework.core.base.service.dao.annotation.*;

import java.util.List;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 角色模型
 * File name : SysRole
 * Author : zhoujiajun
 * Date : 2019/3/20 18:02
 * Version : 1.0
 * Description : 角色可以用于职位模型， 部门unit-角色/职位-人员
 ******************************/
@Table
@Data
@NoArgsConstructor
public class SysRole extends BaseModel {
    @Id
    @Column
    @Type(type = CType.VARCHAR, width = 32)
    @Comment("id")
    @ApiModelProperty(value = "id", required = true)
    private String id;

    @Column
    @Type(type = CType.VARCHAR)
    @Comment("角色名称")
    @ApiModelProperty(value = "角色名称", required = true)
    private String name;

    @Column
    @Type(type = CType.VARCHAR, vacancy = true)
    @Comment("角色别名")
    @ApiModelProperty(value = "角色别名", required = true)
    private String alias;

    @Column
    @Type(type = CType.VARCHAR, vacancy = true)
    @Comment("描述")
    @ApiModelProperty(value = "描述", required = true)
    private String description;

    @Column
    @Type(type = CType.BOOLEAN)
    @Comment("是否禁用")
    @ApiModelProperty(value = "是否禁用", required = true)
    private Boolean disable;

    @Column
    @Type(type = CType.VARCHAR, vacancy = true)
    @Comment("部门id")
    @ApiModelProperty(value = "部门id", required = true)
    private String unitId;

    private SysUnit unit;

    private List<SysMenu> menus;

    private List<SysUser> users;
}
