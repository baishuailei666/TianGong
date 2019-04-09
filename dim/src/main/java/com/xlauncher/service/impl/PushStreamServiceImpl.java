package com.xlauncher.service.impl;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.xlauncher.dao.dimdao.IChannelDao;
import com.xlauncher.dao.dimdao.IDeviceDao;
import com.xlauncher.entity.Channel;
import com.xlauncher.entity.Device;
import com.xlauncher.service.ConfigService;
import com.xlauncher.service.PushStreamService;
import com.xlauncher.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

/**
 * @author YangDengcheng
 * @time 18-4-10 上午9:37
 */
@Service("pushStream")
public class PushStreamServiceImpl implements PushStreamService {
    @Autowired
    IChannelDao iChannelDao;
    @Autowired
    IDeviceDao iDeviceDao;
    @Autowired
    NET_DVR_ERRORUtil net_dvr_errorUtil;
    @Autowired
    ConfigService configService;
    @Autowired
    RabbitMQUtil rabbitMQUtil;
    private Socket socket;
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(PushStreamServiceImpl.class);

    /**
     * 状态码
     */
    private static final int CODE_ZERO = 0;
    private static final int CODE_ONE = 1;
    private static final int CODE_TWO = 2;
    private static final int CODE_THREE = 3;
    private static final int CODE_FIVE = 5;
    private static final int CODE_ELEVEN = 11;
    /**
     * 推送图片和设备状态
     *
     * @param channelFaultCode
     * @param deviceFaultCode
     * @param map              图片路径
     * @param channel          通道
     * @param device           设备
     */
    @Override
    public void pushStreamAndStatus(int channelFaultCode, int deviceFaultCode, Map<String,  Object> map, Channel channel, Device device) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        // 通道状态 0-正常,1-信号丢失
        if (channelFaultCode == CODE_ZERO) {
            channel.setChannelFaultCode(channelFaultCode);
            channel.setChannelFault("正常运行");
            channel.setChannelFaultTime(DatetimeUtil.getDate(System.currentTimeMillis()));
            LOGGER.info("[channel.正常运行]");
        }
        if (channelFaultCode == CODE_ONE) {
            channel.setChannelFaultCode(channelFaultCode);
            channel.setChannelFault("信号丢失");
            channel.setChannelFaultTime(DatetimeUtil.getDate(System.currentTimeMillis()));
            LOGGER.info("[channel.信号丢失]");
        }
        iChannelDao.updateChannelFault(channel);

        // 设备状态 0-正常,1-CPU占用率太高,超过85%,2-硬件错误,例如串口死掉
        if (deviceFaultCode == CODE_ZERO) {
            device.setDeviceFaultCode(deviceFaultCode);
            device.setDeviceFault("正常运行");
            device.setDeviceFaultTime(DatetimeUtil.getDate(System.currentTimeMillis()));
            LOGGER.info("[device.正常运行]");
        }
        if (deviceFaultCode == CODE_ONE) {
            device.setDeviceFaultCode(deviceFaultCode);
            device.setDeviceFault("CPU占用率太高");
            device.setDeviceFaultTime(DatetimeUtil.getDate(System.currentTimeMillis()));
            LOGGER.info("[device.CPU占用率太高]");
        }
        if (deviceFaultCode == CODE_TWO) {
            device.setDeviceFaultCode(deviceFaultCode);
            device.setDeviceFault("硬件错误");
            device.setDeviceFaultTime(DatetimeUtil.getDate(System.currentTimeMillis()));
            LOGGER.info("[device.硬件错误]");
        }
        if (deviceFaultCode == CODE_THREE) {
            device.setDeviceFaultCode(deviceFaultCode);
            device.setDeviceFault("未知错误");
            device.setDeviceFaultTime(DatetimeUtil.getDate(System.currentTimeMillis()));
            LOGGER.info("[device.未知错误]");
        }
        iDeviceDao.updateRuntimeDevice(device);

        // 转发通道状态给CMS
        HttpEntity<String> channelEntity = new HttpEntity<>(JSON.toJSONString(channel),headers);
        try {
            restTemplate.exchange(ConstantClassUtil.CMS_LOCATION  + "/cms/channel/channelStatus", HttpMethod.PUT,channelEntity,String.class);
            LOGGER.info("[channel] " + channel);
            LOGGER.info("转发通道状态给CMS " + ConstantClassUtil.CMS_LOCATION  + "/cms/channel/channelStatus");
        } catch (ResourceAccessException e) {
            LOGGER.error("转发通道状态 CMS服务异常：" + e.getMessage() + e.getLocalizedMessage());
        }

