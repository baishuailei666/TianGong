package com.xlauncher.entity.Replicaset.items;

/**
 * @author YangDengcheng
 * @date 2018/1/26 16:52
 */
public class LabelsForMetadata {
    private String app;
    private String pod_template_hash;

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getPod_template_hash() {
        return pod_template_hash;
    }

    public void setPod_template_hash(String pod_template_hash) {
        this.pod_template_hash = pod_template_hash;
    }
}
