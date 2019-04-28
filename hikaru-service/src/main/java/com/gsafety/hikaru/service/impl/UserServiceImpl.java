package com.gsafety.hikaru.service.impl;

import com.gsafety.hikaru.common.helper.RedisUtil;
import com.gsafety.hikaru.feign.TestFeign;
import com.gsafety.hikaru.model.business.User;
import com.gsafety.hikaru.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import savvy.wit.framework.core.base.service.cdt.Cdt;
import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.base.service.dao.Dao;
import savvy.wit.framework.core.pattern.factory.CDT;
import savvy.wit.framework.core.pattern.factory.LogFactory;
import savvy.wit.framework.core.service.impl.BaseServiceImpl;

import java.util.List;

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
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    private Log log = LogFactory.getLog();

    @Autowired
    private TestFeign testFeign;

//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
//    @Override
//    public Dao dao() {
//        return super.dao();
//    }

    @Autowired
    public UserServiceImpl (Dao dao) {
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

    @Override
    public ResponseEntity<String> testFeign(String num) {
        log.log(testFeign);
        return testFeign.test(num);
    }

    @Override
    public User save(User user) {
        user = insert(user);
        RedisUtil.me().set(user.getId(), user);
//        ApplicationBeanFactory.getBean(RedisUtil.class).set(user.getId(), user);
//        redisTemplate.opsForValue().set(user.getId(), user);
//        log.log("写入缓存：" + user);
        return user;
    }

    @Override
    public void remove(String id) {
        RedisUtil.me().del(id);
        log.log("清除缓存：" + id);
        delete(CDT.where("id", "=", id));
    }

    @Override
    public User findOne(User user) {
        return  fetch(CDT.where("id", "=", user.getId()));

//        User user1 = (User) RedisUtil.me().get(user.getId());
//        log.log("获取缓存：" + user1);
//        try {
//            user = user1 != null ? user1 : select(user.getClass(), CDT.where("id", "=", user.getId()));
//            return user;
//        }finally {
//            if (user1 == null && user != null && user.getId() != null) {
//                RedisUtil.me().set(user.getId(), user);
//                log.log("写入缓存：" + user);
//            }
//        }
    }

    @Override
    public List<User> query(Cdt cdt) {
        List<User> users = null;
        users = super.query(cdt);
        return users;
    }
}


