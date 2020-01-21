package com.gsafety.hikaru.common.netty.client;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import savvy.wit.framework.core.base.util.HexStringUtils;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : EchoClient
 * Author : zhoujiajun
 * Date : 2020/1/16 19:10
 * Version : 1.0
 * Description : 
 ******************************/
public class EchoClient {
    private final String host;
    private final int port;

    public EchoClient() {
        this(0);
    }

    public EchoClient(int port) {
        this("localhost", port);
    }

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        String result = "";
        CountDownLatch lathc = new CountDownLatch(1);
        EchoClientHandler handle = new EchoClientHandler(lathc);
        try {
            Bootstrap b = new Bootstrap();
            b.group(group) // 注册线程池
                    .channel(NioSocketChannel.class) // 使用NioSocketChannel来作为连接用的channel类
                    .remoteAddress(new InetSocketAddress(this.host, this.port)) // 绑定连接端口和host信息
                    .handler(new ChannelInitializer<SocketChannel>() { // 绑定连接初始化器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("正在连接中...");
                            ch.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
                            ch.pipeline().addLast(handle.setHandler(ctx -> {
                                String sendInfo = "c=40\r";///0x0d
                                sendInfo = HexStringUtils.bytesToHex(sendInfo.getBytes());
                                System.out.println(sendInfo);
                                ctx.writeAndFlush(sendInfo);
                            }));

                            ch.pipeline().addLast(new ByteArrayEncoder());
                            ch.pipeline().addLast(new ChunkedWriteHandler());

                        }
                    });
            // System.out.println("服务端连接成功..");

            ChannelFuture cf = b.connect().sync(); // 异步连接服务器
            System.out.println("服务端连接成功..."); // 连接完成

            lathc.await();//开启等待会等待服务器返回结果之后再执行下面的代码
            System.out.println("服务器返回 1:" + handle.getResult());
            result = handle.getResult();

            cf.channel().closeFuture().sync(); // 异步等待关闭连接channel
            System.out.println("连接已关闭.."); // 关闭完成

        } finally {
            group.shutdownGracefully().sync(); // 释放线程池资源
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        EchoClient clientrequest = new EchoClient("122.11.205.255", 12302);
        clientrequest.start();

    }

}