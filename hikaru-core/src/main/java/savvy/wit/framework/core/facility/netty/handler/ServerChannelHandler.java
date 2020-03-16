package savvy.wit.framework.core.facility.netty.handler;

import io.netty.channel.ChannelInboundHandlerAdapter;

/***************************
 * Title:
 * File name: ServerChannelHandler
 * Author: zhoujiajun
 * Date: 2020/1/20 16:45
 * Description:
 ***************************/
public abstract class ServerChannelHandler extends ChannelInboundHandlerAdapter implements ChannelHandler {

    private ChannelActiveHandler activeHandler;

    private ChannelReadHandler readHandler;

    private ChannelActiveHandler completeHandler;

    public ChannelActiveHandler getActiveHandler() {
        return activeHandler;
    }

    public ChannelReadHandler getReadHandler() {
        return readHandler;
    }

    public ChannelActiveHandler getCompleteHandler() {
        return completeHandler;
    }

    public ChannelHandler active(ChannelActiveHandler activeHandler) {
        this.activeHandler = activeHandler;
        return this;
    }

    public ChannelHandler read(ChannelReadHandler readHandler) {
        this.readHandler = readHandler;
        return this;
    }

    public ChannelHandler complete(ChannelActiveHandler completeHandler) {
        this.completeHandler = completeHandler;
        return this;
    }
}
