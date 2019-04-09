package com.xlauncher.ics.service.impl;

import com.xlauncher.ics.entity.RabbitMQ;
import com.xlauncher.ics.entity.ServiceInfo;
import com.xlauncher.ics.service.ServiceInfoService;
import com.xlauncher.ics.util.PropertiesUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author :baisl
 * @Email :baishuailei@xlauncher.io
 * @Date :2018/12/20 0020
 * @Desc :服务配置实现
 **/
@Service
public class ServiceInfoServiceImpl implements ServiceInfoService{
    @Autowired
    private ServiceInfo serviceInfo;
    @Autowired
    private PropertiesUtil propertiesUtil;
    @Autowired
    private RabbitMQ rabbitMQ;
    private static final String ES = "es";
    private static final String FTP = "ftp";
    private static final String MQ = "mq";
    private static Logger logger = Logger.getLogger(ServiceInfoServiceImpl.class);

    /**
     * 写入ES、FTP、RabbitMQ服务信息
     *
     * @param serviceMap serviceMap
     */
    @Override
    public void writeService(Map<String, Map<String, Object>> serviceMap) {
        Map<String, Object> map;
        /**
         * es服务
         */
        if (serviceMap.get(ES) != null) {
            map = serviceMap.get(ES);
            logger.info("[ES]服务配置." + map);
            String esIp = (String) map.get("esIp");
            String esPort = (String) map.get("esPort");
            String esName = (String) map.get("esName");
            float detectThresh = Float.parseFloat(String.valueOf(map.get("detectThresh")));
            float classThresh = Float.parseFloat(String.valueOf(map.get("classThresh"))) ;
            propertiesUtil.writeProperties("es.ip", esIp);
            propertiesUtil.writeProperties("es.port", esPort);
            propertiesUtil.writeProperties("es.name", esName);
            propertiesUtil.writeProperties("es.detect_thresh", String.valueOf(detectThresh));
            propertiesUtil.writeProperties("es.class_thresh", String.valueOf(classThresh));
//            serviceInfo.setEsIp(esIp);
//            serviceInfo.setEsPort(esPort);
//            serviceInfo.setEsName(esName);
//            serviceInfo.setDetectThresh(detectThresh);
//            serviceInfo.setClassThresh(classThresh);
            logger.info("writeService写入[ES]服务配置!" + map);
        } else {
            logger.warn("[ES]服务配置为空!");
        }

        /**
         * ftp服务
         */
        if(serviceMap.get(FTP) != null) {
            map = serviceMap.get(FTP);
            logger.info("[FTP]服务配置." + map);
            String ftpIp = (String) map.get("ftpIp");
            String ftpPort = (String) map.get("ftpPort");
            String ftpUserName = (String) map.get("ftpUserName");
            String ftpPassword = (String) map.get("ftpPassword");
            String ftpStorePath = (String) map.get("ftpStorePath");
            propertiesUtil.writeProperties("ftp.ip", ftpIp);
            propertiesUtil.writeProperties("ftp.port", ftpPort);
            propertiesUtil.writeProperties("ftp.userName", ftpUserName);
            propertiesUtil.writeProperties("ftp.password", ftpPassword);
            propertiesUtil.writeProperties("ftp.store_path", ftpStorePath);

//            serviceInfo.setFtpIp(ftpIp);
//            serviceInfo.setFtpPort(ftpPort);
//            serviceInfo.setFtpUserName(ftpUserName);
//            serviceInfo.setFtpPassword(ftpPassword);
//            serviceInfo.setFtpStorePath(ftpStorePath);
            logger.info("writeService写入[FTP]服务配置! " + map);
        } else {
            logger.warn("[FTP]服务配置为空!");
        }

        /**
         * rabbitMQ服务
         */
        if (serviceMap.get(MQ) != null) {
            map = serviceMap.get(MQ);
            logger.info("[RabbitMQ]服务配置." + map);
            String mqIp = (String) map.get("mqIp");
            String mqPort = (String) map.get("mqPort");
            String mqUserName = (String) map.get("mqUserName");
            String mqPassword = (String) map.get("mqPassword");
            String mqQueueImg = (String) map.get("queueImg");
            String mqQueueChannel = (String) map.get("queueChannel");
            propertiesUtil.writeProperties("mq.ip", mqIp);
            propertiesUtil.writeProperties("mq.port", mqPort);
            propertiesUtil.writeProperties("mq.userName", mqUserName);
            propertiesUtil.writeProperties("mq.password", mqPassword);
            propertiesUtil.writeProperties("mq.queue_img", mqQueueImg);
            propertiesUtil.writeProperties("mq.queue_channel", mqQueueChannel);
//            rabbitMQ.setMqIp(mqIp);
//            rabbitMQ.setMqPort(mqPort);
//            rabbitMQ.setMqUserName(mqUserName);
//            rabbitMQ.setMqPassword(mqPassword);
//            rabbitMQ.setMqQueueImg(mqQueueImg);
//            rabbitMQ.setMqQueueChannel(mqQueueChannel);
            logger.info("writeService写入[RabbitMQ]服务配置! " + map);
        } else {
            logger.warn("[RabbitMQ]服务配置为空!");
        }

    }
}
