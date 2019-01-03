package com.gsafety.hikaru.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import savvy.wit.framework.core.base.interfaces.Log;
import savvy.wit.framework.core.base.util.DateUtil;
import savvy.wit.framework.core.base.util.ImageUtil;
import savvy.wit.framework.core.pattern.decorate.Counter;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ImageController
 * Author : zhoujiajun
 * Date : 2018/12/19 14:25
 * Version : 1.0
 * Description : 
 ******************************/
@RestController
@RequestMapping("/image")
public class ImageController {

    private static final int WIDTH = 480;
    private static final int HEIGHT = 360;
    private Log log = LogFactory.getLog();

    @RequestMapping(value = "/{name}/{time}", method = RequestMethod.GET)
    public void image(@PathVariable String name, @PathVariable String time,
                      HttpServletRequest request, HttpServletResponse response) {
        int excursion = 10;
        Object object = request.getSession().getAttribute(name);
        final Counter counter = object != null ? (Counter) object : Counter.create();
        BufferedImage image = ImageUtil.me().draw(WIDTH,HEIGHT, 1, (graphics) -> {
            graphics.setColor(Color.BLACK);
            graphics.fillRect(0,0,WIDTH,HEIGHT);
            graphics.setColor(Color.WHITE);
            Point p = new Point(0, counter.getCount() == 0 ? HEIGHT - DateUtil.random(HEIGHT) : counter.getCount());
            int x ,y;
            for ( x = 0; x <= WIDTH; x += excursion) {
                y =  HEIGHT - DateUtil.random(HEIGHT);
                graphics.drawLine(p.x, p.y, x, y);
                p.x = x;
                p.y = y;
                counter.setCount(p.y);
            }
            request.getSession().setAttribute(name, counter);
        }).getImage();
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        try {
            ImageIO.write(image, "JPEG",  response.getOutputStream());
        }catch (IOException e) {

        }
    }
}
