package com.xlauncher.dao;

import com.xlauncher.entity.EventAlert;
import com.xlauncher.util.DatetimeUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
/**
 * EventAlertDaoTest
 * @author baishuailei
 * @since 2018-05-30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml","classpath:spring-*.xml"})
@Transactional
public class EventAlertDaoTest {
    @Autowired
    EventAlertDao eventAlertDao;
    private static EventAlert eventAlert;
    @BeforeClass()
    public static void init() {
        eventAlert = new EventAlert();
        eventAlert.setEventId(9999);
        eventAlert.setChannelId("52b00f5c6838454fb098b6e361f2f2bc");
        eventAlert.setEventStartTime(DatetimeUtil.getDate(System.currentTimeMillis()));
        eventAlert.setTypeDescription("非法侵入");
        eventAlert.setEventSource("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2073626099,2224365240&fm=27&gp=36.jpg");
    }
    @Test
    public void insertAlert() throws Exception {
        System.out.println("insertAlert: " + this.eventAlertDao.insertAlert(eventAlert));
    }

    @Test
    public void overview() throws Exception {
        System.out.println("overview: " + this.eventAlertDao.overview());
    }

    @Test
    public void getImgData() throws Exception {
        System.out.println("getImgData: " + this.eventAlertDao.getImgData(1040));
    }
    @Test
    public void getAlertByEventId() throws Exception {
        System.out.println("getAlertByEventId: " + this.eventAlertDao.getAlertByEventId(1001));
    }
    @Test
    public void updateImgData() throws Exception {
        EventAlert eventAlertUp = new EventAlert();
        eventAlertUp.setEventId(1013);
        byte[] bytes = new byte[1];
        eventAlertUp.setEventData(bytes);
        System.out.println("updateImgData: " + this.eventAlertDao.updateImgData(eventAlertUp));
    }

    @Test
    public void listNotCheckAlertForExcel() throws Exception {
        System.out.println("listNotCheckAlertForExcel: " + this.eventAlertDao.listNotCheckAlertForExcel("undefined","undefined","undefined","undefined","undefined","undefined"));
    }

    @Test
    public void listNotCheckAlert() throws Exception {
        System.out.println("listNotCheckAlert: " + this.eventAlertDao.listNotCheckAlert("00:00:01","23:59:59","undefined","undefined","undefined","undefined","undefined","undefined",1));
    }

    @Test
    public void pageNotCheckCount() throws Exception {
        System.out.println("pageNotCheckCount: " + this.eventAlertDao.pageNotCheckCount("00:00:01","23:59:59","undefined","undefined","undefined","undefined","undefined","undefined"));
    }

    @Test
    public void listCheckAlertForExcel() throws Exception {
        System.out.println("listCheckAlertForExcel: " + this.eventAlertDao.listCheckAlertForExcel("undefined","undefined","undefined","undefined","undefined","undefined","undefined","undefined","undefined","undefined","undefined"));
    }

    @Test
    public void listCheckAlert() throws Exception {
        System.out.println("listCheckAlert: " + this.eventAlertDao.listCheckAlert("undefined","undefined","undefined","undefined","undefined","undefined","undefined","undefined","undefined","undefined","undefined",1));
    }

    @Test
    public void pageCheckCount() throws Exception {
        System.out.println("pageCheckCount: " + this.eventAlertDao.pageCheckCount("undefined","undefined","undefined","undefined","undefined","undefined","undefined","undefined","undefined","undefined","undefined"));
    }

    @Test
    public void updateEventCheck() throws Exception {
        EventAlert eventAlertUp = new EventAlert();
        eventAlertUp.setEventId(1001);
        eventAlertUp.setTypeRectify("错误类型纠正类型");
        eventAlertUp.setEventCheck("已复核");
        eventAlertUp.setEventEndTime(DatetimeUtil.getDate(System.currentTimeMillis()));
        eventAlertUp.setEventReviewer("launcher001");
        System.out.println("updateEventCheck: " + this.eventAlertDao.updateEventCheck(eventAlertUp));
    }

    @Test
    public void getCheckTypeStatusCount() throws Exception {
        System.out.println("getCheckTypeStatusCount: " + this.eventAlertDao.getCheckTypeStatusCount("undefined"));
    }

    @Test
    public void getCheckTypeStatusCountWeek() throws Exception {
        System.out.println("getCheckTypeStatusCountWeek: " + this.eventAlertDao.getCheckTypeStatusCountWeek("undefined",3));
    }

    @Test
    public void getCheckTypeStatusCountMonth() throws Exception {
        System.out.println("getCheckTypeStatusCountMonth: " + this.eventAlertDao.getCheckTypeStatusCountMonth("undefined",30));
    }

    @Test
    public void getCheckTypeStatusCountQuarter() throws Exception {
        System.out.println("getCheckTypeStatusCountQuarter: " + this.eventAlertDao.getCheckTypeStatusCountQuarter("undefined",1));
    }

    @Test
    public void getCheckTypeStatusCountYear() throws Exception {
        System.out.println("getCheckTypeStatusCountYear: " + this.eventAlertDao.getCheckTypeStatusCountYear("undefined",1));
    }

    @Test
    public void getNotCheckCount() throws Exception {
        System.out.println("getNotCheckCount: " + this.eventAlertDao.getNotCheckCount());
    }

    @Test
    public void getNotCheckEvent() throws Exception {
        System.out.println("getNotCheckEvent: " + this.eventAlertDao.getNotCheckEvent("undefined","undefined","undefined"));
    }
}