package com.xlauncher.entity.deployment.spec;

/**
 * Kubernetes Requests实体类
 * @author YangDengcheng
 * @date 2018/1/25 14:26
 */
public class Requests {
    private String cpu;
    private String memory;

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }
}
