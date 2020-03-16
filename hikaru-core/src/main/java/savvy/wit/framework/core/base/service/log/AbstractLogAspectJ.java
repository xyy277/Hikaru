package savvy.wit.framework.core.base.service.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import savvy.wit.framework.core.base.callback.LogCallBack;
import savvy.wit.framework.core.base.callback.LogThrowableCallBack;
import savvy.wit.framework.core.base.util.ClassUtil;
import savvy.wit.framework.core.base.util.StringUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/***************************
 * Title: 抽象Log切面
 * File name: AbstractLogAspectJ
 * Author: zhoujiajun
 * Date: 2019/12/26 17:04
 * Description:
 * 针对ELK日志管理平台搭建服务端日志统一管理模式
 * 实现形式由 各个业务子系统实现
 * 继承与重写切面 实现日志管理
 * 提供日志回调，业务层根据实际业务制定日志管理去向
 ***************************/
public abstract class AbstractLogAspectJ {

    /**
     * 线程池 异步记录日志
     */
    private static ExecutorService logExecutorService =  Executors.newFixedThreadPool(10);

    /**
     * 定义切入点：对要拦截的方法进行定义与限制，如包、类
     *
     */
    @Pointcut("@annotation(savvy.wit.framework.core.base.annotations.Log)")
    protected void log () {

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
     * 任何被切入点定义的方法在运行时发生异常都可捕获到并进行统一回调处理
     * @param joinPoint 连接点
     * @param throwable 异常
     */
    @AfterThrowing(value = "log()", throwing = "throwable")
    public void afterThrowing(JoinPoint joinPoint, Throwable throwable) {
        handleThrowable(joinPoint, throwable, throwableBack());
    }

    /**
     * 环绕通知: 灵活自由的在目标方法中切入代码
     * @param joinPoint 连接点
     * @throws Throwable 异常
     */
    @Around("log()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        // 进行源方法
        Object result = joinPoint.proceed();

        //保存日志 注意如果方法执行错误这不会记录日志
        logExecutorService.submit(() -> {
            try {
                handleMessage(joinPoint, result, logback());
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        });

        return result;
    }

    /**
     * 提供重写的回调
     * @return callback
     */
    public LogCallBack logback() {
        return (joinPoint, log, result) -> {

        };
    }

    /**
     * 提供throwable回调
     * @return callback
     */
    public LogThrowableCallBack throwableBack() {
        return (joinPoint, throwable) -> {

        };
    }

    /**
     * 消息处理
     * @param joinPoint 切入点
     * @param result    结果
     * @param callBack  回调
     * @throws NoSuchMethodException
     */
    private void handleMessage(ProceedingJoinPoint joinPoint, Object result, LogCallBack callBack) throws NoSuchMethodException {
        savvy.wit.framework.core.base.annotations.Log log = ClassUtil.me().getDeclaredAnnotation(joinPoint, savvy.wit.framework.core.base.annotations.Log.class);
        /*
        2020-01-02:add
        用log的实现类，传递参数
        在log id为默认情况下时
        动态提供uuid
         */
        Logs logs = new Logs(StringUtil.isBlank(log.id()) ? StringUtil.uuid() : log.id(), log.name(), log.type());
        callBack.execute(joinPoint, logs, result);
    }

    /**
     * 异常处理
     * @param joinPoint 切入点
     * @param throwable 异常
     * @param callBack  回调
     */
    private void handleThrowable(JoinPoint joinPoint, Throwable throwable, LogThrowableCallBack callBack) {

        callBack.execute(joinPoint, throwable);
    }




}
