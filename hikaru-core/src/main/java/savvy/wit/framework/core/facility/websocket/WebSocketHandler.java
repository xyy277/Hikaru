package savvy.wit.framework.core.facility.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : WebSocketHandler
 * File name : WebSocketHandler
 * Author : zhoujiajun
 * Date : 2020/2/21 15:45
 * Version : 
 * Description :
 *********************************/
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    public static ChannelGroup channelGroup;

    static {
        channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }

    private WebSocketChannelHandler webSocketChannelHandler;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Establish a connection with the client --->> channelActive");
        channelGroup.add(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        if (getWebSocketChannelHandler() != null) {
            getWebSocketChannelHandler().read(ctx, msg);
            return;
        }
        System.out.println("Server receives data --->> channelRead0");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Disconnect from client --->> channelInactive");
        channelGroup.remove(ctx.channel());
    }

    public WebSocketHandler handler(WebSocketChannelHandler webSocketChannelHandler) {
        this.webSocketChannelHandler = webSocketChannelHandler;
        return this;
    }

    public WebSocketChannelHandler getWebSocketChannelHandler() {
        return webSocketChannelHandler;
    }
}
