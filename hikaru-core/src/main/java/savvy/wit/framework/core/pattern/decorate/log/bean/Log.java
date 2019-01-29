package savvy.wit.framework.core.pattern.decorate.log.bean;

import savvy.wit.framework.core.base.service.dao.annotation.*;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Log
 * Author : zhoujiajun
 * Date : 2018/10/16 11:17
 * Version : 1.0
 * Description : 
 ******************************/
@Table
public class Log {

    @Column
    @Type(width = 11)
    @Id(auto = true)
    private int id;

    @Column
    @Type(type = CType.VARCHAR, width = 36, vacancy = true)
    private String optTime;

    @Column
    @Type(type = CType.VARCHAR, width = 255, vacancy = true)
    private String optUser;

    @Column
    @Type(type = CType.VARCHAR, width = 1024)
    private String optValue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOptTime() {
        return optTime;
    }

    public void setOptTime(String optTime) {
        this.optTime = optTime;
    }

    public String getOptUser() {
        return optUser;
    }

    public void setOptUser(String optUser) {
        this.optUser = optUser;
    }

    public String getOptValue() {
        return optValue;
    }

    public void setOptValue(String optValue) {
        this.optValue = optValue;
    }
}
