package savvy.wit.framework.core.facility.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.SimpleChannelInboundHandler;

/***************************
 * Title:
 * File name: AbstractChannelHandler
 * Author: zhoujiajun
 * Date: 2020/1/20 16:34
 * Description:
 ***************************/
public abstract class ClientChannelHandler extends SimpleChannelInboundHandler<ByteBuf> implements ChannelHandler {

    private ChannelActiveHandler activeHandler;

    private ChannelReadHandler readHandler;

    public ChannelActiveHandler getActiveHandler() {
        return activeHandler;
    }

    public ChannelReadHandler getReadHandler() {
        return readHandler;
    }

    public ChannelHandler active(ChannelActiveHandler activeHandler) {
        this.activeHandler = activeHandler;
        return this;
    }

    public ChannelHandler read(ChannelReadHandler readHandler) {
        this.readHandler = readHandler;
        return this;
    }
}
