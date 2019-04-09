package com.xlauncher.dao;

import com.xlauncher.entity.VirtualChannel;
import com.xlauncher.entity.VirtualDevice;
import com.xlauncher.util.Initialise;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

/**
 * VirtualChannelDaoTest
 * @author baishuailei
 * @since 2018-05-09
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml","classpath:spring-*.xml"})
@Transactional
public class VirtualChannelDaoTest {
    @Autowired
    VirtualChannelDao virtualChannelDao;
    @Autowired
    VirtualDeviceDao virtualDeviceDao;
    private static VirtualChannel virtualChannel;
    @BeforeClass()
    public static void init() {
        virtualChannel = new VirtualChannel();
        virtualChannel.setVirtualChannelId(Initialise.initialise());
        virtualChannel.setVirtualChannelName("VirtualChannelDaoTest");
        virtualChannel.setVirtualChannelSourceId("231f06945c584e6ba400682c94ec1891");
        virtualChannel.setVirtualChannelNumber(150);
        virtualChannel.setVirtualChannelGridId("1004");
        virtualChannel.setVirtualChannelLocation("余杭镇联兴街道");
        virtualChannel.setVirtualChannelLongitude("120.19");
        virtualChannel.setVirtualChannelLatitude("30.16");
        virtualChannel.setVirtualChannelHandler("bai");
        virtualChannel.setVirtualChannelHandlerPhone("17826807083");
        virtualChannel.setVirtualChannelCreateTime("2018-05-09 16:50:25");
        virtualChannel.setVirtualChannelModel("XH2000");
        virtualChannel.setVirtualChannelVendor("HAIVISION");
    }
    @Before
    public void beTest() {
        VirtualDevice virtualDevice = new VirtualDevice();
        virtualDevice.setVirtualDeviceId("231f06945c584e6ba400682c94ec1891");
        virtualDevice.setVirtualDeviceName("VirtualChannelDaoTest");
        virtualDevice.setVirtualDeviceIp("8.11.0.13");
        virtualDevice.setVirtualDevicePort("8080");
        virtualDevice.setVirtualDeviceUserName("VirtualDeviceServiceTest");
        virtualDevice.setVirtualDeviceUserPassword("20180509");
        virtualDevice.setVirtualDeviceModel("201805091656");
        virtualDevice.setVirtualDeviceType("dvr");
        virtualDevice.setVirtualDeviceChannelCount(150);
        virtualDevice.setVirtualDeviceOrgId(3);
        virtualDevice.setVirtualDeviceDivisionId(3);
        virtualDevice.setVirtualDeviceCreateTime("2018-05-09 16:50:25");
        virtualDeviceDao.insertVirtualDevice(virtualDevice);
    }
    @Test
    public void listVirtualChannelBySource() throws SQLException {
        System.out.println("listVirtualChannelBySource: " + this.virtualChannelDao.listVirtualChannelBySource("231f06945c584e6ba400682c94ec1891",1));
    }

    @Test
    public void insertVirtualChannel() throws SQLException {
        System.out.println("insertVirtualChannel: " + this.virtualChannelDao.insertVirtualChannel(virtualChannel));
    }

    @Test
    public void deleteVirtualChannel() throws SQLException {
        System.out.println("deleteVirtualChannel: " + this.virtualChannelDao.deleteVirtualChannel("bbd2894e1fe741f7b46e9bf1130ea480"));
    }

    @Test
    public void deleteVirtualChannelByVirtualDeviceId() throws SQLException {
        System.out.println("deleteVirtualChannelByVirtualDeviceId: " + this.virtualChannelDao.deleteVirtualChannelByVirtualDeviceId("231f06945c584e6ba400682c94ec1891"));
    }

    @Test
    public void updateVirtualChannel() throws SQLException {
        VirtualChannel virtualChannelUp = new VirtualChannel();
        virtualChannelUp.setVirtualChannelId("bbd2894e1fe741f7b46e9bf1130ea480");
        virtualChannelUp.setVirtualChannelHandler("launcher");
        virtualChannelUp.setVirtualChannelUpdateTime("2018-05-09 20:50:25");
        System.out.println("updateVirtualChannel: " + this.virtualChannelDao.updateVirtualChannel(virtualChannelUp));
    }

    @Test
    public void getVirtualChannelByVirtualChannelId() throws SQLException {
        System.out.println("getVirtualChannelByVirtualChannelId: " + this.virtualChannelDao.getVirtualChannelByVirtualChannelId("bbd2894e1fe741f7b46e9bf1130ea480"));
    }

    @Test
    public void listVirtualChannel() throws SQLException {
        System.out.println("listVirtualChannel: " + this.virtualChannelDao.listVirtualChannel(1));
    }

    @Test
    public void countPage() throws SQLException {
        System.out.println("countPage: " + this.virtualChannelDao.countPage("231f06945c584e6ba400682c94ec1891"));
    }

    @Test
    public void overview() throws SQLException {
        System.out.println("overview: " + this.virtualChannelDao.overview());
    }

    @Test
    public void virtualChannelConflict() throws SQLException {
        System.out.println("virtualChannelConflict: " + this.virtualChannelDao.virtualChannelConflict("bbd2894e1fe741f7b46e9bf1130ea480",50));
    }

    @Test
    public void virtualDeviceChannelCount() throws SQLException {
        System.out.println("virtualDeviceChannelCount: " + this.virtualChannelDao.virtualDeviceChannelCount("231f06945c584e6ba400682c94ec1891"));
    }

    @Test
    public void updateVirtualChannelStatus() throws SQLException {
        VirtualChannel virtualChannelUpStatus = new VirtualChannel();
        virtualChannelUpStatus.setVirtualChannelId("60f9ba40acc7468fab653f9ddade43b2");
        virtualChannelUpStatus.setVirtualChannelPodStatus(3);
        virtualChannelUpStatus.setVirtualChannelStatus("停用");
        System.out.println("updateVirtualChannelStatus: " + this.virtualChannelDao.updateVirtualChannelStatus(virtualChannelUpStatus));
    }
}