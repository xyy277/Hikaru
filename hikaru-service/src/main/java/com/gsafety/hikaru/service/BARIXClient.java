package com.gsafety.hikaru.service;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import savvy.wit.framework.core.base.annotations.Log;
import savvy.wit.framework.core.base.enums.LogType;
import savvy.wit.framework.core.base.global.Storage;
import savvy.wit.framework.core.facility.client.ClientHandler;
import savvy.wit.framework.core.facility.client.pa.BARIXCommand;
import savvy.wit.framework.core.facility.client.pa.PaFacility;
import savvy.wit.framework.core.facility.netty.util.Command;

import javax.annotation.PreDestroy;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : BARIXClient Control
 * File name : BARIXClient
 * Author : zhoujiajun
 * Date : 2020/02/19 17:42
 * Version : 1.0
 * Description : PA设备控制
 * *****************************/
@Service("BARIXClient")
public class BARIXClient implements PaFacility, InitializingBean {

    @Value("${barix.host}")
    private String host;
    @Value("${barix.port}")
    private int port;

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    /**
     * 客户端Bootstrap
     */
    private Bootstrap bootstrap;
    /**
     * 客户端WORKER
     */
    private EventLoopGroup worker;

    @PreDestroy
    public void close(){
        System.out.println("关闭资源");
        worker.shutdownGracefully();
    }

