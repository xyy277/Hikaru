package savvy.wit.framework.core.pattern.proxy;

import savvy.wit.framework.core.base.callback.ExcelImageCallBack;
import savvy.wit.framework.core.base.callback.ExcelMergedRegionCallBack;
import savvy.wit.framework.core.base.callback.ExcelStyleCallBack;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : Excel代理接口
 * File name : AbstractExcelProxy
 * Author : zhoujiajun
 * Date : 2019/7/31 16:30
 * Version : 1.0
 * Description : 
 ******************************/
public interface AbstractExcelProxy {

    /**
     * 格式处理
     * @param mergedRegionCallBack 回调函数
     * @return 代理
     */
    AbstractExcelProxy addMergeRegion(ExcelMergedRegionCallBack mergedRegionCallBack);

    /**
     * 样式处理
     * @param styleCallBack 回调函数
     * @return 代理
     */
    AbstractExcelProxy getCellStyle(ExcelStyleCallBack styleCallBack);

    /**
     * 图片处理
     * @param imageCallBack 回调函数
     * @return 代理
     */
    AbstractExcelProxy processingImg(ExcelImageCallBack imageCallBack);

    /**
     * 生成excel
     * @return 代理
     */
    AbstractExcelProxy produce();
}
