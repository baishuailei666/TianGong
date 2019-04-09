package com.xlauncher.dao;

import com.xlauncher.entity.Device;
import com.xlauncher.util.DatetimeUtil;
import com.xlauncher.util.Initialise;
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
 * DeviceDaoTest
 * @author baishuailei
 * @since 2018-05-8
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml","classpath:spring-*.xml"})
@Transactional
public class DeviceDaoTest {
    @Autowired
    DeviceDao deviceDao;
    private static Device device;
    @BeforeClass()
    public static void init() {

        device = new Device();
        device.setDeviceId(Initialise.initialise());
        device.setDeviceName("0723");
        device.setDeviceIp("8.11.0.25");
        device.setDevicePort("8000");
        device.setDeviceUserName("admin");
        device.setDeviceUserPassword("1qaz2wsx");
        device.setDeviceModel("20180723");
        device.setDeviceType("IPcamera");
        device.setDeviceChannelCount(100);
        device.setDeviceOrgId("52");
        device.setDeviceDivisionId(new BigInteger("110105011044"));
        device.setDeviceCreateTime(DatetimeUtil.getDate(System.currentTimeMillis()));
    }
    @Test
    public void countPage() throws SQLException {
        System.out.println("countPage: " + this.deviceDao.countPage("undefined",1,"undefined","undefined","undefined"));
    }

    @Test
    public void listDevice() throws SQLException {
        System.out.println("listDevice: " + this.deviceDao.listDevice("undefined",1,1,"undefined","undefined","undefined"));
    }

    @Test
    public void insertDevice() throws SQLException {
        System.out.println("insertDevice: " + this.deviceDao.insertDevice(device));
    }

    @Test
    public void deleteDevice() throws SQLException {
        System.out.println("deleteDevice: " + this.deviceDao.deleteDevice("9287b49ed3da4004a19488c30a1aa9ee"));
    }

    @Test
    public void updateDevice() throws SQLException {
        Device deviceUp = new Device();
        deviceUp.setDeviceId("ec19f012ce8044a0ad3a0fb2b1cf90b1");
        deviceUp.setDeviceModel("9851x");
        deviceUp.setDeviceUpdateTime("2018-05-08 17:32:25");
        System.out.println("updateDevice: " + this.deviceDao.updateDevice(deviceUp));
    }
    @Test
    public void updateRuntimeDevice() throws SQLException {
        Device deviceUpRuntime = new Device();
        deviceUpRuntime.setDeviceId("9287b49ed3da4004a19488c30a1aa9ee");
        deviceUpRuntime.setDeviceFaultCode(1);
        deviceUpRuntime.setDeviceFaultTime("2018-05-30 17:32:25");
        System.out.println("updateRuntimeDevice: " + this.deviceDao.updateRuntimeDevice(deviceUpRuntime));
    }

    @Test
    public void getRuntimeDevice() throws SQLException {
        System.out.println("getRuntimeDevice: " + this.deviceDao.getRuntimeDevice("undefined","undefined","undefined","undefined","undefined","undefined","undefined",1));
    }
    @Test
    public void countRuntimeDevice() throws SQLException {
        System.out.println("countRuntimeDevice: " + this.deviceDao.countRuntimeDevice("undefined","undefined","undefined","undefined","undefined","undefined","undefined"));
    }

    @Test
    public void activeDevice() throws SQLException {
        System.out.println("activeDevice: " + this.deviceDao.activeDevice("b72b3ee099f14435a5b98741b02106b1"));
    }
    @Test
    public void disableDevice() throws SQLException {
        System.out.println("disableDevice: " + this.deviceDao.disableDevice("9287b49ed3da4004a19488c30a1aa9ee"));
    }

    @Test
    public void getDeviceByDeviceId() throws SQLException {
        System.out.println("getDeviceByDeviceId: " + deviceDao.getDeviceByDeviceId("0387b49ed3da4004a19488c30a1bb9aa"));
    }

    @Test
    public void countDeviceName() throws SQLException {
        System.out.println("countDeviceName: " + deviceDao.countDeviceName("7c283abbd34b4988a8a15f2a15e04a1f","周四设备DIM调试006"));
    }
    @Test
    public void getDeviceFault() throws SQLException {
        System.out.println("getDeviceFault: " + this.deviceDao.getDeviceFault());
    }
    @Test
    public void getDeviceFaultType() throws SQLException {
        System.out.println("getDeviceFaultType: " + this.deviceDao.getDeviceFaultType());
    }
}