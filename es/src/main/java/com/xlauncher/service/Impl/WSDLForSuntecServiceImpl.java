package com.xlauncher.service.Impl;

import com.xlauncher.entity.AlertEvent;
import com.xlauncher.entity.ValidBean;
import com.xlauncher.service.AlertEventService;
import com.xlauncher.service.WSDLForSuntecService;
import com.xlauncher.service.runner.WsdlTimerTaskRunner;
import com.xlauncher.utils.WSDLUtil;
import org.apache.axis2.AxisFault;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 推送告警事件至正阳科技的实现类
 * @author 张霄龙
 * @since 2018-03-21
 */
@Service(value = "wsdlForSuntecService")
public class WSDLForSuntecServiceImpl implements WSDLForSuntecService {
    private static Logger logger = Logger.getLogger(WSDLForSuntecServiceImpl.class);

    @Autowired
    AlertEventService alertEventService;

    /**
     * 条用SunTech接口创建告警事件
     * 调用接口推送告警事件，成功写入成功状态；
     * 失败或者因为网络问题导致无法访问，则将推送状态改为推送失败，并启动定时推送线程。
     * @param alertEvent ES系统的告警事件对象
     * @return 如果成功返回创建告警事件的ID，失败返回失败描述
     */
    @Override
    public boolean createOrder(AlertEvent alertEvent) {
        try {
            ValidBean validBean = WSDLUtil.getValidBean(alertEvent);
            if (validBean.isValid()) {
                int num = 8;
                alertEvent.setOrderId(validBean.getRetMsg().getResMessage().substring(num));
                alertEvent.setEventPushSuntecStatus("已推送");
                this.alertEventService.updateEventPushSuntecStatus(alertEvent);
                return true;
            } else {
                WsdlTimerTaskRunner.getInstance().start();
                return false;
            }
        } catch (AxisFault axisFault) {
            WsdlTimerTaskRunner.getInstance().start();
            axisFault.printStackTrace();
            logger.error(axisFault.getMessage());
            return false;
        }
    }

}
