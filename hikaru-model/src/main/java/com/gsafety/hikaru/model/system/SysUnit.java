package com.gsafety.hikaru.model.system;

import com.gsafety.hikaru.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import savvy.wit.framework.core.base.service.dao.annotation.*;

/******************************* * Copyright (C),2018-2099, ZJJ
 * Title : 部门表
 * File name : SysUnit
 * Author : zhoujiajun
 * Date : 2019/3/20 17:25
 * Version : 1.0
 * Description : 系统组织部门表模型  -  分总子公司/部门/单位/BG/BU/组 - 代码中统称“部门”（不涉及业务，只保证扩展）
 ******************************/
@Table
@Data
@NoArgsConstructor
public class SysUnit extends BaseModel {

    // 主键
    @Id
    @Column
    @Type(type = CType.VARCHAR, width = 32)
    @Comment("部门编号")
    @ApiModelProperty(value = "部门编号", required = true)
    private String id;

    // 上级部门id
    @Column
    @Type(type = CType.VARCHAR, vacancy = true, width = 32)
    @Comment("上级编号")
    @ApiModelProperty(value = "上级编号", required = true)
    private String superiors;

    // 部门名称
    @Column
    @Type(type = CType.VARCHAR, width = 128)
    @Comment("部门名称")
    @ApiModelProperty(value = "部门名称", required = true)
    private String name;

    // 部门描述、口号
    @Column
    @Type(type = CType.VARCHAR, vacancy = true, width = 1024)
    @Comment("部门描述、口号")
    @ApiModelProperty(value = "部门描述、口号", required = true)
    private String slogan;

    // 地址 - 国-省-市-县-街道-门牌号
    @Column
    @Type(type = CType.VARCHAR, vacancy = true)
    @Comment("地址 - 国-省-市-县-街道-门牌号")
    @ApiModelProperty(value = "地址 - 国-省-市-县-街道-门牌号", required = true)
    private String address;

    // 位置 - 栋-楼层-办公室编号
    @Column
    @Type(type = CType.VARCHAR, vacancy = true)
    @Comment("位置 - 栋-楼层-办公室编号")
    @ApiModelProperty(value = "位置 - 栋-楼层-办公室编号", required = true)
    private String location;

    // 电话
    @Column
    @Type(type = CType.VARCHAR, vacancy = true, width = 32)
    @Comment("电话")
    @ApiModelProperty(value = "电话", required = true)
    private String phone;

    // 负责人/领导人/法人 编号
    @Column
    @Type(type = CType.VARCHAR, width = 32)
    @Comment("负责人/领导人/法人")
    @ApiModelProperty(value = "负责人/领导人/法人", required = true)
    private String principal;

    // 邮箱
    @Column(unique = true)
    @Type(type = CType.VARCHAR, vacancy = true)
    @Comment("邮箱")
    @ApiModelProperty(value = "邮箱", required = true)
    private String email;

    // 网址
    @Column
    @Type(type = CType.VARCHAR, vacancy = true)
    @Comment("网址")
    @ApiModelProperty(value = "网址", required = true)
    private String website;

    // 描述信息
    @Column
    @Type(type = CType.TEXT, vacancy = true)
    @Comment("描述信息")
    @ApiModelProperty(value = "描述信息", required = true)
    private String description;

}