        // 转发设备状态给CMS
        HttpEntity<String> deviceEntity = new HttpEntity<>(JSON.toJSONString(device),headers);
        try {
            restTemplate.exchange(ConstantClassUtil.CMS_LOCATION + "/cms/device/runTimeStatus", HttpMethod.PUT,deviceEntity,String.class);
            LOGGER.info("[device] " + device);
            LOGGER.info("转发通道状态给CMS " + ConstantClassUtil.CMS_LOCATION + "/cms/device/runTimeStatus");
        } catch (ResourceAccessException e) {
            LOGGER.error("转发设备状态 CMS服务异常：" + e.getMessage() + e.getLocalizedMessage());
        }

        // 使用RabbitMQ消息队列发送消息
        byte[] img1 = (byte[]) map.get("imageData");
        int value1 = (int) map.get("value");
        LOGGER.info("[数据流] imageData.length:" + img1.length + " ,value:" + value1);
        if (img1.length != 0) {
            try {
                int len = 32 + img1.length;
                byte[] b = channel.getChannelId().getBytes();
                byte[] buff1 = new byte[4 + len];
                buff1[0] = (byte)(len >> 24);
                buff1[1] = (byte)(len >> 16);
                buff1[2] = (byte)(len >> 8);
                buff1[3] = (byte)(len);

                System.arraycopy(b,0, buff1,4,32);
                System.arraycopy(img1,0, buff1,36, img1.length);

                com.rabbitmq.client.Channel channelMQ = rabbitMQUtil.createMQChannel();

                // 声明一个队列
                // queueDeclare第一个参数表示队列名称、第二个参数为是否持久化（true表示是，队列将在服务器重启时生存）、
                // 第三个参数为是否独占队列（创建者可以使用的私有队列了，断开后自动删除）、第四个参数为当前所有消费者客户端
                // 连接断开时是否自动删除队列，第五个参数为队列的其他参数。
                channelMQ.queueDeclare(configService.readConfigService("mqQueue_img"), false, false, false, null);
                // 发送消息到队列中
                // basicPublish第一个参数为交换机名称、第二个参数为队列映射的路由key、
                // 第三个参数为消息的其他属性、第四个参数为发送消息的主体
                channelMQ.basicPublish("", configService.readConfigService("mqQueue_img"), null, buff1);
                LOGGER.info("[RabbitMQ. 发布数据img.value] - [" + value1 + "]");
            } catch (IOException e) {
                e.printStackTrace();
                LOGGER.error("RabbitMQ.Err 创建通道和连接:" + e);
            } finally {
                LOGGER.info("[RabbitMQ.release 释放资源]");
                rabbitMQUtil.release();
            }
        } else {
            LOGGER.error("获取视频图片流为空：" + img1.length + ",value." + value1);
        }


