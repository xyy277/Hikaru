package savvy.wit.framework.core.base.interfaces.dao;

import savvy.wit.framework.core.base.callback.DaoCallBack;
import savvy.wit.framework.core.base.interfaces.Cdt;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Dao
 * Author : zhoujiajun
 * Date : 2018/6/29 21:45
 * Version : 1.0
 * Description : 
 ******************************/
public interface Dao<T> {

    /**
     * 实体类建表
     * @param clazz 类型
     */
    void create(Class... clazz);

    /**
     * 执行sql 文件
     * 判断是否为.sql文件
     * 执行
     * @param file
     * @throws SQLException
     */
    void execute(File... file) throws SQLException;

    /**
     * 执行普通sql
     * @param sql sql
     * @throws SQLException 异常
     */
    void execute(String sql) throws SQLException;

    /**
     * 执行普通sql,回调返回结果集
     * TODO：增加条件语句
     * @param sql sql
     * @param callBack 回调
     * @return 结果集
     * @throws SQLException 异常
     */
    List<T> execute(String sql, DaoCallBack<T> callBack) throws SQLException;

    /**
     * 根据实体类名清空数据表
     * @param clazz 类型
     * @throws SQLException 异常
     */
    void truncate(Class clazz) throws SQLException;

    /**
     * 插入
     * @param t
     * @return 插入对象
     * @throws SQLException 异常
     */
    T insert(T t) throws SQLException;

    /**
     * 批量插入
     * @param list
     * @return 插入数
     * @throws SQLException 异常
     */
    int insertBath(List<T> list, Class clazz) throws SQLException;

    boolean delete(Cdt cdt, Class clazz) throws SQLException;

    boolean update(T t) throws SQLException;

    boolean update(T t, Cdt cdt) throws SQLException;

    T select(Cdt cdt, Class clazz) throws SQLException;

    List<T> query(Cdt cdt, Class clazz) throws SQLException;

    List<T> query(Cdt cdt, Class clazz, DaoCallBack<T> callBack) throws SQLException;

    long count(Class clazz) throws SQLException;
}
