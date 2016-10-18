package com.example.administrator.cbwapplication.Icalback;



import java.util.List;

/**
 * 接口回调 接口
 * Created by cbw on 2015/12/25.
 */
public interface LoginListener {
    void LoginSucess(List<String> list,User user);

    void LoginFailued(String errno);
}
