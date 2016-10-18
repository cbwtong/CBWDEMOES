package com.example.administrator.cbwapplication.mvp.model.biz;


import com.example.administrator.cbwapplication.mvp.model.entity.User;

/**
 * 登陆成功与否监听接口
 * Created by cbw on 2015/12/25.
 */
public interface OnLoginListener {

    void loginSuccess(User user);

    void loginFailed();

}
