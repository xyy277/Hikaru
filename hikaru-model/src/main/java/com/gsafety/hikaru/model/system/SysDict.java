package com.gsafety.hikaru.model.system;

import com.gsafety.hikaru.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import savvy.wit.framework.core.base.service.dao.annotation.*;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 数据字典表
 * File name : SysDict
 * Author : zhoujiajun
 * Date : 2019/3/21 10:28
 * Version : 1.0
 * Description : 
 ******************************/
@Table
@Data
@NoArgsConstructor
public class SysDict extends BaseModel {

    @Id
    @Column
    @Type(type = CType.VARCHAR, width = 32)
    @Comment("id")
    @ApiModelProperty(value = "id", required = true)
    private String id;

    @Column
    @Type(type = CType.VARCHAR, vacancy = true, width = 32)
    @Comment("上级编号")
    @ApiModelProperty(value = "上级编号", required = true)
    private String superiors;

    @Column(unique = true)
    @Type(type = CType.VARCHAR)
    @Comment("唯一标识")
    @ApiModelProperty(value = "唯一标识", required = true)
    private String identity;

    @Column
    @Type(type = CType.VARCHAR)
    @Comment("树路径")
    @ApiModelProperty(value = "树路径", required = true)
    private String path;

    @Column
    @Type(type = CType.VARCHAR)
    @Comment("名称")
    @ApiModelProperty(value = "名称", required = true)
    private String name;

    @Column
    @Type(type = CType.VARCHAR)
    @Comment("描述")
    @ApiModelProperty(value = "描述", required = true)
    private String description;

    @Column
    @Type(type = CType.BOOLEAN)
    @Comment("有子节点")
    @ApiModelProperty(value = "有子节点", required = true)
    private Boolean hasChildren;

}
