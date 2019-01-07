package com.gsafety.hikaru.model.system;

import com.gsafety.hikaru.model.BaseModel;
import savvy.wit.framework.core.base.interfaces.dao.annotation.*;

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
    private String id;

    @Column()
    @Type(type = CType.VARCHAR)
    @Comment("姓名")
    private String name;

    @Column()
    @Type(type = CType.INT)
    @Comment("年龄")
    private Integer age;

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

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}