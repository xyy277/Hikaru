package com.gsafety.hikaru.model;

import io.swagger.annotations.ApiModelProperty;
import savvy.wit.framework.core.base.service.dao.annotation.CType;
import savvy.wit.framework.core.base.service.dao.annotation.Column;
import savvy.wit.framework.core.base.service.dao.annotation.Comment;
import savvy.wit.framework.core.base.service.dao.annotation.Type;

import java.io.Serializable;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : BaseModel
 * Author : zhoujiajun
 * Date : 2019/3/5 18:54
 * Version : 1.0
 * Description : 
 ******************************/
public class BaseModel implements Serializable {

    @Column
    @Type(type = CType.VARCHAR, width = 32, vacancy = true)
    @Comment("操作时间")
    @ApiModelProperty(value = "操作时间")
    private String optTime;

    @Column
    @Type(type = CType.VARCHAR, width = 128,  vacancy = true)
    @Comment("操作人")
    @ApiModelProperty(value = "操作人")
    private String optUser;

    public String getOptTime() {
        return optTime;
    }

    public void setOptTime(String optTime) {
        this.optTime = optTime;
    }

    public String getOptUser() {
        return optUser;
    }

    public void setOptUser(String optUser) {
        this.optUser = optUser;
    }

    @Override
    public String toString() {
        return ", optTime='" + optTime + '\'' +
                ", optUser='" + optUser + '\'' ;
    }
}
