package com.gsafety.hikaru.api.test;

import com.gsafety.hikaru.common.global.Error;
import com.gsafety.hikaru.common.global.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import savvy.wit.framework.core.base.model.Mail;
import savvy.wit.framework.core.base.util.JsonUtil;
import savvy.wit.framework.core.pattern.factory.Mails;
import savvy.wit.framework.core.service.MailSender;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : MailController
 * Author : zhoujiajun
 * Date : 2019/10/28 15:57
 * Version : 1.0
 * Description : 
 ******************************/
@RestController
@RequestMapping("/mail")
@Api(value = "邮件控制器", description = "邮件api")
public class MailController {

    @ApiOperation(value = "发送邮件", notes = "发送一封邮件")
    @RequestMapping(value = "/send/{sender}/{pwd}", method = RequestMethod.GET)
    public Result<String> sendMail(@ApiParam(value = "发件人")@PathVariable String sender,
                                   @ApiParam(value = "密码") @PathVariable String pwd,
                                   @ApiParam(value = "收件人") @RequestParam String recipient,
                                   @ApiParam(value = "邮件内容")@RequestParam String mail) {
        MailSender mailSender = Mails.getSender(sender, pwd);
        List<File> fileList = new ArrayList<>();
        fileList.add(new File("G:\\GitHub\\hikaru\\hikaru-server\\test.png"));
        fileList.add(new File("G:\\GitHub\\hikaru\\hikaru-server\\test.gif"));
        try {
            mailSender.send(recipient, JsonUtil.fromJson(mail, Mail.class), fileList);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(new Error(500, e.getMessage()));
        }
        return Result.success("Email sent successfully");
    }
}
