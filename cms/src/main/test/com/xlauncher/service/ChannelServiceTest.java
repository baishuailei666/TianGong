package com.xlauncher.service;

import com.xlauncher.dao.DeviceDao;
import com.xlauncher.dao.UserDao;
import com.xlauncher.entity.Channel;
import com.xlauncher.entity.Device;
import com.xlauncher.entity.User;
import com.xlauncher.util.DatetimeUtil;
import com.xlauncher.util.Initialise;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.SQLException;

/**
 * ChannelServiceTest
 * @author baishuailei
 * @since 2018-05-09
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml","classpath:spring-*.xml"})
@Transactional
public class ChannelServiceTest {
    @Autowired
    ChannelService channelService;
    @Autowired
    UserDao userDao;
    @Autowired
    DeviceDao deviceDao;
    private static Channel channel;
    private static String token = "token is for test";
    @BeforeClass()
    public static void init() {
        channel = new Channel();
        channel.setChannelId(Initialise.initialise());
        channel.setChannelName("ChannelServiceTest 01");
        channel.setChannelSourceId("9a78ac2ddfca45f39f2ed056a9f6ac24");
        channel.setChannelNumber(100);
        channel.setChannelGridId("1001");
        channel.setChannelLocation("人工智能小镇");
        channel.setChannelLongitude("121.19");
        channel.setChannelLatitude("31.16");
        channel.setChannelHandler("launcher");
        channel.setChannelHandlerPhone("17826817083");
        channel.setChannelCreateTime("2018-05-09 10:50:25");
        channel.setChannelModel("CD2000");
        channel.setChannelVendor("HAIVISION");
    }
    @Before
    public void beTest() {
        User user = new User();
        user.setUserName("Test Case");
        user.setUserLoginName("ChannelServiceTest");
        user.setUserPassword("123456");
        user.setUserPhone("1234567890");
        userDao.insertUser(user);
        User userUp = new User();
        userUp.setUserId(user.getUserId());
        userUp.setToken("token is for test");
        userDao.updateUser(userUp);
        Device device = new Device();
        device.setDeviceId("9a78ac2ddfca45f39f2ed056a9f6ac24");
        device.setDeviceName("test case service");
        device.setDeviceIp("8.11.0.1");
        device.setDevicePort("80803");
        device.setDeviceUserName("test service");
        device.setDeviceUserPassword("125080");
        device.setDeviceModel("201850");
        device.setDeviceType("dvr");
        device.setDeviceChannelCount(150);
        device.setDeviceOrgId("3");
        device.setDeviceDivisionId(BigInteger.valueOf(1));
        device.setDeviceCreateTime("2018-05-08 17:30:25");
        deviceDao.insertDevice(device);
    }

    @Test
    public void insertChannel() throws SQLException {
        System.out.println("insertChannel：" + this.channelService.insertChannel(channel, token));
    }

    @Test
    public void deleteChannel() throws SQLException {
        System.out.println("deleteChannel：" + this.channelService.deleteChannel("bc17d8710c304d29aeb5066a294b31bd", token));
    }

    @Test
    public void updateChannel() throws SQLException {
        Channel channelUp = new Channel();
        channelUp.setChannelId("bc17d8710c304d29aeb5066a294b31bd");
        channelUp.setChannelUpdateTime("2018-05-09 10:27:19");
        System.out.println("updateChannel：" + this.channelService.updateChannel(channelUp, token));
    }

    @Test
    public void getChannelByChannelId() throws SQLException {
        System.out.println("getChannelByChannelId：" + this.channelService.getChannelByChannelId("60f9ba40acc7468fab653f9ddade43b2", token));
    }

    @Test
    public void listChannel() throws SQLException {
        System.out.println("listChannel：" + this.channelService.listChannel("undefined","undefined","undefined",1,"undefined", token));
    }

    @Test
    public void countPageChannel() throws SQLException {
        System.out.println("countPageChannel: " + this.channelService.countPageChannel("undefined","undefined","undefined","undefined"));
    }

    @Test
    public void overview() throws SQLException {
        System.out.println("overview：" + this.channelService.overview());
    }

    @Test
    public void updateChannelStatus() throws SQLException {
        Channel channelUpStatus = new Channel();
        channelUpStatus.setChannelId("bc17d8710c304d29aeb5066a294b31bd");
        channelUpStatus.setChannelFaultCode(1);
        channelUpStatus.setChannelFault("信号丢失");
        channelUpStatus.setChannelFaultTime(DatetimeUtil.getDate(System.currentTimeMillis()));
        channelUpStatus.setChannelPodStatus(1);
        System.out.println("updateChannelStatus：" + this.channelService.updateChannelStatus(channelUpStatus));
    }
    @Test
    public void activeChannel() throws SQLException {
        System.out.println("activeChannel: " + this.channelService.activeChannel("b72b3ee099f14435a5b98741b02106b1",token));
    }
    @Test
    public void disableChannel() throws SQLException {
        System.out.println("disableChannel: " + this.channelService.disableChannel("b72b3ee099f14435a5b98741b02106b1",token));
    }
    @Test
    public void getRuntimeChannel() throws SQLException {
        System.out.println("getRuntimeChannel: " + this.channelService.getRuntimeChannel("undefined","undefined","undefined","undefined","undefined",1, token));
    }
    @Test
    public void countRuntimeChannel() throws SQLException {
        System.out.println("countRuntimeChannel: " + this.channelService.countRuntimeChannel("undefined","undefined","undefined","undefined","undefined"));
    }
    @Test
    public void getChannelFault() throws SQLException {
        System.out.println("getChannelFault: " + this.channelService.getChannelFault());
    }
    @Test
    public void getChannelFaultTypeAndCount() throws SQLException {
        System.out.println("getChannelFaultTypeAndCount: " + this.channelService.getChannelFaultTypeAndCount());
    }
    @Test
    public void countChannelNumber() throws SQLException {
        System.out.println("countChannelNumber: " + this.channelService.countChannelNumber(1,"5aced3ea8f254daf82989a5ee4881b55"));
    }
    @Test
    public void countChannelName() throws SQLException {
        System.out.println("countChannelName: " + this.channelService.countChannelName("countChannelName","5aced3ea8f254daf82989a5ee4881b55"));
    }

}