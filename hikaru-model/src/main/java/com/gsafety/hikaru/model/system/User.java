package com.gsafety.hikaru.model.system;

import com.gsafety.hikaru.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import savvy.wit.framework.core.base.service.dao.annotation.*;

import javax.validation.constraints.NotNull;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : User
 * Author : zhoujiajun
 * Date : 2019/1/3 15:15
 * Version : 1.0
 * Description : 
 ******************************/
// 选择表Engine，默认InnoDB
@Table(engine = Table.Engine.MyiSAM)
public class User extends BaseModel {

    @Id
    @Column
    @Type(type = CType.VARCHAR)
    @Comment("id")
    @ApiModelProperty(value = "编号", required = true)
    private String id;

    // 给name字段创建索引， 默认不创建
    @Column(index = true, alias = "index_user_name")
    @Type(type = CType.VARCHAR)
    @Comment("姓名")
    @NotNull(message = "${property.null}")
    @ApiModelProperty(value = "姓名", required = true)
    private String name;

    @Column(index = true, alias = "index_user_username")
    @Type(type = CType.VARCHAR)
    @Comment("用户名")
    @NotNull(message = "${property.null}")
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

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
    @Comment("是否禁用，默认false:禁用")
    @ApiModelProperty(value = "是否禁用，默认false:禁用", required = true)
    private Boolean disable;

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", age='" + age + '\'' +
                ", online='" + online + '\'' +
                ", disable='" + disable + '\'' +
                ", optTime='" + super.getOptTime() + '\'' +
                ", optUser='" + super.getOptUser() + '\'' +
                '}';
    }
}
