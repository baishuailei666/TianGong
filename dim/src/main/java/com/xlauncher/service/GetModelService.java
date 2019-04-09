package com.xlauncher.service;


import org.json.JSONException;


/**
 * 与K8S相关删除模块接口
 * @author YangDengcheng
 * @date 2018/1/15 14:22
 */
public interface GetModelService {
    /**
     * 查询Deployment对应的ReplicasetName
     * @param url   K8S-API
     * @return  ApiServer ResponseBody
     * @throws JSONException
     */
    public String getReplicaset(String url);

}
