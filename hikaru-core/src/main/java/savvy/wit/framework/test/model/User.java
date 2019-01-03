package savvy.wit.framework.test.model;

import savvy.wit.framework.core.base.interfaces.dao.annotation.*;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : User
 * Author : zhoujiajun
 * Date : 2018/12/24 11:02
 * Version : 1.0
 * Description : 
 ******************************/
@Table
public class User {

    @Id
    @Column
    @Type(type = CType.VARCHAR)
    @Comment("id")
    private String id;

    @Column()
    @Type(type = CType.VARCHAR)
    @Comment("姓名")
    private String name;

    @Column
    @Type(type = CType.VARCHAR)
    @Comment("密码")
    private String password;

    @Column
    @Type(type = CType.VARCHAR)
    @Comment("用户名")
    private String username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
