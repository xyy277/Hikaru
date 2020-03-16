package savvy.wit.framework.core.facility.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringEncoder;
import savvy.wit.framework.core.facility.AbstractFacility;
import savvy.wit.framework.core.facility.netty.handler.ServerBootstrapHandler;

import java.nio.charset.Charset;

/***************************
 * Title:
 * File name: Server
 * Author: zhoujiajun
 * Date: 2020/1/19 16:09
 * Description:
 ***************************/
public class Server extends AbstractFacility {

    private ServerBootstrap bootstrap;

    public Server(int port) {
        super(port);
    }

    public Server(String host, int port) {
        super(host, port);
    }

//    private Logger logger = LoggerFactory.getLogger(Server.class);


    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        EventLoopGroup group = new NioEventLoopGroup();

        try {
            if (bootstrap == null)  {
                bootstrap = new ServerBootstrap();
            }
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            // 绑定线程池
            bootstrap.group(group, bossGroup)
                    // 指定使用的channel
                    .channel(NioServerSocketChannel.class)
                    // 绑定监听端口
                    .localAddress(this.getPort())
                    // 绑定客户端连接时触发操作
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("客户端连接IP:Port - " + ch.localAddress().getHostName() + ":" + ch.localAddress().getPort());
                            ch.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
                            ch.pipeline().addLast(new ServerHandler());
                            ch.pipeline().addLast(new ByteArrayEncoder());
                        }
                    });
            // 服务器异步创建绑定
            ChannelFuture future = bootstrap.bind().sync();
            System.out.println(Server.class + "启动正在监听：" + future.channel().localAddress());
            // 异步关闭服务器通道
            future.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 异步释放线程池资源
            try {
                group.shutdownGracefully().sync();
                bossGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Server bootstrap(ServerBootstrapHandler bootstrapHandler) {
        bootstrapHandler.handle(bootstrap);
        return this;
    }

    public void setBootstrap(ServerBootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }

    public ServerBootstrap getBootstrap() {
        return bootstrap;
    }
}
