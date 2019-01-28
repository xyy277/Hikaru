package com.gsafety.hikaru.application.config;

import com.ecwid.consul.v1.ConsulClient;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.discovery.HeartbeatProperties;
import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry;
import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistryAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ConsulConfig
 * Author : zhoujiajun
 * Date : 2019/1/18 11:32
 * Version : 1.0
 * Description : 
 ******************************/
@Configuration
public class ConsulConfig extends ConsulServiceRegistryAutoConfiguration {

    @Override
    public ConsulServiceRegistry consulServiceRegistry(ConsulClient consulClient, ConsulDiscoveryProperties properties, HeartbeatProperties heartbeatProperties) {
        return super.consulServiceRegistry(consulClient, properties, heartbeatProperties);
    }
}
