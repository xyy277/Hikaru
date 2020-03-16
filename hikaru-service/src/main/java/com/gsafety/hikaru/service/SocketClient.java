package com.gsafety.hikaru.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import savvy.wit.framework.core.facility.FacilityCommunication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : SocketClient
 * File name : SocketClient
 * Author : zhoujiajun
 * Date : 2020/2/27 14:53
 * Version : 1.0
 * Description : 测试VLC远程控制
 *********************************/
public class SocketClient implements FacilityCommunication {

    private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);
    Socket client = null;
    BufferedReader is = null;
    Writer writer = null;
    private String host;
    private int port;
    private static SocketClient instance;

    public static SocketClient getInstance() {
        if (instance == null)
            instance = LazyInit.INITIALIZATION;
        return instance;
    }
    private static class LazyInit {

        private static final SocketClient INITIALIZATION = new SocketClient();
    }
    public boolean isPlaying() {
        return playing;
    }

    private boolean playing = false;

    public SocketClient() {
    }

   public void init(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void enqueue(String rtp) {
        try {
            logger.info("VLC enqueue");
            client = new Socket(host, port);
            writer = new OutputStreamWriter(client.getOutputStream());
            writer.write("enqueue " + rtp);
            writer.flush();
            writer.close();
            client.close();
            logger.info("VLC 调试命令控制完毕");
        } catch (IOException e) {
            logger.error("Close the IO session error: ", e);
        }
    }

    @Override
    public void start() {
        try {
            logger.info("VLC播放");
            client = new Socket(host, port);
            writer = new OutputStreamWriter(client.getOutputStream());
            writer.write("play");
            writer.flush();
            writer.close();
            client.close();
            playing = true;
            logger.info("VLC 调试命令控制完毕");
        } catch (IOException e) {
            logger.error("Close the IO session error: ", e);
        }
    }

    @Override
    public void close() {
        try {
            logger.info("VLC停止");
            client = new Socket(host, port);
            writer = new OutputStreamWriter(client.getOutputStream());
            writer.write("stop");
            writer.flush();
            writer.close();
            client.close();
            playing = false;
            logger.info("VLC 调试命令控制完毕");
        } catch (IOException e) {
            logger.error("Close the IO session error: ", e);
        }
    }

    public void setVoiceValue(int value) {
        try {
            logger.info("VLC设置音量值");
            logger.info("volume "+ value*16 );
            client = new Socket(host, port);
            writer = new OutputStreamWriter(client.getOutputStream());
            writer.write("volume "+ value*16 );
            writer.flush();
            writer.close();
            client.close();
            logger.info("VLC 调试命令控制完毕");
        } catch (IOException e) {
            logger.error("Close the IO session error: ", e);
        }
    }

//    public void  getVoiceValue() {
//        try {
//            logger.info("VLC获取音量值");
//            client = new Socket(host, port);
//            writer = new OutputStreamWriter(client.getOutputStream());
//            writer.write("volume");
//            writer.flush();
//            BufferedReader iss = new BufferedReader(new InputStreamReader(client.getInputStream()));
//            StringBuffer sb = new StringBuffer();
//            String temp;
//            int index;
//            while ((temp = iss.readLine()) != null) {
//                if ((index = temp.indexOf("eof")) != -1) {
//                    sb.append(temp.substring(0, index));
//                    break;
//                }
//                sb.append(temp);
//            }
//            // logger.info(sb.toString());
//            logger.info("VLC获取音量值"+ sb.toString());
//            writer.close();
//            client.close();
//            logger.info("VLC 调试命令控制完毕");
//        } catch (IOException e) {
//            logger.error("Close the IO session error: ", e);
//        }
//    }


    public void volDown() {
        try {
            System.out.println("voldown");
            logger.info("VLC音量降低");
            client = new Socket(host, port);
            writer = new OutputStreamWriter(client.getOutputStream());
            writer.write("voldown 1");
            writer.flush();
            writer.close();
            client.close();
            logger.info("VLC 调试命令控制完毕");
        } catch (IOException e) {
            logger.error("Close the IO session error: ", e);
        }
    }

    public void volUp() {
        try {
            logger.info("VLC音量升高");
            client = new Socket(host, port);
            writer = new OutputStreamWriter(client.getOutputStream());
            writer.write("volup 1");
            writer.flush();
            writer.close();
            client.close();
            logger.info("VLC 调试命令控制完毕");
        } catch (IOException e) {
            logger.error("Close the IO session error: ", e);
        }
    }

    public void destroy() {
        try {
            writer.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
