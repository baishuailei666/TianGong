package com.xlauncher.web;


import com.xlauncher.entity.EventType;
import com.xlauncher.utils.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.xlauncher.service.EventTypeService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：事件类型操作控制器
 * @author 白帅雷
 * @date 2018-02-02
 */
@Controller
@RequestMapping(value = "/es_event_type")
public class EventTypeController {

    @Autowired
    private EventTypeService eventTypeService;

    /**
     * 记录器
     */
    private static Logger logger = Logger.getLogger(EventTypeController.class);

    /**
     * 添加事件类型信息
     * @param eventType
     * @return 插入数据库操作的返回结果，成功返回1
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Object> addEventType(@RequestBody EventType eventType) {
        int ret = 0;
        try {
            logger.info("Add event type." + eventType);
            eventType.setTypeStartTime(DateUtil.dateToStamp(eventType.getTypeStartTime()));
            eventType.setTypeEndTime(DateUtil.dateToStamp(eventType.getTypeEndTime()));
            ret = this.eventTypeService.addEventType(eventType);
        } catch (Exception e) {
            logger.warn("Add event type wrong. Exception: [" + e.getMessage() + "]");
        }
        Map<String, Object> retMap = new HashMap<>(1);
        retMap.put("status", ret);
        if (ret == -1) {
            retMap.put("error", "告警类型:" + eventType + " 已存在, 不能重复添加!");
        }
        if (ret == 0) {
            retMap.put("error", eventType + " 添加失败!");
        }
        return retMap;
    }

    /**
     * 删除事件类型信息
     * @param id
     * @return 删除事件类型信息的操作结果，删除成功返回1
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Map<String, Object> deleteEventContentById(@PathVariable("id") String id) {
        int ret = 0;
        try {
            ret = this.eventTypeService.deleteEventType(Integer.parseInt(id));
        } catch (Exception e) {
            logger.warn("Delete event type error. Exception: [" + e.getMessage() + "]");
        }
        Map<String, Object> retMap = new HashMap<>(1);
        retMap.put("status", ret);
        return retMap;
    }

    /**
     * 修改事件类型信息-根据提供的ID修改事件类型信息
     * @param eventType
     * @return 修改结果，修改成功返回1
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public Map<String, Object> updateEventContent(@RequestBody EventType eventType) {
        int retUpd = 0;
        try {
            eventType.setTypeStartTime(DateUtil.dateToStamp(eventType.getTypeStartTime()));
            eventType.setTypeEndTime(DateUtil.dateToStamp(eventType.getTypeEndTime()));
            retUpd = this.eventTypeService.updateEventType(eventType);
        } catch (Exception e) {
            logger.warn("Update event type error. Exception: [" + e.getMessage() + "]");
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("status", retUpd);
        map.put("eventType", eventType);
        return map;
    }

    /**
     * 查询事件类型信息
     * @param id
     * @return 返回查询的事件类型信息，为空返回null
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public EventType getEventContentById(@PathVariable("id") String id) {
        int typeId = Integer.parseInt(id);
        EventType eventType = null;
        try {
            eventType = this.eventTypeService.getEventType(typeId);
            eventType.setTypeStartTime(DateUtil.stampToDate(eventType.getTypeStartTime()));
            eventType.setTypeEndTime(DateUtil.stampToDate(eventType.getTypeEndTime()));
        } catch (Exception e) {
            logger.warn("Search event type error. Exception: [" + e.getMessage() + "]");
        }
        return eventType;
    }

    /**
     * 查询所有事件类型信息
     *
     * @param typeDescription 告警事件类型
     * @return 事件类型信息的列表
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Map<String, Object> getAllEventsType(@RequestParam("typeDescription") String typeDescription) {
        Map<String, Object> userMap = new HashMap<>(2);
        List<EventType> lstEventsType = null;
        try {
            lstEventsType = this.eventTypeService.getAllEventsType(typeDescription);
            for ( EventType eventType:
            lstEventsType) {
                eventType.setTypeStartTime(DateUtil.stampToDate(eventType.getTypeStartTime()));
                eventType.setTypeEndTime(DateUtil.stampToDate(eventType.getTypeEndTime()));
                userMap.put("typeStartTime", eventType.getTypeStartTime());
                userMap.put("typeEndTime", eventType.getTypeEndTime());
            }
        } catch (Exception e) {
            logger.warn("Search event type error. Exception: [" + e.getMessage() + "]");
        }
        userMap.put("eventTypeList", lstEventsType);
        return userMap;
    }

    /**
     * 查询所有事件类型信息用于CMS事件监控中心饼状图分析展示
     *
     * @param typeDescription 告警事件类型
     * @return 事件类型信息的列表
     */
    @ResponseBody
    @RequestMapping(value = "/getTypes", method = RequestMethod.GET)
    public List<Map<String, String>> getTypes(@RequestParam("typeDescription") String typeDescription) {
        List<EventType> lstEventsType;
        List<Map<String, String>> lists = new ArrayList<>(1);
        Map<String, String> map;
        try {
            lstEventsType = this.eventTypeService.getAllEventsType(typeDescription);
            for (EventType eventType : lstEventsType) {
                map = new HashMap<>(1);
                map.put("description", eventType.getTypeDescription());
                map.put("startTime", DateUtil.stampToDate(eventType.getTypeStartTime()));
                map.put("endTime", DateUtil.stampToDate(eventType.getTypeEndTime()));

                lists.add(map);
            }
        } catch (Exception e) {
            logger.warn("Search event type error. Exception: [" + e.getMessage() + "]");
        }
        return lists;
    }
}
