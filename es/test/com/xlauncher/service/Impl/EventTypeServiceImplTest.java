package com.xlauncher.service.Impl;

import com.xlauncher.entity.EventType;
import com.xlauncher.service.EventTypeService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
@Transactional
public class EventTypeServiceImplTest {

    private static EventType eventType;

    @Autowired
    EventTypeService eventTypeService;

    @BeforeClass()
    public static void initEventType() {
        eventType = new EventType();
        eventType.setTypeDescription("告警事件类型单元测试");
        eventType.setTypeStartTime("14400000");
        eventType.setTypeEndTime("57599000");
        eventType.setTypePushStatus("是");
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