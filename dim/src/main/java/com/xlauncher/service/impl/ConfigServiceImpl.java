package com.xlauncher.service.impl;

import com.xlauncher.entity.Service;
import com.xlauncher.service.ConfigService;
import com.xlauncher.util.PropertiesUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 添加服务至配置文件实现类
 * @author YangDengcheng
 * @date 2018/1/22 17:15
 */
@org.springframework.stereotype.Service("configService")
public class ConfigServiceImpl implements ConfigService {
    protected Logger logger =Logger.getLogger(ConfigServiceImpl.class);

    @Autowired
    PropertiesUtil propertiesUtil;

    /**
     * 新增配置信息到配置文件
     *
     * @param service 对象
     * @return success:1 failed:0
     */
    @Override
    public void addConfigService(Service service) {
        if ("k8s".equals(service.getSerName())){
            propertiesUtil.writeProperties("k8sIp", service.getSerIp());
            propertiesUtil.writeProperties("k8sPort", service.getSerPort());
            propertiesUtil.writeProperties("k8sNamespace", service.getSerNamespace());
            propertiesUtil.writeProperties("k8sImagelocation", service.getSerAddress());
        }else{
            propertiesUtil.writeProperties(service.getSerName()+"Ip", service.getSerIp());
            propertiesUtil.writeProperties(service.getSerName()+"Port", service.getSerPort());
        }
    }

    /**
     * 新增RabbitMQ配置信息到配置文件
     *
     * @param service 对象
     */
    @Override
    public void addRabbitMqService(Map<String, Object> service) {
        if (service != null) {
            propertiesUtil.writeProperties("mqIp", String.valueOf(service.get("mqIp")));
            propertiesUtil.writeProperties("mqPort", String.valueOf(service.get("mqPort")));
            propertiesUtil.writeProperties("mqUserName", String.valueOf(service.get("mqUserName")));
            propertiesUtil.writeProperties("mqPassword", String.valueOf(service.get("mqPassword")));
            propertiesUtil.writeProperties("mqQueue_img", String.valueOf(service.get("queueImg")));
            propertiesUtil.writeProperties("mqQueue_channel", String.valueOf(service.get("queueChannel")));
            logger.info("写入[RabbitMQ]服务配置信息." + service);
        }
    }

    /**
     * 修改配置文件中的配置信息
     *
     * @param service 对象
     * @return success：1 failed：0
     */
    @Override
    public int updateConfigService(Service service) {
        String key = service.getSerName();
        String valueIp = service.getSerIp();
        String valuePort = service.getSerPort();
        if (key != null){
            logger.info("service:" + service);
            propertiesUtil.updateProperties(key,valueIp,valuePort);
            return 1;
        }else {
            return 0;
        }
    }

    /**
     * 删除配置信息
     *
     * @param service 对象
     * @return success：1 failed：0
     */
    @Override
    public void deleteConfigService(Service service) {
        String key = service.getSerName();
        if (key != null){
            propertiesUtil.deleteProperties(key);
        }

    }

    /**
     * 根据键名读取键值
     *
     * @param key 键名
     * @return String 键值
     */
    @Override
    public String readConfigService(String key) {
        return PropertiesUtil.readValue(key);
    }


}
