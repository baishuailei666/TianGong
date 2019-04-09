package com.xlauncher.ics.util;

import com.rabbitmq.client.*;
import com.xlauncher.ics.entity.RabbitMQ;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.*;

/**
 * @author :baisl
 * @Email :baishuailei@xlauncher.io
 * @Date :2018/12/19 0019
 * @Desc :RabbitMQ 创建工厂、创建通道
 **/
@Component
public class RabbitMQUtil {
    @Autowired
    private RabbitMQ rabbitMQ;
    private Connection connection;
    private Channel channel;
    private static Logger logger = Logger.getLogger(RabbitMQUtil.class);


    /**
     * 创建RabbitMQ连接工厂、创建连接、创建通道
     * @return
     */
    public Channel createMQChannel() {
        // 创建线程池 CallerRunsPolicy：不在新线程中执行任务，而是由调用者所在的线程来执行
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                5,
                10,
                60,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
                new ThreadPoolExecutor.CallerRunsPolicy());

        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置RabbitMQ属性
        factory.setHost(rabbitMQ.getMqIp());
        factory.setPort(Integer.parseInt(rabbitMQ.getMqPort()));
        factory.setUsername(rabbitMQ.getMqUserName());
        factory.setPassword(rabbitMQ.getMqPassword());
        factory.setVirtualHost("/");
        logger.error("[RabbitMQ]创建连接, ip:" + rabbitMQ.getMqIp() + ", name:" + rabbitMQ.getMqUserName()
                + ", password:" + rabbitMQ.getMqPassword() + ", channel:" + rabbitMQ.getMqQueueImg());
        // 创建一个连接
        connection = null;
        channel = null;
        try {
            connection = factory.newConnection(executor);
            // 创建一个通道
            channel = connection.createChannel();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
            logger.error("[RabbitMQ]创建连接工厂、创建连接异常!" + e);
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
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
            logger.error("[RabbitMQ] 关闭错误!" + e);
        }
    }

    public void createConsumer() throws IOException, TimeoutException {
        Channel channel = createMQChannel();
        channel.queueDeclare(rabbitMQ.getMqQueueImg(),false,false,false,null);
        //这个是每次只处理一条数据，只有接收到ack确认码，才去拿取下一条消息
        channel.basicQos(0,1,false);
        //DefaultConsumer类实现了Consumer接口，通过传入一个频道，
        // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
        Consumer consumer =  new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                System.out.println(Thread.currentThread().getName() + "----id." + Thread.currentThread().getId());
                String routingKey = envelope.getRoutingKey();
                long deliveryTag = envelope.getDeliveryTag();
                System.out.println("routingKey." + routingKey + ",deliveryTag." + deliveryTag);
                String message = new String(body, "UTF-8");
                System.out.println("消费者：'" + message + "'");

                //手动返回确认码
                channel.basicAck(envelope.getDeliveryTag(), true);
            }
        };
        // true自动回复队列应答, false手動確認 -- RabbitMQ中的消息确认机制
        channel.basicConsume(rabbitMQ.getMqQueueImg(),false,consumer);
    }
}
