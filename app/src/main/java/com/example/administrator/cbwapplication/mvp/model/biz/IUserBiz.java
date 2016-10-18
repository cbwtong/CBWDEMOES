package com.example.administrator.cbwapplication.mvp.model.biz;

/**
 * 登陆接口 业务逻辑方法基本罗列出来
 * Created by cbw on 2015/12/25.
 */
public
interface IUserBiz {
    void login(String username, String password, OnLoginListener loginListener);
}