    public void start() {
        try {
            afterPropertiesSet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 远程调用
     * @param ip
     * @param retry
     * @param port
     * @param retries
     */
    public void remoteCall(final ChannelHandler handler, String ip, int retry, int port, int retries){
        try{
            bootstrap.handler(handler);
            System.out.println("connect... ...");
            ChannelFuture channelFuture = bootstrap.connect(ip,port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e){
            retry++;
            if (retry > retries) {
                throw new RuntimeException("Wrong");
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                System.out.println("the "+retries+" times .... trying failure");
                remoteCall(handler, ip,retry,port,retries);
            }
        }

    }

    public void afterPropertiesSet() throws Exception {
        bootstrap = new Bootstrap();
        worker = new NioEventLoopGroup();
        bootstrap.group(worker);
        bootstrap.channel(NioSocketChannel.class);
    }

    public Object play() {
        Storage storage = Storage.NEW();
        remoteCall(new ChannelInitializer<SocketChannel>(){
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                System.out.println("init Channel....");
                ch.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
                ch.pipeline().addLast(new ClientHandler().active(ctx -> {
                    String message = Command.init("BARIX").and(BARIXCommand.PLAY).output();
                    System.out.println("send message: " + message);
                    try {
                        ctx.writeAndFlush(message).sync();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).read((ctx, msg) -> {
                    System.out.println("success");
                    ByteBuf byteBuf = msg.readBytes(msg.readableBytes());
                    System.out.println("response: " + ByteBufUtil.hexDump(byteBuf));
                    String data = byteBuf.toString(StandardCharsets.UTF_8);
                    System.out.println("data: " + data);
                    // 设备服务器响应 返回ask
                    if (Command.OK.equalsIgnoreCase(data) || data.contains(Command.OK)) {
                        storage.set(Command.OK, data);
                        ctx.close();
                    }
                }));
                ch.pipeline().addLast(new ByteArrayEncoder());
                ch.pipeline().addLast(new ChunkedWriteHandler());
            }
        }, this.host, 0, this.port, 3);
        return storage.get(Command.OK);
    }

    public Object play(int count) {
        return null;
    }

    public Object play(long time) {
        return null;
    }

    public Object pause() {
        Storage storage = Storage.NEW();
        remoteCall(new ChannelInitializer<SocketChannel>(){
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                System.out.println("init Channel....");
                ch.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
                ch.pipeline().addLast(new ClientHandler().active(ctx -> {
                    String message = Command.init("BARIX").and("c=2").output();
                    System.out.println("send message: " + message);
                    try {
                        ctx.writeAndFlush(message).sync();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).read((ctx, msg) -> {
                    System.out.println("success");
                    ByteBuf byteBuf = msg.readBytes(msg.readableBytes());
                    System.out.println("response: " + ByteBufUtil.hexDump(byteBuf));
                    String data = byteBuf.toString(StandardCharsets.UTF_8);
                    System.out.println("data: " + data);
                    // 设备服务器响应 返回ask
                    if (Command.OK.equalsIgnoreCase(data) || data.contains(Command.OK)) {
                        storage.set(Command.OK, data);
                        ctx.close();
                    }
                }));
                ch.pipeline().addLast(new ByteArrayEncoder());
                ch.pipeline().addLast(new ChunkedWriteHandler());
            }
        }, this.host, 0, this.port, 3);
        return storage.get(Command.OK);
    }

    public Object setVolLevel(int num) {
        Storage storage = Storage.NEW();
        remoteCall(new ChannelInitializer<SocketChannel>(){
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                System.out.println("init Channel....");
                ch.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
                ch.pipeline().addLast(new ClientHandler().active(ctx -> {
                    String message = Command.init("BARIX").and(BARIXCommand.SET_VOL_LEVEL + num).output();
                    System.out.println("send message: " + message);
                    try {
                        ctx.writeAndFlush(message).sync();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).read((ctx, msg) -> {
                    System.out.println("success");
                    ByteBuf byteBuf = msg.readBytes(msg.readableBytes());
                    System.out.println("response: " + ByteBufUtil.hexDump(byteBuf));
                    String data = byteBuf.toString(StandardCharsets.UTF_8);
                    System.out.println("data: " + data);
                    // 设备服务器响应 返回ask
                    if (Command.OK.equalsIgnoreCase(data) || data.contains(Command.OK)) {
                        storage.set(Command.OK, data);
                        ctx.close();
                    }
                }));
                ch.pipeline().addLast(new ByteArrayEncoder());
                ch.pipeline().addLast(new ChunkedWriteHandler());
            }
        }, this.host, 0, this.port, 3);
        return storage.get(Command.OK);
    }

    @Log(type = LogType.SYSTEM)
    public Object setVolPresent(int num) {
        final Storage storage = Storage.NEW();
        remoteCall(new ChannelInitializer<SocketChannel>(){
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                System.out.println("init Channel....");
                ch.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
                ch.pipeline().addLast(new ClientHandler().active(ctx -> {
                    String message = Command.init("BARIX").and(BARIXCommand.SET_VOL_PERCENT + num).output();
                    System.out.println("send message: " + message);
                    try {
                        ctx.writeAndFlush(message).sync();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).read((ctx, msg) -> {
                    System.out.println("success");
                    ByteBuf byteBuf = msg.readBytes(msg.readableBytes());
                    System.out.println("response: " + ByteBufUtil.hexDump(byteBuf));
                    String data = byteBuf.toString(StandardCharsets.UTF_8);
                    System.out.println("data: " + data);
                    // 设备服务器响应 返回ask
                    if (Command.OK.equalsIgnoreCase(data) || data.contains(Command.OK)) {
                        storage.set(Command.OK, data);
                        ctx.close();
                    }
                }));
                ch.pipeline().addLast(new ByteArrayEncoder());
                ch.pipeline().addLast(new ChunkedWriteHandler());
            }
        }, this.host, 0, this.port, 3);
        return storage.get(Command.OK);
    }

    public Object volUp(int num) {
        return setVolLevel(Integer.parseInt(getVol().toString()) + 1);
    }

    public Object volDown(int num) {
        return setVolLevel(Integer.parseInt(getVol().toString()) - 1);
    }

    public Object getVol() {
        final Map result = new HashMap();
        remoteCall(new ChannelInitializer<SocketChannel>(){
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                System.out.println("正在连接....");
                ch.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
                ch.pipeline().addLast(new ClientHandler().active(ctx -> {
                    String message = Command.init("BARIX").and(BARIXCommand.GET_VOL).output();
                    try {
                        ctx.writeAndFlush(message).sync();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).read((ctx, msg) -> {
                    System.out.println("读取客户端通道信息");
                    ByteBuf byteBuf = msg.readBytes(msg.readableBytes());
                    System.out.println("客户端收到的服务器信息：" + ByteBufUtil.hexDump(byteBuf));
                    String data = byteBuf.toString(StandardCharsets.UTF_8);
                    System.out.println("数据包为：" + data);
                    result.put("volume", data);
                    ctx.close();
                }));
                ch.pipeline().addLast(new ByteArrayEncoder());
                ch.pipeline().addLast(new ChunkedWriteHandler());
            }
        }, this.host, 0, this.port, 3);
        return result.get("volume");
    }

    public Object deviceState() {
        return null;
    }

}
