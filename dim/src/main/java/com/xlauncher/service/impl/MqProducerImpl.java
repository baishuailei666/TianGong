package com.xlauncher.service.impl;

import com.xlauncher.service.ConfigService;
import com.xlauncher.service.MqProducer;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author :baisl
 * @Email :baishuailei@xlauncher.io
 * @Date :2019/1/3 0003
 * @Desc :
 **/
@Service
public class MqProducerImpl implements MqProducer {
    private final AmqpTemplate amqpTemplate;
    private final ConfigService configService;
    @Autowired
    public MqProducerImpl(AmqpTemplate amqpTemplate, ConfigService configService) {
        this.amqpTemplate = amqpTemplate;
        this.configService = configService;
    }

    /**
     * 生产者 发送channelId
     *
     * @param object
     */
    @Override
    public void sendChannelIdToQueue(Object object) {
        String queue = configService.readConfigService("mqQueue_channel");
        amqpTemplate.convertAndSend(queue, object);
    }

    /**
     * 生产者 发送image
     *
     * @param object
     */
    @Override
    public void sendImageDataToQueue(Object object) {
        String queue = configService.readConfigService("mqQueue_img");
        amqpTemplate.convertAndSend(queue, object);
    }
}
