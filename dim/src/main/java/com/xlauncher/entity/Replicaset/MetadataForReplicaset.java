package com.xlauncher.entity.Replicaset;

/**
 *
 * @author YangDengcheng
 * @date 2018/1/26 16:44
 */
public class MetadataForReplicaset {
    private String selfLink;
    private String resourceVersion;

    public String getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    public String getResourceVersion() {
        return resourceVersion;
    }

    public void setResourceVersion(String resourceVersion) {
        this.resourceVersion = resourceVersion;
    }
}
