package savvy.wit.framework.core.pattern.proxy;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : SqlProxy
 * Author : zhoujiajun
 * Date : 2019/3/22 11:36
 * Version : 1.0
 * Description : 
 ******************************/
public interface SqlProxy {

    /**
     * 代理sql
     * @param sql
     * @return
     */
    SqlProxy proxy(String sql);

    /**
     * 动态设置参数
     * 可以直接put一个数组或者集合用于 in ，将自动转换为（1,2,3）形式
     * @param placeholder
     * @param value
     * @return
     */
    SqlProxy param(String placeholder, Object value);

    /**
     * 开启注入
     * @return
     */
    SqlProxy inject();

    /**
     * 拼接sql
     * 搭配inject开启sql注入模式
     * @param placeholder
     * @param sql
     * @return
     */
    SqlProxy joint(String placeholder, String sql);

    /**
     * 获取sql
     * @return
     */
    String request();
}
