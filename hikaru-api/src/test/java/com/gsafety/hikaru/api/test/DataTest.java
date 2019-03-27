package com.gsafety.hikaru.api.test;

import com.gsafety.hikaru.model.enumerate.Ultraviolet;
import com.gsafety.hikaru.model.system.SysUser;
import com.gsafety.hikaru.model.test.Monday;
import com.gsafety.hikaru.model.test.Sunday;
import org.junit.Before;
import org.junit.Test;
import savvy.wit.framework.core.base.pool.ThreadPool;
import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.base.util.DateUtil;
import savvy.wit.framework.core.base.util.StringUtil;
import savvy.wit.framework.core.pattern.adapter.TimerAdapter;
import savvy.wit.framework.core.pattern.decorate.Counter;
import savvy.wit.framework.core.pattern.factory.Daos;
import savvy.wit.framework.core.pattern.factory.ConfigFactory;
import savvy.wit.framework.core.pattern.factory.Files;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.sql.SQLException;
import java.util.*;

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
    private static final String PROJECT_PATH = System.getProperty("user.dir");
    public static void main(String[] args) {
        ConfigFactory.me().setSource(PROJECT_PATH + "\\hikaru-application\\src\\main\\resources\\db.properties")
                .setEnumClassList("com.gsafety.hikaru.model.enumerate");         // 设置泛型package
        for (int var = 1; var <= 100; var++) {
            ThreadPool.me().newThread(() -> {
                TimerAdapter.me().execute(new TimerTask() {
                    @Override
                    public void run() {
                        List<SysUser> users = new ArrayList<>();
                        for (int i =1; i <= 100; i++) {
                            SysUser user = new SysUser();
                            user.setName(StringUtil.createCode());
                            user.setUsername(StringUtil.createCode());
                            user.setPassword(StringUtil.createCode());
                            user.setAge(DateUtil.random(120));
                            user.setOnline(DateUtil.random(2));
                            user.setDisable(DateUtil.random(2) > 0 ? true : false);
                            user.setOptTime(DateUtil.getNow());
                            user.setOptUser(StringUtil.createCode());
                            users.add(user);
                        }
                        try {
                            log.log(Daos.acquire().insertBath(users, SysUser.class));
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
        ConfigFactory.me().setSource(PROJECT_PATH.substring(0, PROJECT_PATH.lastIndexOf("\\")) + "\\hikaru-application\\src\\main\\resources\\db.properties")
                .setSource(PROJECT_PATH.substring(0, PROJECT_PATH.lastIndexOf("\\")) + "\\hikaru-application\\src\\main\\resources\\test.properties")
                .setEnumClassList("com.gsafety.hikaru.model.enumerate");// 设置泛型package
//                .setProperty("vacancy", "true");
    }

    @Test
    public void test() {
        insert();
        fetch();
    }
    private void fetch() {
        try {
            Sunday sunday = (Sunday) Daos.get().fetch(null, Sunday.class);
            log.log(sunday);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void insert () {
        Daos.get().dropAndCreate(Sunday.class);
        try {
            Sunday sunday = new Sunday();
            sunday.setTemperature(12.3);
            sunday.setUltraviolet(Ultraviolet.Powerful);
            sunday.setTest(com.gsafety.hikaru.model.enumerate.Test.OK);
            sunday.setTest1(new String[]{"asdasd", "123asfda", "123123"});
            List list = new ArrayList();
            list.add("asdasd");
            list.add(123);
            sunday.setTest2(list);
            Map<String, Monday> map = new HashMap();
            Monday monday = new Monday();
            monday.setName("KSDasd");
            monday.setId("123fsa");
            map.put("asdasd", monday);
            monday.setName("摄入");
            monday.setId("23412");
            map.put("123123", monday);
            sunday.setTest3(map);
            Daos.get().insert(sunday);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
