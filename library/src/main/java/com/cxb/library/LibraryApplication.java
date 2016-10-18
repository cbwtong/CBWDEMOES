package com.cxb.library;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.cxb.library.util.LogUtils;
import com.facebook.drawee.backends.pipeline.Fresco;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能:自定义程序入口,初始化程序<br>
 * Created by cbw on 2016/1/28.
 */
public
class LibraryApplication extends Application {

    static String TAG = LibraryApplication.class.getSimpleName();
    public static LibraryApplication libraryApplication;
    /**
     * 存放活动状态的(未被销毁)的Activity列表
     */
    public static List<Activity> activities = new ArrayList<>();

    @Override
    public void onCreate() {
        if (libraryApplication == null) {
            libraryApplication = this;
        }
        super.onCreate();

        //xUtils初始化
        x.Ext.init(this);
        //初始化Fresco图片异步加载框架
        Fresco.initialize(getContext());
    }

    /**
     * 获得上下文
     */
    public static Context getContext() {
        return libraryApplication.getApplicationContext();
    }

    /**
     * 判断是否有网络连接
     */
    public static boolean isNetworkConnected() {
        Context context = LibraryApplication.getContext();
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;

    }

    /**
     * 添加Activity
     */
    public static void addActivity(Activity activity) {
        activities.add(activity);
        for (Activity activity1 : activities) {
            LogUtils.i("activities", activity1.getComponentName() + "");
        }
    }

    public static void removeListActivity(Activity removeActivity) {
        if (null == removeActivity) {
            return;
        }
        activities.remove(removeActivity);
    }

    /**
     * 退出程序 清空activitys
     */
    public static void exit() {
        LogUtils.i(TAG, String.valueOf(activities.size()));
        for (Activity activity : activities) {
            if (activity != null)
                activity.finish();
        }
        activities.clear();
        System.exit(0);
    }

}
