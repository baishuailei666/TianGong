package com.xlauncher.service.Impl;

import com.xlauncher.dao.AlertEventDao;
import com.xlauncher.service.AlertEventService;
import com.xlauncher.service.runner.RestTemplateRunner;
import com.xlauncher.utils.ThreadUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import com.xlauncher.entity.AlertEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 功能：事件告警服务层的实现
 * @author 白帅雷
 * @date 2018-02-02
 */

@Service(value = "alertEventService")
public class AlertEventServiceImpl implements AlertEventService {

    @Qualifier("alertEventDao")
    @Autowired
    private AlertEventDao alertEventDao;
    @Autowired
    private ServiceProperties serviceProperties;
    @Autowired
    ThreadUtil threadUtil;
    private static Logger logger = Logger.getLogger(AlertEventServiceImpl.class);
//    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    /**
     * 添加告警
     *
     * @param alertEvent 事件信息
     * @return 插入状态码，成功返回1
     */
    @Override
    public Map<String, Object> addEvent(AlertEvent alertEvent) {
        int addEventRet = 0;
        Map<String, Object> map = new HashMap<>(1);
        if (alertEvent != null && alertEvent.getChannelId() != null && !alertEvent.getChannelId().equals("")) {
            boolean duplicate = checkAlertEventDuplicateOrNot(alertEvent);
            if (duplicate) {
                map.put("status", 0);
                map.put("warning", "重复的告警信息，服务器拒绝同一设备同一类型10分钟内的告警。");
                return map;
            } else {
                addEventRet = this.alertEventDao.addEvent(alertEvent);
                AlertEvent alertEventwithDes = this.getEventByEventId(alertEvent.getEventId());
                map.put("status", addEventRet);
                map.put("message", "服务器接受告警事件成功");
                logger.info("[ES 接受告警事件成功!] " + alertEvent);
//                executorService.execute(new RestTemplateRunner(alertEventwithDes, "cms"));
                threadUtil.ThreadUtil(new RestTemplateRunner(alertEventwithDes, "cms"));
            }
        } else {
            map.put("status", 0);
            map.put("errorMessage", "Null Information or channelId can't be null and ''");
            return map;
        }

        /*int pushCMSStatus = 0;
        pushCMSStatus = alertRestTemplateService.postAlertEvent(alertEventwithDes, "cms");
        executorService.execute(new RestTemplateRunner(alertEventwithDes, "cms"));
        map.put("pushCmsStatus", pushCMSStatus);*/
        /*Thread pushThread = new Thread(new RestTemplateRunner(alertEventwithDes, "cms"));
        pushThread.start();*/
        /*Thread pushSuntecThread = new Thread(new SunTechWSDLRunner(alertEventwithDes));
        pushSuntecThread.start();*/

        return map;
    }

    /**
     * 修改事件状态
     * @param alertEvent 事件信息
     * @return 插入状态码，成功返回1
     */
    @Override
    public Map<String, Object> updateEventPushStatus(AlertEvent alertEvent) {
        int ret = 0;
        ret = this.alertEventDao.updateEventPushStatus(alertEvent);
        Map<String, Object> retMap = new HashMap<>(1);
        retMap.put("status", ret);
        return retMap;
    }

    /**
     * 修改事件状态
     * @param alertEvent 事件信息
     * @return 插入状态码，成功返回1
     */
    @Override
    public int updateEventPushSuntecStatus(AlertEvent alertEvent) {
        int ret = 0;
        ret = this.alertEventDao.updateEventPushSuntecStatus(alertEvent);
        return ret;
    }

    /**
     * 查询所有事件信息
     * @return 所有事件信息
     */
    @Override
    public Map<String, Object> getAllEvents() {
        List<AlertEvent> lstEvents = null;
        lstEvents = this.alertEventDao.getAllEvents();
        Map<String, Object> eventMap = new HashMap<>(2);
        eventMap.put("eventList", lstEvents);
        return eventMap;
    }

    /**
     * 获取告警事件的总数
     * @return 告警事件总数
     */
    @Override
    public Map<String, Object> getEventsCount() {
        int eventCount = 0;
        eventCount = this.alertEventDao.getEventsCount();
        Map<String, Object> countMap = new HashMap<>();
        countMap.put("eventCount", eventCount);
        return countMap;
    }

    /**
     * 获取指定通道编号的总数
     * @param channelId 通道编号
     * @return 指定通道编号发生的告警事件总数
     */
    @Override
    public Map<String, Object> getEventsCountByChannelId(String channelId) {
        int eventCount = 0;
        eventCount = this.alertEventDao.getEventsCountByChannelId(channelId);
        Map<String, Object> countMap = new HashMap<>();
        countMap.put("channelId", channelId);
        countMap.put("eventCount", eventCount);
        return countMap;
    }

