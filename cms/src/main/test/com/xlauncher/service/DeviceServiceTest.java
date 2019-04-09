package com.xlauncher.service;

import com.xlauncher.dao.UserDao;
import com.xlauncher.entity.Device;
import com.xlauncher.entity.User;
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
 * DeviceServiceTest
 * @author baishuailei
 * @since 2018-05-8
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml","classpath:spring-*.xml"})
@Transactional
public class DeviceServiceTest {
    @Autowired
    DeviceService deviceService;
    @Autowired
    UserDao userDao;
    private static Device device;
    private static String token = "token is for test";
    @BeforeClass()
    public static void init() {
        device = new Device();
        device.setDeviceId("d3b13f828b184a63a4d0552a488bd4b3");
        device.setDeviceName("test case service");
        device.setDeviceIp("8.11.0.1");
        device.setDevicePort("80803");
        device.setDeviceUserName("test service");
        device.setDeviceUserPassword("125080");
        device.setDeviceModel("201850");
        device.setDeviceType("dvr");
        device.setDeviceChannelCount(150);
        device.setDeviceDivisionId(new BigInteger("111100011110"));
        device.setDeviceOrgId("50");
        device.setDeviceCreateTime("2018-05-08 17:30:25");
    }
    @Before
    public void beTest() {
        User user = new User();
        user.setUserName("Test Case");
        user.setUserLoginName("DeviceServiceTest");
        user.setUserPassword("123456");
        user.setUserPhone("1234567890");
        userDao.insertUser(user);
        User userUp = new User();
        userUp.setUserId(user.getUserId());
        userUp.setToken("token is for test");
        userDao.updateUser(userUp);
    }
    @Test
    public void countPage() throws SQLException {
        System.out.println("countPage：" + this.deviceService.countPage("undefined",0,"undefined","undefined","undefined"));
    }

    @Test
    public void listDevice() throws SQLException {
        System.out.println("listDevice：" + this.deviceService.listDevice("undefined",0,1,"undefined","undefined","undefined", token));
    }

    @Test
    public void insertDevice() throws SQLException {
        System.out.println("insertDevice：" + this.deviceService.insertDevice(device, token));
    }

    @Test
    public void deleteDevice() throws SQLException {
        System.out.println("deleteDevice：" + this.deviceService.deleteDevice("9287b49ed3da4004a19488c30a1aa9ee", token));
    }

    @Test
    public void updateDevice() throws SQLException {
        Device deviceUp = new Device();
        deviceUp.setDeviceId("ec19f012ce8044a0ad3a0fb2b1cf90b1");
        deviceUp.setDeviceUpdateTime("2018-05-08 17:32:25");
        System.out.println("updateDevice：" + this.deviceService.updateDevice(deviceUp, token));
    }

    @Test
    public void updateRuntimeDevice() throws SQLException {
        Device deviceUpRuntime = new Device();
        deviceUpRuntime.setDeviceId("9287b49ed3da4004a19488c30a1aa9ee");
        deviceUpRuntime.setDeviceFaultCode(1);
        deviceUpRuntime.setDeviceFaultTime("2018-05-30 17:32:25");
        System.out.println("updateRuntimeDevice：" + this.deviceService.updateRuntimeDevice(deviceUpRuntime));
    }
    @Test
    public void getRuntimeDevice() throws SQLException {
        System.out.println("getRuntimeDevice: " + this.deviceService.getRuntimeDevice("undefined","undefined","undefined","undefined","undefined","undefined","undefined",1, token));
    }
    @Test
    public void countRuntimeDevice() throws SQLException {
        System.out.println("countRuntimeDevice: " + this.deviceService.countRuntimeDevice("undefined","undefined","undefined","undefined","undefined","undefined","undefined"));
    }

    @Test
    public void getDeviceByDeviceId() throws SQLException {
        System.out.println("getDeviceByDeviceId：" + this.deviceService.getDeviceByDeviceId("5f8151379dcd4111b9308145da05f81e", token));
    }

    @Test
    public void activeDevice() throws SQLException {
        System.out.println("activeDevice：" + this.deviceService.activeDevice("9287b49ed3da4004a19488c30a1aa9ee", token));
    }

    @Test
    public void disableDevice() throws SQLException {
        System.out.println("disableDevice：" + this.deviceService.disableDevice("9287b49ed3da4004a19488c30a1aa9ee", token));
    }
    @Test
    public void getDeviceFault() throws SQLException {
        System.out.println("getDeviceFault: " + this.deviceService.getDeviceFault());
    }
    @Test
    public void getDeviceFaultTypeAndCount() throws SQLException {
        System.out.println("getDeviceFaultTypeAndCount: " + this.deviceService.getDeviceFaultTypeAndCount());
    }
}