package com.gsafety.hikaru.common.netty;

import com.gsafety.hikaru.common.netty.server.TcpServer;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Server
 * Author : zhoujiajun
 * Date : 2020/1/16 16:00
 * Version : 1.0
 * Description : 
 ******************************/
public class Server {

    public static void main(String[] args) {
        TcpServer tcpServer = new TcpServer();
        tcpServer.startServer(5080);
    }
}
