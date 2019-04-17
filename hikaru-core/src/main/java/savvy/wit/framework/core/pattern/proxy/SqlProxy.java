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
     * @param key
     * @param value
     * @return
     */
    SqlProxy param(String key, Object value);

    /**
     * 获取sql
     * @return
     */
    String request();
}