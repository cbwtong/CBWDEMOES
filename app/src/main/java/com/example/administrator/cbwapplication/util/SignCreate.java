package com.example.administrator.cbwapplication.util;


import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;


/**
 * 网络请求
 * Sign参数 签名生成
 *
 * @author cbw
 */
public
class SignCreate {
    /**
     * 请求参数Sign 签名生成
     *
     * @param parms 参数列表数组 如: String arrays = {"id=1","name=abc","age=3"};
     *              SignCreate.CreateSignature(arrays);//
     * @return 生成的签名(Sigin参数)
     */
    public static String CreateSignature(String[] parms) {
        if (parms == null) {
            return "";
        }
        Arrays.sort(parms);
        StringBuffer sb = new StringBuffer(Constants.api_pwd);
        for (int i = 0; i < parms.length; i++) {
            sb.append(parms[i]);
            if (i <= parms.length - 2) {
                sb.append("&");
            }
        }
        sb.append(Constants.api_pwd);
        try {
            String md5 = CrcUtil.MD5(sb.toString());
            return md5;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 请求参数Sign 签名生成
     *
     * @param parms 参数列表数组 如: String arrays = {"id=1","name=abc","age=3"};
     *              SignCreate.CreateSignature(arrays);//
     * @return 生成的签名(Sigin参数)
     */
    public static String newCreateSignature(HashMap<String, String> hashMap) {
        if (hashMap == null) {
            return "";
        }
        // 指定排序器(返回的数据是按key值进行排序)
        String[] params = changHashMapToTreeMap(hashMap);
        // for (int i = 0; i < params.length; i++) {
        // LogUtils.getInstance("debug").print(
        // "------params[" + i + "]-----" + params[i]);
        // }
        StringBuffer sb = new StringBuffer(Constants.api_pwd);
        for (int i = 0; i < params.length; i++) {
            sb.append(params[i]);
            if (i <= params.length - 2) {
                sb.append("&");
            }
        }
        sb.append(Constants.api_pwd);

        // LogUtils.getInstance("debug").print("------sb-----" + sb);
        try {
            String md5 = CrcUtil.MD5(sb.toString());
            return md5;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // 获得按key值顺序排列好的字符串数组
    public static String[] changHashMapToTreeMap(HashMap<String, String> hashMap) {
        String[] params = new String[hashMap.size()];
        TreeMap<String, String> treeMap = new TreeMap<String, String>(
                new Comparator<String>() {
                    /*
                     * int compare(Object o1, Object o2) 返回一个基本类型的整型， 返回负数表示：o1
                     * 大于o2， 返回0 表示：o1和o2相等， 返回正数表示：o1小于o2。
                     */
                    public int compare(String o1, String o2) {
                        // 指定排序器按照升序排列
                        return o1.compareTo(o2);
                    }
                });
        Set<String> keySet = hashMap.keySet();
        for (String key : keySet) {
            String value = hashMap.get(key);
            treeMap.put(key, value);
        }

        Iterator<String> it = treeMap.keySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            // it.next()得到的是key，tm.get(key)得到obj
            String key = it.next();
            String value = treeMap.get(key);
            params[i] = key + "=" + value;
            i++;
        }
        return params;
    }

}
