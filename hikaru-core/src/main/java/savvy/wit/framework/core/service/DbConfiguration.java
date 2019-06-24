package savvy.wit.framework.core.service;

import java.util.List;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : DbConfiguration
 * Author : zhoujiajun
 * Date : 2019/6/20 17:13
 * Version : 1.0
 * Description : 
 ******************************/
public interface DbConfiguration {

    List<Class<?>> getEnumClassList();

    DbConfiguration setEnumClassList(String... pack);

    void setEnumClassList(List<Class<?>> enumClassList);
}
