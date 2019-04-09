package com.xlauncher.dao;

import com.xlauncher.entity.Channel;
import com.xlauncher.util.DatetimeUtil;
import com.xlauncher.util.Initialise;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

/**
 * ChannelDaoTest
 * @author baishuailei
 * @since 2018-05-8
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml","classpath:spring-*.xml"})
@Transactional
public class ChannelDaoTest {
    @Autowired
    ChannelDao channelDao;
    private static Channel channel;
    @BeforeClass()
    public static void init() {
        channel = new Channel();
        channel.setChannelId(Initialise.initialise());
        channel.setChannelName("0723");
        channel.setChannelSourceId("0f95478f523f4685946fa2fa9034fa3f");
        channel.setChannelNumber(1);
        channel.setChannelGridId("1001");
        channel.setChannelLocation("余杭镇联兴街道");
        channel.setChannelLongitude("117.29");
        channel.setChannelLatitude("40.16");
        channel.setChannelHandler("bai");
        channel.setChannelHandlerPhone("17826807083");
        channel.setChannelCreateTime(DatetimeUtil.getDate(System.currentTimeMillis()));
    }

    @Test
    public void insertChannel() throws SQLException {
        System.out.println("insertChannel: " + this.channelDao.insertChannel(channel));
    }

    @Test
    public void deleteChannel() throws SQLException {
        System.out.println("deleteChannel: " + this.channelDao.deleteChannel("60f9ba40acc7468fab653f9ddade43b2"));
    }

    @Test
    public void deleteChannelByDeviceId() throws SQLException {
        System.out.println("deleteChannelByDeviceId: " + this.channelDao.deleteChannelByDeviceId("b8977d7bfce345cbbb85a545b35968a7"));
    }

    @Test
    public void updateChannel() throws SQLException {
        Channel channelUp = new Channel();
        channelUp.setChannelId("60f9ba40acc7468fab653f9ddade43b2");
        channelUp.setChannelHandler("launcher");
        channelUp.setChannelUpdateTime("2018-05-08 20:50:25");
        System.out.println("updateChannel: " + this.channelDao.updateChannel(channelUp));
    }

    @Test
    public void getChannelByChannelId() throws SQLException {
        System.out.println("getChannelByChannelId: " + this.channelDao.getChannelByChannelId("60f9ba40acc7468fab653f9ddade43b2"));
    }

    @Test
    public void listChannel() throws SQLException {
        System.out.println("listChannel: " + this.channelDao.listChannel("undefined","undefined","undefined",1,"undefined"));
    }

    @Test
    public void countPageChannel() throws SQLException {
        System.out.println("countPageChannel: " + this.channelDao.countPageChannel("undefined","undefined","undefined","undefined"));
    }

    @Test
    public void overview() throws SQLException {
        System.out.println("overview: " + this.channelDao.overview());
    }

    @Test
    public void channelConflict() throws SQLException {
        System.out.println("channelConflict: " + this.channelDao.channelConflict("bbd2894e1fe741f7b46e9bf1130ea480",50));

    }

    @Test
    public void updateChannelStatus() throws SQLException {
        Channel channelUpStatus = new Channel();
        channelUpStatus.setChannelId("60f9ba40acc7468fab653f9ddade43b2");
        channelUpStatus.setChannelFaultCode(1);
        channelUpStatus.setChannelFault("信号丢失");
        channelUpStatus.setChannelFaultTime(DatetimeUtil.getDate(System.currentTimeMillis()));
        channelUpStatus.setChannelPodStatus(1);
        System.out.println("updateChannelStatus: " + this.channelDao.updateChannelStatus(channelUpStatus));
    }

    @Test
    public void activeChannel() throws SQLException {
        System.out.println("activeChannel: " + this.channelDao.activeChannel("4978523fe0fd41b79a7acede12dd13e9"));
    }
    @Test
    public void disableChannel() throws SQLException {
        System.out.println("disableChannel: " + this.channelDao.disableChannel("4978523fe0fd41b79a7acede12dd13e9"));
    }
    @Test
    public void activeDeviceChannel() throws SQLException {
        System.out.println("activeDeviceChannel: " + this.channelDao.activeDeviceChannel("b8977d7bfce345cbbb85a545b35968a7"));
    }

    @Test
    public void disableDeviceChannel() throws SQLException {
        System.out.println("disableDeviceChannel: " + this.channelDao.disableDeviceChannel("b8977d7bfce345cbbb85a545b35968a7"));
    }

    @Test
    public void getRuntimeChannel() throws SQLException {
        System.out.println("getRuntimeChannel: " + this.channelDao.getRuntimeChannel("undefined","undefined","undefined","undefined","undefined",1));
    }
    @Test
    public void countRuntimeChannel() throws SQLException {
        System.out.println("countRuntimeChannel: " + this.channelDao.countRuntimeChannel("undefined","undefined","undefined","undefined","undefined"));
    }
    @Test
    public void getChannelFault() throws SQLException {
        System.out.println("getChannelFault: " + this.channelDao.getChannelFault());
    }
    @Test
    public void getChannelFaultType() throws SQLException {
        System.out.println("getChannelFaultType: " + this.channelDao.getChannelFaultType());
    }
    @Test
    public void getChannelFaultCount() throws SQLException {
        System.out.println("getChannelFaultCount: " + this.channelDao.getChannelFaultCount("undefined"));
    }


}