package savvy.wit.framework.core.pattern.aop.aspectj;

import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.pattern.aop.BeforeMethodAdvice;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.lang.reflect.Method;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 后置通知
 * File name : BeforeAdviceAspectJ
 * Author : zhoujiajun
 * Date : 2019/3/29 9:45
 * Version : 1.0
 * Description : 
 ******************************/
public class BeforeAdviceAspectJ implements BeforeMethodAdvice {

    private Log log = LogFactory.getLog();

    @Override
    public void before(Method var1, Object[] var2, Object var3) throws Throwable {

    }
}
