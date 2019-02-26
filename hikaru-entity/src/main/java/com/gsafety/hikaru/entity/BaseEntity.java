package com.gsafety.hikaru.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import java.io.Serializable;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : BaseEntity
 * Author : zhoujiajun
 * Date : 2019/2/21 12:43
 * Version : 1.0
 * Description : 
 ******************************/
public class BaseEntity implements Serializable {

    @Column(name = "opt_time")
    @ApiModelProperty(value = "操作时间")
    private String optTime;

    @Column(name = "opt_user")
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
