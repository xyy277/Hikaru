package com.gsafety.hikaru.api.test;

import com.gsafety.hikaru.model.test.Monday;
import org.junit.Before;
import org.junit.Test;
import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.base.util.DateUtil;
import savvy.wit.framework.core.base.util.StringUtil;
import savvy.wit.framework.core.pattern.decorate.Counter;
import savvy.wit.framework.core.pattern.factory.CDT;
import savvy.wit.framework.core.pattern.factory.Daos;
import savvy.wit.framework.core.pattern.factory.DbFactory;
import savvy.wit.framework.core.pattern.factory.LogFactory;
import savvy.wit.framework.core.service.BaseService;
import savvy.wit.framework.core.service.impl.BaseServiceImpl;

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

    private BaseService<Monday> baseService = null;

    @Before
    public void before() {
        DbFactory.me().setSource("G:\\GitHub\\hikaru\\hikaru-server\\hikaru-application\\src\\main\\resources\\db.properties");
        baseService = new BaseServiceImpl<>(Daos.get());
//        Daos.get().dropAndCreate(User.class);
    }

    @Test
    public void test() {
//        log.log(baseService.query(Monday.class, CDT.NEW().where("name", "like", "zz", CDT.NEW().page(1, 10))));
        log.log(        baseService.query(Monday.class));
//        final Counter counter = Counter.create();
//        for (int var = 1; var <= 10; var++) {
//            List<Monday> mondays = new ArrayList<>();
//            for (int i = 1; i <= 100; i++) {
//                Monday monday = new Monday();
//                monday.setId(counter.getIndex(1) + "");
//                monday.setOptUser(StringUtil.createCode());
//                monday.setOptTime(DateUtil.getNow());
//                monday.setName(StringUtil.createCode());
//                mondays.add(monday);
//            }
//            try {
//                log.log(Daos.get().insertBath(mondays, Monday.class));
//            }catch (Exception e) {
//                log.error(e);
//            }
//        }
    }
}
