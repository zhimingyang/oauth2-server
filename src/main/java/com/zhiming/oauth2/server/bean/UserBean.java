package com.zhiming.oauth2.server.bean;

public class UserBean {
    private int id;
    private String uname;
    private String password;
    private String signKey;

    public int getId() {
        return id;
    }

    public String getUname() {
        return uname;
    }

    public String getPassword() {
        return password;
    }

    public String getSignKey() {
        return signKey;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }
}