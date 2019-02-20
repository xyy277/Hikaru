package com.gsafety.hikaru.service;

import org.springframework.stereotype.Service;
import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.pattern.factory.LogFactory;


/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : KafkaConsumerService
 * Author : zhoujiajun
 * Date : 2018/12/17 14:57
 * Version : 1.0
 * Description : 
 ******************************/
@Service
public class KafkaConsumerService extends Thread {

    private Log log = LogFactory.getLog();

    @Override
    public void run() {
        super.run();
    }

//    @KafkaListener(topics = {KafkaTopic.TOPIC_0, KafkaTopic.TOPIC_1, KafkaTopic.TOPIC_2, KafkaTopic.TOPIC_3,
//    KafkaTopic.TOPIC_4, KafkaTopic.TOPIC_5, KafkaTopic.TOPIC_6, KafkaTopic.TOPIC_7,KafkaTopic.SAVVY_0 })
//    @KafkaListener(topics = "test")
    public void consumer(String message) {
        log.log("consumer message: " + message);
    }

}
