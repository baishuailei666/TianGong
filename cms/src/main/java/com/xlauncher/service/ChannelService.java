package com.xlauncher.service;

import com.xlauncher.entity.Channel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 真实通道Service层
 * @date 2018-05-08
 * @author 白帅雷
 */
@Service
public interface ChannelService {

    /**
     * 添加通道信息
     * @param channel 通道信息
     * @param token 用户令牌
     * @return 数据库操作影响行数
     */
    int insertChannel(Channel channel, @Param("token") String token);

    /**
     * 删除通道信息
     * @param channelId 通道编号
     * @param token 用户令牌
     * @return 数据操作影响行数
     */
    int deleteChannel(String channelId, @Param("token") String token);

    /**
     * 更新通道信息
     * @param channel 通道的更新信息
     * @param token 用户令牌
     * @return 数据操作影响行数
     */
    int updateChannel(Channel channel, @Param("token") String token);

    /**
     * 根据通道编号查询通道信息
     * @param token 用户令牌
     * @param channelId 通道编号
     *
     * @return 通道信息
     */
    Channel getChannelByChannelId(String channelId, @Param("token") String token);

    /**
     * 查询通道信息
     * @param number 页码
     * @param channelName 通道名称
     * @param channelLocation 通道位置
     * @param channelStatus 通道状态
     * @param token 用户令牌
     * @param channelSourceId 通道资源编号
     * @return 一个包含十个通道的列表
     */
    List<Channel> listChannel(@Param("channelName") String channelName, @Param("channelLocation") String channelLocation
            , @Param("channelStatus")String channelStatus, @Param("number") int number, @Param("channelSourceId") String channelSourceId, @Param("token") String token);

    /**
     * 获取所有的通道数量，用于分页
     * @param channelName 通道名称
     * @param channelLocation 通道位置
     * @param channelStatus 通道状态
     * @param channelSourceId 通道资源编号
     * @return 该设备所拥有的通道数
     */
    int countPageChannel(@Param("channelName") String channelName, @Param("channelLocation") String channelLocation
            , @Param("channelStatus")String channelStatus, @Param("channelSourceId") String channelSourceId);

    /**
     * 将所有的通道的地理位置取出，供地图打点使用
     * @return 通道地理位置信息列表
     */
    List<Channel> overview();

    /**
     * DIM反馈通道运行状态
     * @param channel 通道的状态，通道编号
     * @return 数据操作影响行数
     */
    int updateChannelStatus(Channel channel);

    /**
     * 激活通道
     * @param channelId 通道编号
     * @param token 用户令牌
     * @return 数据库的操作情况
     */
    int activeChannel(String channelId, String token);

    /**
     * 停用通道
     * @param channelId 通道编号
     * @param token 用户令牌
     * @return 数据库的操作情况
     */
    int disableChannel(String channelId, String token);

    /**
     * 查询通道告警信息并分页展示
     *
     * @param upStartTime 查询条件开始时间
     * @param lowStartTime 查询条件结束时间
     * @param channelHandler 通道负责人
     * @param channelLocation 通道位置
     * @param channelName 通道名称
     * @param number 页码数
     * @param token 用户令牌
     * @return 数据库信息
     */
    List<Channel> getRuntimeChannel(@Param("upStartTime") String upStartTime, @Param("lowStartTime") String lowStartTime
            , @Param("channelHandler") String channelHandler, @Param("channelLocation") String channelLocation
            , @Param("channelName") String channelName, @Param("number") int number, @Param("token") String token);

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
    Map<String, Object> getChannelFaultTypeAndCount();

    /**
     * 通道名称查重
     * @param channelName
     * @param id 添加通道时发送deviceId，修改通道时发送channelId
     * @return 已经存在返回1，未存在返回0
     */
    int countChannelName(String channelName, String id);

    /**
     * 通道序号查重
     * @param channelNumber
     * @param id 添加通道时发送deviceId，修改通道时发送channelId
     * @return 已经存在返回1，未存在返回0
     */
    int countChannelNumber(int channelNumber, String id);
}
