package savvy.wit.framework.core.pattern.aop;

import java.lang.reflect.Method;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : AfterMethodAdvice
 * Author : zhoujiajun
 * Date : 2019/3/29 9:58
 * Version : 1.0
 * Description : 
 ******************************/
public interface AfterMethodAdvice extends AfterAdvice {

    void after(Method var1, Object[] var2, Object var3) throws Throwable;

}
