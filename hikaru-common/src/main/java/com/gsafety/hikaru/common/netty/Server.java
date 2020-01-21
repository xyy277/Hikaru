package com.gsafety.hikaru.common.netty;


/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Server
 * Author : zhoujiajun
 * Date : 2020/1/16 16:00
 * Version : 1.0
 * Description : 
 ******************************/
public class Server extends Test {

    public static void main(String[] args) {
//        TcpServer tcpServer = new TcpServer();
//        tcpServer.startServer(5080);
        Server server = new Server();
        System.out.println(server.start());
        System.out.println(server.isRunning());
        System.out.println(server.close());
        System.out.println(server.isRunning());
    }
}
