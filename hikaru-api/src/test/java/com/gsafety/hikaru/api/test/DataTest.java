package com.gsafety.hikaru.api.test;

import com.gsafety.hikaru.model.system.User;
import org.junit.Before;
import org.junit.Test;
import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.base.util.DateUtil;
import savvy.wit.framework.core.base.util.StringUtil;
import savvy.wit.framework.core.pattern.decorate.Counter;
import savvy.wit.framework.core.pattern.factory.Daos;
import savvy.wit.framework.core.pattern.factory.DbFactory;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.util.ArrayList;
import java.util.List;

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

    @Before
    public void before() {
        DbFactory.me().setSource("G:\\GitHub\\hikaru\\hikaru-server\\hikaru-application\\src\\main\\resources\\db.properties");
    }

    @Test
    public void test() {
        Daos.get().create(User.class);
        final Counter counter = Counter.create();
        for (int var = 1; var <= 100; var++) {
////            List<Monday> mondays = new ArrayList<>();
////            for (int i = 1; i <= 100; i++) {
////                Monday monday = new Monday();
////                monday.setId(counter.getIndex(1) + "");
////                monday.setOptUser(StringUtil.createCode());
////                monday.setOptTime(DateUtil.getNow());
////                monday.setName(StringUtil.createCode());
////                mondays.add(monday);
////            }
            List<User> users = new ArrayList<>();
            for (int i =1; i <= 10000; i++) {
                User user = new User();
                user.setName(StringUtil.createCode());
                user.setAge(DateUtil.random(120));
                user.setOnline(DateUtil.random(2));
                user.setDisable(DateUtil.random(2) > 0 ? true : false);
                user.setOptTime(DateUtil.getNow());
                user.setOptUser(StringUtil.createCode());
                users.add(user);
            }
            try {
                log.log(Daos.get().insertBath(users, User.class));
//                log.log(Daos.get().insertBath(mondays, Monday.class));
            }catch (Exception e) {
                log.error(e);
            }

        }
    }
}
