package com.xlauncher.entity.deployment.spec;

import org.springframework.stereotype.Component;

/**
 * Kubernetes Volume实体类
 * TODO 暂无配置项
 * @author YangDengcheng
 * @date 2018/1/25 10:31
 */
@Component
public class Volumes {
    private ConfigMapIn configMap;
    private String name;

    public ConfigMapIn getConfigMap() {
        return configMap;
    }

    public void setConfigMap(ConfigMapIn configMap) {
        this.configMap = configMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
