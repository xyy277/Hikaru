package com.gsafety.hikaru.entity.system;

import com.gsafety.hikaru.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : User
 * Author : zhoujiajun
 * Date : 2019/1/3 15:14
 * Version : 1.0
 * Description : 
 ******************************/
@Entity
@Table(name = "sys_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @Column(name = "id")
    @ApiModelProperty(value = "编号", required = true)
    private String id;

    @Column(name = "name")
    @ApiModelProperty(value = "姓名", required = true)
    private String name;

    @Column(name = "age")
    @ApiModelProperty(value = "年龄", required = true)
    private Integer age;

    @Column(name = "online")
    @ApiModelProperty(value = "是否在线，默认0不在线", required = true)
    private int online;
}

