package savvy.wit.framework.core.pattern.factory;

import savvy.wit.framework.core.base.service.enumerate.EnumValue;
import savvy.wit.framework.core.base.service.enumerate.EnumValueContract;
import savvy.wit.framework.core.base.util.Scanner;
import savvy.wit.framework.core.service.DbConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : DbFactory
 * Author : zhoujiajun
 * Date : 2019/6/20 17:06
 * Version : 1.0
 * Description : 
 ******************************/
public class DbFactory implements DbConfiguration {

    private List<Class<?>> enumClassList = new ArrayList<>();

    protected DbFactory() {
    }

    public static DbConfiguration me() {
        return LazyInit.INITIALIZATION;
    }

    private static class LazyInit {
        private static final DbFactory  INITIALIZATION = new DbFactory();
    }

    public List<Class<?>> getEnumClassList() {
        return enumClassList;
    }

    /**
     * 扫描并设置Enum类，为dao初始化流程之一
     * @param pack
     * @return
     */
    public DbConfiguration setEnumClassList(String... pack) {
        // 扫描Enum中 带EnumValue注解或者实现了EnumValueContract接口
        this.enumClassList.addAll(Scanner.scanning(pack).stream()
                .filter(aClass -> aClass.isAnnotationPresent(EnumValue.class) ? true :
                        Stream.of(aClass.getInterfaces())
                                .filter(aClass1 -> aClass1 == EnumValueContract.class)
                                .collect(Collectors.toList()).size() > 0 ? true : false)
                .collect(Collectors.toList()));
        return LazyInit.INITIALIZATION;
    }

    public void setEnumClassList(List<Class<?>> enumClassList) {
        this.enumClassList = enumClassList;
    }
}
