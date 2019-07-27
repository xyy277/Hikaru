package savvy.wit.framework.core.base.callback;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ExcelDataCallBack
 * Author : zhoujiajun
 * Date : 2019/7/26 10:31
 * Version : 1.0
 * Description : 
 ******************************/
public interface ExcelDataCallBack<T> {

    String[] getValues(T t, int count);
}