                // 向ICS推流
//        try {
//             socket = new Socket(ConstantClassUtil.ICS_IP,Integer.parseInt(ConstantClassUtil.ICS_PORT));
//            LOGGER.info("向ICS推流:" + ConstantClassUtil.ICS_IP + " , " + ConstantClassUtil.ICS_PORT);
//            //设置超时时间5秒
//            socket.setSoTimeout(5000);
//            byte[] img = (byte[]) map.get("imageData");
//            int value = (int) map.get("value");
//            LOGGER.info("数据流：" + img + " , " + value);
//            if (value != 0) {
//                int len = 32 + img.length;
//                byte[] b = channel.getChannelId().getBytes();
//                byte[] buff1 = new byte[4 + len];
//                buff1[0] = (byte)(len >> 24);
//                buff1[1] = (byte)(len >> 16);
//                buff1[2] = (byte)(len >> 8);
//                buff1[3] = (byte)(len);
//                System.arraycopy(b,0, buff1,4,32);
//                System.arraycopy(img,0, buff1,36, img.length);
//                LOGGER.info("img.length:" + img.length);
//                OutputStream outputStream = socket.getOutputStream();
//                outputStream.write(buff1,0,36 + img.length);
////                Thread.currentThread().interrupt();
//                outputStream.flush();
//                outputStream.close();
//                LOGGER.info("ICS-推流成功！" + img.length + "，"+ img  + "，"+ value);
//            } else {
//                LOGGER.warn("获取视频图片流为空：" + img.length + value);
//            }
//        } catch (ConnectException e) {
//            LOGGER.warn("ICS拒绝连接ConnectException：" + e.getMessage()  + ", "+ e.getLocalizedMessage());
//        } catch (IOException e) {
//            LOGGER.warn("数据流错误IOException：" + e.getMessage()  + ", "+ e.getLocalizedMessage());
//        }

    }

    /**
     * 推送设备状态（当出现设备连接故障等问题时调用）
     *
     * @param deviceStatus
     * @param channel
     * @param device
     */
    @Override
    public void pushStatus(int channelStatus, int deviceStatus, Channel channel, Device device) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        LOGGER.info("[推送设备状态（当出现设备连接故障等问题时调用）pushStatus]");
        if (channelStatus == CODE_ONE) {
            channel.setChannelFaultCode(2);
            channel.setChannelFault("设备用户名密码错误");
            channel.setChannelFaultTime(DatetimeUtil.getDate(System.currentTimeMillis()));
        } else if (channelStatus == CODE_FIVE) {
            channel.setChannelFaultCode(channelStatus);
            channel.setChannelFault("设备初始化或注册失败");
            channel.setChannelFaultTime(DatetimeUtil.getDate(System.currentTimeMillis()));
        } else {
            LOGGER.info("pushStatus－海康sdk全局错误码：" + channelStatus + ", "+ net_dvr_errorUtil.getNET_DVR_ERROR("ERROR."+channelStatus));
            channel.setChannelFaultCode(channelStatus);
            channel.setChannelFault(net_dvr_errorUtil.getNET_DVR_ERROR("ERROR."+channelStatus));
            channel.setChannelFaultTime(DatetimeUtil.getDate(System.currentTimeMillis()));
        }
        iChannelDao.updateChannelFault(channel);
        // 设备状态 0-正常,1-CPU占用率太高,超过85%,2-硬件错误,例如串口死掉
        if (deviceStatus == CODE_FIVE) {
            device.setDeviceFaultCode(deviceStatus);
            device.setDeviceFault("设备初始化或注册失败");
            LOGGER.info("设备初始化或注册失败deviceStatus: " + deviceStatus);
            device.setDeviceFaultTime(DatetimeUtil.getDate(System.currentTimeMillis()));
        }
        iDeviceDao.updateRuntimeDevice(device);

        // 转发通道状态给CMS
        HttpEntity<String> channelEntity = new HttpEntity<>(JSON.toJSONString(channel),headers);
        try {
            restTemplate.exchange(ConstantClassUtil.CMS_LOCATION  + "/cms/channel/channelStatus", HttpMethod.PUT,channelEntity,String.class);
            LOGGER.info("转发通道状态给CMS" + ConstantClassUtil.CMS_LOCATION  + "/cms/channel/channelStatus");
            LOGGER.info("channel:" + channel);
        } catch (ResourceAccessException e) {
            LOGGER.warn("CMS服务异常：" + e.getMessage() + e.getLocalizedMessage());
        }

        // 转发设备状态给CMS
        HttpEntity<String> deviceEntity = new HttpEntity<>(JSON.toJSONString(device),headers);
        try {
            restTemplate.exchange(ConstantClassUtil.CMS_LOCATION + "/cms/device/runTimeStatus", HttpMethod.PUT,deviceEntity,String.class);
            LOGGER.info("转发通道状态给CMS" + ConstantClassUtil.CMS_LOCATION + "/cms/device/runTimeStatus");
            LOGGER.info("device:" + device);
        } catch (ResourceAccessException e) {
            LOGGER.warn("CMS服务异常：" + e.getMessage() + e.getLocalizedMessage());
        }
    }

    /**
     * 当通道没有父设备时，将错误信息上报
     *
     * @param channelStatus
     * @param channel
     */
    @Override
    public void pushChannelStatus(int channelStatus, Channel channel) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        LOGGER.info("[当通道没有父设备时，将错误信息上报pushChannelStatus]");
        if (channelStatus == CODE_ELEVEN) {
            channel.setChannelFaultCode(11);
            channel.setChannelFault("通道不可用");
            channel.setChannelFaultTime(DatetimeUtil.getDate(System.currentTimeMillis()));
        }
        iChannelDao.updateChannelFault(channel);

        // 转发错误信息给CMS
        HttpEntity<String> channelEntity = new HttpEntity<>(JSON.toJSONString(channel),headers);
        try {
            restTemplate.exchange(ConstantClassUtil.CMS_LOCATION  + "/cms/channel/channelStatus", HttpMethod.PUT,channelEntity,String.class);
            LOGGER.info("[pushChannelStatus] - 转发错误信息给CMS" + ConstantClassUtil.CMS_LOCATION  + "/cms/channel/channelStatus");
            LOGGER.info("channel:" + channel.toString());
        } catch (ResourceAccessException e) {
            LOGGER.error("[pushChannelStatus] - CMS服务异常：" + e);
        }
    }
}
