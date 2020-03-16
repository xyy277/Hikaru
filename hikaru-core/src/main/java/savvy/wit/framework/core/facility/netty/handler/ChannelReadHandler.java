package savvy.wit.framework.core.facility.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/***************************
 * Title:
 * File name: ChannelReadHandler
 * Author: zhoujiajun
 * Date: 2020/1/20 15:55
 * Description:
 ***************************/
public interface ChannelReadHandler {


    void handle(ChannelHandlerContext ctx, ByteBuf msg);
}
