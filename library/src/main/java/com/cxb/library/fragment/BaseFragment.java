package com.cxb.library.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.x;

import java.lang.ref.WeakReference;

/**
 * 功能:fragment基类<br>
 * Created by cbw on 2016/1/28.
 */
public
abstract class BaseFragment extends Fragment implements View.OnClickListener {

    private boolean injected = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }

        init();
    }

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

    public abstract void init();

    /**
     * handler与activity、fragment的生命周期不一样，activity退出时handler引用的activity阻止GC
     * 对Activity的回收 容易引起内存泄露
     * 使用弱引用处理handler避免activity引起的内存泄露
     *
     * @param <T>
     */
    public static class MyHandle<T extends BaseFragment> extends Handler {
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

}
