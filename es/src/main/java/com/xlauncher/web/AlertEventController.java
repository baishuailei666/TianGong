package com.xlauncher.web;


import com.xlauncher.service.AlertEventService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.xlauncher.entity.AlertEvent;

import java.util.*;

/**
 * 功能：事件操作控制器
 * @author 白帅雷
 * @date 2018-02-02
 */
@Controller
@RequestMapping(value = "/es_alert_event")
public class AlertEventController {

    @Autowired
    private AlertEventService alertEventService;

    private static Logger logger = Logger.getLogger(AlertEventController.class);

    /**
     * 添加事件( eventId,eventStartTime,eventchannelId,typeId,eventSource)
     * @param alertEvent 事件信息
     * @return 插入数据库操作的返回结果，成功返回1
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Object> addEvent(@RequestBody AlertEvent alertEvent) {
        return this.alertEventService.addEvent(alertEvent);
    }


    /**
     * 修改事件状态(eventStatus,eventEndTime)
     * 返回ID
     * @param alertEvent 事件信息
     * @return 插入数据库操作的返回结果，成功返回1
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public Map<String, Object> updateEventPushStatus(@RequestBody AlertEvent alertEvent) {
        logger.info("Update event push status." + alertEvent);
        return this.alertEventService.updateEventPushStatus(alertEvent);
    }

    /**
     * 查询所有事件的信息
     * @return 事件信息的列表
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Map<String, Object> getAllEvents() {
        logger.info("Search all events");
        return this.alertEventService.getAllEvents();
    }

    /**
     * 获取告警事件的数量
     * @return 告警事件数量的map对象
     */
    @ResponseBody
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public Map<String, Object> getEventsCount() {
        return this.alertEventService.getEventsCount();
    }

    /**
     * 获取指定channelId的告警事件的数量
     * @param channelId 通道编号
     * @return 包含channelId和告警事件总数的map对象
     */
    @ResponseBody
    @RequestMapping(value = "/{channelId}/count", method = RequestMethod.GET)
    public Map<String, Object> getEventsCountByChannelId(@PathVariable String channelId) {
        return this.alertEventService.getEventsCountByChannelId(channelId);
    }

    /**
     * 根据channel_id查询事件
     * @param channelId 通道编号
     * @return 返回查询的事件，为空返回null
     */
    @ResponseBody
    @RequestMapping(value = "/channelid/{id}", method = RequestMethod.GET)
    public Map<String, Object> getEventBychannelId(@PathVariable("id") String channelId) {
        logger.info("Search events with channelId[" + channelId + "]");
        return this.alertEventService.getEventBychannelId(channelId);
    }

    /**
     * 根据指定页码查询告警事件，每页count条数据
     * @param number 页码
     * @param count 每页指定数量
     * @return 指定页码的告警事件列表
     */
    @ResponseBody
    @RequestMapping(value = "/page/{number}/{count}", method = RequestMethod.GET)
    public Map<String, Object> getAllEventsByPage(@PathVariable int number, @PathVariable int count) {
        return this.alertEventService.getAllEventsByPage(number, count);
    }

    /**
     * 根据通道和页码查询告警事件
     * @param channelId 通道编号
     * @param number 页码
     * @return 指定通道编号和页码的告警事件列表
     */
    @ResponseBody
    @RequestMapping(value = "/channelid/{channelId}/page/{number}/{count}", method = RequestMethod.GET)
    public Map<String, Object> getEventsByChannelAndPage(@PathVariable String channelId, @PathVariable int number, @PathVariable int count) {
        return this.alertEventService.getAllEventsByChannelIdAndPage(channelId, number, count);
    }

    /**
     * 根据eventID查询事件
     * @param id 事件编号
     * @return 返回查询的事件，为空返回null
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public AlertEvent getEventById(@PathVariable("id") String id) throws Exception {
        int eventId = Integer.parseInt(id);
        logger.info("Search evnnts with eventID["+eventId+"]");
        return this.alertEventService.getEventByEventId(eventId);
    }

}
