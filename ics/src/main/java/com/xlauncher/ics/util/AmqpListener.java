//package com.xlauncher.ics.util;
//
//import com.rabbitmq.client.*;
//import com.xlauncher.ics.entity.RabbitMQ;
//import com.xlauncher.ics.entity.ServiceInfo;
//import com.xlauncher.ics.util.watergaugedetector.Evaluator;
//import org.apache.log4j.Logger;
//import org.springframework.amqp.core.MessageListener;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.imageio.IIOException;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author :baisl
// * @Email :baishuailei@xlauncher.io
// * @Date :2018/12/18 0018
// * @Desc :消息处理（接收消息，并模型预测，推送告警给ES，并上传图片至FTP服务器）
// **/
//@Configuration
//public class AmqpListener {
//    @Autowired
//    private RabbitMQ rabbitMQ;
//    @Autowired
//    private ServiceInfo serviceInfo;
//    @Autowired
//    private PushEventUtil pushEventUtil;
//    @Autowired
//    private FtpThreadUtil ftpThreadUtil;
//    @Autowired
//    private PropertiesUtil propertiesUtil;
//    @Autowired
//    private RabbitMQUtil rabbitMQUtil;
//    private String currentTime;
//    private static Logger logger = Logger.getLogger(AmqpListener.class);
//
//    /**
//     * 从一个byte[]数组中截取一部分
//     *
//     * @param src
//     * @param begin
//     * @param count
//     * @return
//     */
//    public static byte[] subBytes(byte[] src, int begin, int count) {
//        byte[] bs = new byte[count];
//        System.arraycopy(src, begin, bs, 0, begin + count - begin);
//        return bs;
//    }
//
//    /**
//     * 处理函数，监听channelID队列、回调图片队列函数
//     *
//     * @return MessageListener
//     */
//    @Bean
//    public MessageListener exampleListener() {
//        return message -> {
////            logger.info("[RabbitMQ exampleListener---------------------->]" + message);
////            currentTime = DateTimeUtil.getDate(System.currentTimeMillis());
////
////            logger.info("message." + message.toString());
////
////            Channel channel = rabbitMQUtil.createMQChannel();
////            // queueDeclare第一个参数表示队列名称、第二个参数为是否持久化（true表示是，队列将在服务器重启时生存）、
////            // 第三个参数为是否独占队列（创建者可以使用的私有队列了，断开后自动删除）、第四个参数为当前所有消费者客户端
////            // 连接断开时是否自动删除队列，第五个参数为队列的其他参数。
////            try {
////                channel.queueDeclare(rabbitMQ.getMqQueueImg(),false,false,false,null);
////                //这个是每次只处理一条数据，只有接收到ack确认码，才去拿取下一条消息
////                channel.basicQos(0,1,false);
////            } catch (IOException e) {
////                e.printStackTrace();
////                logger.error("[RabbitMQ Err] 声明队列消息错误!" + e);
////            }
////
////            //DefaultConsumer类实现了Consumer接口，通过传入一个频道，
////            // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
////            Consumer consumer =  new DefaultConsumer(channel) {
////                @Override
////                public void handleDelivery(String consumerTag, Envelope envelope,
////                                           AMQP.BasicProperties properties, byte[] body)
////                        throws IOException {
////                    Map<String, Object> map = new HashMap<>(1);
////                    // 截取channelId
////                    byte[] bodyChannelId = subBytes(body,4,32);
////                    String channelId = new String(bodyChannelId);
////                    logger.info("[RabbitMQ channelId] " + channelId);
////                    // 截取image
////                    byte[] bodyImage = subBytes(body,36,body.length - 36);
////                    logger.info("[RabbitMQ image.length] " + bodyImage.length);
////
////                    String classifierSavedModel = rabbitMQ.getClassifierModelPath();
////                    String detectorSavedModel = rabbitMQ.getDetectorModelPath();
////                    String predictorSavedModel = rabbitMQ.getPredictorModelPath();
////                    Evaluator evaluator = Evaluator.getInstance(propertiesUtil.getPath(detectorSavedModel)
////                            , propertiesUtil.getPath(predictorSavedModel)
////                            , propertiesUtil.getPath(classifierSavedModel));
////                    if (null == evaluator) {
////                        System.err.println("Load Evaluator model failed, exit.");
////                        logger.error("Load Evaluator model failed, exit.");
////                    } else {
////                        int eval;
////                        try {
////                            float thresh = 0.75F;
////                            eval = Evaluator.evaluate(bodyImage, thresh, 100);
////                            if (eval == -1) {
////                                String fileName = currentTime.replaceAll(" ","_") + ".915321-img.jpg";
////                                map.put("status", eval);
////                                map.put("Err", "图片有遮挡无法识别!");
////                                logger.warn("图片有遮挡无法识别! eval." + eval);
////                                map.put("status", eval);
////                                map.put("typeId", 1);
////                                map.put("eventSource", ftpSource() + fileName);
////                                map.put("channelId", channelId);
////                                map.put("eventStartTime", currentTime);
////                                try {
////                                    // 线程执行上传图片
////                                    ftpThreadUtil.doTask(fileName, bodyImage);
////                                } catch (InterruptedException e) {
////                                    e.printStackTrace();
////                                    logger.error("FtpThreadUtil.线程中断异常!" + e);
////                                }
////                                // 推送告警
////                                int pushStatus = pushEventUtil.pushEvent(map);
////                                logger.info("map." + map + " ,pushStatus." + pushStatus);
////                            } else {
////                                map.put("status", eval);
////                                map.put("typeId", 2);
////                                map.put("eventSource", ftpSource() + currentTime.replaceAll(" ","_") + ".915321-img.jpg");
////                                map.put("channelId", channelId);
////                                map.put("eventStartTime", currentTime);
////                                try {
////                                    // 线程执行上传图片
////                                    ftpThreadUtil.doTask(currentTime.replaceAll(" ","_") + ".915321-img.jpg", bodyImage);
////                                } catch (InterruptedException e) {
////                                    e.printStackTrace();
////                                    logger.error("FtpThreadUtil.线程中断异常!" + e);
////                                }
////                                // 推送告警
////                                int pushStatus = pushEventUtil.pushEvent(map);
////                                logger.info("map." + map + " ,pushStatus." + pushStatus);
////                            }
////                        } catch (IIOException | NumberFormatException e) {
////                            e.printStackTrace();
////                            map.put("status", -1);
////                            map.put("Err", e.toString().split(":")[1]);
////                            logger.error("evaluate.Err!" + e);
////                        }
////                        long ack = envelope.getDeliveryTag();
////                        //手动返回确认码
////                        channel.basicAck(ack, true);
////                        logger.info("Thread-id [" + Thread.currentThread().getId() + "]");
////                        logger.info("消息确认码basicAck." + ack);
////                    }
////                }
////            };
////            // true自动回复队列应答, false手動確認 -- RabbitMQ中的消息确认机制
////            try {
////                channel.basicConsume(rabbitMQ.getMqQueueImg(),false,consumer);
////                logger.info("[RabbitMQ release释放资源!]");
////                rabbitMQUtil.release();
////            } catch (IOException e) {
////                e.printStackTrace();
////                logger.error("[RabbitMQ basicConsume] 消息确认出错!" + e);
////            }
//        };
//    }
//
//    /**
//     * 创建监听队列
//     *
//     * @return SimpleMessageListenerContainer
//     */
//    @Bean
//    public SimpleMessageListenerContainer messageListenerContainer() {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(rabbitConnectionFactory());
//        //设置监听的队列名
//        String[] types = {rabbitMQ.getMqQueueImg()};
//        container.setQueueNames(types);
//        container.setMessageListener(exampleListener());
//        return container;
//    }
//
//    /**
//     * 创建连接工厂
//     *
//     * @return ConnectionFactory
//     */
//    @Bean
//    public ConnectionFactory rabbitConnectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitMQ.getMqIp());
//        connectionFactory.setUsername(rabbitMQ.getMqUserName());
//        connectionFactory.setPassword(rabbitMQ.getMqPassword());
//        connectionFactory.setPort(Integer.parseInt(rabbitMQ.getMqPort()));
//        return connectionFactory;
//    }
//
//    /**
//     * 告警事件存储FTP服务器路径
//     *
//     * @return String
//     */
//    private String ftpSource() {
//       return "ftp://" + serviceInfo.getFtpIp() + ":" + serviceInfo.getFtpPort() + serviceInfo.getFtpStorePath() + "/";
//    }
//}
