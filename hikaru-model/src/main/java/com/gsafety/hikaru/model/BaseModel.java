package com.gsafety.hikaru.model;

import savvy.wit.framework.core.base.interfaces.dao.annotation.CType;
import savvy.wit.framework.core.base.interfaces.dao.annotation.Column;
import savvy.wit.framework.core.base.interfaces.dao.annotation.Comment;
import savvy.wit.framework.core.base.interfaces.dao.annotation.Type;

import java.io.Serializable;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : BaseModel
 * Author : zhoujiajun
 * Date : 2019/1/3 15:16
 * Version : 1.0
 * Description : 
 ******************************/
public class BaseModel implements Serializable {

    @Column()
    @Type(type = CType.VARCHAR)
    @Comment("操作时间")
    private String optTime;

    @Column()
    @Type(type = CType.VARCHAR)
    @Comment("操作人")
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
}