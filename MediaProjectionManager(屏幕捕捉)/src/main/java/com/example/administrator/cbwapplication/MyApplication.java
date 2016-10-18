package com.example.administrator.cbwapplication;


import android.app.Application;



import org.xutils.x;

/**
 *
 * Created by cbw on 2015/12/29.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //xutils初始化
        x.Ext.init(this);
    }
}
