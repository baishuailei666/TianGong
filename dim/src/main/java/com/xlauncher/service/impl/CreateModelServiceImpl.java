package com.xlauncher.service.impl;


import com.alibaba.fastjson.JSON;
import com.xlauncher.entity.configmap.ConfigMap;
import com.xlauncher.entity.deployment.Deployment;
import com.xlauncher.service.CreateModelService;
import org.apache.log4j.Logger;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 与K8S相关创建模块实现类
 * @author YangDengcheng
 * @date 2018/1/15 12:06
 */

@Service("createModelService")
public class CreateModelServiceImpl implements CreateModelService {

    private static final Logger LOGGER = Logger.getLogger(CreateModelServiceImpl.class);

    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders httpHeaders;
    private HttpEntity<String> httpEntity;
    private ResponseEntity responseEntity;

    /**
     * 创建Deployment
     * @param url   K8S-Deployment API
     * @param deployment
     * @return  ApiServer ResponseBody
     */
    @Override
    public ResponseEntity<String> createDeployment(String url, Deployment deployment) {
        //设置请求头
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        //将deployment实体序列化并替换其中属性名
        String requestJson = JSON.toJSONString(deployment).replace("apolloNamespace","apollo-namespace");
        System.out.println(requestJson);
        httpEntity = new HttpEntity<>(requestJson,httpHeaders);
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.POST,httpEntity,String.class);
            LOGGER.info("[URL:Kubernetes API-Deployment,Methods=[POST]] Invoke HTTP request successfully");
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("[URL:Kubernetes API-Deployment,Methods=[POST]] Invoke HTTP request unsuccessfully");
        }
        return responseEntity;
    }

    /**
     * 创建configMap
     * @param url   K8S-Deployment API
     * @param configMap
     * @return  ApiServer ResponseBody
     */
    @Override
    public ResponseEntity<String> createConfigMap(String url, ConfigMap configMap) {
        //设置请求头
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        //将configMap实体序列化并替换其中属性名
        String requestJson = JSON.toJSONString(configMap).replace("properties","config.properties");
        System.out.println(requestJson);
        httpEntity = new HttpEntity<>(requestJson,httpHeaders);
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.POST,httpEntity,String.class);
            LOGGER.info("[URL:Kubernetes API-ConfigMap,Methods=[POST]] Invoke HTTP request successfully");
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("[URL:Kubernetes API-ConfigMap,Methods=[POST]] Invoke HTTP request unsuccessfully");
        }
        return responseEntity;
    }

}
