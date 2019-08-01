package savvy.wit.framework.core.base.model;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 复杂Excel Model
 * File name : Excel
 * Author : zhoujiajun
 * Date : 2019/7/31 15:29
 * Version : 1.0
 * Description : 
 ******************************/
public class Excel {

    /**
     * 总页数
     */
    private int sheetNum;

    /**
     * 总表数
     */
    private int tableNum;

    /**
     * Excel 文件名
     */
    private String name;

    /**
     * 所有页
     */
    private Sheet[] sheets;

    public int getSheetNum() {
        return sheetNum;
    }

    public void setSheetNum(int sheetNum) {
        this.sheetNum = sheetNum;
    }

    public int getTableNum() {
        return tableNum;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sheet[] getSheets() {
        return sheets;
    }

    public void setSheets(Sheet[] sheets) {
        this.sheets = sheets;
    }
}
