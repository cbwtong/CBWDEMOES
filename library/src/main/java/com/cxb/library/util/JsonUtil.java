package com.cxb.library.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能:JSON工具类<br>
 * Created by cbw on 2015/12/14.
 */
public
class JsonUtil {

    static Gson gson;

    static {
        gson = new Gson();
    }

    /**
     * 获取的 Code 状态码
     *
     * @return 状态码 200 ：响应成功 400：错误
     */
    public static String getJsonCode(String jsonStr) {
        try {
            JSONObject jsonObject = new JSONObject(jsonStr.substring(jsonStr.indexOf("{")));
            String code = (String) jsonObject.get("msgcode");
            return code;
        } catch (JSONException e) {
            LogUtils.i("getJsonCode", "---error---jsonStr-----" + jsonStr);
            e.printStackTrace();
        }
        return "200";
    }

    /**
     * 根据Key值查找到 对应key的 Json字符串
     *
     * @param jsonStr JSON字符串
     * @param key     Json中的key
     * @return key对应的 JSON字符串 Value值
     */
    public static String getKeyFindJsonStr(String jsonStr, String key) {
        try {
            JSONObject jsonObject = new JSONObject(jsonStr.substring(jsonStr.indexOf("{")));
            String str = jsonObject.getString(key);
            return str;
        } catch (JSONException e) {
            LogUtils.i("getKeyFindJsonStr", "------jsonStr-----" + jsonStr);
            LogUtils.i("getKeyFindJsonStr", "------key-----" + key);
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 把 JSON 字符串 转换成 实体类
     *
     * @param jsonStr JSON字符串
     * @param clazz   实体类字节码
     * @return 实体类
     */
    public static <T> T toEntity(String jsonStr, Class<T> clazz) {
        try {
            if (jsonStr == null || jsonStr.trim().length() < 1) {
                return null;
            }
            T fromJson = gson.fromJson(jsonStr.substring(jsonStr.indexOf("{")), clazz);
            return fromJson;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            LogUtils.i("JsonUtil",e.getMessage());
            return null;
        }
    }

    /**
     * 把 JSON 字符串 转换成 实体集合
     *
     * @param jsonStr JSON字符串
     * @param tt      TypeToken对象
     * @return 返回转换后的 集合
     * @code 示例代码： String data = JsonUtil.getKeyFindJsonStr(result,"data");
     * List<Order> orderList = JsonUtil.toList(data,new
     * TypeToken<List<Order>>(){});
     */
    public static <T> List<T> toList(String jsonStr, TypeToken<List<T>> tt) {
        try {
            if (jsonStr == null || jsonStr.trim().length() < 1 || jsonStr.equals("")) {
                return null;
            }
            Type type = tt.getType();
            List<T> list = gson.fromJson(jsonStr.substring(jsonStr.indexOf("{")), type);
            return list;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 把 JSON 字符串 转换成 实体集合
     *
     * @param jsonStr JSON字符串
     * @param tt      TypeToken对象
     * @return 返回转换后的 集合
     * @code 示例代码： String data = JsonUtil.getKeyFindJsonStr(result,"data");
     * List<Order> orderList = JsonUtil.toList(data,new
     * TypeToken<List<Order>>(){});
     */
    public static <T> ArrayList<T> toArrayList(String jsonStr,
                                               TypeToken<List<T>> tt) {
        try {
            if (jsonStr == null || jsonStr.trim().length() < 1) {
                return null;
            }
            Type type = tt.getType();
            ArrayList<T> list = gson.fromJson(jsonStr.substring(jsonStr.indexOf("{")), type);
            return list;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

}