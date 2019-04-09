package com.xlauncher.service;

import org.springframework.stereotype.Service;

/**
 * @author :baisl
 * @Email :baishuailei@xlauncher.io
 * @Date :2019/1/3 0003
 * @Desc :
 **/
@Service("MqProducer")
public interface MqProducer {

    /**
     * 生产者 发送channelId
     *
     * @param object
     */
     void sendChannelIdToQueue(Object object);

    /**
     * 生产者 发送image
     *
     * @param object
     */
     void sendImageDataToQueue(Object object);
}
