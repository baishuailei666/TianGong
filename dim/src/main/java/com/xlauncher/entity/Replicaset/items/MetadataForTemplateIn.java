package com.xlauncher.entity.Replicaset.items;

/**
 * @author YangDengcheng
 * @date 2018/1/26 17:15
 */
public class MetadataForTemplateIn {
    private String creationTimestamp;
    private LabelsForMetadataIn labels;

    public String getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(String creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public LabelsForMetadataIn getLabels() {
        return labels;
    }

    public void setLabels(LabelsForMetadataIn labels) {
        this.labels = labels;
    }
}
