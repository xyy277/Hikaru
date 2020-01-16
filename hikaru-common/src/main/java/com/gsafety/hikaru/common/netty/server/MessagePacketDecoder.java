package com.gsafety.hikaru.common.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : MessagePacketDecoder
 * Author : zhoujiajun
 * Date : 2020/1/16 15:50
 * Version : 1.0
 * Description : 
 ******************************/
public class MessagePacketDecoder  extends ByteToMessageDecoder {

    public MessagePacketDecoder() throws Exception
    {
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception
    {
        try {
            if (buffer.readableBytes() > 0) {
                // 待处理的消息包
                byte[] bytesReady = new byte[buffer.readableBytes()];
                buffer.readBytes(bytesReady);
                //这之间可以进行报文的解析处理
                out.add(bytesReady );
            }
        }finally {

        }
    }


}
