package com.xlauncher.service.Impl;

import com.xlauncher.entity.Service;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * 功能：操作为操作service.properties提供接口
 * 作者：张霄龙
 * 时间：2018-02-03
 */

@org.springframework.stereotype.Service
public class ServiceProperties {

    /**
     * 读写properties文件的对象
     */
    private Properties properties;

    /**
     * 添加一个记录器
     */
    private static Logger logger = Logger.getLogger(ServiceProperties.class);

    /**
     * 文件名
     */
    public String fileName;

    public static boolean isHostReachable = true;

    /**
     * 初始化构造函数
     */
    public ServiceProperties() {
        properties = new Properties();
        this.fileName = checkIfExist();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF-8"));
            properties.load(br);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断service.properties在target/classes下是否存在,如果不存在则创建文件
     * @return 返回文件名（包含路径）
     */
    public String checkIfExist() {
        String fileName = ServiceProperties.class.getClassLoader().getResource("").getPath() + "service.properties";
        logger.info("[servive.properties]: " + fileName);
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

    /**
     * 根据服务名获取服务信息
     * @param serName 服务名称
     * @return Service对象
     */
    public Service getService(String serName) {
        Service service = new Service();
        service.setSerName(properties.getProperty(serName + ".name", "noneService"));
        service.setSerIp(properties.getProperty(serName + ".ip", "noneIp"));
        service.setSerPort(properties.getProperty(serName + ".port", "8080"));
        if ("noneService" == service.getSerName()) {
            logger.error("Transpond Error: noneService[" + serName + "]");
        }
        return service;
    }

    /**
     * 根据服务列表写入配置文件
     * @param serviceList 服务列表信息
     * @return 写入的服务的个数
     */
    public int setProperties(List<Service> serviceList) {
        return operateProperties(serviceList, "Add");
    }

    /**
     * 单个服务信息写入
     * @param service 服务信息
     * @return 成功返回1；异常返回0
     */
    public int setProperties(Service service) {
        return operateProperties(service, "Add");
    }

    /**
     * 写入事件配置的时间信息
     * @param eventConfMap 事件配置信息
     * @return 成功返回1；异常返回0
     */
    public int setEventConf(Map<String, String> eventConfMap) {
        String eventTime = eventConfMap.getOrDefault("eventTime","600000");
        return operateEventConf(eventTime);
    }

    /**
     * 获取事件配置的时间信息
     *
     * @return 事件配置的时间信息
     */
    public String getEventTime() {
        return properties.getProperty("eventTime", "600000");
    }

    /**
     * 写入事件配置的时间信息
     *
     * @param eventTime 事件配置的时间信息
     * @return 成功返回1；异常返回0
     */
    private int operateEventConf(String eventTime) {
        System.out.println(" *****事件配置的时间信息(毫秒级)***** ");
        properties.setProperty("eventTime", eventTime);
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName),"UTF-8"));
            properties.store(bw , "eventTime" + eventTime);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    /**
     * 添加或者更新properties文件调用此函数
     * @param serviceList 服务列表
     * @param action 动作
     * @return 返回动作影响的服务的个数
     */
    private int operateProperties(List<Service> serviceList, String action) {
        int serCount = 0;
        String comments = action + " services: ";
        StringBuffer buf = new StringBuffer();
        BufferedWriter bw = null;
        for (Service service : serviceList) {
            String serName = service.getSerName();
            String serIp = service.getSerIp();
            String serPort = service.getSerPort();
            properties.setProperty(serName + ".name", serName);
            properties.setProperty(serName + ".ip", serIp);
            properties.setProperty(serName + ".port", serPort);
            buf.append(serName).append(" ");
            comments = buf.toString();
            serCount++;
        }
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName),"UTF-8"));
            properties.store(bw , comments);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serCount;
    }

    /**
     * 单个服务对象的操作
     * @param service 服务信息
     * @param action 动作
     * @return 成功返回1；异常返回0
     */
    private int operateProperties(Service service, String action) {
        String comments = action + " services: ";
        String serName = service.getSerName();
        String serIp = service.getSerIp();
        String serPort = service.getSerPort();
        properties.setProperty(serName + ".name", serName);
        properties.setProperty(serName + ".ip", serIp);
        properties.setProperty(serName + ".port", serPort);
        logger.info("operateProperties写入服务对象：" + service);
        comments += serName;
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName),"UTF-8"));
            properties.store(bw , comments);
            logger.info("写入服务成功：" + comments);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

}
