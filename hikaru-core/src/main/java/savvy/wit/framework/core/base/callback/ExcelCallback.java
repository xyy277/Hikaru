package savvy.wit.framework.core.base.callback;

import savvy.wit.framework.core.pattern.adapter.ScExcelAdapter;

import java.util.List;
import java.util.Map;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ExcelCallback
 * Author : zhoujiajun
 * Date : 2018/9/27 18:44
 * Version : 1.0
 * Description : 
 ******************************/
public interface ExcelCallback {


    Object use(List<Map<String, String>> list, ScExcelAdapter adapter);

}
