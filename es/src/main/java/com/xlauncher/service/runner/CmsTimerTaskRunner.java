package com.xlauncher.service.runner;

import com.xlauncher.entity.AlertEvent;
import com.xlauncher.service.AlertEventService;
import com.xlauncher.service.AlertRestTemplateService;
import com.xlauncher.service.Impl.ServiceProperties;
import org.apache.log4j.Logger;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.Timer;
import java.util.TimerTask;

/**
 * CMS定时推送多线程类
 * @author 张霄龙
 * @since 2018-03-24
 */
public class CmsTimerTaskRunner extends Thread {


    /**
     * 静态私有变量
     */
    private static CmsTimerTaskRunner instance;

    /**
     * 获取操作告警事件的bean
     */
    private AlertEventService alertEventService =
            (AlertEventService) new FileSystemXmlApplicationContext("classpath:spring-*.xml")
                    .getBean("alertEventService");

    private AlertRestTemplateService alertRestTemplateService =
            (AlertRestTemplateService) new FileSystemXmlApplicationContext("classpath:spring-*.xml")
                    .getBean("alertRestTemplateService");

    private ServiceProperties serviceProperties =
            (ServiceProperties) new FileSystemXmlApplicationContext("classpath:spring-*.xml")
                    .getBean("serviceProperties");

    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * 构造函数
     */
    private CmsTimerTaskRunner() {
        
    }

    /**
     * 获取单例的接口
     * @return 单例对象
     */
    public static synchronized CmsTimerTaskRunner getInstance() {
        if (instance == null) {
            instance = new CmsTimerTaskRunner();
        }
        return instance;
    }

    /**
     * 单例模式按时重发推送失败的告警事件
     */
    @Override
    public synchronized void run() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                AlertEvent alertEvent = alertEventService.getPushCmsRestTop1();
                logger.info("*****定时器启动*****");
                boolean pushRet = true;
                if (alertEvent == null) {
                    Thread t = Thread.currentThread();
                    logger.warn("*****定时器暂时中断*****");
                    logger.info("*****暂无告警事件*****");
                    logger.info("线程" + t.getName() + "getPushCmsRestTop1 is null");
                }
                while (alertEvent != null && pushRet) {
                    try {
                        int pushResult = alertRestTemplateService.pushAlertForObject(alertEvent, "cms");
                        if (pushResult == 1) {
                            alertEvent.setEventPushStatus("已推送");
                            alertEventService.updateEventPushStatus(alertEvent);
                            alertEvent = alertEventService.getPushCmsRestTop1();
                        } else {
                            System.out.println("pushRet=" + pushResult + serviceProperties.getService("cms").toString());
                            pushRet = false;
                        }
                    } catch (Exception e) {
                        Thread t = Thread.currentThread();
                        logger.error("*****定时器*****\n" + t.getName() + ", Exception: " + e.getMessage() + serviceProperties.getService("cms").toString());
                        logger.error("*****因为异常导致的timer任务暂时中断。*****");
                        break;
                    }
                }
            }
        }, 1000, 10000);
    }

}
