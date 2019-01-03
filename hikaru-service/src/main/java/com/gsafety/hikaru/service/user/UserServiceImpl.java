package com.gsafety.hikaru.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import savvy.wit.framework.core.base.interfaces.dao.Dao;
import savvy.wit.framework.core.service.impl.BaseServiceImpl;
import savvy.wit.framework.test.model.User;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : UserServiceImpl
 * Author : zhoujiajun
 * Date : 2018/12/28 10:08
 * Version : 1.0
 * Description : 
 ******************************/
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{

//    @Override
//    public Dao dao() {
//        return super.dao();
//    }

    @Autowired
    private UserServiceImpl (Dao dao) {
        super(dao);
    }

//    @Resource(name = "dao")
//    @Override
//    public void setDao(Dao dao) {
//        super.setDao(dao);
//    }

//    @Override
//    public Dao getDao() {
//        return super.getDao();
//    }
}


