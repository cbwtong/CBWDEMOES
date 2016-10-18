package com.cxb.library.util;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.cxb.library.R;

/**
 * 功能:倒计时功能类<br>
 * 需要注意:1 控件必须使用 TextView （也可以使用其他的 需要将本类中的TextView 替换掉）<br>
 * 2
 * onFinish（）中 的background 需要图片 <br>
 * 3
 * 使用方式
 * 如：TimeCount.getInstance(60000, 1000, tv_send, this).start();<br>
 * <p>
 * Created by cbw on 2016/1/29.
 */
public
class TimeCount extends CountDownTimer {
    private static TimeCount time;
    private Context context;
    private TextView textView;

    public TimeCount(long millisInFuture, long countDownInterval,
                     TextView textView, Context context) {
        super(millisInFuture, countDownInterval);
        this.context = context;
        this.textView = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        textView.setClickable(false);
        textView.setBackground(null);
        textView.setText(millisUntilFinished / 1000 + "秒后重新发送");

    }

    ;

    @Override
    public void onFinish() {
        textView.setText("");
        textView.setClickable(true);
        textView.setBackground(context.getResources().getDrawable(
                R.mipmap.clock1, null));
    }

    /**
     * @param millisInFuture    剩余时间
     * @param countDownInterval 倒计时时间间隔
     * @param textView          显示时间的地方
     * @param context           上下文
     * @return
     */
    public static TimeCount getInstance(long millisInFuture,
                                        long countDownInterval, TextView textView, Context context) {
        if (time == null) {
            time = new TimeCount(millisInFuture, countDownInterval, textView,
                    context);
        }
        return time;
    }

    // 调用的时候直接一句话：TimeCount.getInstance(60000, 1000, tv_send, this).start();
}
