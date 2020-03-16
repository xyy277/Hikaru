package com.gsafety.hikaru.service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringEncoder;
import savvy.wit.framework.core.facility.server.Server;
import savvy.wit.framework.core.facility.server.ServerHandler;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class BARIXServer extends Server {

    public static String  voiceValue = "0";

    private SocketClient socketClient = SocketClient.getInstance();

    public BARIXServer(int port) {
        super(port);
    }

    public BARIXServer(String host, int port) {
        super(host, port);
    }

    public VLCControlClient vlc = new VLCControlClient();
    public SocketClient  sc = new SocketClient();

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        EventLoopGroup group = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            // 绑定线程池
            bootstrap.group(group, bossGroup)
                    // 指定使用的channel
                    .channel(NioServerSocketChannel.class)
                    // 绑定监听端口
                    .localAddress(this.getPort())
                    // 绑定客户端连接时触发操作
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("客户端连接IP:Port - " + ch.localAddress().getHostName() + ":" + ch.localAddress().getPort());
                            ch.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
                            ch.pipeline().addLast(new ServerHandler().complete(ctx -> {

                            }).read((ctx, msg) -> {
                               String res =  getString(msg);
                                System.out.println("收到客户端数据：" + res);
                                String reply = "";
                                switch(res) {
                                    case "c=1":
                                        socketClient.start();
                                        reply = "OK";
                                        break;
                                    case "c=2":
                                        socketClient.close();
                                        reply = "OK";
                                        break;
                                    case "c=40":  // 降低音量
                                        if(Integer.valueOf(voiceValue) > 0 ){
                                            socketClient.volDown();
                                            int value = Integer.valueOf(voiceValue) -1;
                                            voiceValue = String.valueOf(value);
                                            reply =  "OK";
                                        }else{
                                            reply =  "ERROR";
                                        }
                                        break;
                                    case "c=8":  // 静音
                                        socketClient.setVoiceValue(0);
                                        System.out.println("静音");
                                        reply = voiceValue;  // 静音前的音量
                                        voiceValue = "0";  // 静音处理
                                        break;
                                    case "c=60":  //
                                        System.out.println("B级！");
                                        reply =  "OK";
                                        break;
                                    case "c=61":
                                        System.out.println("C级！");
                                        reply =  "OK";
                                        break;
                                    case "c=77":   //循环播放
//                                        vlc.repeat();
                                        System.out.println("D级！");
                                        reply =  "OK";
                                        break;
                                    case "v=?":
                                        System.out.println("获取音量值");
//                                        socketClient.getVoiceValue();
                                        reply = voiceValue;
                                        break;
                                    case "v=0":
                                        socketClient.setVoiceValue(0);
                                        System.out.println("设置最小音量值");
                                        voiceValue = "0";
                                        reply =  "OK";
                                        break;
                                    case "v=20":
                                        socketClient.setVoiceValue(20);
                                        System.out.println("设置最大音量值");
                                        voiceValue = "20";
                                        reply =  "OK";
                                        break;
                                    default:
                                        System.out.println("default 设置音量");
                                        voiceValue = res.substring(2);
                                        System.out.println( voiceValue);
                                        socketClient.setVoiceValue(Integer.valueOf(voiceValue));
                                        reply =  "OK";
                                        break;
                                }

                                ByteBuf resp = Unpooled.copiedBuffer(reply.getBytes());
                                ctx.writeAndFlush(resp);

                            }));
                            ch.pipeline().addLast(new ByteArrayEncoder());
                        }
                    });
            // 服务器异步创建绑定
            ChannelFuture future = bootstrap.bind().sync();
            System.out.println(Server.class + "启动正在监听：" + future.channel().localAddress());
            // 异步关闭服务器通道
            future.channel().closeFuture().sync();

        } catch (Exception e) {

        } finally {
            // 异步释放线程池资源
            try {
                group.shutdownGracefully().sync();
                bossGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 封装一个方法
     */

    public String  getString(ByteBuf msg){
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        String res = new String(bytes, StandardCharsets.UTF_8);
        return res.replaceAll("\r", "");
    }
}
