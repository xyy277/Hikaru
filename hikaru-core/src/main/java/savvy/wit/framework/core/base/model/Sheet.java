package savvy.wit.framework.core.base.model;

import java.awt.image.BufferedImage;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : Sheet 页 Model
 * File name : Sheet
 * Author : zhoujiajun
 * Date : 2019/7/31 15:32
 * Version : 1.0
 * Description : 
 ******************************/
public class Sheet {


    /**
     * 页名
     */
    private String name;

    /**
     * 表
     */
    private Table[] tables;

    /**
     * 插图
     */
    private BufferedImage[] images;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Table[] getTables() {
        return tables;
    }

    public void setTables(Table[] tables) {
        this.tables = tables;
    }

    public BufferedImage[] getImages() {
        return images;
    }

    public void setImages(BufferedImage[] images) {
        this.images = images;
    }
}
