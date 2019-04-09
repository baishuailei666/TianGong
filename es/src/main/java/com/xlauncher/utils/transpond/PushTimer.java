package com.xlauncher.utils.transpond;

import com.xlauncher.entity.AlertEvent;
import com.xlauncher.service.AlertEventService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

/**
 * 功能：单例模式实现告警事件的重发
 * 作者：张霄龙
 * 时间：2018-02-08
 */


public class PushTimer extends TimerTask {

    AlertEventService alertEventService;

    private static Logger logger = Logger.getLogger(PushTimer.class);

    private static PushTimer instance;

    private String postUrl;

    private HttpEntity httpEntity;

    private AlertEvent alertEvent;

    private int times;

    private PushTimer() {

    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public void setHttpEntity(HttpEntity httpEntity) {
        this.httpEntity = httpEntity;
    }

    public void setAlertEvent(AlertEvent alertEvent) {
        this.alertEvent = alertEvent;
    }

    public static PushTimer getInstance(String postUrl, HttpEntity httpEntity, AlertEvent alertEvent, int times) {
        if (instance == null) {
            instance = new PushTimer();
        }
        instance.postUrl = postUrl;
        instance.httpEntity = httpEntity;
        instance.alertEvent = alertEvent;
        instance.times = times;
        return instance;
    }

    public static PushTimer getInstance() {
        if (instance == null) {
            instance = new PushTimer();
        }
        return instance;

    }

    @Override
    public void run() {
        RestTemplate rest = new RestTemplate();
        try {
            ResponseEntity<Map> againRestMap = rest.postForEntity(postUrl, httpEntity, Map.class);
            int againRestRet = againRestMap.getStatusCodeValue();
            int ret = 200;
            if (againRestRet == ret) {
                alertEvent.setEventPushStatus("已推送");
                alertEventService.updateEventPushStatus(alertEvent);
                this.cancel();  //停止这个run方法
            }
            if (this.times == 0) {
                this.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }
    }
}
