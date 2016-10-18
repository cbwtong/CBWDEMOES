package com.example.administrator.cbwapplication.common.network;


import com.example.administrator.cbwapplication.util.Constants;
import com.example.administrator.cbwapplication.util.SignCreate;
import com.example.administrator.cbwapplication.util.SmartLog;

import org.xutils.http.RequestParams;

import java.util.HashMap;

/**
 * 功能:请求参数存储类
 * Created by cbw on 2015/12/16.
 */
public
class Params {

    private static final String TAG = "HttpRequest";

    /**
     * 获得加密签名sign参数
     */
    public static String getSign() {
        HashMap<String, String> maps = new HashMap<>();
        maps.put("api_user", Constants.api_user);
        maps.put("from", Constants.from);
        //签名sign参数
        String sign = SignCreate.newCreateSignature(maps);
        return sign;
    }

    /**
     * 对http请求进行参数配置
     */
    public static RequestParams getRequestParams(String url) {
        RequestParams requestParams = new RequestParams(url);
        //缓存存活时间
        requestParams.setCacheMaxAge(10 * 1000);
        //请求超时时间
        requestParams.setConnectTimeout(5 * 1000);
        return requestParams;
    }

    /**
     * 配置请求参数
     *
     * @param username
     * @param password
     * @return
     */
    public static RequestParams getMyParams(int type, int pageNum, int count, String phnum) {
        //RequestParams requestParams = new RequestParams(UrlConstants.GET_APPLIST);
        RequestParams requestParams = getRequestParams(UrlConstants.GET_APPLIST);
        requestParams.addParameter("sign", getSign());

        requestParams.addParameter("type", type);
        requestParams.addParameter("page", pageNum);
        requestParams.addParameter("count", count);
        requestParams.addParameter("tel", phnum);

        //或者这么写 GET方法时
        /*RequestParams requestParams = new RequestParams(UrlConstants.GET_APPLIST +
                "?sign=" + getSign() + "&username=" + "username" + "&password=" + "password");*/

        SmartLog.i(TAG, "请求url参数 = " + requestParams.getStringParams());

        return requestParams;
    }

    public static RequestParams login(String username, String user_pwd) {
        //RequestParams requestParams = new RequestParams(UrlConstants.GET_APPLIST);
        RequestParams requestParams = getRequestParams(UrlConstants.LOGIN_EVENT);
        //requestParams.addParameter("sign", getSign());

        requestParams.addParameter("username", username);
        requestParams.addParameter("user_pwd", user_pwd);
        requestParams.addParameter("models", android.os.Build.BRAND + " "
                + android.os.Build.MODEL);

        //或者这么写 GET方法时
        /*RequestParams requestParams = new RequestParams(UrlConstants.GET_APPLIST +
                "?sign=" + getSign() + "&username=" + "username" + "&password=" + "password");*/

        SmartLog.i(TAG, "请求url参数 = " + requestParams.getStringParams());

        return requestParams;
    }


}
