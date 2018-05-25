package com.nullptr.one.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/23
 * @DESCRIPTION MD5加密工具，用于把图片的url加密为图片文件名
 */
public class MD5Encoder {

    public static String encode(String text) {
        StringBuffer hexString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());
            byte[] hash = md.digest();
            hexString = new StringBuffer();
            //填充字符串(不足的地方补0,凑够位数)
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hexString.toString();
    }

}
