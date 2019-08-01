package savvy.wit.framework.core.base.model;

import java.util.Map;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 表格
 * File name : Table
 * Author : zhoujiajun
 * Date : 2019/7/31 15:32
 * Version : 1.0
 * Description : 
 ******************************/
public class Table {

    /**
     * 附表头
     */
    private String[] titles;

    /**
     * 起始行
     */
    private int startRow;

    /**
     * 起始列
     */
    private int startCell;

    private Map<String, Object> data;

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String[] getTitles() {
        return titles;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getStartCell() {
        return startCell;
    }

    public void setStartCell(int startCell) {
        this.startCell = startCell;
    }
}
