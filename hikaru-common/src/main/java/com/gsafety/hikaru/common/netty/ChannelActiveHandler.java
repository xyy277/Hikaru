package com.gsafety.hikaru.common.netty;

import io.netty.channel.ChannelHandlerContext;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ChannelHandler
 * Author : zhoujiajun
 * Date : 2020/1/20 15:55
 * Version : 1.0
 * Description : 
 ******************************/
public interface ChannelActiveHandler {


    void handle(ChannelHandlerContext ctx);
}
