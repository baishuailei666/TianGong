package com.xlauncher.entity.deployment.spec;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Kubernetes Containers实体类
 * @author YangDengcheng
 * @date 2018/1/25 10:20
 */
@Component
public class Containers {
    private String imagePullPolicy = "IfNotPresent";
    private String terminationMessagePath = "/dev/termination-log";
    private String terminationMessagePolicy = "File";
    private String image;
    private String name;
    private Resources resources;
    private SecurityContext securityContext;
    private List<VolumeMounts> volumeMounts;
    private List<Env> envs;
    private List<Ports> ports;

    public String getImagePullPolicy() {
        return imagePullPolicy;
    }

    public void setImagePullPolicy(String imagePullPolicy) {
        this.imagePullPolicy = imagePullPolicy;
    }

    public String getTerminationMessagePath() {
        return terminationMessagePath;
    }

    public void setTerminationMessagePath(String terminationMessagePath) {
        this.terminationMessagePath = terminationMessagePath;
    }

    public String getTerminationMessagePolicy() {
        return terminationMessagePolicy;
    }

    public void setTerminationMessagePolicy(String terminationMessagePolicy) {
        this.terminationMessagePolicy = terminationMessagePolicy;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Resources getResources() {
        return resources;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }

    public SecurityContext getSecurityContext() {
        return securityContext;
    }

    public void setSecurityContext(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    public List<VolumeMounts> getVolumeMounts() {
        return volumeMounts;
    }

    public void setVolumeMounts(List<VolumeMounts> volumeMounts) {
        this.volumeMounts = volumeMounts;
    }

    public List<Env> getEnvs() {
        return envs;
    }

    public void setEnvs(List<Env> envs) {
        this.envs = envs;
    }

    public List<Ports> getPorts() {
        return ports;
    }

    public void setPorts(List<Ports> ports) {
        this.ports = ports;
    }
}
