package com.xlauncher.service.Impl;

import com.xlauncher.entity.AlertEvent;
import com.xlauncher.entity.Service;
import com.xlauncher.service.AlertEventService;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
@Transactional
public class AlertEventServiceImplTest {

    @Autowired
    AlertEventService alertEventService;
    @Autowired
    ServiceProperties serviceProperties;
    private static AlertEvent alertEvent;

    private static String channelId = "1207ac513f13478e9765f61b84b6c7d0";

    @BeforeClass
    public static void initAlertEvent() {
        alertEvent = createAlertEvent();
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addEvent() {
        Service service = new Service();
        service.setSerId(1001);
        service.setSerIp("8.16.0.41");
        service.setSerPort("8080");
        service.setSerName("cms");
        serviceProperties.setProperties(service);
        System.out.println("addEvent: " + this.alertEventService.addEvent(alertEvent));
    }

    @Test
    public void updateEventPushStatus() {
        alertEvent.setEventPushStatus("已推送");
        System.out.println("updateEventPushStatus: " + this.alertEventService.updateEventPushStatus(alertEvent).size());
    }

    @Test
    public void updateEventPushSuntecStatus() {
        alertEvent.setEventPushSuntecStatus("已推送");
        System.out.println("setEventPushSuntecStatus: " + this.alertEventService.updateEventPushSuntecStatus(alertEvent));
    }

    @Test
    public void getAllEvents() {
        System.out.println("getAllEvents: " + this.alertEventService.getAllEvents().size());
    }

    @Test
    public void getEventsCount() {
        System.out.println("getEventsCount: " + this.alertEventService.getEventsCount());
    }

    @Test
    public void getEventsCountByChannelId() {
        System.out.println("getEventsCountByChannelId: " + this.alertEventService.getEventBychannelId(channelId).size());
    }

    @Test
    public void getEventBychannelId() {
        System.out.println("getEventBychannelId: " + this.alertEventService.getEventBychannelId(channelId).size());
    }

    @Test
    public void getEventByEventId() {
        System.out.println("getEventByEventId: " + this.alertEventService.getEventByEventId(1).getChannelId());
    }

    @Test
    public void getAllEventsByPage() {
        System.out.println("getAllEventsByPage: " + this.alertEventService.getAllEventsByPage(1, 10).size());
    }

    @Test
    public void getAllEventsByChannelIdAndPage() {
        System.out.println("getAllEventsByChannelIdAndPage: " + this.alertEventService.getAllEventsByChannelIdAndPage(channelId, 1, 10));
    }

    @Test
    public void getPushEventsRest() {
        System.out.println("getPushEventRest: " + this.alertEventService.getPushEventsRest().size());
    }

    @Test
    public void getPushSuntecEventsRest() {
        System.out.println("getPushSuntecEventsRest: " + this.alertEventService.getPushSuntecEventsRest().size());
    }

    @Test
    public void getPushSuntecRestTop1() {
        if (this.alertEventService.getPushSuntecRestTop1() != null) {
            System.out.println("getPushSuntecRestTop1: " + this.alertEventService.getPushSuntecRestTop1().getChannelId());
        } else {
            System.out.println("getPushSuntecRestTop1: null" );
        }
    }

    @Test
    public void getPushCmsRestTop1() {
        if (this.alertEventService.getPushCmsRestTop1() != null) {
            System.out.println("getPushCmsRestTop1: " + this.alertEventService.getPushCmsRestTop1().getChannelId());
        } else {
            System.out.println("getPushCmsRestTop1: null" );
        }

    }

    public static AlertEvent createAlertEvent() {
        AlertEvent alertEvent = new AlertEvent();
        String dateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        alertEvent.setEventId(3);
        alertEvent.setEventStartTime(dateTime);
        alertEvent.setChannelId(channelId);
        alertEvent.setTypeId(1);
        alertEvent.setTypeDescription("船只非法侵入");
        alertEvent.setEventSource("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2073626099,2224365240&fm=27&gp=0.jpg");
        alertEvent.setEventPushStatus("未推送");
        alertEvent.setEventPushSuntecStatus("未推送");
        return alertEvent;
    }
}