package savvy.wit.framework.core.base.interfaces;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : condition 条件
 * File name : Cdt
 * Author : zhoujiajun
 * Date : 2018/7/7 22:47
 * Version : 1.0
 * Description : 
 ******************************/
public interface Cdt {
    String getCondition();

    Cdt where(String... var);

    Cdt where(String var1, String var2, Object var3);

    Cdt where(String var1, String var2, Object var3, Cdt cdt);

    Cdt and(String var1, String var2, Object var3);

    Cdt and(String var1, String var2, Object var3, Cdt cdt);

    Cdt orderBy(Object var3);

}
