package com.gsafety.hikaru.api.image;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.base.util.DateUtil;
import savvy.wit.framework.core.base.util.ImageUtil;
import savvy.wit.framework.core.base.util.JsonUtil;
import savvy.wit.framework.core.pattern.decorate.Counter;
import savvy.wit.framework.core.pattern.factory.LogFactory;
import savvy.wit.framework.core.structure.physical.loopStructure.circle.Circle;
import savvy.wit.framework.core.structure.physical.shape.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

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
@Api(value = "image控制器", description = "后台图片Api")
public class ImageController {

    private static final int WIDTH = 480;
    private static final int HEIGHT = 360;
    private Log log = LogFactory.getLog();

    @ApiOperation(value = "获取图片", notes = "根据名称随机生成图片")
    @RequestMapping(value = "/{name}/{time}", method = RequestMethod.GET)
    public void image(@ApiParam(value = "图片名称") @PathVariable String name,
                      @ApiParam(value = "时间") @PathVariable String time,
                      HttpServletRequest request, HttpServletResponse response) {
        int excursion = 10;
        Object object = request.getSession().getAttribute(name);
        final Counter counter = object != null ? (Counter) object : Counter.create();
        BufferedImage image = ImageUtil.me().draw(WIDTH,HEIGHT, 1, (graphics,bufferedImage) -> {
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

    @RequestMapping(value = "/{width}/{height}/{radian}/{arc}/{r}",method = RequestMethod.GET)
    public void test(@PathVariable int width, @PathVariable int height, @PathVariable int arc, @PathVariable int r,
                    @PathVariable int radian, @RequestParam String point, HttpServletResponse response) {
        Circle circle = new Circle();
        circle.setCenter(JsonUtil.fromJson(point, savvy.wit.framework.core.structure.physical.shape.Point.class));
        double precision = 1;
        // -------------------------------------------------------------
        circle.setArc(arc);
        circle.setR(r);
        circle.setPrecision(precision);
        circle.setRadian(radian);
        Curve curve = circle.calculateCurve();
        Image image = circle.draw( (graphics,bufferedImage) -> {
            graphics.setColor(Color.gray);
            graphics.fillRect(0,0,width,height);
            graphics.setColor(Color.green);
            Arrays.asList(curve.getPoints()).forEach(p -> {
                graphics.drawLine(p.getX(), p.getY(), circle.getCenter().getX(), circle.getCenter().getY());
            });
            if (arc < 360) {
                graphics.drawLine(
                        curve.getPoints()[0].getX(), curve.getPoints()[0].getY(),
                        curve.getPoints()[1].getX(), curve.getPoints()[1].getY());
                graphics.drawLine(
                        curve.getPoints()[curve.getPoints().length-1].getX(),
                        curve.getPoints()[curve.getPoints().length-1].getY(),
                        curve.getPoints()[0].getX(),
                        curve.getPoints()[0].getY());
            }
            graphics.setColor(Color.black);
        }, width, height, curve, false);

        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        try {
            ImageIO.write((BufferedImage)image, "JPEG",  response.getOutputStream());
        }catch (IOException e) {

        }
    }
}
