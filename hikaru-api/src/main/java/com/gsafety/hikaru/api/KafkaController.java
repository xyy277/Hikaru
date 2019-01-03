package com.gsafety.hikaru.api;

import com.gsafety.hikaru.service.KafkaProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import savvy.wit.framework.core.base.interfaces.Log;
import savvy.wit.framework.core.base.pool.ThreadPool;
import savvy.wit.framework.core.base.util.DateUtil;
import savvy.wit.framework.core.base.util.JsonUtil;
import savvy.wit.framework.core.base.util.StringUtil;
import savvy.wit.framework.core.pattern.adapter.TimerAdapter;
import savvy.wit.framework.core.pattern.decorate.Counter;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.util.TimerTask;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : KafkaController
 * Author : zhoujiajun
 * Date : 2018/12/19 14:21
 * Version : 1.0
 * Description : 
 ******************************/
@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private Log log = LogFactory.getLog();

    @Autowired
    private KafkaProviderService kafkaProviderService;


    /**
     * 推送消息，由于只需要测试kafka数据存储量的变化趋势
     * 所以仅需不断的推送消息即可
     * @param topic 主题
     * @param amount 主题数
     * @param num 线程数
     * @return
     */
    @RequestMapping("/{topic}/{amount}/{num}")
    public ResponseEntity kafkaSender(@PathVariable String topic, @PathVariable int amount, @PathVariable int num) {
        if (amount * num >= 1000) {
            return new ResponseEntity("too much producer", HttpStatus.PAYLOAD_TOO_LARGE);
        }
        ThreadPool.me().clean();
        for (int t = 0; t < amount; t++) {
            final Counter counter = Counter.create();
            counter.setValue("topic", topic + DateUtil.getNow("yyyyMMdd-") + t);
            log.log("topic: \t" + counter.getValue("topic") + " started");
            for (int var = 0; var < (num > 100 ? 100 : num); var++) { // 最多100个线程
                ThreadPool.me().newThread(() -> {
                    TimerAdapter.me().execute(new TimerTask() {
                        @Override
                        public void run() {
                            kafkaProviderService.sender((String) counter.getValue("topic"), JsonUtil.toJson("message", StringUtil.createCode(77)));
                        }
                    }, 0, 10);
                }, (pool, thread) -> thread.start());
            }
        }
        log.warn(amount + "个 topic: " + topic + DateUtil.getNow("yyyyMMdd-") + num + " 个线程 started \t" + DateUtil.getNow());
        return  new ResponseEntity(amount + "个 topic: " + topic + DateUtil.getNow("yyyyMMdd-") + num + " 个线程 started \t" + DateUtil.getNow(), HttpStatus.OK);
    }

    @RequestMapping("/stop")
    public ResponseEntity stop() {
        int index = 0;
        if (ThreadPool.me().getPoolList() != null) {
            index = ThreadPool.me().getPoolList().size();
            ThreadPool.me().getPoolList().forEach(thread -> {
                thread.interrupt();
            });
        }else
            index = TimerAdapter.me().getTimers().size();
        TimerAdapter.me().cancel();
        log.warn(index + "\t service stop success \t" + DateUtil.getNow());
        return new ResponseEntity(index + "\t service stop success \t" + DateUtil.getNow(), HttpStatus.OK);
    }

}
