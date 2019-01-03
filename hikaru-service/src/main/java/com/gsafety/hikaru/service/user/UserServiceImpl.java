package com.gsafety.hikaru.service.user;

import com.gsafety.hikaru.common.application.ApplicationBeanFactory;
import com.gsafety.hikaru.common.redis.util.RedisUtil;
import com.gsafety.hikaru.model.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import savvy.wit.framework.core.base.interfaces.Log;
import savvy.wit.framework.core.base.interfaces.dao.Dao;
import savvy.wit.framework.core.pattern.factory.CDT;
import savvy.wit.framework.core.pattern.factory.LogFactory;
import savvy.wit.framework.core.service.impl.BaseServiceImpl;

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

    private Log log = LogFactory.getLog();

//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
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


    @Override
    public User save(User user) {
        user = insert(user);
        RedisUtil.me().set(user.getId(), user);
//        ApplicationBeanFactory.getBean(RedisUtil.class).set(user.getId(), user);
//        redisTemplate.opsForValue().set(user.getId(), user);
        log.log("缓存：" + user);
        return user;
    }

    @Override
    public void remove(String id) {
        RedisUtil.me().del(id);
        log.log("清除缓存：" + id);
        delete(User.class, CDT.where("id", "=", id));
    }

    @Override
    public User findOne(User user) {
        User user1 = (User) RedisUtil.me().get(user.getId());
        log.log("获取缓存：" + user1);
        return user1 != null ? user1 : select(user.getClass(), CDT.where("id", "=", user.getId()));
    }
}


