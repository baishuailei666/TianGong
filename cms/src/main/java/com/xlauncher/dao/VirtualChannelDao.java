package com.xlauncher.dao;

import com.xlauncher.entity.VirtualChannel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 虚拟通道Dao层
 * @date 2018-05-09
 * @author 白帅雷
 */
@Service
public interface VirtualChannelDao {
    /**
     * 查询某个虚拟设备所拥有的所有通道，分页显示
     * @param virtualChannelSourceId 虚拟设备的资源编号
     * @param number 页码
     * @return 一个具有相同channelSourceId的通道列表
     */
    List<VirtualChannel> listVirtualChannelBySource(@Param("virtualChannelSourceId") String virtualChannelSourceId, @Param("number") int number);

    /**
     * 添加虚拟通道信息
     * @param virtualChannel 虚拟通道信息
     * @return 数据库操作影响行数
     */
    int insertVirtualChannel(VirtualChannel virtualChannel);

    /**
     * 删除虚拟通道信息
     * @param virtualChannelId 虚拟通道编号
     * @return 数据操作影响行数
     */
    int deleteVirtualChannel(String virtualChannelId);

    /**
     * 删除某个虚拟设备下所有的通道信息
     * @param virtualDeviceId 虚拟设备编号，即虚拟通道中的virtualChannelSourceId
     * @return 数据库操作影响的行数
     */
    int deleteVirtualChannelByVirtualDeviceId(String virtualDeviceId);

    /**
     * 更新虚拟通道信息
     * @param virtualChannel 虚拟通道的更新信息
     * @return 数据操作影响行数
     */
    int updateVirtualChannel(VirtualChannel virtualChannel);

    /**
     * 根据虚拟通道编号查询虚拟通道信息
     * @param virtualChannelId 虚拟通道编号
     * @return 虚拟通道信息
     */
    VirtualChannel getVirtualChannelByVirtualChannelId(String virtualChannelId);

    /**
     * 查询虚拟通道信息
     * @param number 页码
     * @return 一个包含十个通道的列表
     */
    List<VirtualChannel> listVirtualChannel(int number);

    /**
     * 获取某虚拟设备下所有的虚拟通道数量，用于分页
     * @param virtualChannelSourceId 虚拟设备的编号
     * @return 该虚拟设备所拥有的通道数
     */
    int countPage(@Param("virtualChannelSourceId") String virtualChannelSourceId);

    /**
     * 将所有的虚拟通道的地理位置取出，供地图打点使用
     * @return 虚拟通道地理位置信息列表
     */
    List<VirtualChannel> overview();

    /**
     * 查询通道是否已经存在，用于正阳系统推送通道信息时重复推送
     *
     * @param virtualChannelId 虚拟通道编号
     * @return 已经存在返回1，不存在返回0
     */
    int virtualChannelExistence(@Param("virtualChannelId")String virtualChannelId);

    /**
     * 查询虚拟通道序号是否重复，要求是在同一个设备下的虚拟通道编号不能大于设备最大通道数且不能重复
     * @param virtualChannelId 虚拟通道编号
     * @param virtualChannelNumber 虚拟通道序号
     * @return 已经存在返回1，不存在返回0
     */
    int virtualChannelConflict(@Param("virtualChannelId")String virtualChannelId,@Param("virtualChannelNumber") int virtualChannelNumber);

    /**
     * 查询某一个虚拟设备下支持的通道总数
     * @param virtualChannelSourceId 虚拟通道资源编号
     * @return 数据操作影响行数
     */
    int virtualDeviceChannelCount(@Param("virtualChannelSourceId") String virtualChannelSourceId);

    /**
     * DIM反馈虚拟通道运行状态
     * @param virtualChannel 虚拟通道的状态，通道编号
     * @return 数据操作影响行数
     */
    int updateVirtualChannelStatus(VirtualChannel virtualChannel);
}
