package com.xlauncher.util;

import org.junit.Test;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class TodoUtilTest {
    @Test
    public void registerTodo() throws Exception {
        DecimalFormat df = new DecimalFormat("#.0000");
        int all = 15;
        int count = 10;
        System.out.println(df.format(count / all));
        double percent = Double.parseDouble(df.format((double) count / all));
        System.out.println(percent * 100 + "%");

        double x = 3.14159;
        System.out.println(new DecimalFormat("#.##%").format(x));

        int diliverNum=3;//举例子的变量
        int queryMailNum=9;//举例子的变量
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        String result = numberFormat.format((float)diliverNum/(float)queryMailNum*100);
        System.out.println("diliverNum和queryMailNum的百分比为:" + result + "%");

        Date date = new Date();
        SimpleDateFormat dateFm = new SimpleDateFormat("MMMM");
        String monthNow = dateFm.format(date);
        System.out.println(date);
        System.out.println(monthNow);

        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改

        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        String[] months = {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月", };
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        String monthTest = months[month];
        System.out.println(monthTest);
        int week = c.get(Calendar.WEEK_OF_MONTH);
        String weekNow = weekDays[week];
        System.out.println(week + weekNow);
        int date1 = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        System.out.println(year + "/" + month + "/" + date1 + " " +hour + ":" +minute + ":" + second);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        System.out.println(w);
    }

}