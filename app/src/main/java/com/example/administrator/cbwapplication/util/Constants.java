package com.example.administrator.cbwapplication.util;

/**
 * 功能:静态常量存放<br>
 * Created by cbw on 2015/12/14.
 */
public
class Constants {

    public static final int ERROR_CODE_OK = 200;

    /**
     * 请求数据的方式 xListview状态标识
     */
    public static final int DEFAULT_REQUEST = 0;// 默认请求
    public static final int MORE_REQUEST = 1;// 加载更多请求
    public static final int REFRESH_REQUEST = 2;// 下拉刷新请求

    /**
     * 默认页数
     */
    public static final int DEFAULT_PAGE = 1;
    /**
     * 默认数量
     */
    public static final int DEFAULT_NUM = 10;

    public static final String APP_CONFIG_FILE_NAME = "AppConfig.json";

    public static String MAPID = "v9XO1DszGbO4qTXGaW9NFQZe";

    /**
     * api_user 值
     */
    public static final String api_user = "snail";
    /**
     * api_pwd 值
     */
    public static final String api_pwd = "ao$i8nJ*S2AL";
    /**
     * from 值
     */
    public static final String from = "android";

    /**
     * 连接超时 Handler Message标识
     */
    public static final int CONNECT_TIMEOUT = -1;
    /**
     * 服务器内部错误(服务器错误本来是500,但是这里只有 0 和 404 200的状态码 所以指定 400为服务器内部错误)
     */
    public static final int SERVER_ERROR = 400;
    /**
     * 连接访问成功
     */
    public static final int CONNECT_SUCCESS = 200;
    /**
     * 无网络连接
     */
    public static final int NO_NETWORK = 999;
    /**
     * 网络异常
     */
    public static final int NETWORK_ERROR = 888;
    /**
     * 该条咨询已被其他销售抢到，你已经没有应答权限
     */
    public static final int NO_SEND_PERMISSION = 777;
}
