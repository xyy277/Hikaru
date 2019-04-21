package com.gsafety.hikaru.service;

import com.gsafety.hikaru.model.test.Sunday;
import org.springframework.stereotype.Service;
import savvy.wit.framework.core.base.service.dao.annotation.Sql;
import savvy.wit.framework.core.service.BaseService;

import java.util.List;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : SundayService
 * Author : zhoujiajun
 * Date : 2019/2/25 9:22
 * Version : 1.0
 * Description : 
 ******************************/
public interface SundayService extends BaseService<Sunday> {

    @Sql("SELECT * FROM user u LEFT JOIN sunday s ON u.id = s.id where u.name in ?1 and s.name like ?3 and u.name = ?2")
    List<Sunday> select(String[] var1, String var2, String var3);

}
