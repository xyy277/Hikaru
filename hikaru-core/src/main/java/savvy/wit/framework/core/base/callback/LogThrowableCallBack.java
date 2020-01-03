package savvy.wit.framework.core.base.callback;

import org.aspectj.lang.JoinPoint;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : LogThrowableCallBack
 * Author : zhoujiajun
 * Date : 2020/1/3 15:43
 * Version : 1.0
 * Description : 
 ******************************/
public interface LogThrowableCallBack {


    void execute(JoinPoint joinPoint, Throwable throwable);
}
