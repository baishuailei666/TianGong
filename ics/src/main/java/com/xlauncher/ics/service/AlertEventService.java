package com.xlauncher.ics.service;

import org.springframework.stereotype.Service;


/**
 * @author :baisl
 * @Email :baishuailei@xlauncher.io
 * @Date :2018/12/19 0019
 * @Desc :报警事件服务层
 **/
@Service
public interface AlertEventService {

    /**
     * 通过RabbitMQ消息队列，接收到图片并预测分析上报给ES服务
     *
     * @param channelId channelId
     */
    void getAlertEvent(String channelId);
}
