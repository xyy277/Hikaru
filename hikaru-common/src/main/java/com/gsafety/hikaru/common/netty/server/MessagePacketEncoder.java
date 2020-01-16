package com.gsafety.hikaru.common.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : MessagePacketEncoder
 * Author : zhoujiajun
 * Date : 2020/1/16 15:50
 * Version : 1.0
 * Description : 
 ******************************/
public class MessagePacketEncoder extends MessageToByteEncoder<Object> {
    public MessagePacketEncoder()
    {
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception
    {
        try {
            //在这之前可以实现编码工作。
            out.writeBytes((byte[])msg);
        }finally {

        }
    }
}