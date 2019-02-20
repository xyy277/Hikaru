package savvy.wit.framework.core.structure.physical.function;

import savvy.wit.framework.core.base.callback.DrawImageCallBack;
import savvy.wit.framework.core.structure.physical.shape.Curve;
import savvy.wit.framework.core.structure.physical.shape.Point;

import java.awt.*;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : loop
 * Author : zhoujiajun
 * Date : 2018/9/27 10:42
 * Version : 1.0
 * Description : 
 ******************************/
public interface LoopFactory {


    /**
     * 精确度计算，由半径大小决定
     * 中心点确定位置
     * 半径决定大小
     * 弧度确定curve
     * @param center 中心
     * @param r 半径
     * @param arc 大小
     * @param precision 精度
     * @param radian 方向
     * @return
     */
    Curve calculateCurve(Point center, double r, double arc, double precision, double radian);

    void draw(DrawImageCallBack callBack, int width, int height, Curve curve, String... param);

    Image draw(int width, int height, Curve curve, boolean save, String... param);

    Image draw(DrawImageCallBack callBack, int width, int height, Curve curve, boolean save, String... param);

}
