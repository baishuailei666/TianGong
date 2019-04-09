package com.xlauncher.service.impl;

import com.xlauncher.dao.ChannelDao;
import com.xlauncher.dao.EventAlertDao;
import com.xlauncher.entity.EventAlert;
import com.xlauncher.service.EventAlertService;
import com.xlauncher.util.*;
import com.xlauncher.util.synsunnyitec.PushEventThread;
import com.xlauncher.util.synsunnyitec.PushEventToSunnyintec;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 告警事件service层
 * @author 白帅雷
 * @date 2018-05-23
 */
@Service
public class EventAlertServiceImpl implements EventAlertService {
    @Autowired
    private EventAlertDao eventAlertDao;
    @Autowired
    private ChannelDao channelDao;
    @Autowired
    private RestTemplateUtil templateUtil;
    @Autowired
    private OperationLogUtil logUtil;
    @Autowired
    private ImgUtil imgUtil;
    @Autowired
    PushEventToSunnyintec pushEventToSunnyintec;
    @Autowired
    private CheckToken checkToken;
    private static final int len = 10;
    private static final String MODULE = "事件告警复核";
    private static final String SYSTEM_MODULE = "事件管理";
    private static final String CATEGORY = "运营面";
    private static Logger logger = Logger.getLogger(EventAlertServiceImpl.class);
    /**
     * 从ES模块接收告警事件信息
     *
     * @param eventAlert 告警事件信息
     * @return 添加成功返回1，失败返回0
     */
    @Override
    public int insertAlert(EventAlert eventAlert) {
        if (channelDao.channelExistence(eventAlert.getChannelId())!=0) {
            logger.info("从ES模块接收告警事件信息" + eventAlert);
            eventAlert.setChannelName(channelDao.getChannelByChannelId(eventAlert.getChannelId()).getChannelName());
            int insertResult = this.eventAlertDao.insertAlert(eventAlert);

            if (insertResult == 1) {
                if (imgUtil.getImgDataFromSource(eventAlert.getEventSource()) != null) {
                    logger.info("更新图片！insertResult:" + insertResult + "img.length:" + imgUtil.getImgDataFromSource(eventAlert.getEventSource()).length);
                    eventAlert.setEventData(imgUtil.getImgDataFromSource(eventAlert.getEventSource()));
                    if (eventAlert.getEventData() == null || eventAlert.getEventData().length == 0) {
                        logger.warn("图片资源不可达！" + eventAlert);
                    } else {
                        int updateImg = this.eventAlertDao.updateImgData(eventAlert);

                        if (updateImg == 1) {
                            logger.info("更新图片成功！" + updateImg);
                        } else {
                            logger.warn("更新图片失败！" + eventAlert);
                        }
                    }
                } else {
                    logger.warn("ImgUtil.getImgDataFromSource" + Arrays.toString(imgUtil.getImgDataFromSource(eventAlert.getEventSource())));
                    logger.warn("图片资源不可达getImgDataFromSource！" + eventAlert);
                }
                logger.info("接收到告警, 启动上报告警线程!");
//                new Thread(new PushEventThread(eventAlert)).start();
            }
            return insertResult;
        }
        return 0;
    }


    /**
     * 根据提供的事件ID获取事件图片的byte数组
     *
     * @param eventId 事件ID
     * @return 根据事件ID获取的图片资源的字节数组
     */
    @Override
    public byte[] getImgData(int eventId) {
        logger.info("根据事件ID获取事件图片" + eventId);
        return this.eventAlertDao.getImgData(eventId).getEventData();
    }

    /**
     * 根据提供的事件ID获取事件
     *
     * @param token 用户令牌
     * @param eventId 事件ID
     * @return EventAlert
     */
    @Override
    public EventAlert getAlertByEventId(int eventId, String token) {
        logger.info("根据提供的事件ID获取事件" + eventId);
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询",MODULE,SYSTEM_MODULE,"根据提供的事件ID" + eventId + "查询告警事件",CATEGORY);
        EventAlert eventAlert = this.eventAlertDao.getAlertByEventId(eventId);
        eventAlert.setEventStartTime(eventAlert.getEventStartTime().substring(0,19));
        return eventAlert;
    }

    /**
     * 设置指定告警事件的图片资源字段
     *
     * @param eventAlert 告警事件对象
     * @return 更新动作影响的数据库的行数，正确返回1；错误返回0
     */
    @Override
    public int updateImgData(EventAlert eventAlert) {
        logger.info("设置指定告警事件的图片资源字段" + eventAlert);
        int retUpd = 0;
        eventAlert.setEventData(imgUtil.getImgDataFromSource(eventAlert.getEventSource()));
        retUpd = this.eventAlertDao.updateImgData(eventAlert);
        return retUpd;
    }

