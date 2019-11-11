package savvy.wit.framework.core.service.impl;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import savvy.wit.framework.core.base.model.Mail;
import savvy.wit.framework.core.permission.MailAuthenticator;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 邮件发送
 * File name : MailSender
 * Author : zhoujiajun
 * Date : 2019/10/22 16:17
 * Version : 1.0
 * Description : 
 ******************************/
public class MailSenderImpl implements savvy.wit.framework.core.service.MailSender {

    private Properties props = System.getProperties();

    private MailAuthenticator authenticator;

    private Session session;

    public MailSenderImpl() {

    }

    public MailSenderImpl(String username, String password) {
        String smtpHostName = "smtp." + username.split("@")[1];

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", smtpHostName);
        authenticator = new MailAuthenticator(username,password);
        session = Session.getInstance(props, authenticator);
    }

    /**
     * 单独发送
     * @param recipient 收件人
     * @param mail      邮件
     * @throws MessagingException 信息异常
     */
    public void send(String recipient, Mail mail) throws MessagingException {
        //根据session创建MimeMessage
        MimeMessage message = new MimeMessage(session);
        //发件人
        message.setFrom(new InternetAddress(authenticator.getUsername()));
        //收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

        //主题
        message.setSubject(mail.getSubject());
        //内容
        message.setContent(mail.getContent().toString(), "text/html;charset=utf-8");
        //发送
        Transport.send(message);
    }

    /**
     * 群发邮件
     * @param recipients 收件人集合
     * @param mail       邮件
     * @throws MessagingException
     */
    public void send(List<String> recipients, Mail mail) throws MessagingException {
        //根据session创建MimeMessage
        MimeMessage message = new MimeMessage(session);
        //发件人
        message.setFrom(new InternetAddress(authenticator.getUsername()));
        //收件人们
        InternetAddress[] addresses = new InternetAddress[recipients.size()];
        for(int i=0; i< addresses.length; i++) {
            addresses[i] = new InternetAddress(recipients.get(i));
        }
        message.setRecipients(MimeMessage.RecipientType.TO, addresses);

        //主题
        message.setSubject(mail.getSubject());
        //内容
        message.setContent(mail.getContent().toString(), "text/html;charset=utf-8");
        //发送
        Transport.send(message);
    }

    /**
     * 向一个人发送邮件(带多个附件)
     * @param recipient     收件人
     * @param mail          邮件
     * @param attachments   附件集合
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public void send(String recipient, Mail mail, List<File> attachments) throws MessagingException, UnsupportedEncodingException {
        //根据session创建MimeMessage
        MimeMessage message = new MimeMessage(session);
        //发件人
        message.setFrom(new InternetAddress(authenticator.getUsername()));
        //收件人
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recipient));

        //主题
        message.setSubject(mail.getSubject());
        Multipart multipart = getMultipart(attachments);
        //邮件正文
        BodyPart contentPart = new MimeBodyPart();
        contentPart.setContent(mail.getContent(), "text/html;charset=utf-8");
        multipart.addBodyPart(contentPart);
        message.setContent(multipart);
        //保存邮件
        message.saveChanges();
        Transport.send(message);
    }

    /**
     * 群发带附件的邮件
     * @param recipients    收件人
     * @param mail          邮件
     * @param attachments   附件
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public void send(List<String> recipients, Mail mail, List<File> attachments) throws MessagingException, UnsupportedEncodingException {
        //根据session创建MimeMessage
        MimeMessage message = new MimeMessage(session);
        //发件人
        message.setFrom(new InternetAddress(authenticator.getUsername()));

        //收件人们
        InternetAddress[] addresses = new InternetAddress[recipients.size()];
        for(int i=0; i< addresses.length; i++) {
            addresses[i] = new InternetAddress(recipients.get(i));
        }
        message.setRecipients(MimeMessage.RecipientType.TO, addresses);

        //主题
        message.setSubject(mail.getSubject());
        Multipart multipart = getMultipart(attachments);
        //邮件正文
        BodyPart contentPart = new MimeBodyPart();
        contentPart.setContent(mail.getContent(), "text/html;charset=utf-8");
        multipart.addBodyPart(contentPart);
        message.setContent(multipart);
        //保存邮件
        message.saveChanges();
        Transport.send(message);
    }

    private Multipart getMultipart(List<File> attachments) throws MessagingException, UnsupportedEncodingException {
        Multipart multipart = new MimeMultipart();
        //邮件附件
        if(attachments != null) {
            for(File attachment : attachments) {
                BodyPart attachmentPart = new MimeBodyPart();
                DataSource source = new FileDataSource(attachment);
                attachmentPart.setDataHandler(new DataHandler(source));
                //避免中文乱码的处理
                attachmentPart.setFileName(MimeUtility.encodeWord(attachment.getName()));
                multipart.addBodyPart(attachmentPart);
            }
        }
        return multipart;
    }

    public void sendFullTextMail(String recipient, Mail mail) throws MessagingException {

        //创建一封富文本邮件
        MimeMessage message = new MimeMessage(session);

        //第二个参数表明传递进来的第一个参数是html文本
        MimeMessageHelper helper=new MimeMessageHelper(message,true,"utf-8");

        //发件人
        helper.setFrom(authenticator.getUsername());

        //收件人
        helper.setTo(recipient);
        //发送的邮件主题
        helper.setSubject("New spittle from " + mail.getSubject());
        //cid：spitterLogo，标记一个变量，后面为这个变量添加一张图片
        //第二个参数表示传递的第一个参数是html
        helper.setText("<html><body><img src='cid:spittleLogo'>"+"<h4>" +
                mail.getSubject() + "says...</h4>"+"<i>" + mail.getContent() +
                "</i>"+"</body></html>",true);
        //创建一个变量存放图片
        ClassPathResource image=new ClassPathResource("/img/coupon.jpg");

        //为消息添加嵌入式的图片
        helper.addInline("spittleLogo", image);

        //发送邮件
        Transport.send(message);
    }
}
