package com.xlauncher.entity.Replicaset.items;

/**
 * @author YangDengcheng
 * @date 2018/1/26 16:48
 */
public class StatusForItem {
    private Integer replicas;
    private Integer fullyLabeledReplicas;
    private Integer readyReplicas;
    private Integer availableReplicas;
    private Integer observedGeneration;

    public Integer getReplicas() {
        return replicas;
    }

    public void setReplicas(Integer replicas) {
        this.replicas = replicas;
    }

    public Integer getFullyLabeledReplicas() {
        return fullyLabeledReplicas;
    }

    public void setFullyLabeledReplicas(Integer fullyLabeledReplicas) {
        this.fullyLabeledReplicas = fullyLabeledReplicas;
    }

    public Integer getReadyReplicas() {
        return readyReplicas;
    }

    public void setReadyReplicas(Integer readyReplicas) {
        this.readyReplicas = readyReplicas;
    }

    public Integer getAvailableReplicas() {
        return availableReplicas;
    }

    public void setAvailableReplicas(Integer availableReplicas) {
        this.availableReplicas = availableReplicas;
    }

    public Integer getObservedGeneration() {
        return observedGeneration;
    }

    public void setObservedGeneration(Integer observedGeneration) {
        this.observedGeneration = observedGeneration;
    }
}
