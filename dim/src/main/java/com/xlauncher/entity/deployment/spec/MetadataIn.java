package com.xlauncher.entity.deployment.spec;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Kubernetes MetadataIn实体类
 * @author YangDengcheng
 * @date 2018/1/25 10:16
 */
@Component
public class MetadataIn {
    private Map<String,String> labels;
    private String creationTimestamp = null;

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }

    public String getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(String creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }
}
