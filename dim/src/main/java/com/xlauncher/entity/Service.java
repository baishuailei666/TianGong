package com.xlauncher.entity;

/**
 * 服务配置实体类
 * @author YangDengcheng
 * @date 2018/1/22 9:45
 */
public class Service {
    private Integer serId;
    private String serName;
    private String serIp;
    private String serPort;
    private String serNamespace;
    private String serAddress;

    public Integer getSerId() {
        return serId;
    }

    public void setSerId(Integer serId) {
        this.serId = serId;
    }

    public String getSerName() {
        return serName;
    }

    public void setSerName(String serName) {
        this.serName = serName;
    }

    public String getSerIp() {
        return serIp;
    }

    public void setSerIp(String serIp) {
        this.serIp = serIp;
    }

    public String getSerPort() {
        return serPort;
    }

    public void setSerPort(String serPort) {
        this.serPort = serPort;
    }

    public String getSerNamespace() {
        return serNamespace;
    }

    public void setSerNamespace(String serNamespace) {
        this.serNamespace = serNamespace;
    }

    public String getSerAddress() {
        return serAddress;
    }

    public void setSerAddress(String serAddress) {
        this.serAddress = serAddress;
    }

    public Service() {
    }

    public Service(Integer serId, String serName, String serIp, String serPort, String serNamespace, String serAddress) {
        this.serId = serId;
        this.serName = serName;
        this.serIp = serIp;
        this.serPort = serPort;
        this.serNamespace = serNamespace;
        this.serAddress = serAddress;
    }

    @Override
    public String toString() {
        return "Service{" +
                "serId=" + serId +
                ", serName='" + serName + '\'' +
                ", serIp='" + serIp + '\'' +
                ", serPort='" + serPort + '\'' +
                ", serNamespace='" + serNamespace + '\'' +
                ", serAddress='" + serAddress + '\'' +
                '}';
    }
}
