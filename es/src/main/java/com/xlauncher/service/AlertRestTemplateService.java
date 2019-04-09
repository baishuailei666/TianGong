package com.xlauncher.service;

import com.xlauncher.entity.AlertEvent;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public interface AlertRestTemplateService {
    /**
     * 将事件信息转发推送给CMS端
     *
     * @param alertEvent 需要转发的告警事件信息(包含eventId，eventStarttime, eventDescription, channelId, eventSource5个字段的信息)
     * @param serName    需要转发到的服务名称
     * @return 返回转发的状态码，成功为200; 失败返回0.
     */
    int postAlertEvent(AlertEvent alertEvent, String serName);

    /**
     * 推送单条信息并返回状态
     *
     * @param alertEvent 告警事件信息
     * @param serName    服务名称
     * @return 返回推送的状态成功200；失败0
     */
    int pushAlertForEntity(AlertEvent alertEvent, String serName);

    /**
     * 推送单条信息并返回状态
     *
     * @param alertEvent 告警事件信息
     * @param serName    服务名称
     * @return 返回推送的状态成功200；失败0
     */
    int pushAlertForObject(AlertEvent alertEvent, String serName) throws Exception;

    /**
     * 推送数据库中未推送成功的
     *
     * @param serName 需要推送到的对应的服务的名称
     */
    void checkPostRest(String serName);
}
