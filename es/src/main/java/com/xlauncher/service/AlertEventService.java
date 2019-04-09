package com.xlauncher.service;

import org.springframework.stereotype.Service;
import com.xlauncher.entity.AlertEvent;

import java.util.List;
import java.util.Map;

/**
 * 功能：事件告警服务层
 * @author 白帅雷
 * @date 2018-02-02
 */

@Service
public interface AlertEventService {

    /**
     * 添加事件
     * @param alertEvent 事件信息
     * @return 插入数据库影响的行数，成功返回1;失败返回0
     */
    Map<String, Object> addEvent(AlertEvent alertEvent);


    /**
     * 添加事件状态
     * @param alertEvent 事件信息
     * @return 修改数据库影响的行数，成功返回1
     */
    Map<String, Object> updateEventPushStatus(AlertEvent alertEvent);

    /**
     * 修改推送正阳科技告警事件状态
     * @param alertEvent 事件信息
     * @return 修改数据库影响行数，成功返回1
     */
    int updateEventPushSuntecStatus(AlertEvent alertEvent);

    /**
     * 获取告警事件的总数
     * @return 告警事件总数
     */
    Map<String, Object> getEventsCount();

    /**
     * 获取指定通道编号的总数
     * @param channelId 通道编号
     * @return 指定通道编号发生的告警事件总数
     */
    Map<String, Object> getEventsCountByChannelId(String channelId);

    /**
     * 查询所有事件信息
     * @return 所有事件的列表
     */
    Map<String, Object> getAllEvents();

    /**
     * 查询事件，根据channel_id获取事件信息
     * @param channelId 通道编号
     * @return 查到的事件的AlertEvent对象
     */
    Map<String, Object> getEventBychannelId(String channelId);

    /**
     * 查询事件，根据eventId获取事件信息
     * @param eventId 事件编号
     * @return 查到的事件的AlertEvent对象
     */

    AlertEvent getEventByEventId(int eventId);

    /**
     * 查询指定页所有事件信息，一页显示25条数据
     * @param page 页数
     * @param count 总数
     * @return 指定页码的事件列表
     */
    Map<String, Object> getAllEventsByPage(int page, int count);

    /**
     * 查询指定通道号和页码的所有事件信息，一页显示25条数据
     * @param channelId 通道编号
     * @param page 页数
     * @param count 总数
     * @return 指定通道号和页码的事件列表
     */
    Map<String, Object> getAllEventsByChannelIdAndPage(String channelId, int page, int count);

    /**
     * 查询所有未推送的告警事件
     * @return 未推送的告警事件列表
     */
    List<AlertEvent> getPushEventsRest();

    /**
     * 查询所有未推送的告警事件
     * @return 未推送给正阳科技的告警事件列表
     */
    List<AlertEvent> getPushSuntecEventsRest();

    /**
     * 查询推送失败的第一条信息
     * @return 推送失败的第一条信息
     */
    AlertEvent getPushSuntecRestTop1();

    /**
     * 查询推送失败的第一条信息
     * @return 推送失败的第一条信息
     */
    AlertEvent getPushCmsRestTop1();

    /**
     * 查询同设备同类型告警最近的一次告警事件
     * @return 最近的一次告警事件
     */
    boolean checkAlertEventDuplicateOrNot(AlertEvent alertEvent);
}
