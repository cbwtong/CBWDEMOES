package com.example.administrator.cbwapplication.mvp.view;


import com.example.administrator.cbwapplication.mvp.model.entity.User;

/**
 * presenter层与view层互通的接口 具体方法根据实际情况来定
 * Created by cbw on 2015/12/25.
 */
public
interface IUserLoginView {

    String getUsername();

    String getPassword();

    void clearUsername();

    void clearpassword();

    void showLoading();

    void hideLoading();

    void toManinActivity(User user);

    void showFailedError();
}
