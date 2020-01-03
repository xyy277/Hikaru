package savvy.wit.framework.core.base.service.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import savvy.wit.framework.core.base.callback.LogCallBack;
import savvy.wit.framework.core.base.callback.LogThrowableCallBack;
import savvy.wit.framework.core.base.util.ClassUtil;
import savvy.wit.framework.core.base.util.StringUtil;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : AbstractLogAspectJ
 * Author : zhoujiajun
 * Date : 2019/12/26 19:17
 * Version : 1.0
 * Description : 
 ******************************/
public abstract class AbstractLogAspectJ {

    /**
     * 定义切入点：对要拦截的方法进行定义与限制，如包、类
     *
     * 1、execution(public * *(..)) 任意的公共方法
     * 2、execution（* set*（..）） 以set开头的所有的方法
     * 3、execution（* savvy.wit.framework.core.base.annotations.LoggerApply.*（..））savvy.wit.framework.core.base.annotations.LoggerApply这个类里的所有的方法
     * 4、execution（* savvy.wit.framework.core.base.annotations.*.*（..））savvy.wit.framework.core.base.annotations包下的所有的类的所有的方法
     * 5、execution（* savvy.wit.framework.core.base.annotations..*.*（..））savvy.wit.framework.core.base.annotations包及子包下所有的类的所有的方法
     * 6、execution(* savvy.wit.framework.core.base.annotations..*.*(String,?,Long)) savvy.wit.framework.core.base.annotations包及子包下所有的类的有三个参数，第一个参数为String类型，第二个参数为任意类型，第三个参数为Long类型的方法
     * 7、execution(@annotation(savvy.wit.framework.core.base.annotations.Log))
     */
    @Pointcut("@annotation(savvy.wit.framework.core.base.annotations.Log)")
    protected void log() {

    }

    /**
     * 前置通知：在目标方法执行前调用
     */
    @Before("log()")
    public void begin() {

    }

    /**
     * 后置通知：在目标方法执行后调用，若目标方法出现异常，则不执行
     */
    @AfterReturning("log()")
    public void afterReturning() {

    }

    /**
     * 后置/最终通知：无论目标方法在执行过程中出现异常都会在它之后调用
     */
    @After("log()")
    public void after() {

    }

    /**
     * 异常通知：目标方法抛出异常时执行
     */
    @AfterThrowing(value = "log()", throwing = "throwable")
    public void afterThrowing(JoinPoint joinPoint, Throwable throwable) {
        handleThrowable(joinPoint, throwable, throwableBack());
    }



    public LogThrowableCallBack throwableBack () {
        return (joinPoint, throwable) -> {

        };
    }

    /**
     *
     * 环绕通知：灵活自由的在目标方法中切入代码
     * @param joinPoint 连接点
     * @throws Throwable 错误
     */
    @Around("log()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 执行源方法
        Object result = joinPoint.proceed();
        handleMessage(joinPoint, logback(), result);
    }

    public LogCallBack logback() {
        return (joinPoint,log,result)-> {

        };
    }

    private void handleMessage(ProceedingJoinPoint joinPoint, LogCallBack callBack, Object result) throws NoSuchMethodException {
        savvy.wit.framework.core.base.annotations.Log log = ClassUtil.me().getDeclaredAnnotation(joinPoint, savvy.wit.framework.core.base.annotations.Log.class);
        /*
        在log id为默认情况下
        动态提供uuid
         */
        Logs logs = new Logs(StringUtil.isBlank(log.id()) ? StringUtil.uuid() : log.id(), log.name(), log.type());
        callBack.execute(joinPoint, logs, result);
    }

    private void handleThrowable(JoinPoint joinPoint, Throwable throwable, LogThrowableCallBack callBack) {

        callBack.execute(joinPoint, throwable);
    }

}
