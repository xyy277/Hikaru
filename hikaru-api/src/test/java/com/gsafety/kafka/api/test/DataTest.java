package com.gsafety.kafka.api.test;

import com.gsafety.hikaru.model.system.User;
import org.junit.Before;
import org.junit.Test;
import savvy.wit.framework.core.base.service.Log;
import savvy.wit.framework.core.pattern.factory.CDT;
import savvy.wit.framework.core.pattern.factory.Daos;
import savvy.wit.framework.core.pattern.factory.DbFactory;
import savvy.wit.framework.core.pattern.factory.LogFactory;
import savvy.wit.framework.core.service.BaseService;
import savvy.wit.framework.core.service.impl.BaseServiceImpl;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : DataTest
 * Author : zhoujiajun
 * Date : 2019/1/28 11:22
 * Version : 1.0
 * Description : 
 ******************************/
public class DataTest {

    private Log log = LogFactory.getLog();

    private BaseService<User> baseService = null;

    @Before
    public void before() {
        DbFactory.me().setSource("G:\\GitHub\\hikaru\\hikaru-server\\hikaru-application\\src\\main\\resources\\db.properties");
        baseService = new BaseServiceImpl<>(Daos.get());
//        Daos.get().dropAndCreate(User.class);
    }

    @Test
    public void test() {
        log.log(baseService.query(User.class, CDT.NEW().where("name", "like", "zz", CDT.NEW().page(1, 10))));

//        final Counter counter = Counter.create();
//        for (int var = 1; var <= 10; var++) {
//            List<User> users = new ArrayList<>();
//            for (int i = 1; i <= 100; i++) {
//                User user = new User();
//                user.setId(counter.getIndex(1) + "");
//                user.setOptUser(StringUtil.createCode());
//                user.setOptTime(DateUtil.getNow());
//                user.setName(StringUtil.createCode());
//                user.setAge(DateUtil.random(100));
//                user.setOnline(DateUtil.random(2));
//                users.add(user);
//            }
//            try {
//                log.log(Daos.get().insertBath(users, User.class));
//            }catch (Exception e) {
//                log.error(e);
//            }
//        }
    }
}
