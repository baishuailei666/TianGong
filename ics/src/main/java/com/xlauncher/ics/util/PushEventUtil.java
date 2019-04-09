package com.xlauncher.ics.util;

import com.xlauncher.ics.entity.AlertEvent;
import com.xlauncher.ics.entity.ServiceInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author :baisl
 * @Email :baishuailei@xlauncher.io
 * @Date :2018/12/19 0019
 * @Desc :上报告警给ES服务
 **/
@Component
public class PushEventUtil {

    private RestTemplate restTemplate;
    private final ServiceInfo serviceInfo;

    /**
     * 定义上报报警状态
     */
    private int pushStatus = 0;
    private static Logger logger = Logger.getLogger(PushEventUtil.class);

    @Autowired
    public PushEventUtil(ServiceInfo serviceInfo) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(10000);
        restTemplate = new RestTemplate(requestFactory);
        this.serviceInfo = serviceInfo;
    }

    /**
     * 推送告警信息给ES服务(eventChannelId、eventSource、eventStartTime、typeId)
     *
     * @param alertEventMap alertEventMap
     * @return
     */
    public int pushEvent(Map<String, Object> alertEventMap) {
        ResponseEntity<Map> mapResponseEntity;
        try {
            AlertEvent alertEvent = new AlertEvent();
            alertEvent.setChannelId((String) alertEventMap.get("channelId"));
            alertEvent.setEventSource((String) alertEventMap.get("eventSource"));
            alertEvent.setEventStartTime((String) alertEventMap.get("eventStartTime"));
            alertEvent.setTypeId((Integer) alertEventMap.get("typeId"));
            logger.info("pushEvent推送告警信息! alertEvent." + alertEvent);

            //header设置
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
            headers.add("Content-Type", "application/json;charset=UTF-8");
            HttpEntity<Object> httpEntity = new HttpEntity<Object>(alertEvent, headers);
            String postUrl = "http://" + serviceInfo.getEsIp() + ":" + serviceInfo.getEsPort() + "/" + serviceInfo.getEsName() + "/es_alert_event";
            logger.info("pushEvent推送告警信息! postUrl." + postUrl);

            mapResponseEntity = restTemplate.postForEntity(postUrl, httpEntity, Map.class);
            int value = 200;
            if (mapResponseEntity.getStatusCodeValue() == value) {
                pushStatus = 200;
                logger.info("pushEvent推送告警信息成功! pushStatus." + pushStatus);
            } else {
                logger.info("ics推送失败: 因为ES未知原因导致, pushEvent：" + mapResponseEntity.getBody());
            }

        } catch (RestClientException e) {
            e.printStackTrace();
            logger.error("*****ES服务器异常*****pushEvent: " + e.getMessage());
        }
        return pushStatus;
    }
}
