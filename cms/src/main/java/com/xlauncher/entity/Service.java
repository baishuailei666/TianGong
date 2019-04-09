package com.xlauncher.entity;

/**
 * 同步服务DIM、ES
 * @author 白帅雷
 * @date 2018-08-16
 */
public class Service {
    private Integer serId;
    private String serName;
    private String serIp;
    private String serPort;

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

    @Override
    public String toString() {
        return "Service{" +
                "serId=" + serId +
                ", serName='" + serName + '\'' +
                ", serIp='" + serIp + '\'' +
                ", serPort='" + serPort + '\'' +
                '}';
    }
}
