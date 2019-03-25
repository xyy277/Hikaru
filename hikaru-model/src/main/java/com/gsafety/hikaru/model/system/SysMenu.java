package com.gsafety.hikaru.model.system;

import com.gsafety.hikaru.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import savvy.wit.framework.core.base.service.dao.annotation.*;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Menu
 * Author : zhoujiajun
 * Date : 2019/1/15 11:25
 * Version : 1.0
 * Description : 
 ******************************/
@Table
@Data
@NoArgsConstructor
public class SysMenu extends BaseModel {

    // 菜单编号
    @Id
    @Column
    @Type(type = CType.VARCHAR, width = 32)
    @Comment("菜单编号")
    @ApiModelProperty(value = "菜单编号", required = true)
    private String id;

    // 上级菜单
    @Column
    @Type(type = CType.VARCHAR, vacancy = true, width = 32)
    @Comment("上级菜单")
    @ApiModelProperty(value = "上级菜单", required = true)
    private String superiors;

    // 菜单名
    @Column
    @Type(type = CType.VARCHAR)
    @Comment("菜单名")
    @ApiModelProperty(value = "菜单名", required = true)
    private String name;

    // 菜单别名
    @Column
    @Type(type = CType.VARCHAR, vacancy = true)
    @Comment("菜单别名")
    @ApiModelProperty(value = "菜单别名", required = true)
    private String alias;

    // 树路径
    @Column(index = true, alias = "index_sys_menu_path")
    @Type(type = CType.VARCHAR, vacancy = true)
    @Comment("树路径")
    @ApiModelProperty(value = "树路径", required = true)
    private String path;

    // 菜单链接
    @Column
    @Type(type = CType.VARCHAR, vacancy = true)
    @Comment("菜单链接")
    @ApiModelProperty(value = "菜单链接", required = true)
    private String href;

    @Column
    @Type(type = CType.VARCHAR, vacancy = true)
    @Comment("菜单图标")
    @ApiModelProperty(value = "菜单图标", required = true)
    private String icon;

    // 权限标识 与 api的Permission对应就赋予权限
    @Column(index = true, alias = "index_sys_menu_identity")
    @Type(type = CType.VARCHAR)
    @Comment("权限标识")
    @ApiModelProperty(value = "权限标识", required = true)
    private String identity;

    // 数据处理权限（即该菜单下对数据的处理权限）
    @Column
    @Type(type = CType.BOOLEAN)
    @Comment("数据权限，默认false:无")
    @ApiModelProperty(value = "数据处理权限（即该菜单下对数据的处理权限），默认false:无", required = true)
    private Boolean privilege;

    @Column
    @Type(type = CType.BOOLEAN)
    @Comment("是否禁用，默认false:启用")
    @ApiModelProperty(value = "是否禁用，默认false:启用", required = true)
    private Boolean disable;

    @Column
    @Type(type = CType.VARCHAR)
    @Comment("菜单描述")
    @ApiModelProperty(value = "菜单描述", required = true)
    private String description;

    @Column
    @Type(type = CType.BOOLEAN)
    @Comment("有子节点，默认false:无")
    @ApiModelProperty(value = "有子节点，默认false:无", required = true)
    private Boolean hasChildren;

}
