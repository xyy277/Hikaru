package com.gsafety.hikaru.model.system;

import com.gsafety.hikaru.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import savvy.wit.framework.core.base.service.dao.annotation.*;

import javax.validation.constraints.NotNull;

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
@Table(engine = Table.Engine.MyiSAM)
public class Sys_User extends BaseModel {

    @Id(auto = true, step = 3)
    @Column
    @Type(type = CType.INT, width = 11)
    @Comment("id")
    @ApiModelProperty(value = "编号", required = true)
    private int id;

    // 给name字段创建索引， 默认不创建
    @Column
    @Type(type = CType.VARCHAR)
    @Comment("姓名")
    @NotNull(message = "${property.null}")
    @ApiModelProperty(value = "姓名", required = true)
    private String name;

    @Column
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
    @Comment("是否禁用，默认false:禁用")
    @ApiModelProperty(value = "是否禁用，默认false:禁用", required = true)
    private Boolean disable;

    private String uapToken;

    public Sys_User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getUapToken() {
        return uapToken;
    }

    public void setUapToken(String uapToken) {
        this.uapToken = uapToken;
    }

    @Override
    public String toString() {
        return "Sys_User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", online=" + online +
                ", disable=" + disable +
                ", uapToken='" + uapToken + '\'' +
                "} " + super.toString();
    }
}
