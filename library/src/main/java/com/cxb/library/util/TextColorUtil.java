package com.cxb.library.util;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

/**
 * 功能:改变文字颜色工具类<br>
 * Created by cbw on 2016/1/29.
 */
public
class TextColorUtil {
    /**
     * @param str   文字内容
     * @param color 颜色
     * @param start 开始位置
     * @param end   结束位置
     * @return style 文字样式
     */

    public static SpannableStringBuilder updateTextColor(String str, int color, int start, int end) {
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        style.setSpan(new ForegroundColorSpan(color), start,
                end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return style;

    }
}
