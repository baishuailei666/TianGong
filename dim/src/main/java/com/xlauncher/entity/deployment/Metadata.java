package com.xlauncher.entity.deployment;

import org.springframework.stereotype.Component;

/**
 * Kubernetes Metadata实体类
 * @author YangDengcheng
 * @date 2018/1/25 9:54
 */
@Component
public class Metadata {
    private Labels labels;
    private String name;
    private String namespace;

    public Labels getLabels() {
        return labels;
    }

    public void setLabels(Labels labels) {
        this.labels = labels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }



}
