package savvy.wit.framework.core.pattern.factory;

import savvy.wit.framework.core.service.MailSender;
import savvy.wit.framework.core.service.impl.MailSenderImpl;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Mails
 * Author : zhoujiajun
 * Date : 2019/10/29 11:41
 * Version : 1.0
 * Description : 
 ******************************/
public class Mails {

    public static MailSender getSender(String username, String password) {
        return new MailSenderImpl(username, password);
    }
}
