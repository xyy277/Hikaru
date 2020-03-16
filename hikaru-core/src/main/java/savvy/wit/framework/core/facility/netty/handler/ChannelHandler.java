package savvy.wit.framework.core.facility.netty.handler;

/***************************
 * Title:
 * File name: ChannelHadler
 * Author: zhoujiajun
 * Date: 2020/1/20 16:41
 * Description:
 ***************************/
public interface ChannelHandler extends io.netty.channel.ChannelHandler {

    /**
     * 激活通道
     * @param activeHandler
     * @return
     */
    ChannelHandler active(ChannelActiveHandler activeHandler);

    /**
     * 获取通道
     * @param readHandler
     * @return
     */
    ChannelHandler read(ChannelReadHandler readHandler);
}
