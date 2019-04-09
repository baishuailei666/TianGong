package com.xlauncher.es;

import com.xlauncher.entity.AlertEvent;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class RestTemplateTest {

    private static AlertEvent alertEvent = new AlertEvent();

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            String dateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
            alertEvent.setEventStartTime(dateTime);
            alertEvent.setChannelId("1207ac513f13478e9765f61b84b6c7d0");
            alertEvent.setTypeId(2);
            alertEvent.setEventSource("http://file06.16sucai.com/2016/0604/aef1c01b78c8b1342c99c0e27f86dbc9.jpg");
            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> retMap = restTemplate.postForObject("http://localhost:8080/es_alert_event", alertEvent, Map.class);
            System.out.println(retMap);
        }
    }
}
