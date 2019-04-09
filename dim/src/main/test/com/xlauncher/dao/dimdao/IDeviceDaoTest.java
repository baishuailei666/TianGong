package com.xlauncher.dao.dimdao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author YangDengcheng
 * @time 18-4-18 上午10:34
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@Transactional
public class IDeviceDaoTest {

    @Autowired
    private IDeviceDao iDeviceDao;

    @Test
    public void insertDevice() throws Exception {
    }

    @Test
    public void queryAllDevice() throws Exception {
    }

    @Test
    public void updateDeviceMsg() throws Exception {
    }

    @Test
    public void deleteDevice() throws Exception {
    }

    @Test
    public void queryDeviceMsg() throws Exception {
    }

    @Test
    public void queryAllInSameDevice() throws Exception {
        System.out.println("queryAllInSameDevice: " + this.iDeviceDao.queryAllInSameDevice("3c9ceb34e40749ef88026d5e9e10f9ce"));
    }

}