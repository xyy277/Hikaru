package savvy.wit.framework.core.facility.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import savvy.wit.framework.core.facility.netty.handler.ServerChannelHandler;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/***************************
 * Title:
 * File name: ServerHandler
 * Author: zhoujiajun
 * Date: 2020/1/19 16:17
 * Description:
 ***************************/
public class ServerHandler extends ServerChannelHandler {

//    private Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    /*
     * channelActive
     * channel 通道 active 活跃的
     * 当客户端主动连接服务器后，这个通道就是活跃的
     * 服务器和客户端之间建立通信可以传输数据
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if (getActiveHandler() != null) {
            getActiveHandler().handle(ctx);
            return;
        }
        System.out.println(ctx.channel().localAddress() + " channelActive");
    }

    /*
     * channelInactive
     * channel 通道 Inactive 不活跃的
     * 当客户端主动断开服务器连接后，这个通道不活跃
     * 客户端关闭了与服务端的通信通道，不可传输数据
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().localAddress() + " channelInactive");
    }

    /*
     * 读取服务器发送过来的信息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (getReadHandler() != null) {
            getReadHandler().handle(ctx, (ByteBuf) msg);
            return;
        }
        ByteBuf byteBuf = (ByteBuf) msg;
        String res = getMsg(byteBuf);
        System.out.println("收到客户端数据：" + res);
        String reply = "msg received, date: " + new Date();
        ByteBuf resp = Unpooled.copiedBuffer(reply.getBytes());
        ctx.writeAndFlush(resp);
    }

    /*
     * 读取完毕客户端发送的数据之后的操作
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        if (getCompleteHandler() != null) {
            getCompleteHandler().handle(ctx);
            return;
        }
        System.out.println("channelReadComplete");
        // 写一个空buf，刷新写出区域，完成后关闭socket channel连接
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);

        // 异步刷新关闭
//        ctx.flush().close().sync();
    }

    /*
     * 服务器发生异常信息
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        System.out.println("exceptionCaught：\r\n" + cause.getMessage());
    }

    private String getMsg(ByteBuf byteBuf) {
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
