package com.xlauncher.service;

import com.xlauncher.entity.Service;

import java.util.Map;

/**
 * 添加服务至配置文件接口
 * @author YangDengcheng
 * @date 2018/1/22 17:08
 */

public interface ConfigService {
    /**
     * 新增配置信息到配置文件
     * @param service 对象
     * @return success:1 failed:0
     */
    public void addConfigService(Service service);

    /**
     * 新增RabbitMQ配置信息到配置文件
     *
     * @param service 对象
     * @return success:1 failed:0
     */
    public void addRabbitMqService(Map<String, Object> service);

    /**
     * 修改配置文件中的配置信息
     * @param service 对象
     * @return success：1 failed：0
     */
    public int updateConfigService(Service service);

    /**
     * 删除配置信息
     * @param service 对象
     * @return success：1 failed：0
     */
    public void deleteConfigService(Service service);

    /**
     * 根据键名读取键值
     * @param key 键名
     * @return String
     */
    public String readConfigService(String key);


}
