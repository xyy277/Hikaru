package savvy.wit.framework.core.facility.netty.handler;

import io.netty.channel.ChannelHandlerContext;

/***************************
 * Title:
 * File name: ChannelActiveHandler
 * Author: zhoujiajun
 * Date: 2020/1/20 15:52
 * Description:
 ***************************/
public interface ChannelActiveHandler {

    void handle(ChannelHandlerContext ctx);
}
