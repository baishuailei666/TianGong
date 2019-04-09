package com.xlauncher.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * @Auther :baisl
 * @Email :baishuailei@xlauncher.io
 * @Date :2018/9/10 0010
 * @Desc :
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml","classpath:spring-*.xml"})
@Transactional
public class SynSunnyintecTest {
    @Autowired
    SynSunnyintec synSunnyintec;
    @Test
    public void synDevice() throws Exception {
        System.out.println(" *****synDevice()***** ");
        this.synSunnyintec.synDevice("");
    }

    @Test
    public void synChannel() throws Exception {
        System.out.println(" *****synChannel()***** ");
        this.synSunnyintec.synChannel("");
    }

    @Test
    public void synOrg() throws Exception {
        System.out.println(" *****synOrg()***** ");
        this.synSunnyintec.synOrg("");
    }

    @Test
    public void getCount() throws Exception {
    }

    @Test
    public void checkDeviceIfExist() throws Exception {
    }

    @Test
    public void checkChannelIfExist() throws Exception {
    }

}