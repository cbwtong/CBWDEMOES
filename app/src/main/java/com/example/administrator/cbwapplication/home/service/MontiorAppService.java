package com.example.administrator.cbwapplication.home.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 监控手机程序开启与关闭服务<br>
 * Created by Administrator on 2016/10/20.
 */
public
class
MontiorAppService extends Service {
    //自定义动作
    public static final String ACTION = "org.leepood.monitordemo.APPS_CHANGED";
    private ActivityManager am = null;
    //保存开启和关闭的程序进程名称列表
    private HashMap<String, Integer> appsStored = null;
    //刚开启的程序标记为0
    private final int STARTED_APP = 0;
    //刚关闭的程序标记为1
    private final int CLOSED_APP = 1;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        Log.i("cbw", "service------->" + "start");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Thread th_monitor = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    appsStored = new HashMap<String, Integer>();

                    /**
                     * 获取正在运行程序名称列表
                     */
                    List<ActivityManager.RunningAppProcessInfo> oldrunningapps =
                            am.getRunningAppProcesses();//获取运行的程序
                    List<String> oldrunningappsprocessnameList = new ArrayList<>();//保存所有正在运行程序的进程名称

                    for (ActivityManager.RunningAppProcessInfo old : oldrunningapps) {
                        oldrunningappsprocessnameList.add(old.processName);
                        //Log.i("cbw", "old------>" + old.processName);
                    }

                    try {
                        Thread.sleep(1000);//休眠1秒钟
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    /**
                     * 再次获取正在运行的程序名称列表
                     */
                    List<ActivityManager.RunningAppProcessInfo> newrunningapps =
                            am.getRunningAppProcesses();//再次获取运行的程序名
                    List<String> newrunningappsprocessnameList = new ArrayList<>();//再次保存所有正在运行程序进程名称

                    for (ActivityManager.RunningAppProcessInfo newapp : newrunningapps) {
                        newrunningappsprocessnameList.add(newapp.processName);
                        //Log.i("cbw", "new----->" + newapp.processName);
                    }

                    /**
                     * 开始对比
                     */
                    for (String newapp : newrunningappsprocessnameList) {
                        //如果新获取的程序在原来获取程序列表中则该程序没有变活,否在为刚启动
                        if (!oldrunningappsprocessnameList.contains(newapp)) {
                            appsStored.put(newapp, STARTED_APP);
                            Log.i("cbw", "newstart------>" + newapp);
                        }
                    }

                    for (String oldapp : oldrunningappsprocessnameList) {
                        //如果以前获取的程序在刚刚获取的程序列表中则改程序没有变化,否则该程序为刚关闭
                        if (!newrunningappsprocessnameList.contains(oldapp)) {
                            appsStored.put(oldapp, CLOSED_APP);
                            Log.i("cbw", "newclose------>" + oldapp);
                        }
                    }

                    //发出广播
                    if (appsStored.size() != 0) {
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("app_info", appsStored);
                        intent.putExtra("bundle", bundle);
                        intent.setAction(ACTION);
                        MontiorAppService.this.sendBroadcast(intent);
                        appsStored = null;
                    }
                }
            }
        });
        th_monitor.start();//启动监控线程
    }
}
