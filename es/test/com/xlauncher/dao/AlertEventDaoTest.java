package com.xlauncher.dao;

import com.xlauncher.entity.AlertEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
@Transactional
public class AlertEventDaoTest {

    @Autowired
    com.xlauncher.dao.AlertEventDao alertEventDao;

    private static AlertEvent alertEvent;
    private static int eventId = 1;
    private static String channelId = "";

    @BeforeClass
    public static void initAlertEvent() {
        alertEvent = new AlertEvent();
        alertEvent.setEventStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        channelId = UUID.randomUUID().toString().replace("-", "");
        alertEvent.setChannelId(channelId);
        alertEvent.setTypeId(1);
        alertEvent.setTypeDescription("船只非法侵入");
        alertEvent.setEventSource("http://www.taopic.com/uploads/allimg/140418/235136-14046PRS783.jpg");
        alertEvent.setEventPushStatus("未推送");
        alertEvent.setEventPushSuntecStatus("未推送");
    }

    @Before
    public void setUp() throws Exception {
        eventId++;
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addEvent() {
        int addRet = this.alertEventDao.addEvent(alertEvent);
        System.out.println("addEvent: " + addRet);
    }

    @Test
    public void updateEventPushStatus() {
        alertEvent.setEventPushStatus("已推送");
        int updateRet = this.alertEventDao.updateEventPushStatus(alertEvent);
        System.out.println("updateEventPushStatus: " + updateRet);
    }

    @Test
    public void updateEventPushSuntecStatus() {
        alertEvent.setEventPushSuntecStatus("已推送");
        alertEvent.setOrderId(UUID.randomUUID().toString().replace("-", ""));
        int updateSuntecRet = this.alertEventDao.updateEventPushSuntecStatus(alertEvent);
        System.out.println("updateEventPushSuntecStatus: " + updateSuntecRet);
    }

    @Test
    public void getEventsCountByChannelId() {
        int count = this.alertEventDao.getEventsCountByChannelId(channelId);
        System.out.println("getEventsCountByChannelId: " + count);
    }

    @Test
    public void getAllEvents() {
        List<AlertEvent> eventsList = this.alertEventDao.getAllEvents();
        System.out.println("getAllEvents: " + eventsList.size());
    }

    @Test
    public void getEventBychannelId() {
        List<AlertEvent> eventsList = this.alertEventDao.getEventBychannelId(channelId);
        System.out.println("getEventByChannelId: " + eventsList.size());
    }

    @Test
    public void getEventByEventId() {
        AlertEvent alertEventLocal = this.alertEventDao.getEventByEventId(1);
        System.out.println("getEventByEventId: " + alertEventLocal.getEventId());
    }

    @Test
    public void getAllEventsByPage() {
        List<AlertEvent> pageEventsList = this.alertEventDao.getAllEventsByPage(1, 5);
        System.out.println("getAllEventsByPage: " + pageEventsList.size());
    }

    @Test
    public void getAllEventsByChannelIdAndPage() {
        List<AlertEvent> channelPageList = this.alertEventDao.getAllEventsByChannelIdAndPage(channelId, 1, 5);
        System.out.println("getAllEventsByChannelIdAndPage: " + channelPageList.size());
    }

    @Test
    public void getPushEventsRest() {
        List<AlertEvent> pushRestList = this.alertEventDao.getPushEventsRest();
        System.out.println("getPushEventsRest: " + pushRestList.size());
    }

    @Test
    public void getPushSuntecEventsRest() {
        List<AlertEvent> pushSuntecRestList = this.alertEventDao.getPushSuntecEventsRest();
        System.out.println("getPushSuntecEventRest: " + pushSuntecRestList.size());
    }

    @Test
    public void getPushSuntecRestTop1() {
        AlertEvent alertEventTop1 = this.alertEventDao.getPushSuntecRestTop1();
        if (alertEventTop1 != null) {
            System.out.println("getPushSuntecRestTop1: " + alertEventTop1.getEventId());
        } else {
            System.out.println("getPushSuntecRestTop1: null");
        }

    }

    @Test
    public void getPushCmsRestTop1() {
        AlertEvent alertEventTop1 = this.alertEventDao.getPushCmsRestTop1();
        if (alertEventTop1 != null) {
            System.out.println("getPushCmsRestTop1: " + alertEventTop1.getEventId());
        } else {
            System.out.println("getPushCmsRestTop1: null");
        }

    }

    @Test
    public void getEventsCount() {
        int count = this.alertEventDao.getEventsCount();
        System.out.println("getEventsCount: " + count);
    }
}