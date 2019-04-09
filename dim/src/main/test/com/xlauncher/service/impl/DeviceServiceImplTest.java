package com.xlauncher.service.impl;

import com.xlauncher.entity.Device;
import com.xlauncher.service.DeviceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * 设备Dao层测试
 * @author YangDengcheng
 * @time 18-4-3 上午9:36
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@Transactional
public class DeviceServiceImplTest {

    @Autowired
    private DeviceService deviceService;

    static String deviceId = "testtesttesttesttesttesttesttest";


    @Test
    public void insertDevice() throws Exception {
        Device device = new Device();
        device.setDeviceId(deviceId);
        device.setDeviceIp("8.11.0.76");
        device.setDevicePort("8000");
        device.setDeviceName("设备Dao层测试");
        device.setDeviceUserName("admin");
        device.setDeviceUserPassword("1qaz2wsx");
        device.setDeviceType("dvr");
        device.setDeviceChannelCount(20);
        device.setDeviceStatus("-1");
        deviceService.insertDevice(device);
    }

    @Test
    public void queryAllDevice() throws Exception {
        deviceService.queryAllDevice();
    }

    @Test
    public void updateDeviceMsg() throws Exception {
        Device device = new Device();
        device.setDeviceId(deviceId);
        device.setDeviceStatus("0");

        deviceService.updateDeviceMsg(device);
    }


    @Test
    public void queryDeviceMsg() throws Exception {
        Device device = new Device();
        device.setDeviceId(deviceId);
        device.setDeviceIp("8.11.0.76");
        device.setDevicePort("8000");
        device.setDeviceName("设备Dao层测试");
        device.setDeviceUserName("admin");
        device.setDeviceUserPassword("1qaz2wsx");
        device.setDeviceType("dvr");
        device.setDeviceChannelCount(20);
        device.setDeviceStatus("-1");
        deviceService.insertDevice(device);

        deviceService.queryDeviceMsg(device.getDeviceId());
    }

    @Test
    public void queryAllInSameDevice() throws Exception {
        deviceService.queryAllInSameDevice(deviceId);
    }

    @Test
    public void deleteDevice() throws Exception {
        deviceService.deleteDevice(deviceId);
    }

}