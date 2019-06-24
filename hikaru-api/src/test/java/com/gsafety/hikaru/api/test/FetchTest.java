package com.gsafety.hikaru.api.test;

import com.gsafety.hikaru.model.enumerate.Ultraviolet;
import com.gsafety.hikaru.model.test.Monday;
import com.gsafety.hikaru.model.test.Sunday;
import org.junit.Before;
import org.junit.Test;
import savvy.wit.framework.core.base.callback.impl.AbstractDaoCallBack;
import savvy.wit.framework.core.base.service.cdt.Cdt;
import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.pattern.factory.CDT;
import savvy.wit.framework.core.pattern.factory.ConfigFactory;
import savvy.wit.framework.core.pattern.factory.Daos;
import savvy.wit.framework.core.pattern.factory.LogFactory;
import savvy.wit.framework.core.pattern.builder.SqlBuilder;
import savvy.wit.framework.core.pattern.proxy.SqlProxy;
import savvy.wit.framework.core.service.Configuration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : FetchTest
 * Author : zhoujiajun
 * Date : 2019/3/22 9:28
 * Version : 1.0
 * Description : 
 ******************************/
public class FetchTest {

    private static Log log = LogFactory.getLog();
    private static final String PROJECT_PATH = System.getProperty("user.dir");

    public static void main(String[] args) {
        SqlProxy proxy = SqlBuilder.ask();
        proxy.param("id", 1)
                .proxy("select * from monday where id = ${id} ${m}" +( 1 == 1 ? "${aaa}" :  "${b2s}"))
                .inject().joint("aaa", "and a = b")
                .joint("aaa", "1233333");
        Monday monday = new Monday();
        monday.setId(UUID.randomUUID().toString().replaceAll("-",""));
        String sql = proxy.request();
        log.log(sql);
        Cdt cdt = CDT.NEW().where("a", "like", monday.getId());
        log.log(cdt.getCondition());
    }

    @Before
    public void before() {
        Configuration configuration = ConfigFactory.me();
        configuration.setSource("db.properties", "./db.properties")                 // 设置dao数据源
                .setProperty("vacancy", "true")                                     // 设置参数vacancy - 插入数据为null时，补“”
                .setProperty("intervalMark", "@$")
                .setEnumClassList("com.gsafety.hikaru.model.enumerate");            // 设置泛型package
//        Daos.get().create(false, Sunday.class, Monday.class);
    }

    @Test
    public void test() {
        insert();
        List list = new ArrayList();
        list.add("12312");
        list.add("vasd");
        list.add(3);
        /**
         * TODO: 自动生成关联sql
         */
        fetch(SqlBuilder.ask().proxy("SELECT  * FROM sunday s_1 LEFT JOIN monday m_2 ON (s_1.monday_id = m_2.id) WHERE s_1.monday_id IN ${ids}")
                .param("ids", new String[]{"123123", "asdasdas"})
                .param("mid", "123123123123")
                .param("asdasd", list).request());
    }

    private void insert() {
        Daos.get().create(Monday.class, Sunday.class);
        Monday monday = new Monday();
        monday.setId("123123");
        monday.setName("ojbk");
        monday.setOptUser("admin");
        try {
            Daos.get().insert(monday);
        } catch (SQLException e) {
            log.error(e);
        }
        Sunday sunday = new Sunday();
        sunday.setTemperature(12.3);
        sunday.setUltraviolet(Ultraviolet.Powerful);
        sunday.setTest(com.gsafety.hikaru.model.enumerate.Test.OK);
        sunday.setTest1(new String[]{"asdasd", "123asfda", "123123"});
        sunday.setMondayId("123");
        List list = new ArrayList();
        list.add("asdasd");
        list.add(123);
        sunday.setTest2(list);
        Map<String, Monday> map = new HashMap();
        map.put("1", monday);
        sunday.setOptUser("admin");
        sunday.setTest3(map);
        try {
            Daos.get().insert(sunday);
        } catch (SQLException e) {
            log.error(e);
        }
    }

    private void fetch(String sql) {
//        Sunday sunday = new Sunday();
//        Daos.get().execute(sql, resultSet -> {
//            sunday.setId(resultSet.getString("s_1.id"));
//            sunday.setMondayId(resultSet.getString("s_1.monday_id"));
//            sunday.monday().setId(resultSet.getString("m_2.id"));
//            sunday.monday().setName(resultSet.getString("m_2.name"));
//            return sunday;
//        });

        List list = Daos.get().execute(sql, new AbstractDaoCallBack<Sunday>(){
            @Override
            public Sunday savvy(ResultSet resultSet) throws Exception {
                Sunday sunday = super.savvy(resultSet);
//                Monday monday = new Monday();
//                monday.setId(resultSet.getString("m_2.id"));
//                monday.setName(resultSet.getString("m_2.name"));
//                sunday.setMonday(monday);
                return sunday;
            }
        });
        log.log(list);
//        log.log(sunday);
    }

}
