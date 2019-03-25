package com.gsafety.hikaru.model.system;

import com.gsafety.hikaru.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import savvy.wit.framework.core.base.service.dao.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Sys_User
 * Author : zhoujiajun
 * Date : 2019/3/14 12:18
 * Version : 1.0
 * Description : 
 ******************************/
// 选择表Engine，默认InnoDB
@Table(engine = Table.Engine.InnoDB)
@Data
@NoArgsConstructor
public class SysUser extends BaseModel {

    @Id
    @Column
    @Type(type = CType.VARCHAR, width = 32)
    @Comment("id")
    @ApiModelProperty(value = "编号", required = true)
    private String id;

    @Column
    @Type(type = CType.VARCHAR, width = 1024)
    @Comment("角色id(多个以,分割)")
    @ApiModelProperty(value = "角色id(多个以,分割)", required = true)
    private String[] roleId;

    private List<String> roleList;

    @Column
    @Type(type = CType.VARCHAR, width = 1024)
    @Comment("职位id")
    @ApiModelProperty(value = "职位id", required = true)
    private String postId;

    @Column
    @Type(type = CType.VARCHAR)
    @Comment("姓名")
    @NotNull(message = "${property.null}")
    @ApiModelProperty(value = "姓名", required = true)
    private String name;

    @Column(unique = true)
    @Type(type = CType.VARCHAR)
    @Comment("用户名")
    @NotNull(message = "${property.null}")
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @Column
    @Type(type = CType.VARCHAR)
    @Comment("密码")
    @NotNull(message = "${property.null}")
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @Column
    @Type(type = CType.INT, width = 3)
    @Comment("年龄")
    @ApiModelProperty(value = "年龄", required = true)
    private Integer age;

    @Column
    @Type(type = CType.INT, width = 1)
    @Comment("是否在线，默认0：离线")
    @ApiModelProperty(value = "是否在线，默认0不在线", required = true)
    private Integer online;

    @Column
    @Type(type = CType.BOOLEAN)
    @Comment("是否禁用，默认false:启用")
    @ApiModelProperty(value = "是否禁用，默认false:启用", required = true)
    private Boolean disable;

    // Token
    private String uapToken;


}
