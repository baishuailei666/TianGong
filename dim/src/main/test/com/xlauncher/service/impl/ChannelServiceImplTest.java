package com.xlauncher.service.impl;

import com.xlauncher.dao.dimdao.IChannelDao;
import com.xlauncher.dao.dimdao.IDeviceDao;
import com.xlauncher.entity.Channel;
import com.xlauncher.entity.Device;
import com.xlauncher.service.ChannelService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author YangDengcheng
 * @time 18-4-19 下午7:13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@WebAppConfiguration
@Transactional
public class ChannelServiceImplTest {
    private static String deviceId = "serviceDevice";
    private static String channelId = "serviceChannel";


    @Autowired
    ChannelService channelService;
    @Autowired
    IChannelDao iChannelDao;
    @Autowired
    IDeviceDao iDeviceDao;
    @Autowired
    WebApplicationContext context;
    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception{
        // 初始化MockMvc对象
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void queryAllChannel() throws Exception {
        channelService.queryAllChannel();
    }

    @Test
    public void queryChannelMsg() throws Exception {
        String channelId = "testtesttesttesttesttesttesttest";
        channelService.queryChannelMsg(channelId);
    }

    @Test
    public void queryChannelMsgWithDevice() throws Exception {
        String channelId = "testtesttesttesttesttesttesttest";
        channelService.queryChannelMsgWithDevice(channelId);
    }

//    @Before
    public void insertChannelWithDIM() throws Exception {
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
        device.setDeviceOrgId("1111111");
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

        channelService.insertChannelWithDIM(channel);
    }

    @Test
    public void updateChannelWithDIM() throws Exception {
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

        channelService.updateChannelWithDIM(channel);
    }


    @Test
    public void deleteChannelWithDIM() throws Exception {
        channelService.deleteChannelWithDIM(channelId);
    }

    @Test
    public void interruptThread() throws Exception {
    }

    @Test
    public void insertChannel() throws Exception{
        Device device = new Device();
        device.setDeviceId("testDevice2");
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
        channel.setChannelId("testChannel2");
        channel.setChannelName("testChannel2");
        channel.setChannelNumber(1);
        channel.setChannelSourceId("testDevice2");
        channel.setChannelGridId("10");
        channel.setChannelHandler("ZhangSan");
        channel.setChannelHandlerPhone("13800138000");
        channel.setChannelStatus("-10");
        channel.setChannelLocation("xx水域");
        channel.setChannelLatitude("45");
        channel.setChannelLongitude("45");
        channel.setChannelThreadId(10);
        channel.setChannelPodStatus(-1);
        channelService.insertChannel(channel);
    }

    @Test
    public void updateChannelMsg() throws Exception{
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
        channel.setChannelPodStatus(0);

        channelService.updateChannelMsg(channel);
    }

    @Test
    public void deleteChannel() throws Exception{

    }

    @Test
    public void activeChannel() throws Exception{
        channelService.activeChannel("e9c7030725fb41ccb49da409ec0f5801");
    }

}