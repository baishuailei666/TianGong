package com.xlauncher.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml","classpath:spring-*.xml"})
@Transactional
public class HeartBeatUtilTest {
    @Autowired
    HeartBeatUtil heartBeatUtil;
    @Test
    public void getESStatus() throws Exception {
        System.out.println("getESStatus:" + heartBeatUtil.getESStatus());
    }

    @Test
    public void getDIMStatus() throws Exception {
        System.out.println("getDIMStatus:" + heartBeatUtil.getDIMStatus());
    }

    @Test
    public void getICSStatus() throws Exception {
        System.out.println("getICSStatus:" + heartBeatUtil.getICSStatus());
    }

    @Test
    public void getCMSStatus() throws Exception {
        System.out.println("getCMSStatus:" + heartBeatUtil.getCMSStatus());
    }

    @Test
    public void heartBeat() throws Exception {
        System.out.println("heartBeat:" + heartBeatUtil.heartBeat());
    }

    @Test
    public void run() throws Exception {
    }

}