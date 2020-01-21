package com.gsafety.hikaru.common.netty;

import com.gsafety.hikaru.common.netty.client.Client;
import savvy.wit.framework.core.base.util.HexStringUtils;
import savvy.wit.framework.core.base.util.StringUtil;

import java.util.concurrent.TimeUnit;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Test
 * Author : zhoujiajun
 * Date : 2020/1/16 16:28
 * Version : 1.0
 * Description : 
 ******************************/
public class Test {

    private boolean running;

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Object start() {
        return running = true;
    }

    public Object close() {
        return running = false;
    }

    public static void main(String[] args) throws Exception {
        String sendInfo = "c=40 \r";///0x0d
        byte[] bytes = HexStringUtils.hexStringToByteArray(sendInfo) ;
        bytes[1] = 0x0d;
        for (byte b : bytes) {
            System.out.print(b);
        }
//        for (;;) {
//            Client.sendMsg("c = 83");
//            TimeUnit.SECONDS.sleep(3);
//        }

    }
}
