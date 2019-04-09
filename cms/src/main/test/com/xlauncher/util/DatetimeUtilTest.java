package com.xlauncher.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;
@RunWith(JUnit4.class)
public class DatetimeUtilTest {
    @Test
    public void getDate() throws Exception {
        System.out.println(DatetimeUtil.getDate(System.currentTimeMillis()));
    }

    @Test
    public void getTimestamp() throws Exception {
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calNow = Calendar.getInstance();
        Date now = calNow.getTime();
        System.out.println("now: "+ df.format(now));
        for (int i=0; i<30; i++) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -i);
            Date date = cal.getTime();
            System.out.println(i + " : "+ df.format(date));
        }
        calNow.add(Calendar.DATE, 4);
        now=calNow.getTime();
        System.out.println("now+4 : " + df.format(now));
    }

    @Test
    public void getDate1() throws Exception {
        System.out.println(DatetimeUtil.getDate(System.currentTimeMillis()));
        System.out.println(DatetimeUtil.getDate(DatetimeUtil.getDate(System.currentTimeMillis())));
        System.out.println(DatetimeUtil.getDate("2018-05-08 17:30:25"));
    }

    @Test
    public void getFormatDate() throws Exception {
        System.out.println(DatetimeUtil.stampToDate("1536314893000"));
    }

    @Test
    public void getFormateDate() throws Exception {
    }

}