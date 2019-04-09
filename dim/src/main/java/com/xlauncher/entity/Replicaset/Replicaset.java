package com.xlauncher.entity.Replicaset;

import java.util.List;

/**
 * replicaset包下面的所有实体类均为replicaset配置对象
 * Replicaset实体类
 * @author YangDengcheng
 * @date 2018/1/26 16:43
 */
public class Replicaset {
    private String kind;
    private String apiVersion;
    private MetadataForReplicaset metadata;
    private List<ItemsForReplicaset> items;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public MetadataForReplicaset getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataForReplicaset metadata) {
        this.metadata = metadata;
    }

    public List<ItemsForReplicaset> getItems() {
        return items;
    }

    public void setItems(List<ItemsForReplicaset> items) {
        this.items = items;
    }
}
