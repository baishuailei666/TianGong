package com.xlauncher.es;

import com.xlauncher.dao.AlertEventDao;
import com.xlauncher.entity.AlertEvent;
import com.xlauncher.service.AlertEventService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
@Transactional
public class AlertEventServiceTest {

    @Autowired
    AlertEventService alertEventService;

    @Autowired
    AlertEventDao alertEventDao;

    @Test
    public void addEventTest() {
        AlertEvent alertEvent = this.createAlertEvent();
        try {
            RestTemplate restTemplate = new RestTemplate();
//            Map<String, Object> resultMap = restTemplate.postForObject("http://47.92.38.247:8080/cms/alert", alertEvent, Map.class);
//            System.out.println(resultMap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void WSDLUtilTest() {

    }

    public AlertEvent createAlertEvent() {
        AlertEvent alertEvent = new AlertEvent();
        String dateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        alertEvent.setEventId(3);
        alertEvent.setEventStartTime(dateTime);
        alertEvent.setChannelId("1207ac513f13478e9765f61b84b6c7d0");
        alertEvent.setTypeId(1);
        alertEvent.setTypeDescription("船只非法侵入");
        alertEvent.setEventSource("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2073626099,2224365240&fm=27&gp=0.jpg");
        alertEvent.setEventPushStatus("未推送");
        return alertEvent;
    }

    @Test
    public void updatePushSuntecStatusTest() {
        AlertEvent alertEvent = createAlertEvent();
        this.alertEventDao.addEvent(alertEvent);
        alertEvent.setEventPushSuntecStatus("已推送");
        String orderIdStr = "orderId:40288123624cb5f301624cdddbe40005";
        alertEvent.setOrderId(orderIdStr.substring(8));
        this.alertEventDao.updateEventPushSuntecStatus(alertEvent);
    }

    @Test
    public void getPushEventSuntecRest() {
        List<AlertEvent> restAlertEventList = this.alertEventService.getPushSuntecEventsRest();
        for (AlertEvent alertEvent : restAlertEventList) {
            System.out.println(alertEvent);
        }
    }

    @Test
    public void getPushCmsRestTop1() {
        AlertEvent alertEvent = this.alertEventService.getPushCmsRestTop1();
        while (alertEvent != null) {
            System.out.println(alertEvent);
            alertEvent.setEventPushStatus("已推送");
            this.alertEventService.updateEventPushStatus(alertEvent);
            alertEvent = this.alertEventService.getPushCmsRestTop1();
        }
    }
}
