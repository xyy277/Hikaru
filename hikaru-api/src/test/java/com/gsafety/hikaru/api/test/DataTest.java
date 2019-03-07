package com.gsafety.hikaru.api.test;

import com.gsafety.hikaru.model.system.User;
import org.junit.Before;
import org.junit.Test;
import savvy.wit.framework.core.base.pool.ThreadPool;
import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.base.util.DateUtil;
import savvy.wit.framework.core.base.util.StringUtil;
import savvy.wit.framework.core.pattern.adapter.TimerAdapter;
import savvy.wit.framework.core.pattern.decorate.Counter;
import savvy.wit.framework.core.pattern.factory.Daos;
import savvy.wit.framework.core.pattern.factory.DbFactory;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

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

    private static Log log = LogFactory.getLog();

    public static void main(String[] args) {
        DbFactory.me().setSource("G:\\GitHub\\hikaru\\hikaru-server\\hikaru-application\\src\\main\\resources\\db.properties");
        for (int var = 1; var <= 100; var++) {
            ThreadPool.me().newThread(() -> {
                TimerAdapter.me().execute(new TimerTask() {
                    @Override
                    public void run() {
                        List<User> users = new ArrayList<>();
                        for (int i =1; i <= 100; i++) {
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
                            log.log(Daos.acquire().insertBath(users, User.class));
                        }catch (Exception e) {
                            log.error(e);
                        }
                    }
                }, 0 , 1000);
            },(pool, thread) -> thread.start());
        }
    }

    @Before
    public void before() {
        DbFactory.me().setSource("G:\\GitHub\\hikaru\\hikaru-server\\hikaru-application\\src\\main\\resources\\db.properties");
    }

    @Test
    public void test() {
        final Counter counter = Counter.create();
        Daos.get().dropAndCreate(User.class);
    }
}
