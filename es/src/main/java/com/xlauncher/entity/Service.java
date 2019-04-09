package com.xlauncher.entity;

/**
 * 功能：服务实体类
 * 作者：张霄龙
 * 创建时间：2018-02-03
 */

public class Service {
    //服务编号
    private int serId;
    //服务名称（cms,dim,ics,es)
    private String serName;
    //服务IP地址
    private String serIp;
    //服务端口号
    private String serPort;

    public int getSerId() {
        return serId;
    }

    public String getSerName() {
        return serName;
    }

    public String getSerIp() {
        return serIp;
    }

    public String getSerPort() {
        return serPort;
    }

    public void setSerId(int serId) {
        this.serId = serId;
    }

    public void setSerName(String serName) {
        this.serName = serName;
    }

    public void setSerIp(String serIp) {
        this.serIp = serIp;
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
