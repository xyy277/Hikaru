package com.gsafety.hikaru.common.middleware.websocket.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : WebSocketConfig
 * Author : zhoujiajun
 * Date : 2019/2/25 17:18
 * Version : 1.0
 * Description : 
 ******************************/
@SpringBootConfiguration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
