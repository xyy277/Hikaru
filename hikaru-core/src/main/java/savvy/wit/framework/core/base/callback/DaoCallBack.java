package savvy.wit.framework.core.base.callback;/**
 * Created by zhoujiajun on 2018/7/3.
 */

import java.sql.ResultSet;
import java.sql.SQLException;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : DaoCallBack
 * Author : zhoujiajun
 * Date : 2018/7/3 12:48
 * Version : 1.0
 * Description : 
 ******************************/
public interface DaoCallBack<T> {

    T savvy(ResultSet resultSet) throws SQLException;
}
