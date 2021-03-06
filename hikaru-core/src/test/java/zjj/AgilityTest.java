package zjj;

import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.pattern.factory.LogFactory;
import savvy.wit.framework.core.structure.physical.loopStructure.circle.Circle;
import savvy.wit.framework.core.structure.physical.shape.Curve;
import savvy.wit.framework.core.structure.physical.shape.Point;

import java.awt.*;
import java.util.Arrays;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : AgilityTest
 * Author : zhoujiajun
 * Date : 2018/9/29 15:06
 * Version : 1.0
 * Description : 
 ******************************/
public class AgilityTest {
    private static Log log = LogFactory.getLog();

    public static void main(String[] args) {

        Circle circle = new Circle();
        circle.setCenter(new Point(300,300));
        double arc = 555;
        double r = 300;
        double precision = 1;
        double radian = 60;
        // -------------------------------------------------------------
        circle.setArc(arc);
        circle.setR(r);
        circle.setPrecision(precision);
        circle.setRadian(radian);
        Curve curve = circle.calculateCurve();
        log.log(circle.draw( (graphics, image) -> {
            graphics.setColor(Color.gray);
            graphics.fillRect(0,0,600,600);
            graphics.setColor(Color.green);
            Arrays.asList(curve.getPoints()).forEach(point -> {
                graphics.drawLine(point.getX(), point.getY(), circle.getCenter().getX(), circle.getCenter().getY());
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
            log.log(Arrays.asList(curve.getPoints()));
            }, 600, 600, curve, true, "jpg", "awe"));
    }
}
