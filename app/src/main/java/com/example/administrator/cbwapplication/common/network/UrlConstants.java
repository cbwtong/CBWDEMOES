package com.example.administrator.cbwapplication.common.network;

/**
 * 功能:接口常量接口
 * Created by Administrator on 2015/12/15.
 */
public
interface UrlConstants {

    /**
     * 正式服务器
     */
    String SERVER = "http://140.207.233.208:8082/cdb/";

    /**
     * 本地测试服务器
     */
//
// String SERVER = "http://117.121.49.226:8081/cdb/";

    /**
     * 获取应用列表
     */
    String GET_APPLIST = SERVER + "apporderlist.php";

    /*会唐登陆*/
    String LOGIN_EVENT ="http://www.evtown.com/api/user/login";

}
