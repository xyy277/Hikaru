package savvy.wit.framework.core.base.service.log;

import savvy.wit.framework.core.base.enums.LogType;

import java.lang.annotation.Annotation;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Logs
 * Author : zhoujiajun
 * Date : 2020/1/2 14:42
 * Version : 1.0
 * Description : 
 ******************************/
public class Logs implements savvy.wit.framework.core.base.annotations.Log {

    private String id;

    private Class<?> name;

    private LogType[] types;

    public Logs() {
    }

    public Logs(String id, Class<?> name, LogType[] types) {
        this.id = id;
        this.name = name;
        this.types = types;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(Class<?> name) {
        this.name = name;
    }

    public void setTypes(LogType[] types) {
        this.types = types;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public Class<?> name() {
        return name;
    }

    @Override
    public LogType[] type() {
        return types;
    }

    /**
     * Returns the annotation type of this annotation.
     *
     * @return the annotation type of this annotation
     */
    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
