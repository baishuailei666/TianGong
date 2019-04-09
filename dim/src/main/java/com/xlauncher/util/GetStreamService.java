package com.xlauncher.util;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.xlauncher.entity.Device;
import com.xlauncher.entity.DeviceConfig;
import com.xlauncher.service.HCNetSDK;
import org.apache.log4j.Logger;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * 设备取流工具
 * @author YangDengcheng
 * @time 18-4-11 上午10:18
 */
public final class GetStreamService {
    private static Logger LOGGER = Logger.getLogger(GetStreamService.class);
    private static HCNetSDK hcNetSDK;
    private volatile static GetStreamService getStreamService;
    private volatile static boolean sdkLoaded;
    private static Map<String, Object> getDeviceStatusAndStreamMap;

    /**
     * 设备初始化状态值(初始化成功：1 ; 初始化失败：0)
     */
    private static final String INITIALIZATION = "initialization";

    /**
     * 设备注册状态值(注册成功：>=0 ; 注册失败：<0)
     */
    private static final String REGISTERS = "registers";


    /**
     * 单例模式加锁操作，并保证SDK只加载一次，否则返回null
     *
     * @param deviceConfig
     * @param channelNum
     * @return
     */
    public static GetStreamService getInstance(DeviceConfig deviceConfig, int channelNum) {
        synchronized (GetStreamService.class) {
            LOGGER.info("[getInstance new GetStreamService]");
            LOGGER.info("1___getDeviceStatusAndStreamMap." + getDeviceStatusAndStreamMap);
            getStreamService = new GetStreamService(deviceConfig, channelNum);
            if (!sdkLoaded) {
                boolean load = getStreamService.load();
                LOGGER.info("[getInstance sdkLoaded] " + load);
                if (!load) {
                    getStreamService = null;
                }
            }
        }
        return getStreamService;
    }

    /**
     * 判断是否初始化成功
     *
     * @return
     */
    private boolean load(){

        if (getDeviceStatusAndStreamMap.get(INITIALIZATION).equals(0)) {
            LOGGER.error("[SDK初始化失败!]");
            sdkLoaded = false;
        } else {
            LOGGER.info("[SDK初始化成功!]");
            sdkLoaded = true;
        }
        return sdkLoaded;
    }

