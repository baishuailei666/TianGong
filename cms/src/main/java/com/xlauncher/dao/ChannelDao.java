package com.xlauncher.dao;

import com.xlauncher.entity.Channel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 真实通道Dao层
 * @date 2018-05-08
 * @author 白帅雷
 */
@Service
public interface ChannelDao {

    /**
     * 添加通道信息
     * @param channel 通道信息
     * @return 数据库操作影响行数
     */
    int insertChannel(Channel channel);

    /**
     * 删除通道信息
     * @param channelId 通道编号
     * @return 数据操作影响行数
     */
    int deleteChannel(String channelId);

    /**
     * 删除某个设备下所有的通道信息
     * @param deviceId 设备编号，即通道中的channelSourceId
     * @return 数据库操作影响的行数
     */
    int deleteChannelByDeviceId(String deviceId);

    /**
     * 更新通道信息
     * @param channel 通道的更新信息
     * @return 数据操作影响行数
     */
    int updateChannel(Channel channel);

    /**
     * 根据通道编号查询通道信息
     *
     * @param channelId 通道编号
     * @return 通道信息
     */
    Channel getChannelByChannelId(String channelId);

    /**
     * 根据通道资源编号查询通道信息
     *
     * @param sourceId 通道资源编号
     * @return 通道信息
     */
    Channel getChannelBySourceId(String sourceId);
    /**
     * 根据索引条件查询通道信息
     * @param number 页码
     * @param channelName 通道名称
     * @param channelLocation 通道位置
     * @param channelStatus 通道状态
     * @param channelSourceId 通道资源编号
     * @return 一个包含十个通道的列表
     */
    List<Channel> listChannel(@Param("channelName") String channelName, @Param("channelLocation") String channelLocation
            , @Param("channelStatus")String channelStatus, @Param("number") int number, @Param("channelSourceId")String channelSourceId);

    /**
     * 获取所有的通道数量，用于分页
     * @param channelName 通道名称
     * @param channelLocation 通道位置
     * @param channelStatus 通道状态
     * @param channelSourceId 通道资源编号
     * @return 该设备所拥有的通道数
     */
    int countPageChannel(@Param("channelName") String channelName, @Param("channelLocation") String channelLocation, @Param("channelStatus")String channelStatus, @Param("channelSourceId")String channelSourceId);

    /**
     * 将所有的通道的地理位置取出，供地图打点使用
     * @return 通道地理位置信息列表
     */
    List<Channel> overview();

    /**
     * 查询通道序号是否重复，要求是在同一个设备下的通道编号不能大于设备最大通道数且不能重复
     * @param channelId 通道编号
     * @param channelNumber 通道序号
     * @return 已经存在返回1，不存在返回0
     */
    int channelConflict(@Param("channelId")String channelId,@Param("channelNumber") int channelNumber);

    /**
     * DIM反馈通道运行状态
     * @param channel 通道的状态，通道编号
     * @return 数据操作影响行数
     */
    int updateChannelStatus(Channel channel);

    /**
     * 激活通道
     *
     * @param channelId 通道编号
     * @return 数据库的操作情况
     */
    int activeChannel(String channelId);

    /**
     * 停用通道
     *
     * @param channelId 通道编号
     * @return 数据库的操作情况
     */
    int disableChannel(String channelId);

    /**
     * 激活设备以及对应通道disableDeviceChannel
     *
     * @param channelSourceId 通道资源编号
     * @return 数据库的操作情况
     */
    int activeDeviceChannel(String channelSourceId);

    /**
     * 停用设备以及对应通道disableDeviceChannel
     *
     * @param channelSourceId 通道资源编号
     * @return 数据库的操作情况
     */
    int disableDeviceChannel(String channelSourceId);

    /**
     * 验证告警事件的通道是否存在
     *
     * @param channelId 通道编号
     * @return 数据库的操作情况
     */
    int channelExistence(String channelId);

    /**
     * 查询通道告警信息并分页展示
     *
     * @param upStartTime 查询条件开始时间
     * @param lowStartTime 查询条件结束时间
     * @param channelHandler 通道负责人
     * @param channelLocation 通道位置
     * @param channelName 通道名称
     * @param number 页码数
     * @return 数据库信息
     */
    List<Channel> getRuntimeChannel(@Param("upStartTime") String upStartTime, @Param("lowStartTime") String lowStartTime
            , @Param("channelHandler") String channelHandler, @Param("channelLocation") String channelLocation
            , @Param("channelName") String channelName, @Param("number") int number);

    /**
     * 查询通道告警信息count数
     *
     * @param upStartTime 查询条件开始时间
     * @param lowStartTime 查询条件结束时间
     * @param channelHandler 通道负责人
     * @param channelLocation 通道位置
     * @param channelName 通道名称
     * @return 数据库信息
     */
    int countRuntimeChannel(@Param("upStartTime") String upStartTime, @Param("lowStartTime") String lowStartTime
            , @Param("channelHandler") String channelHandler, @Param("channelLocation") String channelLocation, @Param("channelName") String channelName);

    /**
     * 将所有的通道的地理位置取出，供地图打点使用
     * @return 通道地理位置信息列表
     */
    List<Channel> overviewStatus();

    /**
     * 将所有的通道的地理位置取出，供地图打点使用
     * @return 通道地理位置信息列表
     */
    List<String> overviewError();

    /**
     * 显示最新5条的设备告警信息
     *
     * @return List<Channel>
     */
    List<Channel> getChannelFault();

    /**
     * 饼状图、获取设备告警类型数量
     *
     * @return List<Channel>
     */
    List<String> getChannelFaultType();

    /**
     * 饼状图、获取设备告警数量
     *
     * @param channelFault 通道故障信息
     * @return int
     */
    int getChannelFaultCount(@Param("channelFault") String channelFault);

    /**
     * 用于判断通道经纬度是否存在
     * @param channelLatitude 通道经度
     * @param channelId
     * @return 通道维度
     */
    String getLongitudeByLatitude(@Param("channelId")String channelId, @Param("channelLatitude") String channelLatitude);

    /**
     * 通道名称查重
     * @param channelId
     * @param channelName
     * @return 已经存在返回1，未存在返回0
     */
    int countChannelName(@Param("channelId")String channelId, @Param("channelName") String channelName);

    /**
     * 通道序号查重
     * @param channelNumber
     * @param channelId
     * @return 已经存在返回1，未存在返回0
     */
    int countChannelNumber(@Param("channelId")String channelId, @Param("channelNumber") int channelNumber);

    /**
     * 通道序号查重 根据deviceId查询所属channelId
     * @param channelSourceId
     * @return
     */
    List<String> listChannelId(String channelSourceId);
}
