package com.xlauncher.service.runner;

import com.xlauncher.entity.AlertEvent;
import com.xlauncher.entity.ValidBean;
import com.xlauncher.service.AlertEventService;
import com.xlauncher.utils.WSDLUtil;
import org.apache.axis2.AxisFault;
import org.apache.log4j.Logger;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 定时推送runnable实现子类
 * @author 张霄龙
 * @since 2018-03-23
 */
public class WsdlTimerTaskRunner extends Thread {

    private static Logger logger = Logger.getLogger(WsdlTimerTaskRunner.class);

    /**
     * 静态私有变量
     */
    private static WsdlTimerTaskRunner instance;

    /**
     * 获取操作告警事件的bean
     */
    private AlertEventService alertEventService =
            (AlertEventService) new FileSystemXmlApplicationContext("classpath:spring-*.xml")
                    .getBean("alertEventService");

    /**
     * 构造函数
     */
    private WsdlTimerTaskRunner() {

    }

    /**
     * 获取单例的接口
     * @return 单例对象
     */
    public static synchronized WsdlTimerTaskRunner getInstance() {
        if (instance == null) {
            instance = new WsdlTimerTaskRunner();
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
                AlertEvent alertEvent = alertEventService.getPushSuntecRestTop1();
                if (alertEvent == null) {
                    timer.cancel();
                }
                boolean pushRet = true;
                while (alertEvent != null && pushRet) {
                    try {
                        ValidBean validBean = WSDLUtil.getValidBean(alertEvent);
                        if (validBean.isValid()) {
                            alertEvent.setOrderId(validBean.getRetMsg().getResMessage().substring(8));
                            alertEvent.setEventPushSuntecStatus("已推送");
                            alertEventService.updateEventPushSuntecStatus(alertEvent);
                            alertEvent = alertEventService.getPushSuntecRestTop1();
                        } else {
                            pushRet = false;
                        }
                    } catch (AxisFault axisFault) {
                        logger.error(axisFault.getMessage());
                        pushRet = false;
                    }
                }
            }
        }, 1000, 10000);
    }

    /**
     * 检查并推送之前推送正阳科技失败的告警事件
     * 注意：需要对方法加锁，防止高并发状态下对同一告警事件多次推送和多次写入状态
     */
    public synchronized boolean checkRestAfterPost() {
        List<AlertEvent> alertEventList = this.alertEventService.getPushSuntecEventsRest();
        int pushRest = alertEventList.size();
        int pushSuccess = 0;
        for (AlertEvent alertEvent : alertEventList) {
            try {
                ValidBean validBean = WSDLUtil.getValidBean(alertEvent);
                if (validBean.isValid()) {
                    pushSuccess++;
                    alertEvent.setOrderId(validBean.getRetMsg().getResMessage().substring(8));
                    alertEvent.setEventPushSuntecStatus("已推送");
                    this.alertEventService.updateEventPushSuntecStatus(alertEvent);
                }
            } catch (AxisFault axisFault) {
                axisFault.printStackTrace();
                continue;
            }
        }
        return (pushRest == pushSuccess) ? false : true;
    }
}
