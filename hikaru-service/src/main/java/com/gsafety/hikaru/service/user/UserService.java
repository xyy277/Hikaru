package com.gsafety.hikaru.service.user;

import com.gsafety.hikaru.model.system.User;
import org.springframework.http.ResponseEntity;
import savvy.wit.framework.core.base.service.cdt.Cdt;
import savvy.wit.framework.core.service.BaseService;

import java.util.List;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : UserService
 * Author : zhoujiajun
 * Date : 2018/12/28 10:07
 * Version : 1.0
 * Description : 
 ******************************/
public interface UserService extends BaseService<User> {


    ResponseEntity<String> testFeign(String num);

    User save(User user);

    void remove(String id);

    User findOne(User user);

    List<User> query(Cdt cdt);
}
