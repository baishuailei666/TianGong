package com.xlauncher.service.Impl;

import com.xlauncher.entity.EventType;
import com.xlauncher.service.EventTypeService;
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

@RunWith(SpringJUnit4ClassRunner.class) // 整合
@ContextConfiguration(locations={"classpath:spring-mybatis.xml"}) // 加载配置
@Transactional
public class EventTypeServiceTest {
    private static EventType eventType;

    @Autowired
    EventTypeService eventTypeService;

    @BeforeClass()
    public static void initEventType() {
        eventType = new EventType();
        eventType.setTypeDescription("检测到河面垃圾");
        eventType.setTypeStartTime("-28799000");
        eventType.setTypeEndTime("57599000");
        eventType.setTypePushStatus("否");
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addEventType() {
        System.out.println("addEventType: " + this.eventTypeService.addEventType(eventType));
    }

    @Test
    public void updateEventType() {
        eventType.setTypeId(1);
        eventType.setTypeDescription("修改后的告警事件描述");
        eventType.setTypeStartTime("152642025");
        eventType.setTypeEndTime("152652025");
        System.out.println("updateEventType: " + this.eventTypeService.updateEventType(eventType));
    }

    @Test
    public void getEventType() {
        System.out.println("getEventType: " + this.eventTypeService.getEventType(1000));
    }

    @Test
    public void deleteEventType() {
        System.out.println("deleteEventTYpe: " + this.eventTypeService.deleteEventType(1000));
    }

    @Test
    public void getAllEventsType() {
        System.out.println("getAllEventsType: " + this.eventTypeService.getAllEventsType("undefined").size());
    }


}