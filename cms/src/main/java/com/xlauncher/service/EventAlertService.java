package com.xlauncher.service;

import com.xlauncher.entity.EventAlert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 告警事件service层
 * @author 白帅雷
 * @date 2018-05-23
 */
@Service
public interface EventAlertService {

    /**
     * 从ES模块接收告警事件信息
     *
     * @param eventAlert 告警事件信息
     * @return 添加成功返回1，失败返回0
     */
    int insertAlert(EventAlert eventAlert);

    /**
     * 根据提供的事件ID获取事件图片的byte数组
     *
     * @param eventId 事件ID
     * @return 根据事件ID获取的Blob对象
     */
    byte[] getImgData(int eventId);

    /**
     * 根据提供的事件ID获取事件
     *
     * @param token 用户令牌
     * @param eventId 事件ID
     * @return EventAlert
     */
    EventAlert getAlertByEventId(int eventId, @Param("token")String token);

    /**
     * 设置指定告警事件的图片资源字段
     *
     * @param eventAlert 告警事件对象
     * @return 更新动作影响的数据库的行数，正确返回1；错误返回0
     */
    int updateImgData(EventAlert eventAlert);

    /**
     * 告警事件复核
     * @param eventAlert 告警事件对象
     * @param token 用户令牌
     * @return 成功返回1；失败返回0
     */
    int updateEventCheck(EventAlert eventAlert, String token);

    /**
     * 根据索引条件查询告警事件未复核信息并导出
     *
     * @param upStartTime 查询的起始时间
     * @param lowStartTime 查询的结束时间
     * @param typeDescription 事件类型
     * @param channelLocation 通道位置
     * @param channelHandler  事件负责人
     * @param channelOrg   通道所属组织
     * @return 告警事件event列表
     */
    List<EventAlert> listNotCheckAlertForExcel(@Param("upStartTime") String upStartTime, @Param("lowStartTime") String lowStartTime
            , @Param("typeDescription") String typeDescription, @Param("channelLocation") String channelLocation
            , @Param("channelHandler") String channelHandler, @Param("channelOrg") String channelOrg);

    /**
     * 根据索引条件查询分页告警事件未复核信息
     *
     * @param token 用户令牌
     * @param upStartTime 查询的起始时间
     * @param lowStartTime 查询的结束时间
     * @param typeDescription 事件类型
     * @param channelLocation 通道位置
     * @param channelHandler  事件负责人
     * @param channelOrg   通道所属组织
     * @param number 页码
     * @return 告警事件event列表
     */
    List<EventAlert> listNotCheckAlert(@Param("upStartTime") String upStartTime, @Param("lowStartTime") String lowStartTime
            , @Param("typeDescription") String typeDescription, @Param("channelLocation") String channelLocation
            , @Param("channelHandler") String channelHandler, @Param("channelOrg") String channelOrg
            , @Param("number") int number, @Param("token") String token);

    /**
     * 获得未复核事件的总数
     *
     * @param upStartTime 查询的起始时间
     * @param lowStartTime 查询的结束时间
     * @param typeDescription 事件类型
     * @param channelLocation 通道位置
     * @param channelHandler  事件负责人
     * @param channelOrg   通道所属组织
     * @return 告警事件event列表
     */
    int pageNotCheckCount(@Param("upStartTime") String upStartTime, @Param("lowStartTime") String lowStartTime
            , @Param("typeDescription") String typeDescription, @Param("channelLocation") String channelLocation
            , @Param("channelHandler") String channelHandler, @Param("channelOrg") String channelOrg);

    /**
     * 根据索引条件查询告警事件已复核信息并导出
     *
     * @param upStartTime 查询的起始时间
     * @param lowStartTime 查询的结束时间
     * @param upCheckStartTime 复核的起始时间
     * @param lowCheckStartTime 复核的结束时间
     * @param typeDescription 事件类型
     * @param typeStatus 告警状态
     * @param channelLocation 通道位置
     * @param channelHandler  事件负责人
     * @param channelOrg   通道所属组织
     * @param eventReviewer 事件复核人
     * @param typeRectify 事件复核类型
     * @return 告警事件event列表
     */
    List<EventAlert> listCheckAlertForExcel(@Param("upStartTime") String upStartTime, @Param("lowStartTime") String lowStartTime
            , @Param("upCheckStartTime") String upCheckStartTime, @Param("lowCheckStartTime") String lowCheckStartTime
            , @Param("typeDescription") String typeDescription, @Param("channelLocation") String channelLocation
            , @Param("channelHandler") String channelHandler, @Param("channelOrg") String channelOrg
            , @Param("eventReviewer") String eventReviewer, @Param("typeRectify") String typeRectify, @Param("typeStatus") String typeStatus);

