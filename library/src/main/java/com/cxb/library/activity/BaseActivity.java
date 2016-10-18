package com.cxb.library.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cxb.library.LibraryApplication;

import org.xutils.x;

import java.lang.ref.WeakReference;


/**
 * 功能:Activity基类<br>
 * Created by cbw on 2016/1/28.
 */
public
abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 获取每个界面class的名字 方便打印信息使用
     */
    protected final String TAG = getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注入视图

        x.view().inject(this);

        LibraryApplication.addActivity(this);

        init();
    }

    /**
     * 初始化
     *
     * @param
     */
    public abstract void init();


    @Override
    public void onClick(View v) {
        onClickEvent(v);
    }

    /**
     * onClick方法的封装，在此方法中处理点击
     *
     * @param view 被点击的View对象
     */
    abstract public void onClickEvent(View view);

    /**
     * handler与activity、fragment的生命周期不一样，activity退出时handler引用的activity阻止GC
     * 对Activity的回收 容易引起内存泄露
     * 使用弱引用处理handler避免activity引起的内存泄露
     *
     * @param <T>
     */
    public static class MyHandle<T extends BaseActivity> extends Handler {
        final WeakReference<T> mActivity;

        public MyHandle(T t) {
            mActivity = new WeakReference<>(t);
        }

        public void handleMessage(Message msg) {
            T activity = mActivity.get();
            if (activity != null) {
                activity.handleReturnMessage(msg);
            }
        }
    }

    public abstract void handleReturnMessage(Message msg);


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LibraryApplication.removeListActivity(this);
    }
}