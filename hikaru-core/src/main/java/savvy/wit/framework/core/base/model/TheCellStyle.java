package savvy.wit.framework.core.base.model;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : TheCellStyle
 * Author : zhoujiajun
 * Date : 2019/7/29 14:46
 * Version : 1.0
 * Description : 
 ******************************/
public class TheCellStyle {

    private short alignment;

    private short fillPattern;

    private short fillForegroundColor;

    private short verticalAlignment;

    public TheCellStyle() {
    }

    public TheCellStyle(short alignment, short fillPattern, short fillForegroundColor, short verticalAlignment) {
        this.alignment = alignment;
        this.fillPattern = fillPattern;
        this.fillForegroundColor = fillForegroundColor;
        this.verticalAlignment = verticalAlignment;
    }

    public short getAlignment() {
        return alignment;
    }

    public void setAlignment(short alignment) {
        this.alignment = alignment;
    }

    public short getFillPattern() {
        return fillPattern;
    }

    public void setFillPattern(short fillPattern) {
        this.fillPattern = fillPattern;
    }

    public short getFillForegroundColor() {
        return fillForegroundColor;
    }

    public void setFillForegroundColor(short fillForegroundColor) {
        this.fillForegroundColor = fillForegroundColor;
    }

    public short getVerticalAlignment() {
        return verticalAlignment;
    }

    public void setVerticalAlignment(short verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
    }
}
