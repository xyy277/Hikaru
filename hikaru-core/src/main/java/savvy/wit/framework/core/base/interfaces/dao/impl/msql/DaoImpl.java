package savvy.wit.framework.core.base.interfaces.dao.impl.msql;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import savvy.wit.framework.core.base.callback.DaoCallBack;
import savvy.wit.framework.core.base.interfaces.Cdt;
import savvy.wit.framework.core.base.interfaces.Log;
import savvy.wit.framework.core.base.interfaces.dao.Dao;
import savvy.wit.framework.core.base.interfaces.dao.annotation.*;
import savvy.wit.framework.core.base.util.DbUtil;
import savvy.wit.framework.core.base.util.ObjectUtil;
import savvy.wit.framework.core.base.util.Strings;
import savvy.wit.framework.core.pattern.factory.Config;
import savvy.wit.framework.core.pattern.factory.DbFactory;
import savvy.wit.framework.core.pattern.factory.LogFactory;
import savvy.wit.framework.core.pattern.adapter.FileAdapter;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : DaoImpl
 * Author : zhoujiajun
 * Date : 2018/6/29 21:45
 * Version : 1.0
 * Description : 
 ******************************/
@Repository("dao")
public class DaoImpl<T> implements Dao<T> {

    private Log log = LogFactory.getLog();
    private DbUtil db = DbUtil.me();
    private DbFactory dbFactory = DbFactory.me();
    private Config config = Config.init("/json/config.json");

    private DaoImpl() {
    }

    public DaoImpl(String dbSource) {
        Properties properties = new Properties();
        try {
            properties.load(DaoImpl.class.getResourceAsStream(dbSource));

        }catch (IOException e) {

        }
        dbFactory.setProperties(properties);
    }

    @Override
    public void execute(File... files) throws SQLException {
        try {
            for (File file : files) {
                String name = file.getName();
                if (!name.substring(name.length() - 4, name.length()).equals(".sql")) {
                    return;
                }
                List<String> sqls = new ArrayList<>();
                final StringBuilder sql = new StringBuilder();
                FileAdapter.me().readFile(null, false, "utf-8", "utf-8", (bufferedReader, bufferedWriter) -> {
                    String line;
                    boolean collect = true;
                    while ((line = bufferedReader.readLine()) != null) {
                        if (line.indexOf("/*") != -1) {
                            collect = false;
                        }
                        if (line.indexOf("*/") != -1) {
                            collect = true;
                            continue;
                        }
                        if (line.indexOf("--") != -1) {
                            continue;
                        }
                        if (collect) {
                            sql.append(line + "\n");
                        } else {
                            continue;
                        }
                        if (line.indexOf(";") != -1) {
                            sqls.add(sql.toString());
                            sql.delete(0, sql.length());
                        }
                    }
                }, file);
                sqls.forEach(s -> {
                    try {
                        execute(s);
                    } catch (Exception e) {
                        log.error(e);
                    }
                });
            }
        }catch (Exception e) {
            log.error(e);
        }finally {
        }
    }

