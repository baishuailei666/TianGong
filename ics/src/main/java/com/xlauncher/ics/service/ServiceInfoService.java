package com.xlauncher.ics.service;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author :baisl
 * @Email :baishuailei@xlauncher.io
 * @Date :2018/12/20 0020
 * @Desc :服务配置服务层
 **/
@Service
public interface ServiceInfoService {

    /**
     * 写入ES、FTP、RabbitMQ服务信息
     *
     * @param serviceMap serviceMap
     */
    void writeService(Map<String, Map<String, Object>> serviceMap);
}
