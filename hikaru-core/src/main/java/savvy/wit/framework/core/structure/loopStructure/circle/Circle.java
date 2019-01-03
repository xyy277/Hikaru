package savvy.wit.framework.core.structure.loopStructure.circle;

import savvy.wit.framework.core.structure.loopStructure.AbstractCircle;
import savvy.wit.framework.core.structure.shape.Curve;
import savvy.wit.framework.core.structure.shape.Point;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 圆
 * File name : Circle
 * Author : zhoujiajun
 * Date : 2018/9/26 17:00
 * Version : 1.0
 * Description : 
 ******************************/
public class Circle extends AbstractCircle {

    // 弧度 - 确定方向
    private double radian;

    public Circle() {
    }

    public Circle(double radian,Point center, double r, double precision, double arc) {
        this.radian = radian;
        super.setCenter(center);
        super.setR(r);
        super.setArc(arc);
        super.setPrecision(precision);
    }

    public double getRadian() {
        return radian;
    }

    public void setRadian(double radian) {
        this.radian = radian;
    }

    public double calculateAcreage() {
        return super.calculateAcreage();
    }

    public Curve calculateCurve() {
        return super.calculateCurve(this.getCenter(), this.getR(), this.getArc(), this.getPrecision(), this.getRadian());
    }

    public Curve calculateCurve(Point center, double r, double precision, double arc) {
        return super.calculateCurve(center, r, arc, precision, this.radian);
    }

    @Override
    public String toString() {
        return "Circle{" +
                "radian=" + radian +
                "super=" + super.toString() +
                '}';
    }
}