    /**
     * 设备进行初始化
     *
     * @param deviceConfig
     * @param channelNum
     */
    public GetStreamService(DeviceConfig deviceConfig, int channelNum){
        LOGGER.info("[___________new GetStreamService___________]");
        LOGGER.info("[GetStreamService - 设备初始化]");
        LOGGER.info("___channelNum." + channelNum);
        LOGGER.info("___deviceConfig." + deviceConfig.toString());
        getDeviceStatusAndStreamMap = new Hashtable<>();
        LOGGER.info("2___getDeviceStatusAndStreamMap." + getDeviceStatusAndStreamMap);
        // 调用海康SDK
        try {
            hcNetSDK = HCNetSDK.INSTANCE;
        } catch (Exception e) {
            LOGGER.error("[GetStreamService - 初始化海康SDK Err!]." + e);
        }
        LOGGER.info("[GetStreamService - 成功初始化海康SDK!]" + hcNetSDK);
        // 通过通道号获取通道ID
        NativeLong id = new NativeLong(channelNum);
        deviceConfig.setDeviceSingleID(id);
        LOGGER.info("[GetStreamService] - 通过通道号获取通道ID:" + id);
        if (!hcNetSDK.NET_DVR_Init()){
            getDeviceStatusAndStreamMap.put("initialization", 0);
            LOGGER.error("[GetStreamService - SDK初始化失败] 直接返回结果" + hcNetSDK.NET_DVR_Init() + getDeviceStatusAndStreamMap);
//            return getDeviceStatusAndStreamMap;
        }
        // 设置连接时间和重连次数
        hcNetSDK.NET_DVR_SetConnectTime(3000,1);
        hcNetSDK.NET_DVR_SetReconnect(10000,true);
        LOGGER.info("[GetStreamService] - 设置连接时间和重连次数:" + 3000 + "," + 10000);

        // 设备信息
        HCNetSDK.NET_DVR_DEVICEINFO_V30 deviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();
        LOGGER.info("[GetStreamService - 设备信息] " + deviceInfo);

        /**
         * 设备注册
         * 设备注册后会生成一个ID，并将这个ID赋予DeviceConfig对象中的设备ID属性
         */
        NativeLong deviceID = hcNetSDK.NET_DVR_Login_V30(deviceConfig.getDeviceIP(),(short)deviceConfig.getDevicePort(),deviceConfig.getDeviceUserName(),deviceConfig.getDevicePassWord(),deviceInfo);
        deviceConfig.setId(deviceID);
        LOGGER.info("[GetStreamService] - 设备注册后会生成一个ID，并将这个ID赋予DeviceConfig对象中的设备ID属性:" + deviceID);
        LOGGER.info("[GetStreamService] - 设备注册-DeviceID :" + deviceConfig.getId().intValue());
        if (deviceConfig.getId().intValue() < 0){
            getDeviceStatusAndStreamMap.put("registers", deviceConfig.getId().intValue());
            getDeviceStatusAndStreamMap.put("deviceStatus", 5);
            getDeviceStatusAndStreamMap.put("channelStatus", 5);
            getDeviceStatusAndStreamMap.put("NET_DVR_GetLastError", hcNetSDK.NET_DVR_GetLastError());
            getDeviceStatusAndStreamMap.put("initialization", 0);
            LOGGER.error("[GetStreamService - 设备注册失败] 返回结果: " + getDeviceStatusAndStreamMap);
//            return getDeviceStatusAndStreamMap;
        } else {
            getDeviceStatusAndStreamMap.put("registers", deviceConfig.getId().intValue());
            getDeviceStatusAndStreamMap.put("initialization", 1);
            LOGGER.info("[GetStreamService - 设备注册成功] Device ID :" + deviceConfig.getId().intValue());
        }
    }

    /**
     * 设备登录注册
     *
     * @param deviceConfig
     * @return
     */
    private static Map<String, Object> login(DeviceConfig deviceConfig) {
        Map<String, Object> loginMap = new HashMap<>(1);
        // 设备信息
        HCNetSDK.NET_DVR_DEVICEINFO_V30 deviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();
        LOGGER.info("[设备login - deviceInfo]." + deviceInfo);
        LOGGER.info("3___getDeviceStatusAndStreamMap." + getDeviceStatusAndStreamMap);
        /**
         * 设备注册
         * 设备注册后会生成一个ID，并将这个ID赋予DeviceConfig对象中的设备ID属性
         */
        NativeLong deviceID = hcNetSDK.NET_DVR_Login_V30(deviceConfig.getDeviceIP(),(short)deviceConfig.getDevicePort(),deviceConfig.getDeviceUserName(),deviceConfig.getDevicePassWord(),deviceInfo);
        deviceConfig.setId(deviceID);
        LOGGER.info("[设备注册] - 设备注册后会生成一个ID，并将这个ID赋予DeviceConfig对象中的设备ID属性:" + deviceID);
        LOGGER.info("[设备注册] - DeviceID :" + deviceConfig.getId().intValue());
        if (deviceConfig.getId().intValue() < 0){
            getDeviceStatusAndStreamMap.put("registers", deviceConfig.getId().intValue());
            getDeviceStatusAndStreamMap.put("deviceStatus", 5);
            getDeviceStatusAndStreamMap.put("channelStatus", 5);
            getDeviceStatusAndStreamMap.put("NET_DVR_GetLastError", hcNetSDK.NET_DVR_GetLastError());
            getDeviceStatusAndStreamMap.put("initialization", 0);
            LOGGER.error("[设备注册 - 失败] 返回结果: " + getDeviceStatusAndStreamMap);
            return loginMap;
        } else {
            getDeviceStatusAndStreamMap.put("registers", deviceConfig.getId().intValue());
            getDeviceStatusAndStreamMap.put("initialization", 1);
            LOGGER.info("[设备注册 - 成功] Device ID: " + deviceConfig.getId().intValue());
        }
        return loginMap;
    }

