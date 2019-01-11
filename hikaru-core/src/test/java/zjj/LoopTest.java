package zjj;

import org.junit.Before;
import org.junit.Test;
import savvy.wit.framework.core.base.interfaces.Log;
import savvy.wit.framework.core.base.util.ImageUtil;
import savvy.wit.framework.core.pattern.factory.LogFactory;
import savvy.wit.framework.core.structure.loopStructure.circle.Circle;
import savvy.wit.framework.core.structure.loopStructure.circle.Loop;
import savvy.wit.framework.core.structure.shape.Curve;
import savvy.wit.framework.core.structure.shape.Point;

import java.awt.*;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 如何画一个圆
 * File name : LoopTest
 * Author : zhoujiajun
 * Date : 2018/9/26 17:19
 * Version : 1.0
 * Description : 
 ******************************/
public class LoopTest {
    private Log log = LogFactory.getLog();

    private Loop loop;

    private Circle circle;

    private Curve curve;

    /**
     * 提供一些必备参数
     */
    @Before
    public void before() {
        loop = new Loop();
        circle = new Circle();
        curve = new Curve();
        Point center = new Point();
        double r = 200;
        center.setX(300);
        center.setY(300);
        circle.setCenter(center);
        circle.setR(r);
        circle.setArc(120);
        circle.setRadian(10);
        circle.setPrecision(0);
        loop.setCircle(circle);
    }

//    @Test
    public void test() {

        curve = loop.calculateCurve();
//        log.log(Arrays.asList(curve.getPoints()));

        /**
         * 自定义画法
         */
        ImageUtil.me().draw(800,800,1,(graphics) -> {
            graphics.setColor(Color.white);graphics.drawString(".",curve.getPoints()[0].getX(), curve.getPoints()[0].getY());
            for (int var = 1; var+1 < curve.getPoints().length; var++) {
                graphics.drawLine(curve.getPoints()[var].getX(), curve.getPoints()[var].getY(), curve.getPoints()[var+1].getX(), curve.getPoints()[var+1].getY());
            }
            graphics.drawLine(curve.getPoints()[0].getX(), curve.getPoints()[0].getY(), curve.getPoints()[1].getX(), curve.getPoints()[1].getY());
            graphics.drawLine(curve.getPoints()[curve.getPoints().length-1].getX(), curve.getPoints()[curve.getPoints().length-1].getY(), curve.getPoints()[0].getX(), curve.getPoints()[0].getY());
        }).save("jpg","www");


    }

}
