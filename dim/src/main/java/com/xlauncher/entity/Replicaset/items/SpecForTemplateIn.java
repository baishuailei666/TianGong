package com.xlauncher.entity.Replicaset.items;

import com.xlauncher.entity.deployment.spec.Containers;
import com.xlauncher.entity.deployment.spec.NodeSelector;
import com.xlauncher.entity.deployment.spec.SecurityContext;

import java.util.List;

/**
 * @author YangDengcheng
 * @date 2018/1/26 17:16
 */
public class SpecForTemplateIn {
    private List<Containers> containers;
    private String restartPolicy;
    private Integer terminationGracePeriodSeconds;
    private String dnsPolicy;
    private NodeSelector nodeSelector;
    private SecurityContext securityContext;
    private String schedulerName;

    public List<Containers> getContainers() {
        return containers;
    }

    public void setContainers(List<Containers> containers) {
        this.containers = containers;
    }

    public String getRestartPolicy() {
        return restartPolicy;
    }

    public void setRestartPolicy(String restartPolicy) {
        this.restartPolicy = restartPolicy;
    }

    public Integer getTerminationGracePeriodSeconds() {
        return terminationGracePeriodSeconds;
    }

    public void setTerminationGracePeriodSeconds(Integer terminationGracePeriodSeconds) {
        this.terminationGracePeriodSeconds = terminationGracePeriodSeconds;
    }

    public String getDnsPolicy() {
        return dnsPolicy;
    }

    public void setDnsPolicy(String dnsPolicy) {
        this.dnsPolicy = dnsPolicy;
    }

    public NodeSelector getNodeSelector() {
        return nodeSelector;
    }

    public void setNodeSelector(NodeSelector nodeSelector) {
        this.nodeSelector = nodeSelector;
    }

    public SecurityContext getSecurityContext() {
        return securityContext;
    }

    public void setSecurityContext(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    public String getSchedulerName() {
        return schedulerName;
    }

    public void setSchedulerName(String schedulerName) {
        this.schedulerName = schedulerName;
    }
}
