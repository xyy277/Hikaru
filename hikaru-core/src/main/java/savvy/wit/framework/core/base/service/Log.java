package savvy.wit.framework.core.base.service;

import savvy.wit.framework.core.base.callback.LogCallBack;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Log
 * Author : zhoujiajun
 * Date : 2018/10/8 17:01
 * Version : 1.0
 * Description : 
 ******************************/
public interface Log {

    void log(LogCallBack callBack);

    void sql(Object o);

    void log(Object o);

    void warn(Object o);

    void error(Exception e);
}
