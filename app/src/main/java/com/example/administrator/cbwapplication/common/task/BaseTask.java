package com.example.administrator.cbwapplication.common.task;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;

/**
 * 功能：异步任务基类，实现Runnable接口，调用run()方法后方法可以并发执行<br>
 * Created by cbw on 2015/7/21.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public
abstract class BaseTask extends AsyncTask<Void, Void, Void> implements
        Runnable {

    // log标签
    protected final String TAG = getClass().getSimpleName();

    @Override
    public void run() {
        // api11以上调用并发执行的方法，11以下，默认
        if (Build.VERSION.SDK_INT >= 11) {
            executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            execute();
        }
    }
}
