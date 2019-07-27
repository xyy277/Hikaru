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


    // 表头
    Integer HEADER = 0;

    // 附表头
    Integer HEADER_ = 1;

    // 正文
    Integer BODY = 2;

    // 表足
    Integer FOOTER = 3;

    Map<Integer, HSSFCellStyle> getCellStyle(HSSFWorkbook workbook, Map<Integer, HSSFCellStyle> styles);
}
