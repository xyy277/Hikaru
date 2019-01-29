package savvy.wit.framework.core.pattern.factory;

import org.springframework.stereotype.Component;
import savvy.wit.framework.core.base.service.Log;
import savvy.wit.framework.core.base.service.impl.LogImpl;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Test
 * Author : zhoujiajun
 * Date : 2018/6/27 14:41
 * Version : 1.0
 * Description : 
 ******************************/
@Component
public abstract class LogFactory {

    // private
    private static Log log = LogImpl.me();

    public static Log getLog() {
        return log;
    }
}
