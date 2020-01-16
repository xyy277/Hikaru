package com.gsafety.hikaru.common.netty.client;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class Client {

    public static String HOST = "127.0.0.1";

    public static int PORT = 5080;

    public static Bootstrap bootstrap=getBootstrap();

    public static Channel channel = getChannel(HOST,PORT);

    public static final Bootstrap getBootstrap(){
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception{

                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast("frameDecoder",new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
                pipeline.addLast("frameEncoder",new LengthFieldPrepender(4));
                pipeline.addLast(new ObjectEncoder());
                pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                pipeline.addLast("handler",new SimpleChannelInboundHandler() {

                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
                        System.out.println(o);
                    }
                });
            }
        });
        bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
        return bootstrap;
    }

    public static final Channel getChannel(String HOST, int PORT){
        Channel channel = null;
        try{
            channel = bootstrap.connect(HOST,PORT).sync().channel();
            System.out.println("连接成功");
        }catch (Exception e){
            System.out.println("连接server(IP{},PROT{})失败");
            return null;
        }
        return channel;
    }

    public static void sendMsg(Object msg) throws Exception{
        if(channel!=null){
            System.out.println(msg);
            channel.writeAndFlush(msg).sync();
        }else{
            System.out.println("消息发送失败，连接尚未建立");
        }
    }

}
