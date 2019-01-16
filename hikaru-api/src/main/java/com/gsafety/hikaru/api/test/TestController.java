package com.gsafety.hikaru.api.test;


import com.gsafety.hikaru.model.system.User;
import com.gsafety.hikaru.service.KafkaProviderService;
import com.gsafety.hikaru.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import savvy.wit.framework.core.base.interfaces.Log;
import savvy.wit.framework.core.base.pool.ThreadPool;
import savvy.wit.framework.core.base.util.DateUtil;
import savvy.wit.framework.core.base.util.StringUtil;
import savvy.wit.framework.core.pattern.adapter.TimerAdapter;
import savvy.wit.framework.core.pattern.factory.CDT;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.util.List;
import java.util.TimerTask;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : TestController
 * Author : zhoujiajun
 * Date : 2018/12/17 10:49
 * Version : 1.0
 * Description : 
 ******************************/
@RestController
@RequestMapping("/test")
public class TestController {

    private Log log = LogFactory.getLog();

    @Autowired
    private KafkaProviderService kafkaProviderService;

    @Autowired
    private UserService userService;

    @RequestMapping("/kafka/{num}")
    public String kafkaSender(@PathVariable int num) {
        ThreadPool.me().clean();
        for (int var = 0; var < (num > 200 ? 200 : num); var++) {
            ThreadPool.me().newThread(() -> {
                TimerAdapter.me().execute(new TimerTask() {
                    @Override
                    public void run() {
                        kafkaProviderService.sender("test", StringUtil.createCode(77));
                    }
                }, 2000, 100);
            }, (pool, thread) -> thread.start());
        }
        log.warn(ThreadPool.me().getPoolList().size() + " started \t" + DateUtil.getNow());
        return ThreadPool.me().getPoolList().size() + " started \t" + DateUtil.getNow();
    }

    @RequestMapping("/kafka/stop")
    public String stop() {
//        log.log(ThreadPool.me().getPoolList());
        int index = ThreadPool.me().getPoolList().size();
        ThreadPool.me().getPoolList().forEach(thread -> {
            log.warn(thread.getName() + " \tstopped");
            thread.interrupt();
        });
        TimerAdapter.me().cancel();
        log.warn(index + "\t service stop success \t" + DateUtil.getNow());
        return index + "\t service stop success \t" + DateUtil.getNow();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<User>> test() {
        List<User> users = null;
//        users = userService.query(User.class, CDT.where("name", "like", "%zhou%"));
        users = userService.query(CDT.where("name", "like", "%zhou%"));
        return new ResponseEntity(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ResponseEntity<User> add() {
        User user = new User();
        user.setName("zhoujiajun");
        user.setAge(DateUtil.random(100));
        User user1 = userService.save(user);
        return new ResponseEntity(user1, HttpStatus.OK);
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> remove(@PathVariable String id) {
        userService.remove(id);
        return new ResponseEntity(id, HttpStatus.OK);
    }

    @RequestMapping(value = "/fetch/{id}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> fetch(@PathVariable String id) {
        User user = new User();
        user.setId(id);
        return new ResponseEntity(userService.findOne(user), HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public ResponseEntity<Boolean> update() {
        User user = new User();
        user.setName("ZJJ");
        user.setAge(0);
        return new ResponseEntity(userService.update(user, CDT.where("name", "=", "zhoujiajun")), HttpStatus.OK);
    }
}
