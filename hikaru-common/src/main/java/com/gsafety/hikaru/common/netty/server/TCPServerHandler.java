package com.gsafety.hikaru.common.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : TCPServerHandler
 * Author : zhoujiajun
 * Date : 2020/1/16 15:51
 * Version : 1.0
 * Description : 
 ******************************/
public class TCPServerHandler extends ChannelInboundHandlerAdapter {
    public TCPServerHandler() {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object source) throws Exception {
        // 拿到传过来的msg数据，开始处理
        ByteBuf recvmg = (ByteBuf) source;// 转化为ByteBuf
        ctx.writeAndFlush(recvmg);// 收到及发送，这里如果没有writeAndFlush，上面声明的ByteBuf需要ReferenceCountUtil.release主动释放

    }
}
