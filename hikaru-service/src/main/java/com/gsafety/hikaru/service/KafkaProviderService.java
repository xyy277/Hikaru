package com.gsafety.hikaru.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : KafkaConsumerService
 * Author : zhoujiajun
 * Date : 2018/12/17 14:54
 * Version : 1.0
 * Description : 
 ******************************/
@Service
public class KafkaProviderService {


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 推送消息
     * @param topic 主题
     * @param value 推送的数据
     */
    public void sender(String topic, String value) {
        kafkaTemplate.send(topic, value);
    }
}
