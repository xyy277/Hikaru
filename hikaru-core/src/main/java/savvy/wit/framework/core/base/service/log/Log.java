package savvy.wit.framework.core.base.service.log;

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

    void sql(Object o);

    void log(Object o);

    void warn(Object o);

    void error(Exception e);

    Log print(String... string);

    Log println(String... string);

    void println(Object... objects);

    void print(Object... objects);

    void front(String front);

    void behind(String behind);
}
