package com.xlauncher.entity.Replicaset.items;

import java.util.List;

/**
 * @author YangDengcheng
 * @date 2018/1/26 16:47
 */
public class MetadataForItem {
    private String name;
    private String namespace;
    private String selfLink;
    private String uid;
    private String resourceVersion;
    private Integer generation;
    private String creationTimestamp;
    private LabelsForMetadata labels;
    private AnnotationsForMetadata annotations;
    private List<OwnerReferencesForMetadata> ownerReferences;

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

    public String getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getResourceVersion() {
        return resourceVersion;
    }

    public void setResourceVersion(String resourceVersion) {
        this.resourceVersion = resourceVersion;
    }

    public Integer getGeneration() {
        return generation;
    }

    public void setGeneration(Integer generation) {
        this.generation = generation;
    }

    public String getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(String creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public LabelsForMetadata getLabels() {
        return labels;
    }

    public void setLabels(LabelsForMetadata labels) {
        this.labels = labels;
    }

    public AnnotationsForMetadata getAnnotations() {
        return annotations;
    }

    public void setAnnotations(AnnotationsForMetadata annotations) {
        this.annotations = annotations;
    }

    public List<OwnerReferencesForMetadata> getOwnerReferences() {
        return ownerReferences;
    }

    public void setOwnerReferences(List<OwnerReferencesForMetadata> ownerReferences) {
        this.ownerReferences = ownerReferences;
    }
}
