package zjj;

import org.junit.Before;
import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.pattern.factory.LogFactory;
import savvy.wit.framework.core.structure.physical.loopStructure.circle.Loop;
import savvy.wit.framework.core.structure.physical.shape.Curve;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : JPanelTest
 * Author : zhoujiajun
 * Date : 2018/10/16 15:28
 * Version : 1.0
 * Description : 
 ******************************/
public class JPanelTest {

    private Log log = LogFactory.getLog();
    private JPanel panel = new JPanel();
    private Loop loop = new Loop();
    @Before
    public void before() {
        loop.setRadian(90);
        loop.setR(200);
        loop.setCenter(100,-100);
        loop.setArc(100);
        loop.setPrecision(1);
    }


//    @Test
    public void test() {
        Curve curve = loop.calculateCurve();
        log.log(() -> Arrays.asList(curve.getPoints()));
        loop.drawUSelf(graphics -> {
            graphics.setColor(Color.BLACK);
            graphics.fillRect(0,0,600,600);
            graphics.setColor(Color.WHITE);
            graphics.drawString(".",curve.getPoints()[0].getX(), curve.getPoints()[0].getY());
            for (int var = 1; var+1 < curve.getPoints().length; var++) {
                graphics.drawLine(
                        curve.getPoints()[var].getX(), curve.getPoints()[var].getY(),
                        curve.getPoints()[var+1].getX(), curve.getPoints()[var+1].getY());
            }
        },400,400, true, "jpg", "test/a");
    }
}
