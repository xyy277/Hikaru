package zjj;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import savvy.wit.framework.core.base.interfaces.Cdt;
import savvy.wit.framework.core.base.interfaces.Log;
import savvy.wit.framework.core.base.interfaces.dao.Dao;
import savvy.wit.framework.core.base.util.StringUtil;
import savvy.wit.framework.core.pattern.factory.CDT;
import savvy.wit.framework.core.pattern.factory.Daos;
import savvy.wit.framework.core.pattern.factory.LogFactory;
import savvy.wit.framework.core.service.BaseService;
import savvy.wit.framework.core.service.impl.BaseServiceImpl;
import savvy.wit.framework.test.model.User;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ServiceTest
 * Author : zhoujiajun
 * Date : 2018/7/7 23:30
 * Version : 1.0
 * Description : 
 ******************************/
public class ServiceTest {
    private Log log = LogFactory.getLog();

    BaseService<User> baseService;

    @Before
    public void init() {
        baseService = new BaseServiceImpl<>(Daos.get());
        log.log( () -> {
            Daos.get().create(User.class);
        });
    }


    @Test
    public void test() {
        List<User> userList = new ArrayList<>();
        for (int var = 0; var < 100; var++) {
            User user = new User();
            user.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            user.setName(StringUtil.createCode(10));
            user.setUsername(StringUtil.createCode(20));
            user.setPassword(StringUtil.createCode(20));
            userList.add(user);
        }
//        baseService.insertBath(userList);

        List<User> users = baseService.query(User.class, CDT.where("name", "=", "zhoujiajun"));
        log.log(users);
    }
}
