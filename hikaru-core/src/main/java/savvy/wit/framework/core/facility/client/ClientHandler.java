package savvy.wit.framework.core.facility.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import savvy.wit.framework.core.facility.netty.handler.ClientChannelHandler;
import savvy.wit.framework.core.facility.netty.util.Command;

import java.nio.charset.StandardCharsets;

/***************************
 * Title:
 * File name: ClientHandler
 * Author: zhoujiajun
 * Date: 2020/1/19 15:28
 * Description:
 ***************************/
public class ClientHandler extends ClientChannelHandler {

    private String result;


    public ClientHandler() {

    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if (getActiveHandler() != null) {
            getActiveHandler().handle(ctx);
            return;
        }
        System.out.println("channel active:" + ctx.channel().localAddress());
        String message = Command.init("test").and("test").output();
        try {
            ctx.writeAndFlush(message).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel inactive： " + ctx.channel().localAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        if (getReadHandler() != null) {
            getReadHandler().handle(ctx, msg);
            return;
        }
        System.out.println("读取客户端通道信息");
        ByteBuf byteBuf = msg.readBytes(msg.readableBytes());
        System.out.println("客户端收到的服务器信息：" + ByteBufUtil.hexDump(byteBuf));
        result = byteBuf.toString(StandardCharsets.UTF_8);
        System.out.println("数据包为：" + result);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        System.out.println("client exception happened: " + cause.getMessage());
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
