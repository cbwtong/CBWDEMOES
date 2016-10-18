package com.example.administrator.cbwapplication.mvp.model.impl;


import com.example.administrator.cbwapplication.mvp.model.biz.IUserBiz;


import com.example.administrator.cbwapplication.mvp.model.biz.OnLoginListener;


import com.example.administrator.cbwapplication.mvp.model.entity.User;

/**
 * 登陆接口实现类 处理业务逻辑基本在这个地方
 * Created by cbw on 2015/12/25.
 */
public
class UserBiz implements IUserBiz {
    @Override
    public void login(final String username, final String password, final OnLoginListener loginListener) {
        //模拟子线程耗时操作, 方法内一般包含网络连接、数据解析、业务逻辑等
        new Thread() {

            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //模拟登陆成功
                if ("cbw".equals(username) && "123456".equals(password)) {
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    loginListener.loginSuccess(user);
                } else {
                    loginListener.loginFailed();
                }
            }
        }.start();
    }
}
