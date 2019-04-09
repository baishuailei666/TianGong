package com.xlauncher.service;

import com.xlauncher.entity.VirtualChannel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * 虚拟通道Service层
 * @date 2018-05-09
 * @author 白帅雷
 */
@Service
public interface VirtualChannelService {
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
     * DIM反馈虚拟通道运行状态
     * @param virtualChannel 虚拟通道的状态，通道编号
     * @return 数据操作影响行数
     */
    int updateVirtualChannelStatus(VirtualChannel virtualChannel);
}
