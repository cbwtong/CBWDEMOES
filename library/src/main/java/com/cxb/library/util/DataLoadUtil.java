package com.cxb.library.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cxb.library.R;

/**
 * 加载数据的进度条
 */
@SuppressLint("NewApi")
public
class DataLoadUtil {

    private boolean isClose;
    private Context context;
    private Dialog dialog;

    public void showloadingView(Context context) {
        LogUtils.i("------showloadingView------", ((Activity) context).getLocalClassName());
        this.context = context;
        isClose = false;
        View view = View.inflate(context, R.layout.new_loading, null);
        view.setEnabled(false);
        view.setFocusable(false);
        view.setFocusableInTouchMode(false);
        keepTime();// 计时
        dialog = new Dialog(context, R.style.Translucent_NoTitle);
        dialog.setOnCancelListener(null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void showloadingView(Context context, String text) {
        this.context = context;
        isClose = false;
        View view = View.inflate(context, R.layout.new_loading, null);
        TextView tv_load = (TextView) view
                .findViewById(R.id.search_place_loading_text);
        tv_load.setText(text);
        view.setEnabled(false);
        view.setFocusable(false);
        view.setFocusableInTouchMode(false);
        keepTime();// 计时
        dialog = new Dialog(context, R.style.Translucent_NoTitle);
        dialog.setOnCancelListener(null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    /**
     * 设置进度条数据加载结束(能获取到网络数据后必须调用此方法)
     */
    public void setLoadEnd() {
        if (dialog != null) {
            dialog.dismiss();
        }
        isClose = true;
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (!isClose) {
                // Toast.makeText(context, "网络繁忙,请稍后再试！", 0).show();
                Log.e("DataLoadUtil", "网络繁忙,请稍后再试！");
                if (dialog != null) {
                    dialog.dismiss();
                }
                isClose = true;
            }
        }

        ;
    };

    private void keepTime() {
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(15 * 1000);// 设置15秒为超时
                    handler.sendEmptyMessage(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public boolean isClose() {
        return isClose;
    }

    public void setClose(boolean isClose) {
        this.isClose = isClose;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

}
