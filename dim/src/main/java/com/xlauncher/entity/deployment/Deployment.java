package com.xlauncher.entity.deployment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;


/**
 * Kubernetes Deployment实体类
 * deployment包下面所有的实体类均属于Deployment配置参数
 * @author YangDengcheng
 * @date 2018/1/25 9:53
 */
@Component
public class Deployment {
    private String apiVersion = "extensions/v1beta1";
    private String kind = "Deployment";
    private Metadata metadata;
    private Spec spec;

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public Spec getSpec() {
        return spec;
    }

    public void setSpec(Spec spec) {
        this.spec = spec;
    }

}
