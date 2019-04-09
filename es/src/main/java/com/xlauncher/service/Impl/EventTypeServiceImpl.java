package com.xlauncher.service.Impl;

import com.xlauncher.dao.EventTypeDao;
import com.xlauncher.entity.EventType;
import com.xlauncher.service.EventTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能：事件类型服务层的实现
 * @author 白帅雷
 * @date 2018-02-02
 */

@Service(value = "EventTypeService")
public class EventTypeServiceImpl implements EventTypeService {

    @Qualifier("eventTypeDao")
    @Autowired
    private EventTypeDao eventTypeDao;

    /**
     * 添加事件类型信息
     * @param eventType 事件类型
     * @return 插入状态码，成功返回1
     */
    @Override
    public int addEventType(EventType eventType) {
        String desc = eventType.getTypeDescription();
        EventType eventType1 = this.eventTypeDao.getTypeDesc(desc);
        if (eventType1 != null) {
            if (eventType1.getTypeDelete() == 1) {
                eventType.setTypeId(eventType1.getTypeId());
                // typeDelete=1表示删除;
                this.eventTypeDao.upDelete(eventType.getTypeId());
                int status = 0;
                try {
                    status = eventTypeDao.updateEventType(eventType);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return status;
            } else {
                return -1;
            }
        }
        return this.eventTypeDao.addEventType(eventType);
    }

    /**
     * 删除事件类型描述信息
     * @param typeId 类型编号
     * @return 删除事件类型描述信息的操作结果，删除成功返回1
     */
    @Override
    public int deleteEventType(int typeId) {
        return this.eventTypeDao.deleteEventType(typeId);
    }

    /**
     * 更新事件类型描述信息，增量更新
     * @param eventType 事件类型
     * @return 更新状态
     */
    @Override
    public int updateEventType(EventType eventType) {
        return this.eventTypeDao.updateEventType(eventType);
    }


    /**
     * 查询事件类型描述信息，根据content_id获取事件类型描述信息
     * @param typeId 类型编号
     * @return 查询事件类型描述信息
     */
    @Override
    public EventType getEventType(int typeId) {
        return this.eventTypeDao.getEventType(typeId);
    }

    /**
     * 查询所有事件类型描述信息
     * @param typeDescription 告警事件类型
     * @return 事件类型信息
     */
    @Override
    public List<EventType> getAllEventsType(String typeDescription) {
        return this.eventTypeDao.getAllEventsType(typeDescription);
    }
}
