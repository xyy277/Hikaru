package savvy.wit.framework.core.pattern.proxy;

import org.springframework.stereotype.Repository;
import savvy.wit.framework.core.base.callback.DaoCallBack;
import savvy.wit.framework.core.base.service.dao.impl.msql.DaoExcutor;

import java.sql.Connection;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : QueryLink
 * Author : zhoujiajun
 * Date : 2019/3/22 14:49
 * Version : 1.0
 * Description : 
 ******************************/
@Repository("dao")
public class QueryLink<T> extends DaoExcutor<T> implements DaoProxy<T> {

    protected QueryLink(){
        super();
    }

    public static QueryLink init() {
        return LazyInit.INITIALIZATION;
    }

    private static class LazyInit {
        private static QueryLink INITIALIZATION = new QueryLink();
    }

    @Override
    public void execute(Connection connection, String sql) {

    }

    @Override
    public void execute(Connection connection, String sql, DaoCallBack callBack) {

    }
}
