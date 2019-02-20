package savvy.wit.framework.core.structure.physical.loopStructure;

import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.pattern.factory.LogFactory;
import savvy.wit.framework.core.base.callback.DrawImageCallBack;
import savvy.wit.framework.core.base.util.ImageUtil;
import savvy.wit.framework.core.structure.physical.function.Acreage;
import savvy.wit.framework.core.structure.physical.function.LoopFactory;
import savvy.wit.framework.core.structure.physical.shape.Curve;
import savvy.wit.framework.core.structure.physical.shape.Point;

import java.awt.*;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : AbstractCircle
 * Author : zhoujiajun
 * Date : 2018/9/26 16:52
 * Version : 1.0
 * Description : 
 ******************************/
public abstract class AbstractCircle implements Acreage, LoopFactory{

    private Log log = LogFactory.getLog();

    /**
     * 确定坐标系
     */

    // 精度 0 - 1
    private double precision;

    private final int ZERO = 0;

    private double START =  - 90;

    private final double PI = Math.PI;

    private Point center;

    private double r;

    // 弧度  - 确定大小
    private double arc;

    private Curve curve;

    public double getPrecision() {
        return precision;
    }

    public void setPrecision(double precision) {
        this.precision = precision < 0 ? 0 : precision > 1 ? 1 :precision;
    }

    public double getArc() {
        return arc;
    }

    public void setArc(double arc) {
        this.arc = arc;
    }

    public Curve getCurve() {
        return curve;
    }