    @Override
    public void execute(String sql) throws SQLException {
        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
        }catch (Exception e) {
            connection.rollback();
        } finally {
            log.sql(sql);
            db.close(connection,preparedStatement);
        }
    }

    @Override
    public List<T> execute(String sql, DaoCallBack<T> callBack)throws SQLException {
        List<T> callbacks = new ArrayList<>();
        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        try {
            while (resultSet.next()) {
                callbacks.add(callBack.savvy(resultSet));
            }
        }catch (Exception e){
            connection.rollback();
        }finally {
            log.sql(sql);
            resultSet.close();
            db.close(connection,preparedStatement);
            if(callbacks.size() == 0){
                log.log(":: List is empty, please check the database.");
            }
        }
        return callbacks;
    }

    @Override
    public void create(Class... clazz) {
        Arrays.asList(clazz).forEach(aClass -> {
            try {
                create(aClass);
            }catch (SQLException e) {
                log.error(e);
            }
        });
    }

    /**
     * 自动建表
     CREATE TABLE `test_x` (
     `id` int(32) NOT NULL AUTO_INCREMENT,
     `keys_c` varchar(32) DEFAULT NULL,
     `values_c` varchar(32) DEFAULT NULL,
     PRIMARY KEY (`id`)
     ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
     * @param clazz 类型
     * @throws SQLException sql异常
     */
    public void create(Class clazz) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            StringBuilder sql = new StringBuilder("create table if not exists" + decorateName(clazz.getSimpleName()) + "( \n");
            Field key = null;
            for (Field field : clazz.getDeclaredFields()){
                if (field.isAnnotationPresent(Column.class)) {
                    sql.append(decorateName(field.getName()));
                }
                if (field.isAnnotationPresent(Type.class)) {
                    Type type = field.getAnnotation(Type.class);
                    String cType = "";
                    if (type.type() == CType.AUTO) {
                        cType = field.getType().getSimpleName().toLowerCase();
                    }else {
                        cType = type.type().toString().toLowerCase();
                    }
                    int width = type.width();
                    sql.append(decorateField(cType, width));
                    if (type.vacancy()) {
                        sql.append(" DEFAULT NULL ");
                    } else {
                        sql.append(" NOT NULL ");
                    }
                }
                if (field.isAnnotationPresent(Comment.class)) {
                    Comment comment = field.getAnnotation(Comment.class);
                    sql.append(Comment.Parameter.COMMENT.toString() + " '" + comment.value() + "' ");
                }
                if (field.isAnnotationPresent(Id.class)) {
                    Id id = field.getAnnotation(Id.class);
                    boolean auto = id.auto();
                    if (auto) sql.append(" AUTO_INCREMENT ");
                    key = field;
                }
                sql.append(",\n");
            }
            sql.append("PRIMARY KEY (`"+key.getName()+"`)").append("\n )ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;");
            log.log(sql);
            connection = db.getConnection();
            preparedStatement = connection.prepareStatement(sql.toString());
            preparedStatement.execute();
        }catch (Exception e){
            connection.rollback();
            log.error(e);
        }finally {
            db.close(connection,preparedStatement);
        }
    }

    @Override
    public void truncate(Class clazz) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql = "TRUNCATE TABLE "+clazz.getName().toLowerCase();
        try {
            connection = db.getConnection();
            connection .prepareStatement(sql).execute();
        }catch (Exception e){
            connection.rollback();
            log.error(e);
        }finally {
            log.sql(sql);
            db.close(connection,preparedStatement);
        }
    }

    @Override
    public T insert(T t) throws SQLException {
        StringBuilder sql =  createInsertSQL(t.getClass());
        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
        try {
            // insert  into `test_x`(`keys_c`,`values_c`) values ('aaa','bbbb')
            List<String> types = new ArrayList<>();
            List<String> names = new ArrayList<>();
            List<Field> fields = new ArrayList<>();
            Arrays.asList(t.getClass().getDeclaredFields()).forEach(field -> {
                types.add(field.getType().getSimpleName());
                names.add(field.getName());
                fields.add(field);
            });
            for(int var = 0 ; var < names.size(); var++) {
                Object value = ObjectUtil.getValueByFiledName(t,names.get(var));
                if (fields.get(var).isAnnotationPresent(Id.class) &&
                        types.get(var).equals("String") && null == value) {
                    value = UUID.randomUUID().toString().replaceAll("-", "");
                    ObjectUtil.setValueByField(t, fields.get(var), value);
                }
                setValueAdapter(preparedStatement, types.get(var), value, var+1);
            }
            if(preparedStatement.execute()) {
                ObjectUtil.setValueByFieldName(t,"id",preparedStatement.getGeneratedKeys());
            }
        }finally {
            log.sql(preparedStatement != null ? preparedStatement.toString() : "preparedStatement is null");
            log.sql(sql);
            db.close(connection,preparedStatement);
        }
        return t;
    }

    @Override
    public boolean delete(Cdt cdt, Class clazz) throws SQLException {
        boolean success;
        String sql = new String("Delete from " + clazz.getSimpleName().toLowerCase() + cdt.getCondition());
        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            success = preparedStatement.execute();
        }finally {
            log.sql(preparedStatement != null ? preparedStatement.toString() : "preparedStatement is null");
            log.sql(sql);
            db.close(connection,preparedStatement);
        }
        return success;
    }

    @Override
    public boolean update(T t) throws SQLException {
        boolean success = false;
        StringBuilder sql = new StringBuilder("update " + t.getClass().getSimpleName().toLowerCase() + " set ");
        String where = "";
        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            List<String> types = new ArrayList<>();
            List<String> names = new ArrayList<>();
            List<Field> fields = new ArrayList<>();
            Arrays.asList(t.getClass().getDeclaredFields()).forEach(field -> {
                types.add(field.getType().getSimpleName());
                names.add(field.getName());
                fields.add(field);
            });
            for(int var = 0 ; var < names.size(); var++) {
                Object value = ObjectUtil.getValueByFiled(t, fields.get(var));
                if (null == value) {
                    continue;
                }
                if (fields.get(var).isAnnotationPresent(Id.class)) {
                    where = " where " +  names.get(var) + " = '" + value + "'";
                    continue;
                }
                sql.append(names.get(var) + " = '" + value + "', ");
            }
            if(-1 != sql.indexOf(",")){
                sql.replace(sql.lastIndexOf(","),sql.lastIndexOf(",") + 1,""); //去除最后一个 逗号,
            }
            sql.append(where);
            preparedStatement = connection.prepareStatement(sql.toString());
            success = preparedStatement.executeUpdate() > 0;
        }catch (Exception e) {
            log.error(e);
        }finally {
            log.sql(sql);
            db.close(connection, preparedStatement);
        }
        return success;
    }

    @Override
    public boolean update(T t, Cdt cdt) throws SQLException {
        boolean success = false;
        StringBuilder sql = new StringBuilder("update " + t.getClass().getSimpleName().toLowerCase() + " set ");
        String where = "";
        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            List<String> types = new ArrayList<>();
            List<String> names = new ArrayList<>();
            List<Field> fields = new ArrayList<>();
            Arrays.asList(t.getClass().getDeclaredFields()).forEach(field -> {
                types.add(field.getType().getSimpleName());
                names.add(field.getName());
                fields.add(field);
            });
            for(int var = 0 ; var < fields.size(); var++) {
                Object value = ObjectUtil.getValueByFiled(t, fields.get(var));
                if (null == value) {
                    continue;
                }
                if (fields.get(var).isAnnotationPresent(Id.class)) {
                    where = " and " +  names.get(var) + " = '" + value + "'";
                    continue;
                }
                sql.append(names.get(var) + " = '" + value + "', ");
            }
            if(-1 != sql.indexOf(",")){
                sql.replace(sql.lastIndexOf(","),sql.lastIndexOf(",") + 1,""); //去除最后一个 逗号,
            }
            sql.append(cdt.getCondition()).append(where);
            preparedStatement = connection.prepareStatement(sql.toString());
            success = preparedStatement.executeUpdate() > 0;
        }catch (Exception e) {
            log.error(e);
        }finally {
            log.sql(sql);
            db.close(connection, preparedStatement);
        }
        return success;
    }

    @Override
    public T select(Cdt cdt, Class clazz) throws SQLException {
        T t = null;
        String sql = new String("select * from " + clazz.getSimpleName().toLowerCase() + (cdt != null ? cdt.getCondition() : "") );
        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
           t = (T)clazz.newInstance();
            ResultSet resultSet = preparedStatement.executeQuery();
            List<String> types = new ArrayList<>();
            List<String> names = new ArrayList<>();
            Arrays.asList(clazz.getDeclaredFields()).forEach(field -> {
                types.add(field.getType().getSimpleName());
                names.add(field.getName());
            });
            while (resultSet.next()) {
                for(int var = 0 ; var < names.size(); var++) {
                    ObjectUtil.setValue(t, names.get(var), getValueAdapter(resultSet, types.get(var), names.get(var)));
                }
            }
        }catch (Exception e) {

        }finally {
            log.sql(sql);
            db.close(connection,preparedStatement);
        }
        return t;
    }

    @Override
    public List<T> query(Cdt cdt, Class clazz) throws SQLException {
        List<T> list = new ArrayList<>();
        String sql = new String("select * from " + clazz.getSimpleName().toLowerCase() + (cdt != null ? cdt.getCondition() : "") );
        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<String> types = new ArrayList<>();
            List<String> names = new ArrayList<>();
            Arrays.asList(clazz.getDeclaredFields()).forEach(field -> {
                types.add(field.getType().getSimpleName());
                names.add(field.getName());
            });
            while (resultSet.next()) {
                T t = (T)clazz.newInstance();
                for(int var = 0 ; var < names.size(); var++) {
                    ObjectUtil.setValue(t, names.get(var), getValueAdapter(resultSet, types.get(var), names.get(var)));
                }
                list.add(t);
            }
        }catch (Exception e) {
            connection.rollback();
        }finally {
            log.sql(sql);
            db.close(connection,preparedStatement);
        }
        return list;
    }

    @Override
    public List<T> query(Cdt cdt, Class clazz, DaoCallBack<T> callBack) throws SQLException {
        List<T> callbacks = new ArrayList<>();
        String sql = new String("select * from " + clazz.getSimpleName().toLowerCase() + cdt != null ? cdt.getCondition() : "");
        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        try {
            while (resultSet.next()) {
                callbacks.add(callBack.savvy(resultSet));
            }
        }catch (Exception e) {
            connection.rollback();
        }finally {
            log.sql(sql);
            db.close(connection,preparedStatement);
        }
        return callbacks;
    }

    @Override
    public int insertBath(List<T> list, Class clazz) throws SQLException {
        int count = list.size();
        if (count == 1) {
            insert(list.get(0));
            return count;
        }
        StringBuilder sql =  createInsertSQL(clazz);
        Connection connection = db.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
        try {
            // insert  into `test_x`(`keys_c`,`values_c`) values ('aaa','bbbb')
            int index = 0;
            int size = 200 > list.size() ? list.size()/2 : 200;
            //缓存对象的基本属性
            List<String> types = new ArrayList<>();
            List<String> names = new ArrayList<>();
            List<Field> fields = new ArrayList<>();
            Arrays.asList(clazz.getDeclaredFields()).forEach(field -> {
                types.add(field.getType().getSimpleName());
                names.add(field.getName());
                fields.add(field);
            });
            for(T t : list) {
                index ++;
                for(int var = 0 ; var < names.size(); var++) {
                    Object value = ObjectUtil.getValueByFiledName(t,names.get(var));
                    if (fields.get(var).isAnnotationPresent(Id.class) &&
                            types.get(var).equals("String") && null == value) {
                        value = UUID.randomUUID().toString().replaceAll("-", "");
                    }
                    setValueAdapter(preparedStatement, types.get(var), value, var+1);
                }
//                log.log(preparedStatement != null ? preparedStatement.toString() : "preparedStatement is null");
                preparedStatement.addBatch();
                if(index % size == 0) {
                    preparedStatement.executeBatch();
                    preparedStatement.clearBatch();
                }
            }
            preparedStatement.executeBatch();
            preparedStatement.clearBatch();
        }catch (Exception e) {
            connection.rollback();
            log.error(e);
        }
        finally {
            log.sql(sql);
            db.close(connection,preparedStatement);
        }
        return count;
    }

    @Override
    public long count(Class clazz) throws SQLException {
        long count = 0;
        
        return count;
    }

    /**
     * 装载数据
     * @param preparedStatement
     * @param name
     * @param value
     * @param index
     */
    private void setValueAdapter(PreparedStatement preparedStatement,String name, Object value, int index) {
        if (Integer.class.getSimpleName().equals(name)) {
            name = int.class.getSimpleName();
            if (null == value)
                value = 0;
        }
        if (Boolean.parseBoolean(config.getValue("vacancy"))) {
            value = (value == null ? "" : value);
        }
        Object[] values = {index, value};
        ObjectUtil.setValue(preparedStatement,name,values);
    }

    private Object getValueAdapter(ResultSet resultSet, String type, String name) {
        if ("Integer".equals(type)) {
            type = "int";
        }
        Object o = ObjectUtil.getValue(resultSet, type, name);
        return o;
    }

    /**
     * 根据注解生成简单sql脚本
     * @param clazz 类型
     * @return sql
     */
    private StringBuilder createInsertSQL (Class clazz) {
        String table = clazz.getSimpleName();
        StringBuilder builder = new StringBuilder("Insert into " + table.toLowerCase()+" (");
        Field[] fields = clazz.getDeclaredFields();
        int length = 0;
        for(Field field : Arrays.asList(fields).parallelStream()
                .filter(field -> null != field.getAnnotation(Column.class))
                .collect(Collectors.toList())) {
            builder.append(Strings.hump2Line(field.getName())).append(", ");
            length++;
        }
        if(-1 != builder.indexOf(",")){
            builder.replace(builder.lastIndexOf(","),builder.lastIndexOf(",") + 1,""); //去除最后一个 逗号,
        }
        builder.append(")").append(" values (");
        for(int i = 0; i < length; i++) {
            builder.append("?,");
        }
        if(-1 != builder.indexOf(",")){
            builder.replace(builder.lastIndexOf(","),builder.lastIndexOf(",") + 1,""); //去除最后一个 逗号,
        }
        return  builder.append(")");
    }

    private String decorateName (String name) {
        return " `" + Strings.hump2Line(name) + "` ";
    }

    private String decorateField (String cType, int width) {
        return " " + cType + "(" + width + ") ";
    }
}
