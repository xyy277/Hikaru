package zjj;

import org.junit.Before;
import savvy.wit.framework.core.base.service.cdt.Cdt;
import savvy.wit.framework.core.base.service.dao.Order;
import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.base.util.JsonUtil;
import savvy.wit.framework.core.pattern.decorate.Counter;
import savvy.wit.framework.core.pattern.factory.CDT;
import savvy.wit.framework.core.pattern.factory.ConfigFactory;
import savvy.wit.framework.core.pattern.factory.Daos;
import savvy.wit.framework.core.pattern.factory.LogFactory;
import savvy.wit.framework.core.service.BaseService;
import savvy.wit.framework.core.service.impl.BaseServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : JPanelTest
 * Author : zhoujiajun
 * Date : 2018/10/16 15:28
 * Version : 1.0
 * Description : 
 ******************************/
public class CdtTest {

    private Log log = LogFactory.getLog();

    private Cdt cdt = null;
    @Before
    public void before() {
        cdt = CDT.NEW();
        cdt.where("name", "like", "zz");
        cdt.and("age", ">" , 30);
        cdt.and("id", ">" , 300);
        cdt.order("age", Order.ASC).order("id", Order.DESC).asc("name");
    }


    @org.junit.Test
    public void test() {
        ConfigFactory.me().setSource(System.getProperty("user.dir") + "\\hikaru-core\\src\\main\\resources\\properties\\db.properties");

        BaseService baseService = new BaseServiceImpl(Daos.get());
//        log.log(baseService.query(User.class, cdt));



        log.log(cdt.getCondition());
        List<Map<String, Object>> lists = new ArrayList<>();
        try {
//            Daos.get().execute("select * from user" + cdt.getCondition() , resultSet -> {
//                String [] strings = new String[6];
//                for (int i = 0 ; i < strings.length; i++) {
//                    strings[i] = resultSet.getString(i+1);
//                }
//                lists.add(strings);
//                return lists;
//            });
            lists = Daos.get().query("select * from user " + cdt.getCondition());
        }catch (Exception e) {
            log.error(e);
        }
        Counter counter = Counter.create();
        log.log(lists);
        log.log(()-> {
            counter.setValue(Daos.get().fetch("select * from user " + cdt.getCondition()));
            log.log(counter.getValue());
        });
        String json = JsonUtil.map2Json(counter.getValue());
        log.log(json);
    }
}
