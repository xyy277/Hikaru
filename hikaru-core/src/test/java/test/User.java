package test;

import java.math.BigDecimal;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : User
 * Author : zhoujiajun
 * Date : 2019/7/30 10:30
 * Version : 1.0
 * Description : 
 ******************************/
public class User {
    private String birthday;
    private BigDecimal name;
    private BigDecimal nickname;
    private BigDecimal username;
    private BigDecimal password;
    private BigDecimal total;

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getNickname() {
        return nickname;
    }

    public void setNickname(BigDecimal nickname) {
        this.nickname = nickname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public BigDecimal getName() {
        return name;
    }

    public void setName(BigDecimal name) {
        this.name = name;
    }

    public BigDecimal getUsername() {
        return username;
    }

    public void setUsername(BigDecimal username) {
        this.username = username;
    }

    public BigDecimal getPassword() {
        return password;
    }

    public void setPassword(BigDecimal password) {
        this.password = password;
    }
}
