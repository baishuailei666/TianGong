package com.xlauncher.service;

import org.springframework.http.ResponseEntity;

/**
 * 与K8S相关删除模块接口
 * @author YangDengcheng
 * @date 2018/1/16 9:22
 */
public interface DeleteModelService {
    /**
     * 删除应用（Deployment / Replicaset / ConfigMap）
     * @param url   K8S-Replicaset API
     * @return  ApiServer ResponseBody
     */
    public ResponseEntity<String> deleteApplication(String url);


}
