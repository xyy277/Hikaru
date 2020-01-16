package com.gsafety.hikaru.common.netty.client;

import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : EchoClientHandler
 * Author : zhoujiajun
 * Date : 2020/1/16 19:11
 * Version : 1.0
 * Description : 
 ******************************/
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private CountDownLatch lathc;
    private String result;//服务端返回的结果

    public EchoClientHandler(CountDownLatch lathc) {
        this.lathc = lathc;
    }

    /**
     * 向服务端发送数据
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端与服务端通道-开启：" + ctx.channel().localAddress() + "channelActive");

        String sendInfo = "Hello 这里是客户端  你好啊！";
        System.out.println("客户端准备发送的数据包：" + sendInfo);
        ctx.writeAndFlush(Unpooled.copiedBuffer(sendInfo, CharsetUtil.UTF_8)); // 必须有flush

    }

    /**
     * channelInactive
     * <p>
     * channel 通道 Inactive 不活跃的
     * <p>
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     */
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端与服务端通道-关闭：" + ctx.channel().localAddress() + "channelInactive");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("读取客户端通道信息..");
        ByteBuf buf = msg.readBytes(msg.readableBytes());
        System.out.println(
                "客户端接收到的服务端信息:" + ByteBufUtil.hexDump(buf) + "; 数据包为:" + buf.toString(Charset.forName("utf-8")));
        result = buf.toString(Charset.forName("utf-8"));
        lathc.countDown();//消息收取完毕后释放同步锁 计数器减去1
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        System.out.println("异常退出:" + cause.getMessage());
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}