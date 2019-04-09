package com.xlauncher.ics.util;

import com.rabbitmq.client.*;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

/**
 * @author :baisl
 * @Email :baishuailei@xlauncher.io
 * @Date :2018/12/20 0020
 * @Desc :
 **/
public class MainSend {

    public static void channel1(String message) throws IOException, TimeoutException {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置RabbitMQ相关信息
        factory.setHost("8.16.0.24");
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        // 创建一个新连接
        Connection connection = factory.newConnection();
        // 创建一个通道
        Channel channel = connection.createChannel();
        // 声明一个队列
        // queueDeclare第一个参数表示队列名称、第二个参数为是否持久化（true表示是，队列将在服务器重启时生存）、
        // 第三个参数为是否独占队列（创建者可以使用的私有队列了，断开后自动删除）、第四个参数为当前所有消费者客户端
        // 连接断开时是否自动删除队列，第五个参数为队列的其他参数。
        channel.queueDeclare("dim_ics_channel",false,false,false,null);
        // 发送消息到队列中
        // basicPublish第一个参数为交换机名称、第二个参数为队列映射的路由key、
        // 第三个参数为消息的其他属性、第四个参数为发送消息的主体
        channel.basicPublish("","dim_ics_channel",null,message.getBytes("UTF-8"));
        System.out.println("channel 1___：" + message);
        channel.close();
        connection.close();
    }

    public static void channel2(byte[] message) throws IOException, TimeoutException {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置RabbitMQ相关信息
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        // 创建一个新连接
        Connection connection = factory.newConnection();
        // 创建一个通道
        Channel channel = connection.createChannel();
        // 声明一个队列
        // queueDeclare第一个参数表示队列名称、第二个参数为是否持久化（true表示是，队列将在服务器重启时生存）、
        // 第三个参数为是否独占队列（创建者可以使用的私有队列了，断开后自动删除）、第四个参数为当前所有消费者客户端
        // 连接断开时是否自动删除队列，第五个参数为队列的其他参数。
        channel.queueDeclare("hello",false,false,false,null);
        // 发送消息到队列中
        // basicPublish第一个参数为交换机名称、第二个参数为队列映射的路由key、
        // 第三个参数为消息的其他属性、第四个参数为发送消息的主体
        channel.basicPublish("","hello",null,message);
        System.out.println("channel 2___：" + Arrays.toString(message));
        channel.close();
        connection.close();
    }

    public static void channel3() throws IOException, TimeoutException {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置RabbitMQ相关信息
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        // 创建一个新连接
        Connection connection = factory.newConnection();
        // 创建一个通道
        Channel channel = connection.createChannel();
        channel.queueDeclare("hello",false,false,false,null);
        //这个是每次只处理一条数据，只有接收到ack确认码，才去拿取下一条消息
        channel.basicQos(0,1,false);
        //DefaultConsumer类实现了Consumer接口，通过传入一个频道，
        // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
        Consumer consumer =  new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {

                System.out.println(Arrays.toString(body));
                System.out.println("--*body*--" + body.length);

                byte[] id = subBytes(body,4,32);
                System.out.println("--*id*--" + id.length);
                String channelId = new String(id);
                System.out.println(channelId);

                byte[] img = subBytes(body,36,body.length-36);
                System.out.println("--*img*--" + img.length);
                File file = new File("C:\\Users\\Administrator\\Desktop\\img20190104.jpg");
                FileImageOutputStream imageOutputStream = new FileImageOutputStream(file);
                imageOutputStream.write(img,0,img.length);

                //手动返回确认码
                channel.basicAck(envelope.getDeliveryTag(), true);
            }
        };
        // true自动回复队列应答, false手動確認 -- RabbitMQ中的消息确认机制
        channel.basicConsume("hello",false,consumer);

    }

    /**
     * 从一个byte[]数组中截取一部分
     * @param src
     * @param begin
     * @param count
     * @return
     */
    public static byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        for (int i = begin; i < begin + count; i++) {
            bs[i - begin] = src[i];
        }
        return bs;

    }

    public static void main(String[] args) throws IOException, TimeoutException {
//        channel1("91f2e8dc033b44df8952d0428fd8e2c5");

//        File file = new File("D:\\朗澈科技\\招聘相关\\20181210-20181214面试\\张明.jpeg");
        File file = new File("C:\\Users\\Administrator\\Desktop\\2018-11-26_16-10-00.jpg");

        BufferedImage bufferedImage = ImageIO.read(file);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", bos);

        int len = 32 + bos.toByteArray().length;
        byte[] b = "91f2e8dc033b44df8952d0428fd8e2c5".getBytes();
        byte[] buff1 = new byte[4 + len];
        buff1[0] = (byte)(len >> 24);
        buff1[1] = (byte)(len >> 16);
        buff1[2] = (byte)(len >> 8);
        buff1[3] = (byte)(len);

        System.arraycopy(b,0, buff1,4,32);
        System.arraycopy(bos.toByteArray(),0, buff1,36, bos.toByteArray().length);

        channel2(buff1);

//        channel3();
    }

}
