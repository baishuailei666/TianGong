package com.xlauncher.util;

import com.xlauncher.dao.dimdao.IChannelDao;
import com.xlauncher.entity.Channel;
import com.xlauncher.entity.Device;
import com.xlauncher.entity.DeviceConfig;
import com.xlauncher.service.ConfigService;
import com.xlauncher.service.PushStreamService;
import com.xlauncher.service.impl.ChannelServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author YangDengcheng
 * @time 18-4-19 上午9:47
 */
@Component
public class ThreadUtil implements Runnable{

    private static IChannelDao iChannelDao;
    private static PushStreamService pushStreamService;
    private static ConfigService configService;
    private static Logger LOGGER = Logger.getLogger(ThreadUtil.class);
    /**
     * 设备初始化状态值(初始化成功：1 ; 初始化失败：0)
     */
    private static final String INITIALIZATION = "initialization";

    /**
     * 设备注册状态值(注册成功：>=0 ; 注册失败：<0)
     */
    private static final String REGISTERS = "registers";

    /**
     * 设备错误状态码
     */
    private static final String NET_DVR_ERR = "NET_DVR_GetLastError";

    @Autowired
    public void setThreadInfo(IChannelDao iChannelDao,PushStreamService pushStreamService, ConfigService configService){
        this.iChannelDao = iChannelDao;
        this.pushStreamService = pushStreamService;
        this.configService = configService;
    }

    ThreadGroup tg = ChannelServiceImpl.tg;

    public Channel channel;

    public ThreadUtil(Channel channel) {
        this.channel = channel;
    }


    /**
     * 线程任务，调用海康SDK、注册设备、登录设备、获得设备状态、抓取图片等
     */
    public void prepareThread(){
        while (!Thread.currentThread().isInterrupted()){
            Channel channelConfig = iChannelDao.queryChannelMsgWithDevice(channel.getChannelId());
            LOGGER.info("[ThreadUtil prepareThread]");
            if (channelConfig != null){
                LOGGER.info("[ThreadUtil.线程获取此通道的设备配置channelConfig]" + channelConfig.toConvertProperties());
                // 配置设备的信息
                DeviceConfig deviceConfig = new DeviceConfig();
                deviceConfig.setDeviceIP(channelConfig.getDevice().getDeviceIp());
                deviceConfig.setDevicePort(Integer.parseInt(channelConfig.getDevice().getDevicePort()));
                deviceConfig.setDeviceUserName(channelConfig.getDevice().getDeviceUserName());
                deviceConfig.setDevicePassWord(channelConfig.getDevice().getDeviceUserPassword());
                LOGGER.info("配置设备的信息:" + deviceConfig.toString());

                // 设备信息
                Device deviceMsg = channelConfig.getDevice();
                LOGGER.info("[GetStreamService.getInstance] 初始化SDK");
                GetStreamService getStreamService = GetStreamService.getInstance(deviceConfig, channel.getChannelNumber());
                if (getStreamService == null) {
                    LOGGER.error("[GetStreamService.getInstance is null!]");
                    continue;
                }

                // 获取设备状态和图片流
                Map<String,  Object> getDeviceStatusAndStreamMap = GetStreamService.getDeviceStatusAndStream(deviceConfig,channel.getChannelNumber());
                int registers = (int) getDeviceStatusAndStreamMap.get(REGISTERS);
                int initialization = (int) getDeviceStatusAndStreamMap.get(INITIALIZATION);
                int NET_DVR_GetLastError = (int) getDeviceStatusAndStreamMap.get(NET_DVR_ERR);
                LOGGER.info("返回设备注册状态registers：" + registers + ",设备初始化状态：" + initialization + ", 错误状态码：" + NET_DVR_GetLastError);
                if (registers < 0 && initialization == 0 && NET_DVR_GetLastError == 1) {
                    LOGGER.error("[设备注册状态registers]." + registers + "　设备注册失败！");
                    LOGGER.error("[设备初始化状态initialization]." + initialization + "　ＳＤＫ初始化错误！");
                    LOGGER.error("[错误状态码NET_DVR_GetLastError]." + initialization + "　用户名密码错误！");
                    // 设备状态
                    int deviceStatus = Integer.parseInt(getDeviceStatusAndStreamMap.get("deviceStatus").toString());
                    // 通道状态
                    int channelStatus = Integer.parseInt(getDeviceStatusAndStreamMap.get("channelStatus").toString());

                    LOGGER.info("[deviceStatus, channelStatus]." + deviceStatus + "," + channelStatus);
                    //　转发状态
                    pushStreamService.pushStatus(channelStatus,deviceStatus,this.channel,deviceMsg);
                    LOGGER.info("转发设备状态给ＣＭＳ－pushStatus");
                    continue;
                }
                if (NET_DVR_GetLastError > 0) {
                    LOGGER.error("[错误状态码NET_DVR_GetLastError]." + NET_DVR_GetLastError + " 设备初始化, 注册成功！调用海康ＳＤＫ出错，给ＣＭＳ转发错误信息。");
                    // 设备状态
                    int deviceStatus = Integer.parseInt(getDeviceStatusAndStreamMap.get("deviceStatus").toString());

                    LOGGER.info("deviceStatus, NET_DVR_GetLastError：" + deviceStatus + "," + NET_DVR_GetLastError);
                    //　转发状态 将全局错误码赋值给通道状态
                    pushStreamService.pushStatus(NET_DVR_GetLastError,deviceStatus,this.channel,deviceMsg);
                    LOGGER.info("转发全局错误状态给ＣＭＳ - pushStatus");
                }
                if (registers >= 0 && NET_DVR_GetLastError == 0){
                    LOGGER.info("[设备初始化、 注册成功]");
                    // 通道状态
                    int channelStatus = Integer.parseInt(getDeviceStatusAndStreamMap.get("channelStatus").toString());

                    // 设备状态
                    String deviceStatus = getDeviceStatusAndStreamMap.get("deviceStatus").toString();
                    LOGGER.info("[deviceStatus, channelStatus] " + deviceStatus + ", " + channelStatus);

                    deviceMsg.setDeviceStatus(deviceStatus);

                    //　转发数据流和设备状态
                    pushStreamService.pushStreamAndStatus(channelStatus,Integer.parseInt(deviceStatus),getDeviceStatusAndStreamMap,this.channel,deviceMsg);
                    LOGGER.info("转发设备状态给ＣＭＳ并推送数据流给ＩＣＳ - pushStreamAndStatus");
                }

            } else {
                LOGGER.error("没有找到对应设备配置!" + channel.getChannelId());
                pushStreamService.pushChannelStatus(11,channel);
                continue;
            }

            try {
                long mills = Long.parseLong(configService.readConfigService("sleepTime"));
                LOGGER.info("线程睡眠等待:Thread.sleep("+ mills +")");
                Thread.sleep(mills);
            } catch (InterruptedException e) {
                LOGGER.error("[Thread:InterruptedException]." + e.getMessage() + e.getLocalizedMessage() + e.getCause());
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void run() {
        LOGGER.info("启动线程运行run()方法, 调用prepareThread()方法");
        this.prepareThread();
    }


}
