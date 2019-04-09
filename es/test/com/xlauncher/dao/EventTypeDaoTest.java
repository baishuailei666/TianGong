package com.xlauncher.dao;

import com.xlauncher.entity.EventType;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
@Transactional
public class EventTypeDaoTest {

    private static EventType eventType;

    @Autowired
    private EventTypeDao eventTypeDao;

    @BeforeClass()
    public static void initEventType() {
        eventType = new EventType();
        eventType.setTypeDescription("告警事件类型单元测试");
        eventType.setTypeStartTime("-28799000");
        eventType.setTypeEndTime("57599000");
        eventType.setTypePushStatus("是");
    }

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    @Test
    public void addEventType() {
        int addRet = eventTypeDao.addEventType(eventType);
        System.out.println("addEventType: " + addRet);
    }

    @Test
    public void updateEventType() {
        eventType.setTypeId(1);
        eventType.setTypeDescription("修改后的告警事件类型描述");
        eventType.setTypeStartTime("152642025");
        eventType.setTypeEndTime("152652025");
        int updateRet = eventTypeDao.updateEventType(eventType);
        System.out.println("updateEventType: " + updateRet);
    }

    @Test
    public void deleteEventType() {
        int delRet = eventTypeDao.deleteEventType(1000);
        System.out.println("deleteEventType: " + delRet);
    }

    @Test
    public void getEventType() {
        EventType eventType = eventTypeDao.getEventType(1);
        System.out.println("getEventType: " + eventType);
    }

    @Test
    public void getAllEventsType() {
        List<EventType> eventTypeList = eventTypeDao.getAllEventsType("undefined");
        System.out.println("getAllEventsType: " + eventTypeList.size());
    }
}