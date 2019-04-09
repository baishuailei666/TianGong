package com.xlauncher.service.impl;

import com.xlauncher.entity.Channel;
import com.xlauncher.entity.Device;
import com.xlauncher.service.PushStreamService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author YangDengcheng
 * @time 18-4-20 上午11:49
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class PushStreamServiceImplTest {
    @Autowired
    PushStreamService pushStreamService;

    private static int channelStatus = 0;
    private static int deviceStatus = 0;
    private static String deviceId = "serviceDevice";
    private static String channelId = "serviceChannel";

    @Test
    public void pushStreamAndStatus() throws Exception {
        Device device = new Device();
        device.setDeviceId(deviceId);
        device.setDeviceName("testDevice");
        device.setDeviceChannelCount(250);
        device.setDeviceUserName("admin");
        device.setDeviceUserPassword("1qaz2wsx");
        device.setDeviceType("dvr");
        device.setDeviceIp("8.11.0.76");
        device.setDevicePort("8000");
        device.setDeviceStatus("-10");

        Channel channel = new Channel();
        channel.setChannelId(channelId);
        channel.setChannelName("channel" + channelId);
        channel.setChannelNumber(1);
        channel.setChannelSourceId(deviceId);
        channel.setChannelGridId("10");
        channel.setChannelHandler("ZhangSan");
        channel.setChannelHandlerPhone("13800138000");
        channel.setChannelStatus("-10");
        channel.setChannelLocation("xx水域");
        channel.setChannelLatitude("45");
        channel.setChannelLongitude("45");
        channel.setChannelThreadId(10);
        channel.setChannelPodStatus(-1);

        Map<String, Object> map = new HashMap<>(1);
        byte[] data = new byte[1024];
        map.put("imageData", data);
        map.put("value",201901);
        // TODO 需要ICS、CMS服务的支持
        pushStreamService.pushStreamAndStatus(channelStatus,deviceStatus,map,channel,device);
    }

}