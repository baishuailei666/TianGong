package com.xlauncher.service.impl;

import com.xlauncher.entity.Channel;
import com.xlauncher.service.UpdateModelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author YangDengcheng
 * @time 18-4-20 上午9:42
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UpdateModelServiceImplTest {
    @Autowired
    UpdateModelService updateModelService;

    private static String channelId = "testtesttesttesttesttesttesttest";

    @Test
    public void updateMsgToCMS() throws Exception {
        Channel channel = new Channel();
        channel.setChannelName("channel" + channelId);
        channel.setChannelNumber(1);
        channel.setChannelSourceId(channelId);
        channel.setChannelGridId("10");
        channel.setChannelHandler("ZhangSan");
        channel.setChannelHandlerPhone("13800138000");
        channel.setChannelStatus("0");
        channel.setChannelLocation("xx水域");
        channel.setChannelLatitude("45");
        channel.setChannelLongitude("45");
        channel.setChannelThreadId(10);
        channel.setChannelPodStatus(0);

        updateModelService.updateMsgToCMS(channel);
    }

}