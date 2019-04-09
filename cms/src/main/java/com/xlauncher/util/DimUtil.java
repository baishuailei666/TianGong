package com.xlauncher.util;

import com.xlauncher.dao.ComponentDao;
import com.xlauncher.entity.Channel;
import com.xlauncher.entity.Device;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;
import java.util.Map;

/**
 * 将设备管理模块信息同步给DIM工具类
 * @author mao ye
 * @since 2018-02-01
 */
@Component
public class DimUtil {
    private RestTemplate restTemplate = new RestTemplate();
    private static Logger logger = Logger.getLogger(DimUtil.class);
    @Autowired
    ComponentDao componentDao;
    private com.xlauncher.entity.Component component;
    private String url;
    /**
     * 将添加的设备同步到DIM模块
     * @param device 设备信息
     */
    public void devicePost(Device device) throws SQLException {
        try {
            component = componentDao.getComponentByAbbr("dim");
            url = "http://" + component.getComponentIp() + ":" + component.getComponentPort() + "/dim/device";
            restTemplate.postForEntity(url, device, String.class);
            logger.info("成功-将添加的设备同步到DIM模块：" + device + "url:" + url);
        } catch (RestClientException e) {
            logger.error("失败-将添加的设备同步到DIM模块.Err" + e);
            logger.info("url." + url + ",[DIM] component." + component);
        }

    }

    /**
     * 将设备更新同步到DIM模块
     * @param device 设备信息
     */
    public void devicePut(Device device) throws SQLException {
        try {
            component = componentDao.getComponentByAbbr("dim");
            url = "http://" + component.getComponentIp() + ":" + component.getComponentPort() + "/dim/device";
            restTemplate.put(url, device);
            logger.info("成功-将设备更新同步到DIM模块：" + device + "url:" + url);
        } catch (RestClientException e) {
            logger.error("失败-将更新的设备同步到DIM模块.Err" + e);
            logger.info("url." + url + ",[DIM] component." + component);
        }
    }

    /**
     * 将设备的删除同步到DIM模块
     * @param deviceId 设备编号
     */
    public void deviceDelete(String deviceId) throws SQLException {
        try {
            component = componentDao.getComponentByAbbr("dim");
            url = "http://" + component.getComponentIp() + ":" + component.getComponentPort() + "/dim/device?id={id}";
            restTemplate.delete(url, deviceId);
            logger.info("成功-将设备的删除同步到DIM模块：" + deviceId + "url:" + url);
        } catch (RestClientException e) {
            logger.error("失败-将删除的设备同步到DIM模块.Err" + e);
            logger.info("url." + url + ",[DIM] component." + component + ", deviceId." + deviceId);
        }
    }

    /**
     * 激活设备同步到DIM模块
     * @param deviceId 设备编号
     */
    public void activeDevice(String deviceId) throws SQLException {
        try {
            component = componentDao.getComponentByAbbr("dim");
            url = "http://" + component.getComponentIp() + ":" + component.getComponentPort() + "/dim/activeDevice/" + deviceId;
            restTemplate.put(url, deviceId);
            logger.info("成功-激活设备同步到DIM模块：" + deviceId + "url:" + url);
        } catch (RestClientException e) {
            logger.error("失败-将激活的设备同步到DIM模块.Err" + e);
            logger.info("url." + url + ",[DIM] component." + component + ", deviceId." + deviceId);
        }
    }

    /**
     * 停用设备同步到DIM模块
     * @param deviceId 设备编号
     */
    public void disableDevice(String deviceId) throws SQLException {
        try {
            component = componentDao.getComponentByAbbr("dim");
            url = "http://" + component.getComponentIp() + ":" + component.getComponentPort() + "/dim/disableDevice/" + deviceId;
            restTemplate.put(url, deviceId);
            logger.info("成功-停用设备同步到DIM模块：" + deviceId + "url:" + url);
        } catch (RestClientException e) {
            logger.error("失败-将停用的设备同步到DIM模块.Err" + e);
            logger.info("url." + url + ",[DIM] component." + component + ", deviceId." + deviceId);
        }
    }


    /**
     * 将添加的通道（摄像头）同步到DIM模块
     * @param channel 通道信息
     */
    public void channelPost(Channel channel) throws SQLException {
        component = componentDao.getComponentByAbbr("dim");
        //header设置
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(channel, headers);
        try {
            url ="http://" + component.getComponentIp() + ":" + component.getComponentPort() + "/dim/channel";
            restTemplate.postForEntity(url, httpEntity, Map.class);
            logger.info("成功-将添加的通道（摄像头）同步到DIM模块：" + channel + "url:" + url);
        } catch (RestClientException e) {
            logger.error("失败--将添加的通道同步到DIM模块.Err" + e);
            logger.info("url." + url + ",[DIM] component." + component);
        }
    }

    /**
     * 将更新的通道（摄像头）同步到DIM模块
     * @param channel 通道信息
     */
    public void channelPut(Channel channel) throws SQLException {
        try {
            component = componentDao.getComponentByAbbr("dim");
            url = "http://" + component.getComponentIp() + ":" + component.getComponentPort() + "/dim/channel";
            restTemplate.put(url, channel);
            logger.info("成功-将更新的通道（摄像头）同步到DIM模块：" + channel + "url:" + url);
        } catch (RestClientException e) {
            logger.error("失败--将更新的通道同步到DIM模块.Err" + e);
            logger.info("url." + url + ",[DIM] component." + component);
        }

    }

    /**
     * 将删除的通道（摄像头）同步到DIM模块
     * @param channelId 通道编号
     */
    public void channelDelete(String channelId) throws SQLException {
        try {
            component = componentDao.getComponentByAbbr("dim");
            url = "http://" + component.getComponentIp() + ":" + component.getComponentPort() + "/dim/channel?id={id}";
            restTemplate.delete(url, channelId);
            logger.info("成功-将删除的通道（摄像头）同步到DIM模块：" + channelId + "url:" + url);
        } catch (RestClientException e) {
            logger.error("失败--将更新的通道同步到DIM模块.Err" + e);
            logger.info("url." + url + ",[DIM] component." + component + ", channelId." + channelId);
        }

    }

    /**
     * 激活通道同步到DIM模块
     * @param channelId 通道编号
     */
    public void activeChannel(String channelId) throws SQLException {
        try {
            component = componentDao.getComponentByAbbr("dim");
            url = "http://" + component.getComponentIp() + ":" + component.getComponentPort() + "/dim/activeChannel/" + channelId;
            restTemplate.put(url, channelId);
            logger.info("成功-激活通道同步到DIM模块：" + channelId + "url:" + url);
        } catch (RestClientException e) {
            logger.error("失败--将激活的通道同步到DIM模块.Err" + e);
            logger.info("url." + url + ",[DIM] component." + component + ", channelId." + channelId);
        }
    }

    /**
     * 停用通道同步到DIM模块
     * @param channelId 通道编号
     */
    public void disableChannel(String channelId) throws SQLException {
        try {
            component = componentDao.getComponentByAbbr("dim");
            url = "http://" + component.getComponentIp() + ":" + component.getComponentPort() + "/dim/disableChannel/" + channelId;
            restTemplate.put(url, channelId);
            logger.info("成功-停用通道同步到DIM模块：" + channelId + "url:" + url);
        } catch (RestClientException e) {
            logger.error("失败--将停用的通道同步到DIM模块.Err" + e);
            logger.info("url." + url + ",[DIM] component." + component + ", channelId." + channelId);
        }
    }
}
