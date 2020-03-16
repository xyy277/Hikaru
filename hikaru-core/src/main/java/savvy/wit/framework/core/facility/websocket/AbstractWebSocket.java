package savvy.wit.framework.core.facility.websocket;

import javax.websocket.Session;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : AbstractWebSocket
 * File name : AbstractWebSocket
 * Author : zhoujiajun
 * Date : 2020/2/21 15:35
 * Version : 
 * Description :
 *********************************/

public class AbstractWebSocket implements SocketIO {

    @Override
    public void onOpen(Session session, String param) {

    }

    @Override
    public void onClose() {

    }

    @Override
    public void onMessage(String message, Session session) {

    }

    @Override
    public void onError(Session session, Throwable error) {

    }
}
