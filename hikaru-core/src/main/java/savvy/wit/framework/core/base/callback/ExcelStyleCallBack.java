package savvy.wit.framework.core.base.callback;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.Map;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ExcelStyleCallBack
 * Author : zhoujiajun
 * Date : 2019/7/26 10:31
 * Version : 1.0
 * Description : 
 ******************************/
public interface ExcelStyleCallBack {

    /**
     * 回调设置任意行列单元格样式
     * 目前仅设计在正文处使用，表头处采用全自定方式
     * @param style 样式
     * @param row   行       number
     * @param cell  列       number
     * @param size  总行数
     * @return
     */
    HSSFCellStyle getCellStyle(HSSFCellStyle style, int row, int cell, int size);
}
