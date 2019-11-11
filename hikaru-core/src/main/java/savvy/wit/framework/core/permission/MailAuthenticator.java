package savvy.wit.framework.core.permission;

import javax.mail.Authenticator;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : MailAuthenticator
 * Author : zhoujiajun
 * Date : 2019/10/22 16:14
 * Version : 1.0
 * Description : 
 ******************************/
public class MailAuthenticator extends Authenticator {

    private String username;

    private String password;

    public MailAuthenticator() {
    }

    public MailAuthenticator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
        return new javax.mail.PasswordAuthentication(username, password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
