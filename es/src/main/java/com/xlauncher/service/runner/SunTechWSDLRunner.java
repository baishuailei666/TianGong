package com.xlauncher.service.runner;

import com.xlauncher.entity.AlertEvent;
import com.xlauncher.service.WSDLForSuntecService;
import org.apache.log4j.Logger;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * 转发告警事件到正阳科技的runner
 * @author 张霄龙
 * @since 2018-03-19
 */
public class SunTechWSDLRunner implements Runnable {

    private static Logger logger = Logger.getLogger(SunTechWSDLRunner.class);

    private AlertEvent alertEvent;

    private WSDLForSuntecService wsdlForSuntecService =
            (WSDLForSuntecService) new FileSystemXmlApplicationContext("classpath:spring-*.xml")
                    .getBean("wsdlForSuntecService");

    public SunTechWSDLRunner(AlertEvent alertEvent) {
        this.alertEvent = alertEvent;
    }

    @Override
    public void run() {
        boolean pushRet = this.wsdlForSuntecService.createOrder(alertEvent);
        if (pushRet) {
            logger.info(alertEvent.getOrderId() + "Push alert event to Suntec success.");
        } else {
            logger.error("Push alert event to Suntec failed.");
        }
    }
}
