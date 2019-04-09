package com.xlauncher.service.Impl;


import com.xlauncher.entity.AlertEvent;
import com.xlauncher.entity.Service;
import com.xlauncher.service.AlertEventService;
import com.xlauncher.service.AlertRestTemplateService;
import com.xlauncher.service.runner.CmsTimerTaskRunner;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.*;


/**
 * 功能：数据包转发
 *
 * @author 白帅雷
 * @date 2018-02-02
 * 备注：RestTemplate是Spring提供的用于访问Rest服务的客户端
 * 通过提供便捷的方法访问远程HTTP服务
 */

@org.springframework.stereotype.Service(value = "alertRestTemplateService")
public class AlertRestTemplateServiceImpl implements AlertRestTemplateService {

    @Autowired
    ServiceProperties serviceProperties;

    @Autowired
    private AlertEventService alertEventService;

    RestTemplate restTemplate;
    private static Logger logger = Logger.getLogger(AlertRestTemplateServiceImpl.class);

    public AlertRestTemplateServiceImpl() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(10000);
        restTemplate = new RestTemplate(requestFactory);
    }

    /**
     * 将事件信息转发推送给CMS端
     *
     * @param alertEvent 需要转发的告警事件信息(包含eventId，eventStarttime, eventDescription, channelId, eventSource5个字段的信息)
     * @param serName 需要转发到的服务名称
     * @return 返回转发的状态码，成功为200; 失败返回0.
     */
    @Override
    public int postAlertEvent(AlertEvent alertEvent, String serName) {
        int ret = 0;
        try {
            ret = pushAlertForObject(alertEvent, serName);
            if (ret == 1) {
                // 转发推送成功则回写数据库已推送
                alertEvent.setEventPushStatus("已推送");
                this.alertEventService.updateEventPushStatus(alertEvent);
            } else {
                alertEvent.setEventPushStatus("推送失败");
                this.alertEventService.updateEventPushStatus(alertEvent);
                CmsTimerTaskRunner.getInstance().start();
                logger.warn("推送失败-触发定时推送触发器");
                logger.warn("pushAlertForObject status : " + ret + "alertEvent : " + alertEvent);
            }
        } catch (Exception e) {
            alertEvent.setEventPushStatus("推送失败");
            this.alertEventService.updateEventPushStatus(alertEvent);
            logger.warn("*****转发捕获异常*****触发定时推送触发器。PushAlertForObject异常是否会经过这里。" + e.toString());
            CmsTimerTaskRunner.getInstance().start();
            logger.warn("Transpond failed: Error ");
        }
        // 返回状态
        return ret;
    }

    /**
     * 推送单条信息并返回状态
     *
     * @param alertEvent 告警事件信息
     * @param serName 服务名称
     * @return 返回推送的状态成功200；失败0
     */
    @Override
    public int pushAlertForEntity(AlertEvent alertEvent, String serName) {
        //header设置
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(alertEvent, headers);

        Service service = this.serviceProperties.getService(serName);
        String ip = service.getSerIp();
        String port = service.getSerPort();
        String name = service.getSerName();
        String postUrl = "http://" + ip + ":" + port + "/" + name + "/eventAlert";
        try {
            ResponseEntity<Map> mapResponseEntity = restTemplate.postForEntity(postUrl, httpEntity, Map.class);
            int ret = mapResponseEntity.getStatusCodeValue();
            int num = 200;
            if (ret == num) {
                return ret;
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 推送单条告警事件并返回告警事件推送状态
     * @param alertEvent 告警事件信息
     * @param serName 服务名称
     * @return
     */
    @Override
    public int pushAlertForObject(AlertEvent alertEvent, String serName) throws Exception {
        int pushRestult = 0;
        try {
            Service service = this.getService(serName);
            StringBuilder sb = new StringBuilder();
            sb.append("http://").append(service.getSerIp()).append(":")
                    .append(service.getSerPort()).append("/").append(service.getSerName()).append("/eventAlert");
            logger.info("*****AlertRestTemplateServiceImpl*****: Try to push to - " + sb.toString());
            logger.info("*****当前CMS服务信息*****" + service);
            Map<String, Object> resultMap = restTemplate.postForObject(sb.toString(), alertEvent, Map.class);
            if (resultMap != null) {
                if ((int) resultMap.get("status") == 1) {
                    pushRestult = 1;
                    logger.info("*****推送成功*****AlertRestTemplateServiceImpl: Push to SUCCESS - " + sb.toString());
                    logger.info("Push alert event to CMS success.");
                }
            } else {
                logger.warn("*****推送失败，因为CMS未知原因导致*****Push alert event to CMS failed, some error occur while operate the database." + sb);
            }
        } catch (Exception e) {
            //可能会存在异常情况，Timer会在告警事件之前推送成功，导致服务器报500错误，需要优化
            logger.warn("*****CMS服务器异常*****pushAlertForObject: " + e.getMessage());
            pushRestult = 0;
            throw e;
        }
        logger.info("提示-告警事件：" + alertEvent.getEventId() + ", 推送状态： " + pushRestult);
        return pushRestult;
    }

    /**
     * 推送数据库中未推送成功的
     * @param serName 需要推送到的对应的服务的名称
     */
    @Override
    public synchronized void checkPostRest(String serName) {
        try {
            int pushSuccess = 0;
            List<AlertEvent> alertEventList = this.alertEventService.getPushEventsRest();
            for (AlertEvent alertEvent : alertEventList) {
                int pushResult = pushAlertForEntity(alertEvent, serName);
                if (pushResult == 200) {
                    alertEvent.setEventPushStatus("已推送");
                    this.alertEventService.updateEventPushStatus(alertEvent);
                    pushSuccess ++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据服务名获取服务信息
     * @param serName 服务名称
     * @return Service对象
     */
    public Service getService(String serName) {
        String filename = CmsTimerTaskRunner.class.getClassLoader().getResource("").getPath() + "service.properties";
        Service service = new Service();
        Properties properties = new Properties();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(filename), "UTF-8"
            ));
            properties.load(br);
            service.setSerName(properties.getProperty(serName + ".name", "noneService"));
            service.setSerIp(properties.getProperty(serName + ".ip", "noneIp"));
            service.setSerPort(properties.getProperty(serName + ".port", "8080"));
            if ("noneService" == service.getSerName()) {
                logger.warn("Transpond Error: noneService[" + serName + "]");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return service;
    }
}
