package com.xlauncher.util;

import com.alibaba.fastjson.JSON;
import com.xlauncher.entity.deployment.Deployment;
import org.apache.log4j.Logger;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 为Kubernetes提供CRUD工具类
 * @author YangDengcheng
 * @date 2018/1/15 11:07
 */
@Component
public class KubernetesUtil {
    private static final Logger LOGGER = Logger.getLogger(KubernetesUtil.class);

    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders headers;
    private MediaType mediaType;
    private HttpEntity<String> httpEntity;
    private ResponseEntity<String> responseEntity;


    /**
     * 创建应用
     * @param url   deployment apiServer
     * @param deployment
     * @return
     */
    public ResponseEntity<String> createDeployment(String url, Deployment deployment){
        headers = new HttpHeaders();
        mediaType = MediaType.parseMediaType("application/json;charset=UTF-8");
        headers.setContentType(mediaType);
        String requsetJson = JSON.toJSONString(deployment).replace("apolloNamespace","apollo-namespace");
        httpEntity = new HttpEntity<>(requsetJson,headers);

        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.POST,httpEntity,String.class);
            LOGGER.info("[URL:Kubernetes API-Deployment,Methods=[POST]] Invoke HTTP request success");
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("[URL:Kubernetes API-Deployment,Methods=[POST]] Invoke HTTP request failed");
        }

        return responseEntity;
    }

    public String getReplicasetName(String url) {
        try {
            String responseEntity = restTemplate.getForObject(url,String.class);
            LOGGER.info("[URL:Kubernetes API-Replicaset,Methods=[GET]] Invoke HTTP request success");
            LOGGER.info("Replicaset'Body:" + responseEntity);
            return responseEntity;
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("[URL:Kubernetes API-Replicaset,Methods=[GET]] Invoke HTTP request failed");
            return null;
        }

    }


    public ResponseEntity<String> deleteReplicaset(String url, String replicasetName){

        try {
            responseEntity = restTemplate.exchange(url,HttpMethod.DELETE, (HttpEntity<?>) null,String.class);
            LOGGER.info("[URL:Kubernetes API-Deployment,Methods=[DELETE]] Invoke HTTP request success");
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("[URL:Kubernetes API-Deployment,Methods=[DELETE]] Invoke HTTP request failed");
        }

        return responseEntity;
    }

}
