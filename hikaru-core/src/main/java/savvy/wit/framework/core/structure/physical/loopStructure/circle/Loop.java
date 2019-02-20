package savvy.wit.framework.core.structure.physical.loopStructure.circle;

import savvy.wit.framework.core.structure.physical.loopStructure.AbstractCircle;
import savvy.wit.framework.core.structure.physical.shape.Curve;

import java.util.Arrays;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 环
 * File name : Loop
 * Author : zhoujiajun
 * Date : 2018/9/26 17:04
 * Version : 1.0
 * Description : 
 ******************************/
public class Loop extends AbstractCircle {

    private double radian;

    private Circle circle;

    private Circle[] circles;

    public double getRadian() {
        return radian;
    }

    public void setRadian(double radian) {
        this.radian = radian;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public Circle[] getCircles() {
        return circles;
    }

    public void setCircles(Circle[] circles) {
        this.circles = circles;
    }

    public Curve calculateCurve() {
        return super.calculateCurve(this.getCenter(), this.getR(), this.getArc(), this.getPrecision(), this.getRadian());
    }

    public Curve calculateCurve(Circle circle) {
        return super.calculateCurve(circle.getCenter(), circle.getR(), circle.getArc(), circle.getPrecision(), circle.getRadian());
    }

    @Override
    public String toString() {
        return "Loop{" +
                "circle=" + circle +
                ", circles=" + Arrays.toString(circles) +
                '}';
    }
}
