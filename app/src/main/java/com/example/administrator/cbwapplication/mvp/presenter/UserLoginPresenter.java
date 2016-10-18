package com.example.administrator.cbwapplication.mvp.presenter;


import android.os.Handler;


import com.example.administrator.cbwapplication.mvp.model.biz.IUserBiz;


import com.example.administrator.cbwapplication.mvp.model.biz.OnLoginListener;


import com.example.administrator.cbwapplication.mvp.model.entity.User;
import com.example.administrator.cbwapplication.mvp.model.impl.UserBiz;
import com.example.administrator.cbwapplication.mvp.view.IUserLoginView;

/**
 * Presenter是用作Model和View之间交互的桥梁 此demo主要有两个功能login和clear
 * Created by cbw on 2015/12/25.
 */
public
class UserLoginPresenter {

    private IUserBiz userBiz;
    private IUserLoginView userLoginView;
    private Handler mHandler = new Handler();

    public UserLoginPresenter(IUserLoginView userLoginView) {
        this.userLoginView = userLoginView;
        this.userBiz = new UserBiz();
    }

    public void login() {
        userLoginView.showLoading();
        userBiz.login(userLoginView.getUsername(), userLoginView.getPassword(), new OnLoginListener() {

            @Override
            public void loginSuccess(final User user) {
                //需要在UI线程执行
                mHandler.post(new Runnable() {


                    @Override
                    public void run() {
                        //对应反馈
                        userLoginView.toManinActivity(user);
                        userLoginView.hideLoading();
                    }
                });
            }

            @Override
            public void loginFailed() {
                //需要在UI线程中执行
                mHandler.post(new Runnable() {


                    @Override
                    public void run() {
                        userLoginView.showFailedError();
                        userLoginView.hideLoading();
                    }
                });
            }
        });
    }

    public void clear() {
        userLoginView.clearUsername();
        userLoginView.clearpassword();
    }

}
