package com.xlauncher.util;

/**
 * Util: Check data length
 * @author mao ye
 * @date 2018-03-08
 */
public class CheckData {

    /**
     * 验证固定长度字符串的数据是否满足条件
     * @param data 数据
     * @param length    字符串需要的满足的长度
     * @return 是否满足条件，true为满足，false为不满足
     */
    public static boolean checkString(String data,int length){
        if (data.length()==length){
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证固定长度的int数据是否满足条件
     * @param data 数据
     * @param length    数据需要满足的长度
     * @return  true为满足，false为不满足
     */
    public static boolean checkint(int data,int length){
        Integer data1 =data;
        if (data1.toString().length()==length){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 验证字符串是否满足长度要求
     * @param data  数据
     * @param minimum   字符串的最小长度
     * @param maximum   字符串的最大长度
     * @return  满足为true，不满足为false
     */
    public static boolean checkString(String data,int minimum,int maximum){
        if (data.length()<=maximum&&data.length()>=minimum){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 验证int变量的数据是否满足长度要求
     * @param data  数据
     * @param minimum   数据的最小长度
     * @param maximum   数据的最大长度
     * @return  满足为true，不满足为false
     */
    public static boolean checkint(int data,int minimum,int maximum){
        Integer data1 =data;
        if (data1.toString().length()<=maximum&&data1.toString().length()>=minimum){
            return true;
        }else {
            return false;
        }
    }
}
