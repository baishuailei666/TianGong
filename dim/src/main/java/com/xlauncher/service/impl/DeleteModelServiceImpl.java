package com.xlauncher.service.impl;

import com.xlauncher.service.DeleteModelService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 与K8S相关删除模块实现类
 * @author YangDengcheng
 * @date 2018/1/16 9:28
 */
@Service("deleteModelService")
public class DeleteModelServiceImpl implements DeleteModelService {

    private static final Logger LOGGER = Logger.getLogger(DeleteModelServiceImpl.class);
    private RestTemplate restTemplate = new RestTemplate();


    /**
     * 删除Replicaset
     * @param url   K8S-Replicaset API
     * @return  ApiServer ResponseBody
     */
    @Override
    public ResponseEntity<String> deleteApplication(String url) {
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE,(HttpEntity<?>)null,String.class);
            LOGGER.info("[URL:" + url + ",Methods=[DELETE]] Invoke HTTP request successfully");
            return responseEntity;
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("[URL:" + url + ",Methods=[DELETE]] Invoke HTTP request unsuccessfully");
            return null;
        }
    }
}
