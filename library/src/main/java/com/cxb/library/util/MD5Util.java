package com.cxb.library.util;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密 解密工具类
 */
public
class MD5Util {
    /**
     * MD5变换 将服务器传过来的MD5加密信息转换为字符串
     */
    public static String Md5(String str) {
        if (str != null && !str.equals("")) {
            try {
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
                        '9', 'a', 'b', 'c', 'd', 'e', 'f'};
                byte[] md5Byte = md5.digest(str.getBytes("UTF8"));
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < md5Byte.length; i++) {
                    sb.append(HEX[(int) (md5Byte[i] & 0xff) / 16]);
                    sb.append(HEX[(int) (md5Byte[i] & 0xff) % 16]);
                }
                str = sb.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    /**
     * MD5加密 32位
     *
     * @param sSecret
     * @return
     */
    public static String MD5_32(String sSecret) {
        if (!TextUtils.isEmpty(sSecret)) {
            try {
                MessageDigest bmd5 = MessageDigest.getInstance("MD5");
                bmd5.update(sSecret.getBytes());
                int i;
                StringBuffer buf = new StringBuffer();
                byte[] b = bmd5.digest();
                for (int offset = 0; offset < b.length; offset++) {
                    i = b[offset];
                    if (i < 0)
                        i += 256;
                    if (i < 16)
                        buf.append("0");
                    buf.append(Integer.toHexString(i));
                }
                return buf.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 对密码进行MD5加密
     *
     * @param pswd_source
     * @return
     */
    public static String eventown_pswd(String pswd_source) {
        String encode = MD5_32(pswd_source) + "eventown";
        return MD5_32(encode);
    }

    /**
     * 对用户名密码进行加密
     *
     * @param username_source
     * @param pswd_source
     * @return
     */
    public static String encrypt_MD5(String username_source, String pswd_source) {
        String decode = MD5_32(pswd_source);
        return MD5_32(username_source + decode);
    }

}
