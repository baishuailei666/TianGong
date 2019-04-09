package com.xlauncher.dao;

import com.xlauncher.entity.EventAlert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 告警事件dao层
 * @author 白帅雷
 * @date 2018-05-23
 */
@Service
public interface EventAlertDao {

    /**
     * 从ES模块接收告警事件信息
     *
     * @param eventAlert 告警事件信息
     * @return 添加成功返回1，失败返回0
     */
    int insertAlert(EventAlert eventAlert);

    /**
     * 将所有的告警事件的channelId取出，供地图打点使用
     *
     * @return 取出所有告警事件相关的通道ID
     */
    List<String> overview();

    /**
     * 根据提供的事件ID获取事件图片的byte数组
     *
     * @param eventId 事件ID
     * @return 根据事件ID获取的图片资源的字节数组
     */
    EventAlert getImgData(int eventId);

    /**
     * 根据提供的事件ID获取事件
     *
     * @param eventId 事件ID
     * @return EventAlert
     */
    EventAlert getAlertByEventId(int eventId);

    /**
     * 设置指定告警事件的图片资源字段
     *
     * @param eventAlert 告警事件对象
     * @return 更新动作影响的数据库的行数，正确返回1；错误返回0
     */
    int updateImgData(EventAlert eventAlert);

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
     * @param startTime 告警事件推送的开始时间
     * @param endTime 告警事件推送的结束时间
     * @param upStartTime 查询的起始时间
     * @param lowStartTime 查询的结束时间
     * @param typeDescription 事件类型
     * @param channelLocation 通道位置
     * @param channelHandler  事件负责人
     * @param channelOrg   通道所属组织
     * @param number 页码
     * @return 告警事件event列表
     */
    List<EventAlert> listNotCheckAlert(@Param("startTime") String startTime, @Param("endTime") String endTime
            , @Param("upStartTime") String upStartTime, @Param("lowStartTime") String lowStartTime
            , @Param("typeDescription") String typeDescription, @Param("channelLocation") String channelLocation
            , @Param("channelHandler") String channelHandler, @Param("channelOrg") String channelOrg
            , @Param("number") int number);

    /**
     * 用户关心的未复核事件编号存放在临时表
     * @param eventId
     * @return
     */
    void addEventIdTemp(int eventId);
    /**
     * 条件查询用户关心的未复核事件且提供分页显示
     * @param upStartTime
     * @param lowStartTime
     * @param channelLocation
     * @param channelHandler
     * @param channelOrg
     * @param number
     * @return
     */
    List<EventAlert> listCareNotCheckAlert(@Param("upStartTime") String upStartTime, @Param("lowStartTime") String lowStartTime
            , @Param("channelLocation") String channelLocation
            , @Param("channelHandler") String channelHandler, @Param("channelOrg") String channelOrg
            , @Param("number") int number);

    /**
     * 获得未复核事件的总数
     *
     * @param startTime 告警事件推送的开始时间
     * @param endTime 告警事件推送的结束时间
     * @param upStartTime 查询的起始时间
     * @param lowStartTime 查询的结束时间
     * @param typeDescription 事件类型
     * @param channelLocation 通道位置
     * @param channelHandler  事件负责人
     * @param channelOrg   通道所属组织
     * @return 告警事件event列表
     */
    int pageNotCheckCount(@Param("startTime") String startTime, @Param("endTime") String endTime
            , @Param("upStartTime") String upStartTime, @Param("lowStartTime") String lowStartTime
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
            , @Param("typeDescription") String typeDescription, @Param("typeStatus") String typeStatus, @Param("channelLocation") String channelLocation
            , @Param("channelHandler") String channelHandler, @Param("channelOrg") String channelOrg
            , @Param("eventReviewer") String eventReviewer, @Param("typeRectify") String typeRectify, @Param("number") int number);

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
            , @Param("channelLocation") String channelLocation
            , @Param("channelHandler") String channelHandler, @Param("channelOrg") String channelOrg
            , @Param("eventReviewer") String eventReviewer, @Param("typeRectify") String typeRectify);

    /**
     * 告警事件复核
     * @param eventAlert 告警事件对象
     * @return 成功返回1；失败返回0
     */
    int updateEventCheck(EventAlert eventAlert);

    /**
     * 得到告警类型数量
     *
     * @param startTime 告警事件推送的开始时间
     * @param endTime 告警事件推送的结束时间
     * @param typeDescription 告警事件类型
     * @return count数
     */
    int getTypeDescriptionCount(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("typeDescription") String typeDescription);

    /**
     * 获取未复核告警事件总数
     *
     * @return count数
     */
    int getNotCheckCount();

    /**
     * 查询最新的未复核告警事件（5条显示）
     *
     * @param startTime 告警事件推送的开始时间
     * @param endTime 告警事件推送的结束时间
     * @param typeDescription 告警事件类型
     * @return eventAlert
     */
    List<EventAlert> getNotCheckEvent(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("typeDescription") String typeDescription);

    /**
     * 按照一周查询已复核告警数量
     *
     * @param typeStatus 告警事件报告状态
     * @param num 需要传入的天数
     * @return 一周已复核告警数量
     */
    int getCheckTypeStatusCountWeek(@Param("typeStatus") String typeStatus, @Param("num") int num);

    /**
     * 按照一个月份查询已复核告警数量
     *
     * @param typeStatus 告警事件报告状态
     * @param num 需要传入的天数
     * @return 一个月份已复核告警数量
     */
    int getCheckTypeStatusCountMonth(@Param("typeStatus") String typeStatus, @Param("num") int num);

    /**
     * 按照一个季度查询已复核告警数量
     *
     * @param typeStatus 告警事件报告状态
     * @param num 需要传入的天数
     * @return 季度已复核告警数量
     */
    int getCheckTypeStatusCountQuarter(@Param("typeStatus") String typeStatus, @Param("num") int num);

    /**
     * 按照半年查询已复核告警数量
     *
     * @param typeStatus 告警事件报告状态
     * @param num 需要传入的天数
     * @return 近半年已复核告警数量
     */
    int getCheckTypeStatusCountYear(@Param("typeStatus") String typeStatus, @Param("num") int num);

    /**
     * 查询已复核告警数量
     *
     * @param typeStatus 告警事件报告状态
     * @return 已复核告警数量
     */
    int getCheckTypeStatusCount(@Param("typeStatus") String typeStatus);

    int countByChannel(String channelId);

}
