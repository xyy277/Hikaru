package savvy.wit.framework.core.pattern.aop.aspectj;

import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.pattern.aop.AfterMethodAdvice;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.lang.reflect.Method;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 前置通知
 * File name : AfterAdviceAspectJ
 * Author : zhoujiajun
 * Date : 2019/3/29 9:46
 * Version : 1.0
 * Description : 
 ******************************/
public class AfterAdviceAspectJ implements AfterMethodAdvice {

    private Log log = LogFactory.getLog();

    @Override
    public void after(Method var1, Object[] var2, Object var3) throws Throwable {

    }
}
