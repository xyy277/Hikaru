package com.gsafety.hikaru.service.impl;

import com.gsafety.hikaru.service.BARIXScheduleService;
import com.gsafety.hikaru.service.SocketClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import savvy.wit.framework.core.base.annotations.Log;
import savvy.wit.framework.core.base.enums.LogType;
import savvy.wit.framework.core.base.util.Cmd;
import savvy.wit.framework.core.pattern.factory.LogFactory;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : BARIXScheduleService
 * File name : BARIXScheduleService
 * Author : zhoujiajun
 * Date : 2020/3/2 15:39
 * Version :
 * Description :
 *********************************/
@Service
@EnableAsync
public class BARIXScheduleServiceImpl implements BARIXScheduleService {

    private SocketClient socketClient = SocketClient.getInstance();

    @Value("${attach}")
    private String attach;

    @Async
    @Log(type = LogType.BUSINESS, id = "BARIXScheduleService")
    @Override
    public void play(String id) {
//        if (storage.get(id) != null) {
//            storage.set(id, (Integer)storage.get(id) + 1);
//            return;
//        }
//        storage.set(id, 1);
        // 获取文件地址
        String url = attach + "/download/" + id;

        /*
         配置VLC串流，网络地址输入本地地址rtp://127.0.0.1:5004  输出地址为实际转发的地址
         前置后置处理增加延迟自动暂停播放 - vlc 机制原因导致播放stream需要手动停止播放，由系统自动实现
         */
        Cmd.get().prefixHandler(() -> {
            new Thread(() -> {
                try {
                    Thread.sleep(1000*3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                socketClient.start();
            }).start();
        }).suffixHandler(() -> {
            new Thread(() -> {
                try {
                    Thread.sleep(1000*10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                socketClient.close();
            }).start();
        }).proxy(
                "ffmpeg -re -i",
                 url,
                "-f rtp_mpegts rtp://127.0.0.1:5004" // rtp_mpegts 将音频流发送到VLC
        );
        LogFactory.open()
                .printL("VLC remoteControl")
                .close();
    }
}
