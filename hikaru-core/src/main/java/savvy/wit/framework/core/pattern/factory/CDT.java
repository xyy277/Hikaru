package savvy.wit.framework.core.pattern.factory;

import savvy.wit.framework.core.base.service.cdt.Cdt;
import savvy.wit.framework.core.base.service.dao.Pagination;
import savvy.wit.framework.core.base.service.cdt.impl.CdtImpl;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : condition
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

    public static Cdt page(Pagination pagination, String... vars) {
        Cdt cdt = CdtImpl.init();
        cdt.where(vars).page(pagination);
        return cdt;
    }
}
