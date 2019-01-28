package savvy.wit.framework.core.base.interfaces;

import savvy.wit.framework.core.base.interfaces.dao.Pagination;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : condition 条件
 * File name : Cdt
 * Author : zhoujiajun
 * Date : 2018/7/7 22:47
 * Version : 1.0
 * Description :condition组合方式是会按照使用的顺序进行组合，考虑修改为可以乱序使用最终组合（似乎不太符合使用习惯）
 * Cdt的使用方式
 * 可以用于简单的单表查询条件组合
 * 详细使用见 CdtTest、DataTest、TestController类（双shift搜索）
 * TODO: 1、fix 潜在bug  2、完善自定义sql  3、增加复杂关联
 ******************************/
public interface Cdt {

    String getCondition();

    Pagination page();

    Cdt where(String... var);

    Cdt where(String var1, String var2, Object var3);

    Cdt page(Pagination pagination);

    Cdt page (int pageIndex, int pageSize);

    Cdt where(String var1, String var2, Object var3, Cdt cdt);

    Cdt and(String var1, String var2, Object var3);

    Cdt and(String var1, String var2, Object var3, Cdt cdt);

    Cdt order(Object var3, Order order);

    Cdt asc(String... columns);

    Cdt desc(String... columns);

}
