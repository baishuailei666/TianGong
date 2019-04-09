package com.xlauncher.service;

import com.xlauncher.entity.Channel;
import com.xlauncher.entity.Device;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 设备引流推流
 * @author YangDengcheng
 * @time 18-4-10 上午9:36
 */
public interface PushStreamService {

    /**
     * 设备引流推流推状态
     *
     * @param channelStatus 通道状态
     * @param deviceStatus 设备状态
     * @param map 图片路径
     * @param channel 通道
     * @param device 设备
     */
    void pushStreamAndStatus(int channelStatus, int deviceStatus, Map<String, Object> map, Channel channel, Device device);

    /**
     * 当出现设备连接故障等问题时调用
     *
     * @param channelStatus
     * @param deviceStatus
     * @param channel
     * @param device
     */
    void pushStatus(int channelStatus, int deviceStatus, Channel channel, Device device);

    /**
     * 当通道没有父设备时，将错误信息上报
     *
     * @param channelStatus
     * @param channel
     */
    void pushChannelStatus(int channelStatus, Channel channel);

}
