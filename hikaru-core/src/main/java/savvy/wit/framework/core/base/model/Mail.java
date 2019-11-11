package savvy.wit.framework.core.base.model;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Mail
 * Author : zhoujiajun
 * Date : 2019/10/22 16:10
 * Version : 1.0
 * Description : 
 ******************************/
public class Mail {

    private String subject;

    private String content;

    public Mail() {
    }

    public Mail(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
