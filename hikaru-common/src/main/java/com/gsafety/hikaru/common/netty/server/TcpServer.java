package com.gsafety.hikaru.common.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : TcpServer
 * Author : zhoujiajun
 * Date : 2020/1/16 15:47
 * Version : 1.0
 * Description : 
 ******************************/
public class TcpServer {

    private Logger log = LoggerFactory.getLogger(getClass());
    //服务器运行状态
    private volatile boolean isRunning = false;
    //处理Accept连接事件的线程，这里线程数设置为1即可，netty处理链接事件默认为单线程，过度设置反而浪费cpu资源
    private final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    //处理hadnler的工作线程，其实也就是处理IO读写 。线程数据默认为 CPU 核心数乘以2
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    public void init(int port) throws Exception{
        //创建ServerBootstrap实例
        ServerBootstrap serverBootstrap=new ServerBootstrap();
        //初始化ServerBootstrap的线程组
        serverBootstrap.group(bossGroup,workerGroup);//
        //设置将要被实例化的ServerChannel类
        serverBootstrap.channel(NioServerSocketChannel.class);//
        //在ServerChannelInitializer中初始化ChannelPipeline责任链，并添加到serverBootstrap中
        serverBootstrap.childHandler(new ChannelInitializer(){
            final EventExecutorGroup group = new DefaultEventExecutorGroup(2);
            @Override
            protected void initChannel(Channel channel) throws Exception {
                ChannelPipeline pipeline = channel.pipeline();
                //IdleStateHandler心跳机制,如果超时触发Handle中userEventTrigger()方法
                pipeline.addLast("idleStateHandler",
                        new IdleStateHandler(15, 0, 0, TimeUnit.MINUTES));
                // netty基于分割符的自带解码器，根据提供的分隔符解析报文，这里是0x7e;1024表示单条消息的最大长度，解码器在查找分隔符的时候，达到该长度还没找到的话会抛异常
//        pipeline.addLast(
//                new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer(new byte[] { 0x7e }),
//                        Unpooled.copiedBuffer(new byte[] { 0x7e })));
                //自定义编解码器
                pipeline.addLast(
                        new MessagePacketDecoder(),
                        new MessagePacketEncoder()
                );
                //自定义Hander,可用于处理耗时操作，不阻塞IO处理线程
                pipeline.addLast(group,"BussinessHandler",new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        super.channelRead(ctx, msg);
                    }
                });
            }
        });
        //标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        // 是否启用心跳保活机机制
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        //绑定端口后，开启监听
        ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
        if(channelFuture.isSuccess()){
            System.out.println("TCP服务启动 成功---------------");
        }
    }

    /**
     * 服务启动
     */
    public synchronized void startServer(int port) {
        try {
            this.init(port);
        }catch(Exception ex) {

        }
    }

    /**
     * 服务关闭
     */
    public synchronized void stopServer() {
        if (!this.isRunning) {
            throw new IllegalStateException(this.getName() + " 未启动 .");
        }
        this.isRunning = false;
        try {
            Future<?> future = this.workerGroup.shutdownGracefully().await();
            if (!future.isDone()) {
                log.error("workerGroup 无法正常停止:{}", future.isCancelled());
            }

            future = this.bossGroup.shutdownGracefully().await();
            if (!future.isDone()) {
                log.error("bossGroup 无法正常停止:{}", future.isCancelled());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.log.info("TCP服务已经停止...");
    }

    private String getName() {
        return "TCP-Server";
    }
}