package savvy.wit.framework.core.pattern.factory;

import savvy.wit.framework.core.base.interfaces.Cdt;
import savvy.wit.framework.core.base.interfaces.impl.CdtImpl;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : CDT
 * Author : zhoujiajun
 * Date : 2018/11/23 11:04
 * Version : 1.0
 * Description : 
 ******************************/
public class CDT {

    public static Cdt NEW() {
        return CdtImpl.init();
    }

    public static Cdt where(String... var) {
        return CdtImpl.init().where(var);
    }

    public static Cdt where(String var1, String var2, Object var3) {
        return CdtImpl.init().where(var1, var2, var3);
    }
}
