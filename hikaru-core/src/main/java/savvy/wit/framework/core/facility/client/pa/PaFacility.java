package savvy.wit.framework.core.facility.client.pa;

import savvy.wit.framework.core.facility.FacilityCommunication;

/***************************
 * Title: 广播设备
 * File name: PaFacility
 * Author: zhoujiajun
 * Date: 2020/1/20 16:51
 * Description:
 * 建立tcp连接，发送报文控制pa设备
 ***************************/
public interface PaFacility extends FacilityCommunication {

    /*

    实时广播、播放音频文件

     */

    /**
     * 播放
     */
    Object play();

    /**
     * 按次数播放
     * @param count 次数
     */
    Object play(int count);

    /**
     * 按时长播放
     * @param time  时长
     */
    Object play(long time);

    /**
     * 暂停
     */
    Object pause();

    /*
    SET/GET
    VOLUME

    Set volume 0 .
    Set maximum volume.
    Restore default volume from EEPROM.
    Get Volume.

    I=0
    l=20
    l=
    l=?

    “ack” : on setting volume
    successfully.
    “nack”: if parameter is not correct.
    volume=value
     */
    /**
     * 提高音量
     */
    Object volUp(int num);

    /**
     * 降低音量
     */
    Object volDown(int num);


    /**
     * 设置音量级别
     * @param num 0-20
     * @return response
     */
    Object setVolLevel(int num);

    /**
     * 设置音量百分比
     * @param num 0-100
     * @return response
     */
    Object setVolPresent(int num);

    /**
     * 获取音量
     */
    Object getVol();

    /**
     * 获取设备状态
     * @return state
     */
    Object deviceState();
}
