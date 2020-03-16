package savvy.wit.framework.core.facility.client.pa;

/***************************
 * Title: BARIX控制命令
 * File name: Command
 * Author: zhoujiajun
 * Date: 2020/1/20 11:29
 * Description:
 * 命令大全
 * 及时更新 - 对应文档 Streaming_Client_technical_doc_v3.17
 ***************************/
public interface BARIXCommand {

    // 播放
    String PLAY = "c=1";

    // 下一首
    String NEXT_SONG = "c=4";

    // 上一首
    String PREV_SONG =  "c=5";

    // 随机播放开
    String SHUFFLE_ON = "c=6";

    // 随机播放关
    String SHUFFLE_OFF = "c=7";

    /**
     * 切换静音
     */
    String MUTE = "c=8";

    /**
     * 音量 + 2%
     */
    String VOLUME_INC = "c=19";

    /**
     * 音量 - 2%
     */
    String VOLUME_DEC = "c=20";

    /**
     * 切换随机播放
     */
    String TOGGLE_SHUFFLE = "c=30";

    /**
     * 设置工厂默认值(如果在安全设置中启用)，保留网络设置和声音IP
     */
    String DEFAULT = "c=94";

    /**
     * 重启硬件设备
     */
    String DEVICE_REST = "c=99";

    /**
     * 切换待机模式 <=> 遥控器的开/关按钮
     */
    String TOGGLE_STANDBY = "c=103";

    /**
     * 静音 v=0 <=> V=0
     */
    String SET_VOL_0 = "v=0";

    /**
     * 最大音量 v=20 <=> V=100
     */
    String SET_VOL_MAX = "v=20";

    /**
     * 设置音量等级 0 - 20
     */
    String SET_VOL_LEVEL = "v=";

    /**
     * 百分比设置音量 0 - 100%
     */
    String SET_VOL_PERCENT = "V=";

    // 弃用了。。
    String GET_VOL = "v=?";


}