    /**
     * 根据索引条件查询分页告警事件已复核信息
     *
     * @param token 用户令牌
     * @param upStartTime 查询的起始时间
     * @param lowStartTime 查询的结束时间
     * @param upCheckStartTime 复核的起始时间
     * @param lowCheckStartTime 复核的结束时间
     * @param typeDescription 事件类型
     * @param typeStatus 告警状态
     * @param channelLocation 通道位置
     * @param channelHandler  事件负责人
     * @param channelOrg   通道所属组织
     * @param eventReviewer 事件复核人
     * @param typeRectify 事件复核类型
     * @param number 页码
     * @return 告警事件event列表
     */
    List<EventAlert> listCheckAlert(@Param("upStartTime") String upStartTime, @Param("lowStartTime") String lowStartTime
            , @Param("upCheckStartTime") String upCheckStartTime, @Param("lowCheckStartTime") String lowCheckStartTime
            , @Param("typeDescription") String typeDescription, @Param("typeStatus") String typeStatus
            , @Param("channelLocation") String channelLocation, @Param("channelHandler") String channelHandler
            , @Param("channelOrg") String channelOrg, @Param("eventReviewer") String eventReviewer
            , @Param("typeRectify") String typeRectify, @Param("number") int number, @Param("token") String token);

    /**
     * 获得已复核事件的总数
     *
     * @param upStartTime 查询的起始时间
     * @param lowStartTime 查询的结束时间
     * @param upCheckStartTime 复核的起始时间
     * @param lowCheckStartTime 复核的结束时间
     * @param typeDescription 事件类型
     * @param typeStatus 告警状态
     * @param channelLocation 通道位置
     * @param channelHandler  事件负责人
     * @param channelOrg   通道所属组织
     * @param eventReviewer 事件复核人
     * @param typeRectify 事件复核类型
     * @return 告警事件event列表
     */
    int pageCheckCount(@Param("upStartTime") String upStartTime, @Param("lowStartTime") String lowStartTime
            , @Param("upCheckStartTime") String upCheckStartTime, @Param("lowCheckStartTime") String lowCheckStartTime
            , @Param("typeDescription") String typeDescription, @Param("typeStatus") String typeStatus
            , @Param("channelLocation") String channelLocation, @Param("channelHandler") String channelHandler
            , @Param("channelOrg") String channelOrg, @Param("eventReviewer") String eventReviewer, @Param("typeRectify") String typeRectify);

    /**
     * 得到ES模块告警类型
     *
     * @param token 用户令牌
     * @param typeDescription 告警事件类型
     * @return List<Object>
     */
    Map getEventType(@Param("token") String token, @Param("typeDescription") String typeDescription);

    /**
     * 根据编号得到ES模块告警类型
     * @param id
     * @return
     */
    Map getEventTypeById(@Param("id") String id);

    /**
     * 添加ES模块告警类型
     *
     * @param token 用户令牌
     * @param addMap 添加的告警类型
     * @return 200
     */
    int addEventType(Map<String, Object> addMap, @Param("token") String token);

    /**
     * 添加ES模块告警事件的时间配置
     *
     * @param token 用户令牌
     * @param eventTime 添加的告警时间配置
     * @return 200
     */
    int addEventTime(Map<String, Object> eventTime, @Param("token") String token);

    /**
     * 删除ES模块告警类型
     *
     * @param token 用户令牌
     * @param deleteMap 删除的告警类型
     * @return List<Object>
     */
    int deleteEventType(Map<String, Object> deleteMap, @Param("token") String token);

    /**
     * 修改ES模块告警类型
     *
     * @param token 用户令牌
     * @param putMap 修改的告警类型
     * @return List<Object>
     */
    int putEventType(Map<String, Object> putMap, @Param("token") String token);

    /**
     * 查询已复核告警事件数量
     *
     * @param token 用户令牌
     * @param typeStatus 事件的正确状态
     * @param time 查询条件（年、季、月、周）
     * @return count数
     */
    Map<String, Object> getCheckTypeStatusCount(@Param("typeStatus") String typeStatus, @Param("time")String time, @Param("token") String token);

    /**
     * 得到告警类型数量
     *
     * @param token 用户令牌
     * @param typeDescription 告警事件类型
     * @return count数
     */
    List< Map<String, Object>> getTypeDescriptionCount(String typeDescription, @Param("token") String token);

    /**
     * 获取未复核告警事件总数
     * @param token 用户令牌
     * @return count数
     */
    int getNotCheckCount(@Param("token") String token);

    /**
     * 查询最新的未复核告警事件（5条显示）
     * @param token 用户令牌
     * @return eventAlert
     */
    List<EventAlert> getNotCheckEvent(@Param("token") String token);

}
