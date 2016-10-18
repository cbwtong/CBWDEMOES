package com.example.administrator.cbwapplication.Icalback;


import com.example.administrator.cbwapplication.common.entity.BaseEntity;

/**
 * Created by Administrator on 2015/12/25.
 */
public
class User extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private String username;

    private String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
