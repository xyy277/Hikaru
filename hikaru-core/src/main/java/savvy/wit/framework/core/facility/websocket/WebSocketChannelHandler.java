package savvy.wit.framework.core.facility.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : WebSocketChannelHandler
 * File name : WebSocketChannelHandler
 * Author : zhoujiajun
 * Date : 2020/2/21 15:35
 * Version :
 * Description :
 *********************************/
public interface WebSocketChannelHandler {


    void read(ChannelHandlerContext ctx, TextWebSocketFrame msg);

}