    public void setCurve(Curve curve) {
        this.curve = curve;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(int x, int y) {
        this.center = new Point(x,y);
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    @Override
    public double calculateAcreage() {
       return PI * r * r;
    }

    @Override
    public Curve calculateCurve(Point center, double r, double arc, double precision,  double radian) {
        Curve curve = new Curve();
        try {
            double R = 36 + precision * 31;
            START = START + radian;
            arc = arc > 360 ? 360 : arc;
            Point start = calculatePoint(center, r, radian);
            Point end = calculatePoint(center, r, radian + arc);
            // 等分
            Point[] points = new Point[transform(R) + 1];
            points[0] = center;
            points[1] = start;
            points[points.length-1] = end;
            for (int var = 2 ; var < points.length - 1; var++) {
                double radians = radian + arc * var / points.length ;
                Point point = calculatePoint(center, r, radians);
                points[var] = point;
            }
            curve.setPoints(points);
        }catch (Exception e) {
            log.error(e);
        }
        return curve;
    }

    @Override
    public String toString() {
        return "AbstractCircle{" +
                "START=" + START +
                ", center=" + center +
                ", r=" + r +
                ", arc=" + arc +
                ", curve=" + curve +
                '}';
    }

    /**
     * 精确度求解
     * 满足点集的下标步进速度为 step
     * step = 1 / precision
     * cosA1 - cosA2 = step / r
     * An =
     * @param precision
     * @return
     */
    private int precison(double precision) {

        return ZERO;
    }


    private Point calculatePoint(Point center, double r, int step) {
        Point point = new Point();
        return point;
    }
    /**
     * 起始点
     * @param r
     * @param radian
     * @return
     */
    private Point calculatePoint(Point center, double r, double radian) {
        Point start = new Point();
        while (radian < 0) {
            radian += 360;
        }
        while (radian > 360) {
            radian -= 360;
        }
        if (radian < 90) {
            start.setX(transform(center.getX() - r * cos(radian) ));
            start.setY(transform(center.getY() - r * sin(radian)));

        } else if (radian == 90) {
            start.setX(center.getX());
            start.setY(transform(center.getY() - r));

        } else if (radian < 180) {
            radian = 180 - radian;
            start.setX(transform(r * cos(radian) + center.getX()));
            start.setY(transform(center.getY() - r * sin(radian)));

        } else if (radian == 180) {
            start.setX(transform(r + center.getX()));
            start.setY(center.getY());

        } else if (radian < 270) {
            radian = radian - 180;
            start.setX(transform(r * cos(radian) + center.getX()));
            start.setY(transform(center.getY() + r * sin(radian)));

        } else if (radian == 270) {
            start.setX(center.getX());
            start.setY(transform(r + center.getY()));

        } else if (radian < 360) {
            radian = 360 - radian;
            start.setX(transform(center.getX() - r * cos(radian)));
            start.setY(transform(center.getY() + r * sin(radian)));

        } else if (radian == 0) {
            start.setX(transform(center.getX() - r));
            start.setY(center.getY());
        }
        return start;
    }

    private int transform(double x) {
        return (int)Math.ceil(x);
    }

    private double sin (double x) {
        return Math.sin( 2 * PI * x / 360 );
    }

    private double cos (double x) {
        return Math.cos( 2 * PI * x / 360 );
    }

    @Override
    public Image draw(int width, int height, Curve curve, boolean save, String... param) {
        return  save ?
                ImageUtil.me().draw(width, height,1,(graphics) -> {
                    graphics.setColor(Color.WHITE);
                    graphics.drawString(".", curve.getPoints()[0].getX(), curve.getPoints()[0].getY());
                    for (int var = 1; var+1 < curve.getPoints().length; var++) {
                        graphics.drawLine(
                                curve.getPoints()[var].getX(), curve.getPoints()[var].getY(),
                                curve.getPoints()[var+1].getX(), curve.getPoints()[var+1].getY());
                    }
                }).save(param).getImage() :
                ImageUtil.me().draw(width, height,1,(graphics) -> {
                    graphics.setColor(Color.WHITE);
                    graphics.drawString(".", curve.getPoints()[0].getX(), curve.getPoints()[0].getY());
                    for (int var = 1; var+1 < curve.getPoints().length; var++) {
                        graphics.drawLine(
                                curve.getPoints()[var].getX(), curve.getPoints()[var].getY(),
                                curve.getPoints()[var+1].getX(), curve.getPoints()[var+1].getY());
                    }
                }).getImage();
    }

    @Override
    public void draw(DrawImageCallBack callBack, int width, int height, Curve curve, String... param) {
        ImageUtil.me().draw(width, height,1,(graphics) -> {
            callBack.draw(graphics);
            graphics.drawString(".", curve.getPoints()[0].getX(), curve.getPoints()[0].getY());
            for (int var = 1; var+1 < curve.getPoints().length; var++) {
                graphics.drawLine(
                        curve.getPoints()[var].getX(), curve.getPoints()[var].getY(),
                        curve.getPoints()[var+1].getX(), curve.getPoints()[var+1].getY());
            }
        }).save(param);
    }

    /**
     * 画好curve后，再提供自定义画法，由你做主
     * @param callBack
     * @param width
     * @param height
     * @param curve
     * @param save
     * @param param
     * @return
     */
    @Override
    public Image draw(DrawImageCallBack callBack, int width, int height, Curve curve, boolean save, String... param) {
        return  save ?
                ImageUtil.me().draw(width, height,1,(graphics) -> {
                    callBack.draw(graphics);
                    graphics.drawString(".", curve.getPoints()[0].getX(), curve.getPoints()[0].getY());
                    for (int var = 1; var+1 < curve.getPoints().length; var++) {
                        graphics.drawLine(
                                curve.getPoints()[var].getX(), curve.getPoints()[var].getY(),
                                curve.getPoints()[var+1].getX(), curve.getPoints()[var+1].getY());
                    }
                }).save(param).getImage() :
                ImageUtil.me().draw(width, height,1,(graphics) -> {
                    callBack.draw(graphics);
                    graphics.drawString(".", curve.getPoints()[0].getX(), curve.getPoints()[0].getY());
                    for (int var = 1; var+1 < curve.getPoints().length; var++) {
                        graphics.drawLine(
                                curve.getPoints()[var].getX(), curve.getPoints()[var].getY(),
                                curve.getPoints()[var+1].getX(), curve.getPoints()[var+1].getY());
                    }
                }).getImage();
    }

    /**
     * 你自己画
     * @param callBack
     * @param width
     * @param height
     * @param save
     * @param param
     * @return
     */
    public Image drawUSelf(DrawImageCallBack callBack, int width, int height, boolean save, String... param) {
        return  save ?
                ImageUtil.me().draw(width, height,1,(graphics) -> {
                    callBack.draw(graphics);
                }).save(param).getImage() :
                ImageUtil.me().draw(width, height,1,(graphics) -> {
                    callBack.draw(graphics);
                }).getImage();

    }
}
