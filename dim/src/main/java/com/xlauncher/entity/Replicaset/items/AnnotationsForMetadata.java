package com.xlauncher.entity.Replicaset.items;

/**
 * @author YangDengcheng
 * @date 2018/1/26 16:53
 */
public class AnnotationsForMetadata {
    private String deployment_kubernetes_io_desired_replicas;
    private String deployment_kubernetes_io_max_replicas;
    private String deployment_kubernetes_io_revision;

    public String getDeployment_kubernetes_io_desired_replicas() {
        return deployment_kubernetes_io_desired_replicas;
    }

    public void setDeployment_kubernetes_io_desired_replicas(String deployment_kubernetes_io_desired_replicas) {
        this.deployment_kubernetes_io_desired_replicas = deployment_kubernetes_io_desired_replicas;
    }

    public String getDeployment_kubernetes_io_max_replicas() {
        return deployment_kubernetes_io_max_replicas;
    }

    public void setDeployment_kubernetes_io_max_replicas(String deployment_kubernetes_io_max_replicas) {
        this.deployment_kubernetes_io_max_replicas = deployment_kubernetes_io_max_replicas;
    }

    public String getDeployment_kubernetes_io_revision() {
        return deployment_kubernetes_io_revision;
    }

    public void setDeployment_kubernetes_io_revision(String deployment_kubernetes_io_revision) {
        this.deployment_kubernetes_io_revision = deployment_kubernetes_io_revision;
    }
}
