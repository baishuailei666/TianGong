package com.xlauncher.entity.deployment.spec;

import org.springframework.stereotype.Component;

/**
 * Kubernetes VolumeMount实体类
 * @author YangDengcheng
 * @date 2018/1/25 10:25
 */
@Component
public class VolumeMounts {
    private String mountPath;
    private String name;

    public String getMountPath() {
        return mountPath;
    }

    public void setMountPath(String mountPath) {
        this.mountPath = mountPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
