package com.xlauncher.service;

import com.xlauncher.entity.configmap.ConfigMap;
import com.xlauncher.entity.deployment.Deployment;
import org.springframework.http.ResponseEntity;

/**
 * 与K8S相关创建模块接口
 * @author YangDengcheng
 * @date 2018/1/15 11:59
 */

public interface CreateModelService {
    /**
     * 创建Deployment
     * @param url   K8S-Deployment API
     * @param deployment
     * @return  ApiServer ResponseBody
     */
    public ResponseEntity<String> createDeployment(String url, Deployment deployment);

    /**
     * 创建ConfigMap
     * @param url   K8S-Deployment API
     * @param configMap
     * @return  ApiServer ResponseBody
     */
    public ResponseEntity<String> createConfigMap(String url, ConfigMap configMap);
}
