package com.example.administrator.cbwapplication.util;

import android.content.Context;
import android.util.Log;


public class LogUtils {
    private static LogUtils logUtils;
    private static String TAG;
    private boolean isRelease = false;// RELEASE为true表示发行模式，为false是表示调试模式，将不打印任何调试信息
    public final static int DEBUG = 1;// 表示调试模式，将打印调试信息
    public final static int VERBOSE = 2;// 将打印所有信息
    public final static int INFO = 3;// 打印一般提示性的消息
    public final static int WARNING = 4;// 打印警告信息
    public final static int ERROR = 5;// 打印错误信息
    private int model = DEBUG;// 当前调试信息等级

    private LogUtils(Context context) {
        this(context.getPackageName());
    }

    private LogUtils(String TAG) {
        LogUtils.TAG = TAG;
    }

    /**
     * 获得LogUtils实例的静态方法，此时TAG默认值为包名
     *
     * @param context
     * @return
     */
    public static LogUtils getInstance(Context context) {
        if (logUtils == null) {
            synchronized (LogUtils.class) {
                logUtils = new LogUtils(context);
            }
        } else {
            LogUtils.TAG = context.getPackageName();
        }
        return logUtils;
    }

    /**
     * 获得LogUtils实例的静态方法，此时TAG值为设置的值
     *
     * @param TAG
     * @return
     */
    public static LogUtils getInstance(String TAG) {
        if (logUtils == null) {
            synchronized (LogUtils.class) {
                logUtils = new LogUtils(TAG);
            }
        } else {
            LogUtils.TAG = TAG;
        }
        return logUtils;
    }

    /**
     * 设置调试模式，当调试模式为false时将不打印任何日志信息，为true时则默认打印所有的调试信息或根据设置的调试等级打印相应的日志信息
     *
     * @param flag
     */
    public void setDebugModel(boolean flag) {
        if (flag) {
            isRelease = false;
        } else {
            isRelease = true;
        }
    }

    /**
     * 设置打印日志信息的调试等级
     *
     * @param flag
     */
    public void setModel(int lovalModel) {
        if (lovalModel < 1 || lovalModel > 5) {
            this.model = DEBUG;
        } else {
            this.model = lovalModel;
        }
    }

    /**
     * 当调试模式为true时打印调试信息,默认以蓝色调试信息打印
     *
     * @param msg日志信息
     */
    public void print(String msg) {
        if (!isRelease) {
            switch (model) {
                case VERBOSE:
                    Log.v(TAG, msg);
                    break;
                case DEBUG:
                    Log.d(TAG, msg);
                    break;
                case INFO:
                    Log.i(TAG, msg);
                    break;
                case WARNING:
                    Log.w(TAG, msg);
                    break;
                case ERROR:
                    Log.e(TAG, msg);
                    break;
                default:
                    Log.d(TAG, msg);
                    break;
            }
        }
    }

    /**
     * 当调试模式为true时根据设置的调试等级用相应颜色打印日志信息
     *
     * @param model调试等级 (VERBOSE-黑色，DEBUG-蓝色，INFO-绿色，WARNING-黄色，ERROR-红色)
     * @param msg日志信息
     */
    public void print(int localModel, String msg) {
        if (!isRelease) {
            switch (localModel) {
                case VERBOSE:
                    Log.v(TAG, msg);
                    break;
                case DEBUG:
                    Log.d(TAG, msg);
                    break;
                case INFO:
                    Log.i(TAG, msg);
                    break;
                case WARNING:
                    Log.w(TAG, msg);
                    break;
                case ERROR:
                    Log.e(TAG, msg);
                    break;
                default:
                    print(msg);
                    break;
            }
        }
    }
}
