package com.xlauncher.entity.deployment.spec;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Kubernetes Spec实体类
 * @author YangDengcheng
 * @date 2018/1/25 10:17
 */
@Component
public class SpecIn {
    private List<Containers> containers;
    private NodeSelector nodeSelector;
    private List<Volumes> volumes;

    public List<Containers> getContainers() {
        return containers;
    }

    public void setContainers(List<Containers> containers) {
        this.containers = containers;
    }

    public NodeSelector getNodeSelector() {
        return nodeSelector;
    }

    public void setNodeSelector(NodeSelector nodeSelector) {
        this.nodeSelector = nodeSelector;
    }

    public List<Volumes> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<Volumes> volumes) {
        this.volumes = volumes;
    }
}
