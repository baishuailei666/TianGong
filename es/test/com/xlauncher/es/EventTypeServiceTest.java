package com.xlauncher.es;

import com.xlauncher.entity.EventType;
import com.xlauncher.service.EventTypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
@Transactional
public class EventTypeServiceTest {
    @Autowired
    EventTypeService eventTypeService;

    @Test
    public void addEventType() throws Exception {
        EventType eventType = new EventType();
        eventType.setTypeDescription("test case");
        eventType.setTypeStartTime("-28799000");
        eventType.setTypeEndTime("57599000");
        eventType.setTypePushStatus("是");
        System.out.println(this.eventTypeService.addEventType(eventType));
    }

    @Test
    public void deleteEventType() throws Exception {
        System.out.println(this.eventTypeService.deleteEventType(1));
    }

    @Test
    public void updateEventType() throws Exception {
        EventType eventType = new EventType();
        eventType.setTypeId(1);
        eventType.setTypeDescription("test case");
        System.out.println(this.eventTypeService.updateEventType(eventType));
    }

    @Test
    public void getEventType() throws Exception {
        System.out.println(this.eventTypeService.getEventType(1));
    }

    @Test
    public void getAllEventsType() throws Exception {
        System.out.println(this.eventTypeService.getAllEventsType("undefined"));
    }


}
