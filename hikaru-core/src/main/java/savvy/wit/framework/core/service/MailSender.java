package savvy.wit.framework.core.service;

import savvy.wit.framework.core.base.model.Mail;

import javax.mail.MessagingException;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : MailSender
 * Author : zhoujiajun
 * Date : 2019/10/29 11:31
 * Version : 1.0
 * Description : 
 ******************************/
public interface MailSender {

    void send(String recipient, Mail mail) throws MessagingException;

    void send(List<String> recipients, Mail mail) throws MessagingException;

    void send(String recipient, Mail mail, List<File> attachments) throws MessagingException, UnsupportedEncodingException;

    void send(List<String> recipients, Mail mail, List<File> attachments) throws MessagingException, UnsupportedEncodingException;

    void sendFullTextMail(String recipient, Mail mail) throws MessagingException;
}
