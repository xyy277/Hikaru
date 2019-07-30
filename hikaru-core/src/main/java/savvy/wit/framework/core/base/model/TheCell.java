package savvy.wit.framework.core.base.model;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Cell
 * Author : zhoujiajun
 * Date : 2019/7/29 10:01
 * Version : 1.0
 * Description : 
 ******************************/
public class TheCell {

    // 行坐标
    private int row;

    // 列坐标
    private int cell;

    // 样式
    private TheCellStyle cellStyle;

    // 值
    private String value;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    public TheCellStyle getCellStyle() {
        return cellStyle;
    }

    public void setCellStyle(TheCellStyle cellStyle) {
        this.cellStyle = cellStyle;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
