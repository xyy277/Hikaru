package savvy.wit.framework.core.facility.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import savvy.wit.framework.core.facility.AbstractFacility;
import savvy.wit.framework.core.facility.netty.handler.BootstrapHandler;

/***************************
 * Title:
 * File name: Client
 * Author: zhoujiajun
 * Date: 2020/1/19 14:55
 * Description:
 ***************************/
public class Client extends AbstractFacility {

    /**
     * 客户端Bootstrap
     */
    private Bootstrap bootstrap;
    /**
     * 客户端WORKER
     */
    private EventLoopGroup worker;

    @Override
    public void close() {
        System.out.println("close");
        worker.shutdownGracefully();
    }

    @Override
    public void start() {
        bootstrap = new Bootstrap();
        worker = new NioEventLoopGroup();
        bootstrap.group(worker);
        bootstrap.channel(NioSocketChannel.class);
    }

    public Client(int port) {
        super(port);
    }

    public Client(String host, int port) {
        super(host, port);
    }

    /**
     * 远程调用
     * @param ip
     * @param retry
     * @param port
     * @param retries
     */
    public void remoteCall(final ChannelHandler handler, String ip, int retry, int port, int retries){
        try{
            bootstrap.handler(handler);
            ChannelFuture channelFuture = bootstrap.connect(ip,port).sync();
            channelFuture.channel().closeFuture().sync();
        }catch (InterruptedException e){
            retry++;
            if (retry > retries) {
                throw new RuntimeException("调用Wrong");
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                System.out.println("第"+retries+"次尝试....失败");
                remoteCall(handler, ip,retry,port,retries);
            }
        }

    }

    public Client bootstrap(BootstrapHandler bootstrapHandler) {
        bootstrapHandler.handle(bootstrap);
        return this;
    }

    public void setBootstrap(Bootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }

    public Bootstrap getBootstrap() {
        return bootstrap;
    }
}