    /**
     * 查询事件，根据channel_id获取事件信息
     * @param channelId 通道编号
     * @return 查到的事件的AlertEvent对象
     */
    @Override
    public Map<String, Object> getEventBychannelId(String channelId) {
        List<AlertEvent> lstEvents = null;
        lstEvents = this.alertEventDao.getEventBychannelId(channelId);
        Map<String, Object> eventMap = new HashMap<>(2);
        eventMap.put("eventList", lstEvents);
        return eventMap;
    }


    /**
     * 查询事件，根据eventId获取事件信息
     * @param eventId 事件编号
     * @return 查到的事件的AlertEvent对象
     */

    @Override
    public AlertEvent getEventByEventId(int eventId) {
        return this.alertEventDao.getEventByEventId(eventId);
    }

    /**
     * 查询指定页所有事件信息，一页显示25条数据
     * @param page 页码
     * @return 指定页码的事件列表
     */
    @Override
    public Map<String, Object> getAllEventsByPage(int page, int count) {
        List<AlertEvent> eventsList = null;
        eventsList = this.alertEventDao.getAllEventsByPage((page - 1) * count, count);
        Map<String, Object> eventMap = new HashMap<>();
        eventMap.put("eventList", eventsList);
        return eventMap;
    }

    /**
     * 查询指定通道号和页码的所有事件信息，一页显示25条数据
     * @param channelId 通道号
     * @param page 页码
     * @return 指定通道号和页码的事件列表
     */
    @Override
    public Map<String, Object> getAllEventsByChannelIdAndPage(String channelId, int page, int count) {
        List<AlertEvent> eventsList = null;
        eventsList = this.alertEventDao.getAllEventsByChannelIdAndPage(channelId, (page-1)*count, count);
        Map<String, Object> eventMap = new HashMap<>();
        eventMap.put("eventList", eventsList);
        return eventMap;
    }


    /**
     * 查询所有未推送的告警事件
     * @return 未推送的告警事件列表
     */
    @Override
    public List<AlertEvent> getPushEventsRest() {
        return this.alertEventDao.getPushEventsRest();
    }

    /**
     * 查询所有未推送的告警事件
     * @return 未推送的告警事件列表
     */
    @Override
    public List<AlertEvent> getPushSuntecEventsRest() {
        return this.alertEventDao.getPushSuntecEventsRest();
    }

    /**
     * 查询推送失败的第一条信息
     * @return 推送失败的第一条信息
     */
    @Override
    public AlertEvent getPushSuntecRestTop1() {
        return this.alertEventDao.getPushSuntecRestTop1();
    }

    /**
     * 查询推送失败的第一条信息
     * @return 推送失败的第一条信息
     */
    @Override
    public AlertEvent getPushCmsRestTop1() {
        return this.alertEventDao.getPushCmsRestTop1();
    }

    /**
     * 查询本条告警与之前的告警是否冲突，规则：
     * 1.检查是否来自同一设备；
     * 2.检查类型是否相同；
     * 3.检查本次告警与上次告警发生时间点相差间隔，10分钟内视为重复告警，返回false。
     * @param alertEvent 待查的告警事件
     * @return 按照规则重复告警的情况返回true；按照规则为最新告警的情况返回false
     */
    @Override
    public boolean checkAlertEventDuplicateOrNot(AlertEvent alertEvent) {
        long eventTime = Long.parseLong(serviceProperties.getEventTime());
        boolean duplicate = false;
        AlertEvent similarAlertEvent = this.alertEventDao.checkAlertEventDuplicateOrNot(alertEvent);
        if (similarAlertEvent == null) {
            duplicate = false;
            logger.info("[ES 告警验证不冲突! 时间间隔eventTime.] " + eventTime + " 毫秒");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                long currentDateTime = sdf.parse(alertEvent.getEventStartTime()).getTime();
                logger.info("ICS 推送告警的告警时间alertEvent.getEventStartTime()" + alertEvent.getEventStartTime());
                logger.info("currentDateTime:" + currentDateTime);
                long similarDateTime = sdf.parse(similarAlertEvent.getEventStartTime()).getTime();
                logger.info("ES 存储告警的告警时间similarAlertEvent.getEventStartTime()" + similarAlertEvent.getEventStartTime());
                logger.info("similarDateTime:" + similarDateTime);
                if ((currentDateTime - similarDateTime) < eventTime) {
                    duplicate = true;
                    logger.info("[ES 告警验证冲突! 时间间隔eventTime.] " + eventTime + " 毫秒");
                }
            } catch (ParseException e) {
                duplicate = true;
                e.printStackTrace();
            }
        }
        return duplicate;
    }
}
