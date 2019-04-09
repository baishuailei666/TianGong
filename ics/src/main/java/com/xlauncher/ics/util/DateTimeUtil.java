package com.xlauncher.ics.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author :baisl
 * @Email :baishuailei@xlauncher.io
 * @Date :2018/12/24 0024
 * @Desc :获得系统当前时间
 **/
public class DateTimeUtil {
    private final static String FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获得系统当前时间
     *
     * @param strDate strDate
     * @return String
     */
    static String getDate(long strDate){
        SimpleDateFormat format =  new  SimpleDateFormat(FORMAT);
        java.util.Date date = new java.util.Date();
        return format.format(date.getTime());

    }

    /**
     * 得到String via Timestamp
     *
     * @param timestamp java.sql.Timestamp object
     * @return Format datetime string
     */
    public static String stampToDate(String timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT);
        if (timestamp == null) {
            timestamp = String.valueOf(System.currentTimeMillis());
        }
        return simpleDateFormat.format(new Date(Long.parseLong(String.valueOf(timestamp))));
    }

    /**
     * 得到java.sql.Timestamp
     *
     * @param strDate Date of String type
     * @return java.sql.Timestamp object
     */
    public static Timestamp getTimestamp(String strDate) {
        Timestamp timestamp = null;
        try {
            timestamp = new Timestamp(new SimpleDateFormat(FORMAT).parse(strDate).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }
}