    /**
     * 注册设备并获取设备状态和视频流
     *
     * @param deviceConfig
     * @param channelNum
     * @return
     */
    static Map<String, Object> getDeviceStatusAndStream(DeviceConfig deviceConfig, int channelNum) {
        LOGGER.info("[getDeviceStatusAndStream - 获取设备状态和视频流服务!]");
        LOGGER.info("deviceConfig." + deviceConfig.toString() + ", channelNum." + channelNum);
        LOGGER.info("4___getDeviceStatusAndStreamMap." + getDeviceStatusAndStreamMap);
        int init = 0;
        int registers = 0;
        try {
            if (!sdkLoaded) {
                LOGGER.info("sdkLoaded");
                getInstance(deviceConfig, channelNum);
            }
            init = (int) getDeviceStatusAndStreamMap.get(INITIALIZATION);
            registers = (int) getDeviceStatusAndStreamMap.get(REGISTERS);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[getDeviceStatusAndStream - Err.]" + e);
        }
        if (init == 1 && registers >=0) {
            LOGGER.info("注册设备成功！Device ID :" + deviceConfig.getId().intValue());

            // 设备状态
            HCNetSDK.NET_DVR_WORKSTATE_V30 deviceWorkStatus = new HCNetSDK.NET_DVR_WORKSTATE_V30();
            if (!hcNetSDK.NET_DVR_GetDVRWorkState_V30(deviceConfig.getId(),deviceWorkStatus)){
                LOGGER.error("获取设备状态失败: " + deviceWorkStatus);
            }

            // 获取IP接入配置参数
            IntByReference ibrBytesReturned = new IntByReference(0);

            // IP接入配置结构
            HCNetSDK.NET_DVR_IPPARACFG ipConfig = new HCNetSDK.NET_DVR_IPPARACFG();
            ipConfig.write();
            Pointer lpIpParaConfig = ipConfig.getPointer();

            // 获取设备状态
            hcNetSDK.NET_DVR_GetDVRConfig(deviceConfig.getId(),HCNetSDK.NET_DVR_GET_IPPARACFG,deviceConfig.getDeviceSingleID(),lpIpParaConfig,ipConfig.size(),ibrBytesReturned);
            ipConfig.read();

            //  设备状态：0正常，1CPU占用过高，2硬件错误，3未知错误
            int deviceStatus = deviceWorkStatus.dwDeviceStatic;
            //0 正常 1 信号丢失
            int channelStatus = deviceWorkStatus.struChanStatic[channelNum-1].bySignalStatic;
            LOGGER.info("[getDeviceStatusAndStream] - Device status:" + deviceWorkStatus.dwDeviceStatic);
            LOGGER.info("Channel " + (channelNum-1) + " status:" + deviceWorkStatus.struChanStatic[channelNum-1].bySignalStatic);

            getDeviceStatusAndStreamMap.put("deviceStatus",deviceStatus);
            getDeviceStatusAndStreamMap.put("channelStatus",channelStatus);
            LOGGER.info("获取设备状态成功 - 设备状态:" + deviceStatus);
            LOGGER.info("[ 设备状态：0 正常，1 CPU占用过高，2 硬件错误，3 未知错误 ]");
            LOGGER.info("获取设备状态成功 - 通道状态:" + channelStatus);
            LOGGER.info("[ 通道状态：0 正常，1 信号丢失 ]");

            LOGGER.info("[getDeviceStatusAndStream - 获取视频流]");
            // 设置图片参数
            HCNetSDK.NET_DVR_JPEGPARA jpg = new HCNetSDK.NET_DVR_JPEGPARA();

            // 设置图片质量
            jpg.wPicQuality = 2;

            IntByReference intByReference = new IntByReference();
            ByteBuffer jpgBuffer = ByteBuffer.allocate(1024*1024);

            /*
             * 抓图 单帧数据捕获并包保存成JPEG存放在指定的内存空间中.
             * 参数说明：
             * lUserID      NET_DVR_Login_V30等登录接口返回值
             * lChannel     通道号
             * lpJpegPara   JPEG图像参数
             * sJpegPicBuffer     保存JPEG数据的缓冲区
             * dwPicSize    输入缓冲区大小
             * lpSizeReturned   返回图片数据大小
             */
            boolean result = hcNetSDK.NET_DVR_CaptureJPEGPicture_NEW(deviceConfig.getId(),deviceConfig.getDeviceSingleID(),jpg,jpgBuffer,1024*1024,intByReference);
            LOGGER.info("[getDeviceStatusAndStream] - 抓取图片、单帧数据捕获并保存成JPEG存放在指定的内存空间中：" + result);
            if (!result){
                LOGGER.error("[错误编码] " + hcNetSDK.NET_DVR_GetLastError());
                LOGGER.error("[保存JPEG数据的缓冲区] " + jpgBuffer.array().length);
                LOGGER.error("[返回图片数据大小] " + intByReference.getValue());
                LOGGER.error("[Device gets pictures unsuccessfully ! ! !]");
                getDeviceStatusAndStreamMap.put("NET_DVR_GetLastError", hcNetSDK.NET_DVR_GetLastError());
                LOGGER.error("[getDeviceStatusAndStream - 抓取图片失败并返回结果]." + getDeviceStatusAndStreamMap);
            } else {
                LOGGER.info("[Device gets pictures successfully ! ! !]");
                LOGGER.info("[错误编码] " + hcNetSDK.NET_DVR_GetLastError());
                LOGGER.info("[保存JPEG数据的缓冲区] " + jpgBuffer.array().length);
                LOGGER.info("[返回图片数据大小] " + intByReference.getValue());

                if (jpgBuffer.array().length != 0) {
                    getDeviceStatusAndStreamMap.put("imageData", jpgBuffer.array());
                    getDeviceStatusAndStreamMap.put("value",  intByReference.getValue());
                    getDeviceStatusAndStreamMap.put("NET_DVR_GetLastError", hcNetSDK.NET_DVR_GetLastError());
                    LOGGER.info("[getDeviceStatusAndStream - 抓取图片成功并返回结果]." + getDeviceStatusAndStreamMap);
                }
            }
            // 注销设备
            hcNetSDK.NET_DVR_Logout(deviceConfig.getId());
            hcNetSDK.NET_DVR_Cleanup();
            LOGGER.info("注销设备！");
            return getDeviceStatusAndStreamMap;
        } else if (init == 0) {
            // 调用SDK、进行初始化失败
            LOGGER.error("[getDeviceStatusAndStream - SDK初始化失败!].直接返回结果：hcNetSDK.NET_DVR_Init=" + hcNetSDK.NET_DVR_Init() + ", " + getDeviceStatusAndStreamMap);
            return getDeviceStatusAndStreamMap;
        } else {
            // 重新登录设备
            Map<String, Object> loginMap = login(deviceConfig);
            int initLogin = 0;
            try {
                initLogin = (int) loginMap.get(INITIALIZATION);
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("[getDeviceStatusAndStream - Err]." + e);
            }
            if (initLogin == 1) {
                // 登录成功
                LOGGER.info("[getDeviceStatusAndStream - 设备登录注册成功!]");
                getDeviceStatusAndStream(deviceConfig, channelNum);
                return getDeviceStatusAndStreamMap;
            } else {
                // 登录失败
                LOGGER.error("[getDeviceStatusAndStream - 设备注册失败!]返回结果: " + getDeviceStatusAndStreamMap);
                return getDeviceStatusAndStreamMap;
            }
        }
    }
}
