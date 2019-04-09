package com.xlauncher.service;

import com.xlauncher.entity.Channel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 通道服务层
 * @author YangDengcheng
 * @date 2018/2/27 15:38
 */
@Service("channelService")
public interface ChannelService {
    /**
     * 数据库操作（查询所有通道信息）
     * @return List<Channel>
     */
    List<Channel> queryAllChannel();

    /**
     * 数据库操作（新增一条通道信息）
     * @param channel
     * @return int success:1 failed:0
     *
     */
    int insertChannel(Channel channel);

    /**
     * 数据库操作（修改一条通道信息）
     * @param channel
     * @return int success:1 failed:0
     */
    int updateChannelMsg(Channel channel);

    /**
     * 数据库操作（删除一条通道信息）
     * @param id
     * @return int success:1 failed:0
     */
    int deleteChannel(String id);

    /**
     * 激活通道
     * @param channelId 通道编号
     * @return 数据库的操作情况
     */
    int activeChannel(String channelId);

    /**
     * 停用通道
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
     * 数据库操作（查询某个通道具体信息）
     * @param id 通道ID
     * @return Channel
     */
    Channel queryChannelMsg(String id);

    /**
     * 数据库操作（查询某个通道的设备相关信息）
     * @return String
     */
    Channel queryChannelMsgWithDevice(String id);

    /**
     * 通过DIM增加设备并引流推流推状态
     * @param channel
     * @return int success:1 failed:0
     */
    int insertChannelWithDIM(Channel channel);

    /**
     * 修改channel相关信息
     * @param channel
     * @return int success：1 failed：0
     */
    int updateChannelWithDIM(Channel channel);

    /**
     * 删除channel相关信息
     * @param id 通道id
     * @return int success：1 failed：0
     */
    int deleteChannelWithDIM(String id);
}
