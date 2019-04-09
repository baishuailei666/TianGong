package com.xlauncher.service;


import com.xlauncher.entity.EventType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能：事件类型服务层
 * @author 白帅雷
 * @date 2018-02-02
 */

@Service
public interface EventTypeService {

    /**
     * 添加事件类型信息
     * @param eventType 事件类型
     * @return 插入状态码，成功返回1
     */
    int addEventType(EventType eventType);

    /**
     * 删除事件描述信息
     * @param typeId 类型编号
     * @return 删除事件描述信息的操作结果，删除成功返回1
     */
    int deleteEventType(int typeId);

    /**
     * 更新事件描述信息，增量更新
     * @param eventType 事件类型
     * @return
     */
    int updateEventType(EventType eventType);


    /**
     * 查询事件描述信息，根据content_id获取事件描述信息
     * @param typeId 类型编号
     * @return 查询事件描述信息
     */
    EventType getEventType(int typeId);

    /**
     * 查询所有事件描述信息
     * @param typeDescription 告警事件类型
     * @return 所有事件描述信息
     */
    List<EventType> getAllEventsType(@Param("typeDescription") String typeDescription);

}
