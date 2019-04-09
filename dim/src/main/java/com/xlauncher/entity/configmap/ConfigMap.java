package com.xlauncher.entity.configmap;

/**
 * ConfigMap实体类
 * @author YangDengcheng
 * @date 2018/2/28 13:49
 */
public class ConfigMap {
    private String apiVersion = "v1";
    private Data data;
    private String kind = "ConfigMap";
    private MetaDataInConfigMap metadata;

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public MetaDataInConfigMap getMetadata() {
        return metadata;
    }

    public void setMetadata(MetaDataInConfigMap metadata) {
        this.metadata = metadata;
    }
}
