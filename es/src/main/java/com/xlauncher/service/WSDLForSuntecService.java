package com.xlauncher.service;

import com.xlauncher.entity.AlertEvent;
import org.springframework.stereotype.Service;

/**
 * 推送告警事件至正阳科技的接口
 * @author 张霄龙
 * @since 2018-03-22
 */
@Service
public interface WSDLForSuntecService {

    /**
     * 条用SunTech接口创建告警事件
     * @param alertEvent ES系统的告警事件对象
     * @return 如果成功返回创建告警事件的ID，失败返回失败描述
     */
    boolean createOrder(AlertEvent alertEvent);

}
