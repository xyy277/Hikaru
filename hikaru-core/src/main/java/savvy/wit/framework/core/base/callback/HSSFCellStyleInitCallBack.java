package savvy.wit.framework.core.base.callback;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 正文单元格样式初始化
 * File name : HSSFCellStyleInitCallBack
 * Author : zhoujiajun
 * Date : 2019/8/6 11:48
 * Version : 1.0
 * Description : 由于Excel本身对同一种样式的单元格有数量上的限制，所以需要手动创建合适数量的单元格样式
 * 根据实际模板进行初始化
 ******************************/
public interface HSSFCellStyleInitCallBack {

    /**
     * 初始化单元格样式
     * @param workbook
     * @param styles
     * @return
     */
    List<HSSFCellStyle> initStyle(HSSFWorkbook workbook, List<HSSFCellStyle>styles);
}
