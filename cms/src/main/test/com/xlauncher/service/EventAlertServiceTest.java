package com.xlauncher.service;

import com.xlauncher.dao.UserDao;
import com.xlauncher.entity.Device;
import com.xlauncher.entity.EventAlert;
import com.xlauncher.entity.User;
import com.xlauncher.util.DatetimeUtil;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
/**
 * EventAlertServiceTest
 * @author baishuailei
 * @since 2018-05-30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml","classpath:spring-*.xml"})
@Transactional
public class EventAlertServiceTest {
    @Autowired
    EventAlertService eventAlertService;
    @Autowired
    UserDao userDao;
    private static EventAlert eventAlert;
    private static String token = "token is for test";
    @BeforeClass()
    public static void init() {
        eventAlert = new EventAlert();
        eventAlert.setEventId(9999);
        eventAlert.setChannelId("db684861d9b34252a380b4e4af63caaa");
        eventAlert.setEventStartTime(DatetimeUtil.getDate(System.currentTimeMillis()));
        eventAlert.setTypeDescription("非法侵入");
        eventAlert.setEventSource("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2073626099,2224365240&fm=27&gp=test.jpg");
    }
    @Before
    public void beTest() {
        User user = new User();
        user.setUserName("Test Case");
        user.setUserLoginName("EventAlertServiceTest");
        user.setUserPassword("123456");
        user.setUserPhone("1234567890");
        userDao.insertUser(user);
        User userUp = new User();
        userUp.setUserId(user.getUserId());
        userUp.setToken("token is for test");
        userDao.updateUser(userUp);
    }

    @Test
    public void insertAlert() throws Exception {
        System.out.println("insertAlert: " + this.eventAlertService.insertAlert(eventAlert));
    }

    @Test
    public void getImgData() throws Exception {
        System.out.println("getImgData: " + this.eventAlertService.getImgData(1040).length);
    }

    @Test
    public void getAlertByEventId() throws Exception {
        System.out.println("getAlertByEventId: " + this.eventAlertService.getAlertByEventId(1001,token));
    }
    @Test
    public void updateImgData() throws Exception {
        EventAlert eventAlertUp = new EventAlert();
        eventAlertUp.setEventId(1030);
        eventAlertUp.setEventSource("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2073626099,2224365240&fm=27&gp=13.jpg");
        System.out.println("updateImgData: " + this.eventAlertService.updateImgData(eventAlertUp));
    }

    @Test
    public void updateEventCheck() throws Exception {
        EventAlert eventAlertUp = new EventAlert();
        eventAlertUp.setEventId(1001);
        eventAlertUp.setTypeRectify("错误类型纠正类型");
        eventAlertUp.setEventCheck("已复核");
        eventAlertUp.setEventEndTime(DatetimeUtil.getDate(System.currentTimeMillis()));
        eventAlertUp.setEventReviewer("launcher001");
        System.out.println("updateEventCheck: " + this.eventAlertService.updateEventCheck(eventAlertUp, token));
    }

    @Test
    public void listNotCheckAlertForExcel() throws Exception {
        System.out.println("listNotCheckAlertForExcel: " + this.eventAlertService.listNotCheckAlertForExcel("undefined","undefined","undefined","undefined","undefined","undefined"));
    }

    @Test
    public void listNotCheckAlert() throws Exception {
        System.out.println("listNotCheckAlert: " + this.eventAlertService.listNotCheckAlert("undefined","undefined","undefined","undefined","undefined","undefined",0,token));
    }

    @Test
    public void pageNotCheckCount() throws Exception {
        System.out.println("pageNotCheckCount: " + this.eventAlertService.pageNotCheckCount("undefined","undefined","undefined","undefined","undefined","undefined"));
    }

    @Test
    public void listCheckAlertForExcel() throws Exception {
        System.out.println("listCheckAlertForExcel: " + this.eventAlertService.listCheckAlertForExcel("undefined","undefined","undefined","undefined","undefined","undefined","undefined","undefined","undefined","undefined","undefined"));
    }

    @Test
    public void listCheckAlert() throws Exception {
        System.out.println("listCheckAlert: " + this.eventAlertService.listCheckAlert("undefined","undefined","undefined","undefined","undefined","undefined","undefined","undefined","undefined","undefined","undefined",1,token));
    }

    @Test
    public void pageCheckCount() throws Exception {
        System.out.println("pageCheckCount: " + this.eventAlertService.pageCheckCount("undefined","undefined","undefined","undefined","undefined","undefined","undefined","undefined","undefined","undefined","undefined"));
    }

    @Test
    public void getEventType() throws Exception {
        System.out.println("getEventType: " + this.eventAlertService.getEventType(token,"undefined"));
    }

    @Test
    public void addEventType() throws Exception {
        Map<String, Object> map = new HashMap<>(1);
        map.put("typeId",1001);
        map.put("typeDescription","测试添加");
        System.out.println("addEventType: " + this.eventAlertService.addEventType(map,token));
    }

    @Test
    public void addEventTime() throws Exception {
        Map<String, Object> map = new HashMap<>(1);
        map.put("eventTime","600000");
        System.out.println("addEventTime: " + this.eventAlertService.addEventTime(map,token));
    }

    @Test
    public void putEventType() throws Exception {
        Map<String, Object> map = new HashMap<>(1);
        map.put("typeId",1001);
        map.put("typeDescription","测试修改");
        System.out.println("putEventType: " + this.eventAlertService.putEventType(map,token));
    }

    @Test
    public void deleteEventType() throws Exception {
        Map<String, Object> map = new HashMap<>(1);
        map.put("id",1001);
        System.out.println("deleteEventType: " + this.eventAlertService.deleteEventType(map,token));
    }

    @Test
    public void getCheckTypeStatusCount() throws Exception {
        System.out.println("getCheckTypeStatusCount: " + this.eventAlertService.getCheckTypeStatusCount("undefined","week",token));
    }

    @Test
    public void getNotCheckCount() throws Exception {
        System.out.println("getNotCheckCount: " + this.eventAlertService.getNotCheckCount(token));
    }

    @Test
    public void getNotCheckEvent() throws Exception {
        System.out.println("getNotCheckEvent: " + this.eventAlertService.getNotCheckEvent(token));
    }
}