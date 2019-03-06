package com.gsafety.hikaru.entity.test;

import com.gsafety.hikaru.model.enumerate.Test;
import com.gsafety.hikaru.model.enumerate.Ultraviolet;
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
 * File name : Sunday
 * Author : zhoujiajun
 * Date : 2019/2/19 9:16
 * Version : 1.0
 * Description : 
 ******************************/
@Entity
@Table(name = "sys_sunday")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sunday {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "temperature")
    private double temperature;


    @Column(name = "ultraviolet")
    private Ultraviolet ultraviolet;

    @Column(name = "test")
    private Test test;


    
}
