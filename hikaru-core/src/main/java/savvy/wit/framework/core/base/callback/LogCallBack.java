package savvy.wit.framework.core.base.callback;

import org.aspectj.lang.ProceedingJoinPoint;
import savvy.wit.framework.core.base.annotations.Log;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : LogCallBack
 * Author : zhoujiajun
 * Date : 2018/10/16 15:19
 * Version : 1.0
 * Description : 
 ******************************/
public interface LogCallBack {

    /**
     * 日志回调
     * @param joinPoint 连接点
     * @param log       注解
     * @param result    目标结果
     */
    void execute(ProceedingJoinPoint joinPoint, Log log,Object result);
}
