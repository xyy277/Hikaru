package savvy.wit.framework.core.pattern.aop;


import java.lang.reflect.Method;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : BeforeMethodAdvice
 * Author : zhoujiajun
 * Date : 2019/3/29 9:57
 * Version : 1.0
 * Description : 
 ******************************/
public interface BeforeMethodAdvice extends BeforeAdvice {

    void before(Method var1, Object[] var2, Object var3) throws Throwable;
}
