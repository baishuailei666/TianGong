package com.xlauncher.service.runner;

import com.xlauncher.entity.AlertEvent;
import com.xlauncher.service.AlertRestTemplateService;
import org.apache.log4j.Logger;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * 功能：告警事件推送处理线程
 * @author 张霄龙
 * @since 2018-03-06
 */

public class RestTemplateRunner implements Runnable {

    private AlertEvent alertEvent;

    private String serName;

    private AlertRestTemplateService alertRestTemplateService =
                    (AlertRestTemplateService) new FileSystemXmlApplicationContext("classpath*:spring-*.xml")
                    .getBean("alertRestTemplateService");

    private Logger logger = Logger.getLogger(RestTemplateRunner.class);


    public RestTemplateRunner(AlertEvent alertEvent, String serName) {
        this.alertEvent = alertEvent;
        this.serName = serName;
    }

    @Override
    public void run() {
        try {
            int pushRet = 0;
            pushRet = this.alertRestTemplateService.postAlertEvent(alertEvent, serName);
            if (pushRet == 0) {
                logger.warn(serName + " service is not available!");
            } else {
                logger.info("Push status: success");
            }
        } catch (Exception e) {
            Thread t = Thread.currentThread();
            logger.warn("*****转发线程*****" + t.getName() + ", RestTemplateRunnerException: " + e.getMessage());
            logger.warn("*****CmsTimerTaskRunner线程状态***** " + CmsTimerTaskRunner.getInstance().getState().toString());
            Thread.yield();
        }
    }
}
