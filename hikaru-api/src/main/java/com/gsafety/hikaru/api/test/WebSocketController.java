package com.gsafety.hikaru.api.test;

import com.gsafety.hikaru.common.global.Result;
import com.gsafety.hikaru.common.middleware.websocket.WebSocketServer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : WebSocketController
 * Author : zhoujiajun
 * Date : 2019/2/26 9:26
 * Version : 1.0
 * Description : 
 ******************************/
@RestController
@RequestMapping("/ws")
public class WebSocketController {

    //页面请求
    @GetMapping("/{cid}")
    public ModelAndView socket(@PathVariable String cid) {
        ModelAndView mav=new ModelAndView("/socket");
        mav.addObject("cid", cid);
        return mav;
    }
    //推送数据接口
    @RequestMapping(value = "/push/{cid}", method = RequestMethod.GET)
    public Result pushToWeb(@PathVariable String cid, @RequestParam String message) {
        try {
            WebSocketServer.sendInfo(message,cid);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error(500,cid+"#"+e.getMessage());
        }
        return Result.success(cid);
    }

}