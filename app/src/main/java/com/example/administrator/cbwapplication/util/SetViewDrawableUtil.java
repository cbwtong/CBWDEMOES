package com.example.administrator.cbwapplication.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;


/**
 * 设置view图片信息工具类
 */
public class SetViewDrawableUtil {

    public static final int LEFT = 0;
    public static final int TOP = 1;
    public static final int RIGHT = 2;
    public static final int BOTTOM = 3;

    /**
     * 设置view的上、下、左、右图片信息，只能设置单一方向， 目前只支持TextView、EditText
     *
     * @param direction
     * @param view
     * @param drawable
     */
    public static void setViewDrawable(int direction, View view,
                                       Drawable drawable) {
        switch (direction) {
            case LEFT:
                setViewDrawableLeft(view, drawable);
                break;
            case TOP:
                setViewDrawableRight(view, drawable);
                break;
            case RIGHT:
                setViewDrawableTop(view, drawable);
                break;
            case BOTTOM:
                setViewDrawableBottom(view, drawable);
                break;
            default:
                Log.e("SetViewDrawableUtil",
                        "setViewDrawable中direction参数只能是SetViewDrawableUtil的四个常量！");
                break;
        }
    }

    /**
     * 设置View左边的图片
     *
     * @param view
     * @param drawable
     */
    public static void setViewDrawableLeft(View view, Drawable drawable) {
        if (view instanceof TextView) {
            setTextViewDrawableLeft((TextView) view, drawable);
        } else if (view instanceof EditText) {
            setEditTextDrawableLeft((EditText) view, drawable);
        }
    }

    /**
     * 设置TextView左边的图片
     *
     * @param textView
     * @param drawable
     */
    public static void setTextViewDrawableLeft(TextView textView,
                                               Drawable drawable) {
        textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null,
                null);
    }

    /**
     * 设置EditText左边的图片
     *
     * @param editText
     * @param drawable
     */
    public static void setEditTextDrawableLeft(EditText editText,
                                               Drawable drawable) {
        editText.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null,
                null);
    }

    /**
     * 设置View右边的图片
     *
     * @param view
     * @param drawable
     */
    public static void setViewDrawableRight(View view, Drawable drawable) {
        if (view instanceof TextView) {
            setTextViewDrawableRight((TextView) view, drawable);
        } else if (view instanceof EditText) {
            setEditTextDrawableRight((EditText) view, drawable);
        }
    }

    /**
     * 设置TextView右边的图片
     *
     * @param textView
     * @param drawable
     */
    public static void setTextViewDrawableRight(TextView textView,
                                                Drawable drawable) {
        textView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable,
                null);
    }

    /**
     * 设置EditText右边的图片
     *
     * @param editText
     * @param drawable
     */
    public static void setEditTextDrawableRight(EditText editText,
                                                Drawable drawable) {
        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable,
                null);
    }

    /**
     * 设置EditText右边的图片
     *
     * @param editText
     * @param drawable
     */
    public static void setRadioButtonDrawableRight(RadioButton radioButton,
                                                   Drawable drawable) {
        radioButton.setCompoundDrawablesWithIntrinsicBounds(null, null,
                drawable, null);
    }

    /**
     * 设置View上边的图片
     *
     * @param view
     * @param drawable
     */
    public static void setViewDrawableTop(View view, Drawable drawable) {
        if (view instanceof TextView) {
            setTextViewDrawableRight((TextView) view, drawable);
        } else if (view instanceof EditText) {
            setEditTextDrawableRight((EditText) view, drawable);
        }
    }

    /**
     * 设置TextView上边的图片
     *
     * @param textView
     * @param drawable
     */
    public static void setTextViewDrawableTop(TextView textView,
                                              Drawable drawable) {
        textView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null,
                null);
    }

    /**
     * 设置EditText上边的图片
     *
     * @param editText
     * @param drawable
     */
    public static void setEditTextDrawableTop(EditText editText,
                                              Drawable drawable) {
        editText.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null,
                null);
    }

    /**
     * 设置View下边的图片
     *
     * @param view
     * @param drawable
     */
    public static void setViewDrawableBottom(View view, Drawable drawable) {
        if (view instanceof TextView) {
            setTextViewDrawableRight((TextView) view, drawable);
        } else if (view instanceof EditText) {
            setEditTextDrawableRight((EditText) view, drawable);
        }
    }

    /**
     * 设置TextView下边的图片
     *
     * @param textView
     * @param drawable
     */
    public static void setTextViewDrawableBottom(TextView textView,
                                                 Drawable drawable) {
        textView.setCompoundDrawablesWithIntrinsicBounds(null, null, null,
                drawable);
    }

    /**
     * 设置EditText下边的图片
     *
     * @param editText
     * @param drawable
     */
    public static void setEditTextDrawableBottom(EditText editText,
                                                 Drawable drawable) {
        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, null,
                drawable);
    }

    /**
     * 设置View左边和右边的图片
     *
     * @param view
     * @param drawable
     */
    public static void setViewDrawableLeftAndRight(View view,
                                                   Drawable leftDrawable, Drawable rightDrawable) {
        if (view instanceof TextView) {
            setTextViewDrawableLeftAndRight((TextView) view, leftDrawable,
                    rightDrawable);
        } else if (view instanceof EditText) {
            setEditTextDrawableLeftAndRight((EditText) view, leftDrawable,
                    rightDrawable);
        }
    }

    /**
     * 设置TextView左边和右边的图片
     *
     * @param textView
     * @param drawable
     */
    public static void setTextViewDrawableLeftAndRight(TextView textView,
                                                       Drawable leftDrawable, Drawable
                                                               rightDrawable) {
        textView.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null,
                rightDrawable, null);
    }

    /**
     * 设置EditText左边和右边的图片
     *
     * @param editText
     * @param drawable
     */
    public static void setEditTextDrawableLeftAndRight(EditText editText,
                                                       Drawable leftDrawable, Drawable
                                                               rightDrawable) {
        editText.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null,
                rightDrawable, null);
    }

    /**
     * 设置View左边和右边的图片
     *
     * @param view
     * @param drawable
     */
    public static void setViewDrawableLeftAndRight(Context context, View view,
                                                   int leftresource, int rightSource) {
        if (view instanceof TextView) {
            setTextViewDrawableLeftAndRight((TextView) view, context
                    .getResources().getDrawable(leftresource), context
                    .getResources().getDrawable(rightSource));
        } else if (view instanceof EditText) {
            setEditTextDrawableLeftAndRight((EditText) view, context
                    .getResources().getDrawable(leftresource), context
                    .getResources().getDrawable(rightSource));
        }
    }

    /**
     * 设置View右边的图片
     *
     * @param view
     * @param drawable
     */
    public static void setViewDrawableRight(Context context, View view,
                                            int rightSource) {
        if (view instanceof TextView) {
            setTextViewDrawableRight((TextView) view, context.getResources()
                    .getDrawable(rightSource));
        } else if (view instanceof EditText) {
            setEditTextDrawableRight((EditText) view, context.getResources()
                    .getDrawable(rightSource));
        } else if (view instanceof RadioButton) {
            setRadioButtonDrawableRight((RadioButton) view, context
                    .getResources().getDrawable(rightSource));
        }
    }

}
