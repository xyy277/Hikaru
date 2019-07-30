package savvy.wit.framework.core.base.callback;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ExcelImageCallBack
 * Author : zhoujiajun
 * Date : 2019/7/27 7:54
 * Version : 1.0
 * Description : 
 ******************************/
public interface ExcelImageCallBack {

    /**
     * 该构造函数有8个参数
     * 前四个参数是控制图片在单元格的位置，分别是图片距离单元格left，top，right，bottom的像素距离
     * 后四个参数，前两个表示图片左上角所在的cellNum和 rowNum，后两个参数对应的表示图片右下角所在的cellNum和 rowNum，
     * excel中的cellNum和rowNum的index都是从0开始的
     * new HSSFClientAnchor(0, 0, 0, 0, (short) 1, 1, (short) 2, 2);
     * @param num       sheet number
     * @param anchors   图片位置数组
     */
    HSSFClientAnchor[] ProcessingImage(int num, HSSFClientAnchor[] anchors);
}
