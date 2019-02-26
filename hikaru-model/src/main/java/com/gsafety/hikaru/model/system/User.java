package com.gsafety.hikaru.model.system;

import savvy.wit.framework.application.model.BaseModel;
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
@Table
public class User extends BaseModel {


    @Id
    @Column
    @Type(type = CType.VARCHAR)
    @Comment("id")
    @ApiModelProperty(value = "编号", required = true)
    private String id;

    @Column()
    @Type(type = CType.VARCHAR)
    @Comment("姓名")
    @NotNull(message = "${property.null}")
    @ApiModelProperty(value = "姓名", required = true)
    private String name;

    @Column()
    @Type(type = CType.INT, width = 3)
    @Comment("年龄")
    @ApiModelProperty(value = "年龄", required = true)
    private Integer age;

    @Column()
    @Type(type = CType.INT, width = 1)
    @Comment("是否在线，默认0：离线")
    @ApiModelProperty(value = "是否在线，默认0不在线", required = true)
    private int online;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age + '\'' +
                ", online=" + online + '\'' +
                ", optTime='" + super.getOptTime() + '\'' +
                ", optUser='" + super.getOptUser() + '\'' +
                '}';
    }
}
