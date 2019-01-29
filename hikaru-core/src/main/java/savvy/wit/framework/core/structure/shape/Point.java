package savvy.wit.framework.core.structure.shape;

import java.io.Serializable;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 点
 * File name : Point
 * Author : zhoujiajun
 * Date : 2018/9/26 16:54
 * Version : 1.0
 * Description : 
 ******************************/
public class Point implements Serializable {

    private int x;

    private int y;

    public Point() {
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
