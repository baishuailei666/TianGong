package com.xlauncher.service.impl;

import com.xlauncher.dao.dimdao.IChannelDao;
import com.xlauncher.dao.dimdao.IDeviceDao;
import com.xlauncher.entity.Channel;
import com.xlauncher.entity.Device;
import com.xlauncher.service.ChannelService;
import com.xlauncher.service.DeviceService;
import com.xlauncher.util.ThreadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 设备服务层实现类
 * @author YangDengcheng
 * @date 2018/2/27 14:49
 */
@Service("deviceService")
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private IDeviceDao iDeviceDao;
    @Autowired
    private IChannelDao iChannelDao;
    @Autowired
    private ChannelService channelService;

    @Override
    public int insertDevice(Device device) {
        return iDeviceDao.insertDevice(device);
    }

    @Override
    public List<Device> queryAllDevice() {
        return iDeviceDao.queryAllDevice();
    }

    @Override
    public int updateDeviceMsg(Device device) {
        List<Channel> channelList = iChannelDao.queryChannelBySourceId(device.getDeviceId());
        channelList.forEach(channel -> {
            channelService.updateChannelWithDIM(channel);
        });
        return iDeviceDao.updateDeviceMsg(device);
    }

    @Override
    public int deleteDevice(String id) {
        return iDeviceDao.deleteDevice(id);
    }

    /**
     * 激活设备
     *
     * @param deviceId 设备编号
     * @return 数据库的操作情况
     */
    @Override
    public int activeDevice(String deviceId) {
        int status = this.iDeviceDao.activeDevice(deviceId);
        iChannelDao.activeDeviceChannel(deviceId);
        return status;
    }

    /**
     * 停用设备
     *
     * @param deviceId 设备编号
     * @return 数据库的操作情况
     */
    @Override
    public int disableDevice(String deviceId) {
        int status = this.iDeviceDao.disableDevice(deviceId);
        iChannelDao.disableDeviceChannel(deviceId);
        return status;
    }

    @Override
    public Device queryDeviceMsg(String id) {
        return iDeviceDao.queryDeviceMsg(id);
    }

    @Override
    public List<String> queryAllInSameDevice(String id) {
        return iDeviceDao.queryAllInSameDevice(id);
    }
}
