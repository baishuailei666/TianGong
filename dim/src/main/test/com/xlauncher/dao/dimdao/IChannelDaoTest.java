package com.xlauncher.dao.dimdao;

import com.xlauncher.entity.Channel;
import com.xlauncher.entity.Device;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author YangDengcheng
 * @time 18-4-18 下午4:16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@Transactional
public class IChannelDaoTest {
    @Autowired
    IChannelDao iChannelDao;
    @Autowired
    IDeviceDao iDeviceDao;

    private static String deviceId = "testDeviceID";
    private static String channelId = "testChannel";

    @Test
    public void queryAllChannel() throws Exception {
        iChannelDao.queryAllChannel();
    }

    @Test
    public void insertChannel() throws Exception {
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
        iDeviceDao.insertDevice(device);

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
        iChannelDao.insertChannel(channel);
    }

    @Test
    public void updateChannelMsg() throws Exception {
        Channel channel = new Channel();
        channel.setChannelId(channelId);
        channel.setChannelName("channel" + channelId);
        channel.setChannelNumber(1);
        channel.setChannelSourceId(deviceId);
        channel.setChannelGridId("10");
        channel.setChannelHandler("ZhangSan");
        channel.setChannelHandlerPhone("13800138000");
        channel.setChannelStatus("0");
        channel.setChannelLocation("xx水域");
        channel.setChannelLatitude("45");
        channel.setChannelLongitude("45");
        channel.setChannelThreadId(10);
        channel.setChannelPodStatus(0);
        iChannelDao.updateChannelMsg(channel);
    }


    @Test
    public void queryChannelMsg() throws Exception {
        iChannelDao.queryChannelMsg(channelId);
    }

    @Test
    public void queryChannelMsgWithDevice() throws Exception {
        iChannelDao.queryChannelMsgWithDevice(channelId);

    }
//
//    @Test
//    public void queryChannelThread() throws Exception {
//        iChannelDao.queryChannelThread(channelId);
//    }

    @Test
    public void deleteChannel() throws Exception {
        iChannelDao.deleteChannel(channelId);
    }

    @Test
    public void activeChannel() throws Exception {
        iChannelDao.activeChannel("e9c7030725fb41ccb49da409ec0f5801",1);
    }
}