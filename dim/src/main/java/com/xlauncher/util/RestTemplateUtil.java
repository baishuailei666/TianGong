package com.xlauncher.util;

import com.alibaba.fastjson.JSON;
import com.xlauncher.entity.Device;
import org.apache.log4j.Logger;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;


/**
 * @author :baisl
 * @Email :baishuailei@xlauncher.io
 * @Date :2019/2/15 0015
 * @Desc :
 **/
public class RestTemplateUtil {
    private static RestTemplate restTemplate;
    private static int pushStatus = 0;
    private static Logger logger = Logger.getLogger(RestTemplateUtil.class);

    public RestTemplateUtil() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        // 设置restTemplate超时时间
        requestFactory.setConnectTimeout(60 * 1000);
        requestFactory.setReadTimeout(60 * 1000);
        restTemplate = new RestTemplate(requestFactory);
    }

    /**
     * 将SDK初始化信息上报给CMS端
     *
     * @param device
     * @param errorCode
     */
    public static void pushDeviceStatusToCMS(Device device, int errorCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        device.setDeviceFaultTime(DatetimeUtil.getDate(System.currentTimeMillis()));
        device.setDeviceFaultCode(errorCode);
        device.setDeviceFault("连接服务器失败");
        // 转发设备状态给CMS
        HttpEntity<String> deviceEntity = new HttpEntity<>(JSON.toJSONString(device),headers);
        try {
            restTemplate.exchange(ConstantClassUtil.CMS_LOCATION + "/cms/device/runTimeStatus", HttpMethod.PUT,deviceEntity,String.class);
            logger.info("转发通道状态给CMS" + ConstantClassUtil.CMS_LOCATION + "/cms/device/runTimeStatus");
            logger.info("device:" + device);
        } catch (ResourceAccessException e) {
            logger.error("CMS服务异常：" + e.getMessage() + e.getLocalizedMessage());
        }
    }
}
