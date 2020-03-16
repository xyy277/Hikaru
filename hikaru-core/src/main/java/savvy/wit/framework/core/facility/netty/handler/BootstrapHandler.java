package savvy.wit.framework.core.facility.netty.handler;

import io.netty.bootstrap.Bootstrap;

/***************************
 * Title:
 * File name: BootstrapHandler
 * Author: zhoujiajun
 * Date: 2020/1/21 9:11
 * Description:
 ***************************/
public interface BootstrapHandler {

    void handle(Bootstrap bootstrap);
}
