package com.xlauncher.util;

import com.xlauncher.util.synsunnyitec.GetDataFromSunnyintec;
import com.xlauncher.util.synsunnyitec.PushEventToSunnyintec;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther :baisl
 * @Email :baishuailei@xlauncher.io
 * @Date :2018/9/11 0011
 * @Desc :
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml","classpath:spring-*.xml"})
@Transactional
public class GetDataFromSunnyintecTest {
    @Autowired
    GetDataFromSunnyintec getDataFromSunnyintec;
    @Autowired
    PushEventToSunnyintec pushEventToSunnyintec;
    private static String ticket = "ticket is for test";
    @Before
    public void init() {
        ticket = (String) pushEventToSunnyintec.sunnyintecLogin().get("ticket");
    }
    @Test
    public void getSunnyintecDeviceCount() throws Exception {
        System.out.println(getDataFromSunnyintec.getSunnyintecDeviceCount("getdevicecount",ticket));
    }

    @Test
    public void getSunnyintecDevice() throws Exception {
        System.out.println(getDataFromSunnyintec.getSunnyintecDevice(1,10,ticket));
    }

    @Test
    public void getSunnyintecChannelCount() throws Exception {
        System.out.println(getDataFromSunnyintec.getSunnyintecChannelCount("getchannelcount",ticket));
    }

    @Test
    public void getSunnyintecChannel() throws Exception {
        System.out.println(getDataFromSunnyintec.getSunnyintecChannel(1,10,ticket));
    }

}