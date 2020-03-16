package com.gsafety.hikaru.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : SocketClient
 * File name : SocketClient
 * Author : litong
 * Date : 2020/3/2  10:22
 * Version : 1.0
 * Description : 对VLC远程控制
 *********************************/
public class VLCControlClient {

    private static final Logger logger = LoggerFactory.getLogger(VLCControlClient.class);


    public  Socket client = null;


    /***
     * 降低音量
     */
    public  void  volDown(){
        System.out.println("voldown");
        String  res = "volume [100]\n";
        BufferedReader is = null;
        Writer writer = null;
        try {
            // VLC socket client
            System.out.println("voldown  client");
            client = new Socket("localhost", 8888);
            // 2、通过VLC控制命令 添加网络地址到VLC播放器
            System.out.println("voldown  writer");
            writer = new OutputStreamWriter(client.getOutputStream());
            writer.write(res);
            writer.flush();
            logger.info("VLC 调试命令控制完毕");
            is = new BufferedReader(new InputStreamReader(client.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String temp;
            int index;
            while ((temp = is.readLine()) != null) {
                if ((index = temp.indexOf("eof")) != -1) {
                    sb.append(temp.substring(0, index));
                    break;
                }
                sb.append(temp);
            }
            // logger.info(sb.toString());
            System.out.printf("输出Socket接收流");
            System.out.println(sb.toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (writer != null) {
                    writer.close();
                }
                if (client != null) {
                    client.close();
                }
            } catch (IOException e) {
                logger.error("Close the IO session error: ", e);
            }
        }
    }

    /***
     * 提高音量
     */
    public  void  volUp(){
        String  res = "volup [1]";
        vlcClient(res);
    }

    /***
     * 静音
     */
    public  void  mute(){

    }

    /***
     *  设置音量值
     */
    public  void  setVolValue(int value){
        String  res = "volume ["+value+"]";
        vlcClient(res);
    }


    /***
     *  循环播放
     */
    public  void  repeat(){
        String  res = "repeat [on]";
        vlcClient(res);
    }



    public void vlcClient(String value) {
        System.out.println(" vlcClient");
        BufferedReader is = null;
        Writer writer = null;
        try {
            // VLC socket client
            client = new Socket("localhost", 8888);
            // 2、通过VLC控制命令 添加网络地址到VLC播放器
            writer = new OutputStreamWriter(client.getOutputStream());
            writer.write(value);
//            writer.write("add http://www.170mv.com/kw/antiserver.kuwo.cn/anti.s?rid=MUSIC_118980&response=res&format=mp3|aac&type=convert_url&br=128kmp3&agent=iPhone&callback=getlink&jpcallback=getlink.mp3\n");
            writer.flush();
            logger.info("VLC 调试命令控制完毕");
            is = new BufferedReader(new InputStreamReader(client.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String temp;
            int index;
            while ((temp = is.readLine()) != null) {
                if ((index = temp.indexOf("eof")) != -1) {
                    sb.append(temp.substring(0, index));
                    break;
                }
                sb.append(temp);
            }
            // logger.info(sb.toString());
            System.out.printf("输出Socket接收流");
            System.out.println(sb.toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (writer != null) {
                    writer.close();
                }
                if (client != null) {
                    client.close();
                }
            } catch (IOException e) {
                logger.error("Close the IO session error: ", e);
            }
        }
    }


    public void client(){
        BufferedReader is = null;
        Writer writer = null;
        try {
            // VLC socket client
            client = new Socket("localhost", 8888);
            // 2、通过VLC控制命令 添加网络地址到VLC播放器
            writer = new OutputStreamWriter(client.getOutputStream());
            writer.write("add http://www.170mv.com/kw/antiserver.kuwo.cn/anti.s?rid=MUSIC_118980&response=res&format=mp3|aac&type=convert_url&br=128kmp3&agent=iPhone&callback=getlink&jpcallback=getlink.mp3\n");
            writer.flush();
            logger.info("VLC 调试命令控制完毕");
            is = new BufferedReader(new InputStreamReader(client.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String temp;
            int index;
            while ((temp = is.readLine()) != null) {
                if ((index = temp.indexOf("eof")) != -1) {
                    sb.append(temp.substring(0, index));
                    break;
                }
                sb.append(temp);
            }
            // logger.info(sb.toString());
            System.out.printf("输出Socket接收流");
            System.out.println(sb.toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (writer != null) {
                    writer.close();
                }
                if (client != null) {
                    client.close();
                }
            } catch (IOException e) {
                logger.error("Close the IO session error: ", e);
            }
        }
    }


}