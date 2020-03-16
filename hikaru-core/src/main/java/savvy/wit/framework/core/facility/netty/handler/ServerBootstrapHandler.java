package savvy.wit.framework.core.facility.netty.handler;

import io.netty.bootstrap.ServerBootstrap;

/***************************
 * Title:
 * File name: ServerBootstrapHandler
 * Author: zhoujiajun
 * Date: 2020/1/21 9:12
 * Description:
 ***************************/
public interface ServerBootstrapHandler {

    void handle(ServerBootstrap serverBootstrap);
}