    /**
     * 告警事件复核
     *
     * @param token 用户令牌
     * @param eventAlert 告警事件对象
     * @return 成功返回1；失败返回0
     */
    @Override
    public int updateEventCheck(EventAlert eventAlert, String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"更新",MODULE,SYSTEM_MODULE,"复核告警编号为：" + eventAlert.getEventId() + "的告警事件",CATEGORY);
        eventAlert.setEventCheck("已复核");
        if (!"无".equals(eventAlert.getTypeRectify())) {
            eventAlert.setTypeRectify(eventAlert.getTypeRectify().split(":")[1]);
        }
        eventAlert.setEventReviewer(checkToken.checkToken(token).getUserName());
        return this.eventAlertDao.updateEventCheck(eventAlert);
    }

    /**
     * 根据索引条件查询告警事件未复核信息并导出
     *
     * @param upStartTime     查询的起始时间
     * @param lowStartTime    查询的结束时间
     * @param typeDescription 事件类型
     * @param channelLocation 通道位置
     * @param channelHandler  事件负责人
     * @param channelOrg      通道所属组织
     * @return 告警事件event列表
     */
    @Override
    public List<EventAlert> listNotCheckAlertForExcel(String upStartTime, String lowStartTime, String typeDescription, String channelLocation, String channelHandler, String channelOrg) {
        logger.info("根据索引条件查询告警事件未复核信息并导出" + typeDescription);
        return this.eventAlertDao.listNotCheckAlertForExcel(upStartTime, lowStartTime, typeDescription, channelLocation, channelHandler, channelOrg);
    }


    /**
     * 根据索引条件查询分页告警事件未复核信息
     *
     * @param token 用户令牌
     * @param upStartTime     查询的起始时间
     * @param lowStartTime    查询的结束时间
     * @param typeDescription 事件类型
     * @param channelLocation 通道位置
     * @param channelHandler  事件负责人
     * @param channelOrg      通道所属组织
     * @param number          页码
     * @return 告警事件event列表
     */
    @Override
    public List<EventAlert> listNotCheckAlert(String upStartTime, String lowStartTime, String typeDescription, String channelLocation, String channelHandler, String channelOrg, int number, String token) {
        logger.info("根据索引条件查询分页告警事件未复核信息:" + "开始时间:" + upStartTime + ",结束时间:" + lowStartTime + ",通道位置:" + channelLocation + ",通道负责人:" + channelHandler + ",通道组织:" + channelOrg + ",告警类型:" + typeDescription);
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询",MODULE,SYSTEM_MODULE,"根据索引条件:开始时间,结束时间,类型描述,通道位置,通道负责人查询未复核告警事件信息",CATEGORY);
        String startTime;
        String endTime;
        String[] eventTypes = {"非法船只入侵报警","非法钓鱼报警","垃圾污染报警","人员非法入侵","检测到河面垃圾"};
        if (lowStartTime.length() <= len && !"undefined".equals(lowStartTime)) {
            lowStartTime = lowStartTime + "23:59:59";
        }
        if (Objects.equals(typeDescription, "undefined")) {
            String startTimeShips = String.valueOf(templateUtil.getEventTypeToES(eventTypes[0]).get("typeStartTime"));
            String endTimeShips = String.valueOf(templateUtil.getEventTypeToES(eventTypes[0]).get("typeEndTime"));
            List<EventAlert> lists0 = this.eventAlertDao.listNotCheckAlert(startTimeShips,endTimeShips,upStartTime, lowStartTime, eventTypes[0], channelLocation, channelHandler, channelOrg, number);
            logger.info("未复核告警-非法船只入侵报警：startTimeShips:" + startTimeShips + " , endTimeShips:" + endTimeShips + " , typeDescription:" + eventTypes[0] + ", lists0:" + lists0.size());

            String startTimeFishing = String.valueOf(templateUtil.getEventTypeToES(eventTypes[1]).get("typeStartTime"));
            String endTimeFishing = String.valueOf(templateUtil.getEventTypeToES(eventTypes[1]).get("typeEndTime"));
            List<EventAlert> lists1 = this.eventAlertDao.listNotCheckAlert(startTimeFishing,endTimeFishing,upStartTime, lowStartTime, eventTypes[1], channelLocation, channelHandler, channelOrg, number);
            logger.info("未复核告警-非法钓鱼报警：startTimeFishing:" + startTimeFishing + " , endTimeFishing:" + endTimeFishing + " , typeDescription:" + eventTypes[1] + ", lists1:" + lists1.size());

            String startTimeRubbish = String.valueOf(templateUtil.getEventTypeToES(eventTypes[2]).get("typeStartTime"));
            String endTimeRubbish = String.valueOf(templateUtil.getEventTypeToES(eventTypes[2]).get("typeEndTime"));
            List<EventAlert> lists2 = this.eventAlertDao.listNotCheckAlert(startTimeRubbish,endTimeRubbish,upStartTime, lowStartTime, eventTypes[2], channelLocation, channelHandler, channelOrg, number);
            logger.info("未复核告警-垃圾污染报警：startTimeRubbish:" + startTimeRubbish + " , endTimeRubbish:" + endTimeRubbish + " , typeDescription:" + eventTypes[2] + ", lists2:" + lists2.size());

            String startTimePerson = String.valueOf(templateUtil.getEventTypeToES(eventTypes[3]).get("typeStartTime"));
            String endTimePerson = String.valueOf(templateUtil.getEventTypeToES(eventTypes[3]).get("typeEndTime"));
            List<EventAlert> lists3 = this.eventAlertDao.listNotCheckAlert(startTimePerson,endTimePerson,upStartTime, lowStartTime, eventTypes[3], channelLocation, channelHandler, channelOrg, number);
            logger.info("未复核告警-人员非法入侵：startTimePerson:" + startTimePerson + " , endTimePerson:" + endTimePerson + " , typeDescription:" + eventTypes[3] + ", lists3:" + lists3.size());

            String startTimePersonRiver = String.valueOf(templateUtil.getEventTypeToES(eventTypes[4]).get("typeStartTime"));
            String endTimePersonRiver = String.valueOf(templateUtil.getEventTypeToES(eventTypes[4]).get("typeEndTime"));
            List<EventAlert> lists4 = this.eventAlertDao.listNotCheckAlert(startTimePersonRiver,endTimePersonRiver,upStartTime, lowStartTime, eventTypes[4], channelLocation, channelHandler, channelOrg, number);
            logger.info("未复核告警-检测到河面垃圾：startTimePersonRiver:" + startTimePersonRiver + " , endTimePersonRiver:" + endTimePersonRiver + " , typeDescription:" + eventTypes[4] + ", lists4:" + lists4.size());

            List<List<EventAlert>> lists = new ArrayList<>(1);
            if (lists0.size() != 0) {
                logger.info("非法船只入侵报警lists0:" + lists0.size());
                lists0.forEach(eventAlert -> {
                    eventAlert.setEventStartTime(eventAlert.getEventStartTime().substring(0,19));
                });
                lists.add(lists0);
            }
            if (lists1.size() != 0) {
                logger.info("非法钓鱼报警lists1:" + lists1.size());
                lists1.forEach(eventAlert -> {
                    eventAlert.setEventStartTime(eventAlert.getEventStartTime().substring(0,19));
                });
                lists.add(lists1);
            }
            if (lists2.size() != 0) {
                logger.info("垃圾污染报警lists2:" + lists2.size());
                lists2.forEach(eventAlert -> {
                    eventAlert.setEventStartTime(eventAlert.getEventStartTime().substring(0,19));
                });
                lists.add(lists2);
            }
            if (lists3.size() != 0) {
                logger.info("人员非法入侵lists3:" + lists3.size());
                lists3.forEach(eventAlert -> {
                    eventAlert.setEventStartTime(eventAlert.getEventStartTime().substring(0,19));
                });
                lists.add(lists3);
            }
            if (lists4.size() != 0) {
                logger.info("检测到河面垃圾lists4:" + lists4.size());
                lists4.forEach(eventAlert -> {
                    eventAlert.setEventStartTime(eventAlert.getEventStartTime().substring(0,19));
                });
                lists.add(lists4);
            }

            lists.forEach(list -> {
                list.forEach(eventAlert -> {
                    eventAlert.setEventStartTime(eventAlert.getEventStartTime().substring(0,19));
                    this.eventAlertDao.addEventIdTemp(eventAlert.getEventId());
                });
            });

            List<EventAlert> alertList = this.eventAlertDao.listCareNotCheckAlert(upStartTime, lowStartTime, channelLocation, channelHandler, channelOrg, number);
            alertList.forEach(eventAlert -> {
                eventAlert.setEventStartTime(eventAlert.getEventStartTime().substring(0,19));
            });
            return alertList;
        } else {
            startTime = String.valueOf(templateUtil.getEventTypeToES(typeDescription).get("typeStartTime"));
            endTime = String.valueOf(templateUtil.getEventTypeToES(typeDescription).get("typeEndTime"));
            logger.info("未复核告警：startTime:" + startTime + " , endTime:" + endTime + " , typeDescription:" + typeDescription);
            List<EventAlert> lists = this.eventAlertDao.listNotCheckAlert(startTime,endTime,upStartTime, lowStartTime, typeDescription, channelLocation, channelHandler, channelOrg, number);
            lists.forEach(eventAlert -> {
                eventAlert.setEventStartTime(eventAlert.getEventStartTime().substring(0,19));
              });
            return lists;
        }
    }

    /**
     * 获得事件的总数
     *
     * @param upStartTime     查询的起始时间
     * @param lowStartTime    查询的结束时间
     * @param typeDescription 事件类型
     * @param channelLocation 通道位置
     * @param channelHandler  事件负责人
     * @param channelOrg      通道所属组织
     * @return 告警事件event列表
     */
    @Override
    public int pageNotCheckCount(String upStartTime, String lowStartTime, String typeDescription, String channelLocation, String channelHandler, String channelOrg) {
        String startTime;
        String endTime;
        String[] eventTypes = {"非法船只入侵报警","非法钓鱼报警","垃圾污染报警","人员非法入侵","检测到河面垃圾"};
        if (lowStartTime.length() <= len && !"undefined".equals(lowStartTime)) {
            lowStartTime = lowStartTime + "23:59:59";
        }
        if (Objects.equals(typeDescription, "undefined")) {
            String startTimeShips = String.valueOf(templateUtil.getEventTypeToES(eventTypes[0]).get("typeStartTime"));
            String endTimeShips = String.valueOf(templateUtil.getEventTypeToES(eventTypes[0]).get("typeEndTime"));
            int ships = this.eventAlertDao.pageNotCheckCount(startTimeShips,endTimeShips,upStartTime, lowStartTime, eventTypes[0], channelLocation, channelHandler, channelOrg);
            logger.info("未复核告警count数-非法船只入侵报警：startTimeShips:" + startTimeShips + " , endTimeShips:" + endTimeShips + " , typeDescription:" + eventTypes[0] + ", count:" + ships);

            String startTimeFishing = String.valueOf(templateUtil.getEventTypeToES(eventTypes[1]).get("typeStartTime"));
            String endTimeFishing = String.valueOf(templateUtil.getEventTypeToES(eventTypes[1]).get("typeEndTime"));
            int fishing = this.eventAlertDao.pageNotCheckCount(startTimeFishing,endTimeFishing,upStartTime, lowStartTime, eventTypes[1], channelLocation, channelHandler, channelOrg);
            logger.info("未复核告警count数-非法钓鱼报警：startTimeFishing:" + startTimeFishing + " , endTimeFishing:" + endTimeFishing + " , typeDescription:" + eventTypes[1] + ", count:" + fishing);

            String startTimeRubbish = String.valueOf(templateUtil.getEventTypeToES(eventTypes[2]).get("typeStartTime"));
            String endTimeRubbish = String.valueOf(templateUtil.getEventTypeToES(eventTypes[2]).get("typeEndTime"));
            int rubbish = this.eventAlertDao.pageNotCheckCount(startTimeRubbish,endTimeRubbish,upStartTime, lowStartTime, eventTypes[2], channelLocation, channelHandler, channelOrg);
            logger.info("未复核告警count数-垃圾污染报警：startTimeRubbish:" + startTimeRubbish + " , endTimeRubbish:" + endTimeRubbish + " , typeDescription:" + eventTypes[2] + ", count:" + rubbish);

            String startTimePerson = String.valueOf(templateUtil.getEventTypeToES(eventTypes[3]).get("typeStartTime"));
            String endTimePerson = String.valueOf(templateUtil.getEventTypeToES(eventTypes[3]).get("typeEndTime"));
            int person = this.eventAlertDao.pageNotCheckCount(startTimePerson,endTimePerson,upStartTime, lowStartTime, eventTypes[3], channelLocation, channelHandler, channelOrg);
            logger.info("未复核告警count数-人员非法入侵：startTimePerson:" + startTimePerson + " , endTimePerson:" + endTimePerson + " , typeDescription:" + eventTypes[3] + ", count:" + person);

            String startTimePersonRiver = String.valueOf(templateUtil.getEventTypeToES(eventTypes[4]).get("typeStartTime"));
            String endTimePersonRiver = String.valueOf(templateUtil.getEventTypeToES(eventTypes[4]).get("typeEndTime"));
            int river = this.eventAlertDao.pageNotCheckCount(startTimePersonRiver,endTimePersonRiver,upStartTime, lowStartTime, eventTypes[4], channelLocation, channelHandler, channelOrg);
            logger.info("未复核告警count数-检测到河面垃圾：startTimePersonRiver:" + startTimePersonRiver + " , endTimePersonRiver:" + endTimePersonRiver + " , typeDescription:" + eventTypes[4] + ", count:" + river);

            return ships + fishing + rubbish + person + river;
        } else {
            startTime = String.valueOf(templateUtil.getEventTypeToES(typeDescription).get("typeStartTime"));
            endTime = String.valueOf(templateUtil.getEventTypeToES(typeDescription).get("typeEndTime"));
            logger.info("未复核告警count数：startTime:" + startTime + " , endTime:" + endTime + " , typeDescription:" + typeDescription);
            return this.eventAlertDao.pageNotCheckCount(startTime,endTime,upStartTime, lowStartTime, typeDescription, channelLocation, channelHandler, channelOrg);
        }
    }

    /**
     * 根据索引条件查询告警事件已复核信息并导出
     *
     * @param upStartTime       查询的起始时间
     * @param lowStartTime      查询的结束时间
     * @param upCheckStartTime  复核的起始时间
     * @param lowCheckStartTime 复核的结束时间
     * @param typeDescription   事件类型
     * @param typeStatus 告警状态
     * @param channelLocation   通道位置
     * @param channelHandler    事件负责人
     * @param channelOrg        通道所属组织
     * @param eventReviewer     事件复核人
     * @param typeRectify       事件复核类型
     * @return 告警事件event列表
     */
    @Override
    public List<EventAlert> listCheckAlertForExcel(String upStartTime, String lowStartTime, String upCheckStartTime, String lowCheckStartTime, String typeDescription, String typeStatus, String channelLocation, String channelHandler, String channelOrg, String eventReviewer, String typeRectify) {
        logger.info("根据索引条件查询告警事件已复核信息并导出" + typeDescription);
        if (lowStartTime.length() <= len && !"undefined".equals(lowStartTime)) {
            lowStartTime = lowStartTime + "23:59:59";
        }
        return this.eventAlertDao.listCheckAlertForExcel(upStartTime, lowStartTime, upCheckStartTime, lowCheckStartTime, typeDescription, typeStatus, channelLocation, channelHandler, channelOrg, eventReviewer, typeRectify);
    }

    /**
     * 根据索引条件查询分页告警事件已复核信息
     *
     * @param token 用户令牌
     * @param upStartTime     查询的起始时间
     * @param lowStartTime    查询的结束时间
     * @param upCheckStartTime 复核的起始时间
     * @param lowCheckStartTime 复核的结束时间
     * @param typeDescription 事件类型
     * @param typeStatus 告警状态
     * @param channelLocation 通道位置
     * @param channelHandler  事件负责人
     * @param channelOrg      通道所属组织
     * @param eventReviewer   事件复核人
     * @param typeRectify     事件复核类型
     * @param number          页码
     * @return 告警事件event列表
     */
    @Override
    public List<EventAlert> listCheckAlert(String upStartTime, String lowStartTime, String upCheckStartTime, String lowCheckStartTime, String typeDescription, String typeStatus, String channelLocation, String channelHandler, String channelOrg, String eventReviewer, String typeRectify, int number, String token) {
        logger.info("根据索引条件查询分页告警事件已复核信息" + typeDescription);
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询",MODULE,SYSTEM_MODULE,"根据索引条件：开始时间,结束时间,复核开始时间,复核结束时间,类型描述,事件状态,通道位置,通道负责人,事件复核人查询已复核告警事件信息",CATEGORY);
        if (lowStartTime.length() <= len && !"undefined".equals(lowStartTime)) {
            lowStartTime = lowStartTime + "23:59:59";
        }
        List<EventAlert> lists = this.eventAlertDao.listCheckAlert(upStartTime, lowStartTime, upCheckStartTime, lowCheckStartTime, typeDescription, typeStatus, channelLocation, channelHandler, channelOrg, eventReviewer, typeRectify, number);
        lists.forEach(eventAlert -> {
            eventAlert.setEventStartTime(eventAlert.getEventStartTime().substring(0,19));
            eventAlert.setEventEndTime(eventAlert.getEventEndTime().substring(0,19));
        });
        return lists;
    }

    /**
     * 获得已复核事件的总数
     *
     * @param upStartTime     查询的起始时间
     * @param lowStartTime    查询的结束时间
     * @param upCheckStartTime 复核的起始时间
     * @param lowCheckStartTime 复核的结束时间
     * @param typeDescription 事件类型
     * @param typeStatus 告警状态
     * @param channelLocation 通道位置
     * @param channelHandler  事件负责人
     * @param channelOrg      通道所属组织
     * @param eventReviewer   事件复核人
     * @param typeRectify     事件复核类型
     * @return 告警事件event列表
     */
    @Override
    public int pageCheckCount(String upStartTime, String lowStartTime, String upCheckStartTime, String lowCheckStartTime, String typeDescription, String typeStatus, String channelLocation, String channelHandler, String channelOrg, String eventReviewer, String typeRectify) {
        if (lowStartTime.length() <= len && !"undefined".equals(lowStartTime)) {
            lowStartTime = lowStartTime + "23:59:59";
        }
        return this.eventAlertDao.pageCheckCount(upStartTime, lowStartTime, upCheckStartTime, lowCheckStartTime, typeDescription, typeStatus, channelLocation, channelHandler, channelOrg, eventReviewer, typeRectify);
    }

    /**
     * 得到ES模块告警类型
     *
     * @param token 用户令牌
     * @param typeDescription 告警事件类型
     * @return List<Object>
     */
    @Override
    public Map getEventType(String token, String typeDescription) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询","事件配置管理",SYSTEM_MODULE,"查询告警事件类型",CATEGORY);
        logger.info("得到ES模块告警类型:" + typeDescription);
        return this.templateUtil.getEventTypeToES(typeDescription);
    }

    /**
     * 根据编号得到ES模块告警类型
     *
     * @param id
     * @return
     */
    @Override
    public Map getEventTypeById(String id) {
        logger.info("根据编号得到ES模块告警类型" + id);
        return this.templateUtil.getEventTypeByIdToES(id);
    }

    /**
     * 添加ES模块告警类型
     *
     * @param token 用户令牌
     * @param addMap 添加的告警类型
     * @return List<Object>
     */
    @Override
    public int addEventType(Map<String, Object> addMap, String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"添加","事件配置管理",SYSTEM_MODULE,"添加告警事件告警类型:" + addMap,CATEGORY);
        logger.info("添加ES模块告警类型:" + addMap);
        return this.templateUtil.addEventTypeToES(addMap);
    }

    /**
     * 添加ES模块告警事件的时间配置
     *
     * @param token 用户令牌
     * @param eventTime 添加的告警时间配置
     * @return 200
     */
    @Override
    public int addEventTime(Map<String, Object> eventTime, String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"添加","事件配置管理",SYSTEM_MODULE,"添加告警事件同类型同设备运行推送告警事件的最大时间" + eventTime,CATEGORY);
        logger.info("添加ES模块告警事件的时间配置:" + eventTime);
        return this.templateUtil.addEventTimeToES(eventTime);
    }

    /**
     * 删除ES模块告警类型
     *
     * @param token 用户令牌
     * @param deleteMap 删除的告警类型
     * @return List<Object>
     */
    @Override
    public int deleteEventType(Map<String, Object> deleteMap, String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"删除","事件配置管理",SYSTEM_MODULE,"删除告警事件告警类型:" + deleteMap,CATEGORY);
        logger.info("删除ES模块告警类型:" + deleteMap);
        return this.templateUtil.deleteEventTypeToES(deleteMap);
    }

    /**
     * 修改ES模块告警类型
     *
     * @param token 用户令牌
     * @param putMap 修改的告警类型
     * @return List<Object>
     */
    @Override
    public int putEventType(Map<String, Object> putMap, String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"更新","事件配置管理",SYSTEM_MODULE,"更新告警事件告警类型:" + putMap,CATEGORY);
        logger.info("修改ES模块告警类型:" + putMap);
        return this.templateUtil.putEventTypeToES(putMap);
    }

    /**
     * 查询已复核告警事件数量
     *
     * @param token 用户令牌
     * @param typeStatus 事件的正确状态
     * @param time 查询条件（年、季、月、周）
     * @return count数
     */
    @Override
    public Map<String, Object> getCheckTypeStatusCount(String typeStatus, String time, String token) {
        logger.info("查询已复核告警事件数量" + typeStatus + "," + time);
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询","事件告警监控中心",SYSTEM_MODULE,"查询事件告警统计数量",CATEGORY);
        Map<String, Object> map = new HashMap<>(1);
        List<Object> listTime = new ArrayList<>(1);
        List<Object> listData = new ArrayList<>(1);
        List<String> listTimeResponse = new ArrayList<>(1);
        List<String> listDataResponse = new ArrayList<>(1);
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        String[] months = {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月", };
        logger.info("weekDays:" + Arrays.toString(weekDays));
        logger.info("months:" + Arrays.toString(months));
        logger.info("请求的参数typeStatus：" + typeStatus + ", time: " + time);
        Calendar calendar = Calendar.getInstance();
        int all = this.eventAlertDao.getCheckTypeStatusCount("undefined");
        String status = "undefined";
        String year = "year";
        String tQuarter = "tQuarter";
        String month = "month";
        String week = "week";
            if (Objects.equals(time, week)) {
                logger.info(" ***查询WEEK*** ");
                int countNow = this.eventAlertDao.getCheckTypeStatusCountWeek(typeStatus, 1);
                int indexWeek = calendar.get(Calendar.DAY_OF_WEEK);
                String weekNow = weekDays[indexWeek-1];
                listTime.add(weekNow);
                listData.add(countNow);
                for (int d = 0; d < weekDays.length; d++) {
                    if (Objects.equals(weekNow, weekDays[d])) {
                        int seven = 7;
                        String weekSub;
                        for (int j = 1; j < seven - d; j++) {
                            weekSub = weekDays[d + j];
                            int countSub = this.eventAlertDao.getCheckTypeStatusCountWeek(typeStatus, seven - j + 1);
                            int countSubSum = this.eventAlertDao.getCheckTypeStatusCountWeek(typeStatus, seven - j);
                            listTime.add(weekSub);
                            listData.add(countSub - countSubSum);
                        }
                        for (int k = 1; k <= d; k++) {
                            weekSub = weekDays[d - k];
                            int countSub = this.eventAlertDao.getCheckTypeStatusCountWeek(typeStatus, k + 1);
                            int countSubSum = this.eventAlertDao.getCheckTypeStatusCountWeek(typeStatus, k);
                            listData.add(countSub - countSubSum);
                            listTime.add(weekSub);
                        }
                    }
                }
                System.out.println(listTime);
                for (int index = 0; index < listTime.size(); index++) {
                    listTimeResponse.add(index, String.valueOf(listTime.get(listTime.size()-1 - index)));
                    listDataResponse.add(index, String.valueOf(listData.get(listTime.size()-1 - index)));
                }
                map.put("time", listTimeResponse);
                map.put("data", listDataResponse);
                logger.info(" ***返回time*** " + listTimeResponse);
                logger.info(" ***返回data*** " + listDataResponse);
                return map;
            } else if (Objects.equals(time, month)) {
                logger.info(" ***查询MONTH*** ");
                int monthSub = 30;
                SimpleDateFormat dateFm = new SimpleDateFormat("dd");
                for (int i = 0; i < monthSub; i++) {
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, -i);
                    Date dateMonth = cal.getTime();
                    String day = dateFm.format(dateMonth);
                    int countSum = this.eventAlertDao.getCheckTypeStatusCountMonth(typeStatus, i);
                    int countSub = this.eventAlertDao.getCheckTypeStatusCountMonth(typeStatus, i + 1);
                    listTime.add(day);
                    listData.add(countSub - countSum);
                }
                for (int index = 0; index < listTime.size(); index++) {
                    listTimeResponse.add(index, String.valueOf(listTime.get(29 - index)));
                    listDataResponse.add(index, String.valueOf(listData.get(29 - index)));
                }
                map.put("time", listTimeResponse);
                map.put("data", listDataResponse);
                return map;
            } else if (Objects.equals(time, tQuarter)) {
                logger.info(" ***查询QUARTER*** ");
                int countNow = this.eventAlertDao.getCheckTypeStatusCountQuarter(typeStatus, 0);
                int indexMonth = calendar.get(Calendar.MONTH);
                String dayNow = months[indexMonth];
                listTime.add(dayNow);
                listData.add(countNow);
                for (int q = 0; q < months.length; q++) {
                    if (Objects.equals(dayNow, months[q])) {
                        int twelve = 12;
                        int there = 3;
                        String daySub;
                        if (q + 1 >= there) {
                            for (int k = 1; k < there; k++) {
                                daySub = months[q - k];
                                int countSub = this.eventAlertDao.getCheckTypeStatusCountQuarter(typeStatus, k);
                                listTime.add(daySub);
                                listData.add(countSub);
                            }
                        } else {
                            for (int x = 0; x < q; x++) {
                                daySub = months[q - x];
                                int countSub = this.eventAlertDao.getCheckTypeStatusCountQuarter(typeStatus, x);
                                listTime.add(daySub);
                                listData.add(countSub);
                            }
                            for (int y = 1; y < there - q; y++) {
                                daySub = months[twelve - (there - q - y)];
                                int countSub = this.eventAlertDao.getCheckTypeStatusCountQuarter(typeStatus, there - y);
                                listTime.add(daySub);
                                listData.add(countSub);
                            }
                        }
                    }
                }
                for (int index = 0; index < listTime.size(); index++) {
                    listTimeResponse.add(index, String.valueOf(listTime.get(listTime.size()-1 - index)));
                    listDataResponse.add(index, String.valueOf(listData.get(listTime.size()-1 - index)));
                }
                map.put("time", listTimeResponse);
                map.put("data", listDataResponse);
                logger.info(" ***返回time*** " + listTimeResponse);
                logger.info(" ***返回data*** " + listDataResponse);
                return map;
            } else if (Objects.equals(time, year)) {
                logger.info(" ***查询YEAR*** ");
                int countNow = this.eventAlertDao.getCheckTypeStatusCountYear(typeStatus, 0);
                int indexMonth = calendar.get(Calendar.MONTH);
                String monthNow = months[indexMonth];
                listTime.add(monthNow);
                listData.add(countNow);
                for (int m = 0; m < months.length; m++) {
                    if (Objects.equals(monthNow, months[m])) {
                        int twelve = 12;
                        int six = 6;
                        String monthSub;
                        if (m + 1 >= six) {
                            for (int k = 1; k < six; k++) {
                                monthSub = months[m - k];
                                int countSub = this.eventAlertDao.getCheckTypeStatusCountYear(typeStatus, k);
                                listTime.add(monthSub);
                                listData.add(countSub);
                            }
                        } else {
                            for (int i = 0; i < m; i++) {
                                monthSub = months[m - i];
                                int countSub = this.eventAlertDao.getCheckTypeStatusCountYear(typeStatus, i);
                                listTime.add(monthSub);
                                listData.add(countSub);
                            }
                            for (int j = 1; j < six - m; j++) {
                                monthSub = months[twelve - (six - m - j)];
                                int countSub = this.eventAlertDao.getCheckTypeStatusCountYear(typeStatus, six - j);
                                listTime.add(monthSub);
                                listData.add(countSub);
                            }
                        }
                    }
                }
                for (int index = 0; index < listTime.size(); index++) {
                    listTimeResponse.add(index, String.valueOf(listTime.get(listTime.size()-1 - index)));
                    listDataResponse.add(index, String.valueOf(listData.get(listTime.size()-1 - index)));
                }
                map.put("time", listTimeResponse);
                map.put("data", listDataResponse);
                logger.info(" ***返回time*** " + listTimeResponse);
                logger.info(" ***返回data*** " + listDataResponse);
                return map;
            } else if (Objects.equals(typeStatus, status)) {
                int countTrue = this.eventAlertDao.getCheckTypeStatusCount("正确告警");
                int countFalse = this.eventAlertDao.getCheckTypeStatusCount("错误识别");
                int countInfo = this.eventAlertDao.getCheckTypeStatusCount("信息误报");
                map.put("countAll", all);
                map.put("countTrue", countTrue);
                map.put("countFalse", countFalse);
                map.put("countInfo", countInfo);
                return map;
            } else {
                int count = this.eventAlertDao.getCheckTypeStatusCount(typeStatus);
                // 创建一个数值格式化对象
                NumberFormat numberFormat = NumberFormat.getInstance();
                // 设置精确到小数点2位
                numberFormat.setMaximumFractionDigits(2);
                String percent = numberFormat.format((float)count / (float)all * 100);
                if (all == 0) {
                    map.put("count", count);
                    map.put("percent", "0%");
                    return map;
                }
                logger.info("百分比count:" + count);
                logger.info("百分比all:" + all);
                logger.info("百分比percent:" + percent);
                map.put("count", count);
                map.put("percent", percent + "%");
                return map;
            }
    }

    /**
     * 得到告警类型数量
     *
     * @param token 用户令牌
     * @param typeDescription 告警事件类型
     * @return count数
     */
    @Override
    public List< Map<String, Object>> getTypeDescriptionCount(String typeDescription, String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询","事件告警统计",SYSTEM_MODULE,"查询告警类型数量",CATEGORY);
        logger.info("得到告警类型数量" + typeDescription);
        List< Map<String, Object>> lists = new ArrayList<>(1);
        String startTime;
        String endTime;
        int countType ;
        List<Map<String, String>> eventTypeData = this.templateUtil.getESTypes("undefined");
        for (Map<String, String> eventType
                :eventTypeData) {
            Map<String, Object> map = new HashMap<>(1);
            typeDescription = eventType.get("description");
            startTime = eventType.get("startTime");
            endTime = eventType.get("endTime");
            countType = this.eventAlertDao.getTypeDescriptionCount(startTime,endTime,typeDescription);
            map.put("name",typeDescription);
            map.put("value",countType);
            lists.add(map);
        }
        return lists;
    }


    /**
     * 获取未复核告警事件总数
     *
     * @param token 用户令牌
     * @return count数
     */
    @Override
    public int getNotCheckCount(String token) {
//        logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询","事件告警监控中心",SYSTEM_MODULE,"查询未复核告警事件总数",CATEGORY);
        return this.eventAlertDao.getNotCheckCount();
    }

    /**
     * 查询最新的未复核告警事件（5条显示）
     *
     * @param token 用户令牌
     * @return eventAlert
     */
    @Override
    public List<EventAlert> getNotCheckEvent(String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询","事件告警监控中心",SYSTEM_MODULE,"查询最新的5条未复核告警事件",CATEGORY);
        String[] eventTypes = {"非法船只入侵报警","非法钓鱼报警","垃圾污染报警","人员非法入侵","检测到河面垃圾"};

        String startTimeShips = String.valueOf(templateUtil.getEventTypeToES(eventTypes[0]).get("typeStartTime"));
        String endTimeShips = String.valueOf(templateUtil.getEventTypeToES(eventTypes[0]).get("typeEndTime"));
        List<EventAlert> lists0 = this.eventAlertDao.getNotCheckEvent(startTimeShips,endTimeShips,eventTypes[0]);
//        logger.info("未复核告警-非法船只入侵报警：startTimeShips:" + startTimeShips + " , endTimeShips:" + endTimeShips + " , typeDescription:" + eventTypes[0]);

        String startTimeFishing = String.valueOf(templateUtil.getEventTypeToES(eventTypes[1]).get("typeStartTime"));
        String endTimeFishing = String.valueOf(templateUtil.getEventTypeToES(eventTypes[1]).get("typeEndTime"));
        List<EventAlert> lists1 = this.eventAlertDao.getNotCheckEvent(startTimeFishing,endTimeFishing,eventTypes[1]);
//        logger.info("未复核告警-非法钓鱼报警：startTimeFishing:" + startTimeFishing + " , endTimeFishing:" + endTimeFishing + " , typeDescription:" + eventTypes[1]);

        String startTimeRubbish = String.valueOf(templateUtil.getEventTypeToES(eventTypes[2]).get("typeStartTime"));
        String endTimeRubbish = String.valueOf(templateUtil.getEventTypeToES(eventTypes[2]).get("typeEndTime"));
        List<EventAlert> lists2 = this.eventAlertDao.getNotCheckEvent(startTimeRubbish,endTimeRubbish,eventTypes[2]);
//        logger.info("未复核告警-垃圾污染报警：startTimeRubbish:" + startTimeRubbish + " , endTimeRubbish:" + endTimeRubbish + " , typeDescription:" + eventTypes[2]);

        String startTimePerson = String.valueOf(templateUtil.getEventTypeToES(eventTypes[3]).get("typeStartTime"));
        String endTimePerson = String.valueOf(templateUtil.getEventTypeToES(eventTypes[3]).get("typeEndTime"));
        List<EventAlert> lists3 = this.eventAlertDao.getNotCheckEvent(startTimePerson,endTimePerson,eventTypes[3]);
//        logger.info("未复核告警-人员非法入侵：startTimePerson:" + startTimePerson + " , endTimePerson:" + endTimePerson + " , typeDescription:" + eventTypes[3]);

        String startTimePersonRiver = String.valueOf(templateUtil.getEventTypeToES(eventTypes[4]).get("typeStartTime"));
        String endTimePersonRiver = String.valueOf(templateUtil.getEventTypeToES(eventTypes[4]).get("typeEndTime"));
        List<EventAlert> lists4 = this.eventAlertDao.getNotCheckEvent(startTimePersonRiver,endTimePersonRiver,eventTypes[4]);
//        logger.info("未复核告警-检测到河面垃圾：startTimePersonRiver:" + startTimePersonRiver + " , endTimePersonRiver:" + endTimePersonRiver + " , typeDescription:" + eventTypes[4]);

        List<List<EventAlert>> lists = new ArrayList<>(1);
        lists.add(lists0);
        lists.add(lists1);
        lists.add(lists2);
        lists.add(lists3);
        lists.add(lists4);
        List<EventAlert> allList = new ArrayList<>(1);
        lists.forEach(list -> {
            list.forEach(eventAlert -> {
                eventAlert.setEventStartTime(eventAlert.getEventStartTime().substring(0,19));
                allList.add(eventAlert);
            });
        });
        List<EventAlert> result = new ArrayList<>(1);
        int size = allList.size();
        if (size >= 5) {
            result.add(allList.get(0));
            result.add(allList.get(1));
            result.add(allList.get(2));
            result.add(allList.get(3));
            result.add(allList.get(4));
        } else {
            result.addAll(allList);
        }
        return result;
    }
////        return this.getNotCheckEvent(token);
//    }
}
