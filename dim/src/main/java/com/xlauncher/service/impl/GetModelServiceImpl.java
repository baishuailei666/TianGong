package com.xlauncher.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xlauncher.entity.Replicaset.Replicaset;
import com.xlauncher.service.GetModelService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 与K8S相关删除模块实现类
 * @author YangDengcheng
 * @date 2018/1/15 14:23
 */
@Service("getModelService")
public class GetModelServiceImpl implements GetModelService {

    private static final Logger LOGGER = Logger.getLogger(GetModelServiceImpl.class);
    private RestTemplate restTemplate = new RestTemplate();

    /**
     * 查询Deployment对应的ReplicasetName
     * @param url   K8S-API
     * @return  ApiServer ResponseBody
     */
    @Override
    public String getReplicaset(String url) {
        if (url != null){
            String responseEntity = restTemplate.getForObject(url,String.class);
            LOGGER.info("[URL:Kubernetes API-Replicaset,Methods=[GET]] Invoke HTTP request successfully");
            LOGGER.info("Replicaset'Body:" + responseEntity);

            //替换特殊符号
            String replicasetResult = responseEntity.replace(".","_").replace("-","_").replace("/","_");
            String replicasetName = JSONObject.parseObject(replicasetResult,Replicaset.class).getItems().iterator().next().getMetadata().getName();
            LOGGER.info("Replicaset'Name:" + replicasetName);
            return replicasetName;
        }else {
            LOGGER.error("[URL:Kubernetes API-Replicaset,Methods=[GET]] Invoke HTTP request successfully");
            LOGGER.error("Get replicaset'name unsuccessfully");
            return null;
        }
    }
}
