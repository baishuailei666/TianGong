package com.xlauncher.dao.dimdao;

import com.xlauncher.entity.Channel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 通道Dao层
 * @author YangDengcheng
 * @date 2018/2/27 15:15
 */
@Service("iChannelDao")
public interface IChannelDao {

    /**
     * 数据库操作（查询所有通道信息）
     * @return List<Channel>
     */
    List<Channel> queryAllChannel();

    /**
     * 查询所有在用的通道信息
     * @return
     */
    List<Channel> queryAllChannelIn();

    /**
     * 查询某一资源编号下的所有通道信息
     * @param channelSourceId
     * @return
     */
    List<Channel> queryChannelBySourceId(@Param("channelSourceId") String channelSourceId);

    /**
     * 数据库操作（新增一条通道信息）
     * @param channel
     * @return int success:1 failed:0
     *
     */
    int insertChannel(Channel channel);
    /**
     * 数据库操作（更新通道线程id）
     *
     * @param channelThreadId
     * @param channelId
     * @return int success:1 failed:0
     */
    int updateChannelThreadId(@Param("channelThreadId") Integer channelThreadId, @Param("channelId") String channelId);
    /**
     * 数据库操作（修改一条通道信息）
     * @param channel
     * @return int success:1 failed:0
     */
    int updateChannelMsg(Channel channel);

    /**
     * 反馈通道故障状态信息
     * @param channel
     * @return int success:1 failed:0
     */
    int updateChannelFault(Channel channel);

    /**
     * 数据库操作（删除一条通道信息）
     * @param id
     * @return int success:1 failed:0
     */
    int deleteChannel(String id);

    /**
     * 激活通道
     *
     * @param channelId 通道编号
     * @return 数据库的操作情况
     */
    int activeChannel(@Param("channelId") String channelId, @Param("threadId") int threadId);

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
     * 数据库操作（查看通道的线程数）
     * @param id
     * @return
     */
    int queryChannelThread(String id);

}
