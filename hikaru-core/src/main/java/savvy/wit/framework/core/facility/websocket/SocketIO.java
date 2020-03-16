package savvy.wit.framework.core.facility.websocket;

import javax.websocket.Session;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : SocketIO
 * File name : SocketIO
 * Author : zhoujiajun
 * Date : 2020/2/21 15:35
 * Version :
 * Description :
 *********************************/
public interface SocketIO {

    void onOpen(Session session, String param);

    void onClose();

    void onMessage(String message, Session session);

    void onError(Session session, Throwable error);
}
