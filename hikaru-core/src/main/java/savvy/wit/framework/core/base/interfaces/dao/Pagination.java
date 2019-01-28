package savvy.wit.framework.core.base.interfaces.dao;

import java.io.Serializable;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Page
 * Author : zhoujiajun
 * Date : 2019/1/27 21:43
 * Version : 1.0
 * Description : 
 ******************************/
public class Pagination implements Serializable {

    private int pageIndex;

    private int pageSize;

    private int total;

    private String[] sorts;


    public String limit() {
        return " limit " + (pageIndex - 1) * pageSize + " , " + pageSize;
    }

    public int size() {
        return (int)Math.floor(total/pageSize) + 1;
    }

    private Pagination(){}

    public static Pagination NEW() {
        return new Pagination();
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String[] getSorts() {
        return sorts;
    }

    public void setSorts(String[] sorts) {
        this.sorts = sorts;
    }
}
