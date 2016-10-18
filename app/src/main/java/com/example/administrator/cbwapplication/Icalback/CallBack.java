package com.example.administrator.cbwapplication.Icalback;


import java.util.ArrayList;
import java.util.List;

/**
 * 对数据做一些处理以供后面activity调用
 * Created by cbw on 2015/12/25.
 */
public
class CallBack {

    private LoginListener loginListener;
    private User user;
    private List<String> mList;
    private String erron;

    public CallBack() {
        super();
    }

    public void init() {
        user = new User();
        user.setUsername("cbw");
        user.setPassword("123456");

        mList = new ArrayList<>();
        mList.add("one");
        mList.add("two");
        mList.add("three");

        erron = "400";

    }

    void setCallBack(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    public void data() {
        init();
        loginListener.LoginSucess(mList,user);
        loginListener.LoginFailued(erron);
    }

}
