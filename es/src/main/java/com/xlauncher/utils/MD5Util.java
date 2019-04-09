package com.xlauncher.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密算法工具类
 * @author 张霄龙
 * @since 2018-03-19
 */
public class MD5Util {

    /**
     * 对字符串md5加密（大写+数字）
     * @param s
     * @return
     */
    public static String MD5(String s) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            /*注意：切记在将字符串转换为字节数组的时候一定要指定编码格式，
            否则如果默认会导致调用的时候编码格式不一致而导致加密结果异常*/
            byte[] btInput = s.getBytes("UTF-8");
            //获得MD5摘要算法的MessageDigest对象
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            //使用指定的字节更新摘要
            messageDigest.update(btInput);
            //获得密文
            byte[] sResult = messageDigest.digest();
            //把密文转换成十六进制的字符串形式
            int j = sResult.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = sResult[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对字符串进行加密（小写+字母）
     * @param str 传入要加密的字符串
     * @return MD5加密后的字符串
     */
    public static String getMD5(String str) {
        try {
            //生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            //计算MD5函数
            try {
                md.update(str.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            /**
             * digest()最后确定返回MD5 hash值，返回值为8位字符串。
             * 因为MD5 hash值是16位的hex值，实际上就是8位的字符
             * BigInteger函数则将8位的字符串转换成16位的hex值，用字符串来表示；
             * 得到字符串形式的hash值
             */
            return new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
