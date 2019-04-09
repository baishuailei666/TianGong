package com.xlauncher.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author :baisl
 * @Email :baishuailei@xlauncher.io
 * @Date :2019/1/3 0003
 * @Desc :
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MqProducerImplTest {
    @Autowired
    MqProducerImpl mqProducer;
    @Test
    public void sendDataToQueue() throws Exception {
        mqProducer.sendChannelIdToQueue("1223456");
    }

}