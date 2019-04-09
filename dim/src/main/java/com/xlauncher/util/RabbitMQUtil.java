package com.xlauncher.util;

import com.rabbitmq.client.*;
import com.xlauncher.service.ConfigService;
import com.xlauncher.service.impl.ConfigServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author :baisl
 * @Email :baishuailei@xlauncher.io
 * @Date :2018/12/19 0019
 * @Desc :RabbitMQ 创建工厂、创建通道
 **/
@Component
public class RabbitMQUtil {
    @Autowired
    ConfigService configService;
    private static Logger logger = Logger.getLogger(RabbitMQUtil.class);
    @Value("${mqIp}")
    private String mqIp;
    @Value("${mqPort}")
    private String mqPort;
    @Value("${mqUserName}")
    private String mqUserName;
    @Value("${mqPassword}")
    private String mqPassword;
    @Value("${mqQueue_img}")
    private String mqChannel;
    private static ConnectionFactory factory;

    private Connection connection;

    private Channel channel;
    /**
     * 创建RabbitMQ连接工厂、创建连接、创建通道
     *
     * @return
     */
    public Channel createMQChannel() {
        // 创建连接工厂
        factory = new ConnectionFactory();
        // 设置RabbitMQ属性
        factory.setHost(mqIp);
        factory.setPort(Integer.parseInt(mqPort));
        factory.setUsername(mqUserName);
        factory.setPassword(mqPassword);
        factory.setVirtualHost("/");
        logger.info("[RabbitMQ ] ip." + mqIp + ", port." + mqPort + ", user." + mqUserName
                + ", password." + mqPassword + ", channel." + mqChannel);

        try {
            // 创建一个通道
            connection = factory.newConnection();
            channel = connection.createChannel();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                channel.close();
                connection.close();
            } catch (IOException e1) {
                e1.printStackTrace();
                logger.error("[RabbitMQ Err]" + e1);
            }
            logger.error("[RabbitMQ 创建连接工厂、创建连接异常!]" + e);
        }
        return channel;
    }

    /**
     * 释放资源
     */
    public void release() {
        try {
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("[RabbitMQ] 关闭错误!" + e);
        }
    }

}
