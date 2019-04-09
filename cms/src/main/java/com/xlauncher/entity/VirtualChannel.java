package com.xlauncher.entity;
/**
 * 虚拟通道实体类
 * @date 2018-05-09
 * @author 白帅雷
 */
public class VirtualChannel {
    /** 主键id*/
    private Integer id;
    /** 虚拟通道编号*/
    private String virtualChannelId;
    /** 虚拟通道名称*/
    private String virtualChannelName;
    /** 虚拟通道资源编号*/
    private String virtualChannelSourceId;
    /** 虚拟设备取流使用的通道序号*/
    private Integer virtualChannelNumber;
    /** 网格编号*/
    private String virtualChannelGridId;
    /** 虚拟通道行政地理位置信息*/
    private String virtualChannelLocation;
    /** GIS信息经度*/
    private String virtualChannelLongitude;
    /** GIS信息维度*/
    private String virtualChannelLatitude;
    /** 虚拟通道负责人*/
    private String virtualChannelHandler;
    /** 虚拟通道负责人联系电话*/
    private String virtualChannelHandlerPhone;
    /** 虚拟通道连接Pod状态*/
    private Integer virtualChannelPodStatus;
    /** 虚拟通道使用状态*/
    private String virtualChannelStatus;
    /** 虚拟通道添加时间*/
    private String virtualChannelCreateTime;
    /** 虚拟通道更新时间*/
    private String virtualChannelUpdateTime;
    /** 虚拟通道故障产生时间*/
    private String virtualChannelFaultTime;
    /** 虚拟通道故障编码*/
    private Integer virtualChannelFaultCode;
    /** 虚拟通道故障信息*/
    private String virtualChannelFault;
    /** 虚拟通道型号*/
    private String virtualChannelModel;
    /** 虚拟通道厂商*/
    private String virtualChannelVendor;

    public VirtualChannel() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVirtualChannelId() {
        return virtualChannelId;
    }

    public void setVirtualChannelId(String virtualChannelId) {
        this.virtualChannelId = virtualChannelId;
    }

    public String getVirtualChannelName() {
        return virtualChannelName;
    }

    public void setVirtualChannelName(String virtualChannelName) {
        this.virtualChannelName = virtualChannelName;
    }

    public String getVirtualChannelSourceId() {
        return virtualChannelSourceId;
    }

    public void setVirtualChannelSourceId(String virtualChannelSourceId) {
        this.virtualChannelSourceId = virtualChannelSourceId;
    }

    public Integer getVirtualChannelNumber() {
        return virtualChannelNumber;
    }

    public void setVirtualChannelNumber(Integer virtualChannelNumber) {
        this.virtualChannelNumber = virtualChannelNumber;
    }

    public String getVirtualChannelGridId() {
        return virtualChannelGridId;
    }

    public void setVirtualChannelGridId(String virtualChannelGridId) {
        this.virtualChannelGridId = virtualChannelGridId;
    }

    public String getVirtualChannelLocation() {
        return virtualChannelLocation;
    }

    public void setVirtualChannelLocation(String virtualChannelLocation) {
        this.virtualChannelLocation = virtualChannelLocation;
    }

    public String getVirtualChannelLongitude() {
        return virtualChannelLongitude;
    }

    public void setVirtualChannelLongitude(String virtualChannelLongitude) {
        this.virtualChannelLongitude = virtualChannelLongitude;
    }

    public String getVirtualChannelLatitude() {
        return virtualChannelLatitude;
    }

    public void setVirtualChannelLatitude(String virtualChannelLatitude) {
        this.virtualChannelLatitude = virtualChannelLatitude;
    }

    public String getVirtualChannelHandler() {
        return virtualChannelHandler;
    }

    public void setVirtualChannelHandler(String virtualChannelHandler) {
        this.virtualChannelHandler = virtualChannelHandler;
    }

    public String getVirtualChannelHandlerPhone() {
        return virtualChannelHandlerPhone;
    }

    public void setVirtualChannelHandlerPhone(String virtualChannelHandlerPhone) {
        this.virtualChannelHandlerPhone = virtualChannelHandlerPhone;
    }

    public Integer getVirtualChannelPodStatus() {
        return virtualChannelPodStatus;
    }

    public void setVirtualChannelPodStatus(Integer virtualChannelPodStatus) {
        this.virtualChannelPodStatus = virtualChannelPodStatus;
    }

    public String getVirtualChannelStatus() {
        return virtualChannelStatus;
    }

    public void setVirtualChannelStatus(String virtualChannelStatus) {
        this.virtualChannelStatus = virtualChannelStatus;
    }

    public String getVirtualChannelCreateTime() {
        return virtualChannelCreateTime;
    }

    public void setVirtualChannelCreateTime(String virtualChannelCreateTime) {
        this.virtualChannelCreateTime = virtualChannelCreateTime;
    }

    public String getVirtualChannelUpdateTime() {
        return virtualChannelUpdateTime;
    }

    public void setVirtualChannelUpdateTime(String virtualChannelUpdateTime) {
        this.virtualChannelUpdateTime = virtualChannelUpdateTime;
    }

    public Integer getVirtualChannelFaultCode() {
        return virtualChannelFaultCode;
    }

    public void setVirtualChannelFaultCode(Integer virtualChannelFaultCode) {
        this.virtualChannelFaultCode = virtualChannelFaultCode;
    }

    public String getVirtualChannelFault() {
        return virtualChannelFault;
    }

    public void setVirtualChannelFault(String virtualChannelFault) {
        this.virtualChannelFault = virtualChannelFault;
    }

    public String getVirtualChannelFaultTime() {
        return virtualChannelFaultTime;
    }

    public void setVirtualChannelFaultTime(String virtualChannelFaultTime) {
        this.virtualChannelFaultTime = virtualChannelFaultTime;
    }

    public String getVirtualChannelModel() {
        return virtualChannelModel;
    }

    public void setVirtualChannelModel(String virtualChannelModel) {
        this.virtualChannelModel = virtualChannelModel;
    }

    public String getVirtualChannelVendor() {
        return virtualChannelVendor;
    }

    public void setVirtualChannelVendor(String virtualChannelVendor) {
        this.virtualChannelVendor = virtualChannelVendor;
    }

    @Override
    public String toString() {
        return "VirtualChannel{" +
                "id=" + id +
                ", virtualChannelId='" + virtualChannelId + '\'' +
                ", virtualChannelName='" + virtualChannelName + '\'' +
                ", virtualChannelSourceId='" + virtualChannelSourceId + '\'' +
                ", virtualChannelNumber=" + virtualChannelNumber +
                ", virtualChannelGridId='" + virtualChannelGridId + '\'' +
                ", virtualChannelLocation='" + virtualChannelLocation + '\'' +
                ", virtualChannelLongitude='" + virtualChannelLongitude + '\'' +
                ", virtualChannelLatitude='" + virtualChannelLatitude + '\'' +
                ", virtualChannelHandler='" + virtualChannelHandler + '\'' +
                ", virtualChannelHandlerPhone='" + virtualChannelHandlerPhone + '\'' +
                ", virtualChannelPodStatus=" + virtualChannelPodStatus +
                ", virtualChannelStatus='" + virtualChannelStatus + '\'' +
                ", virtualChannelCreateTime='" + virtualChannelCreateTime + '\'' +
                ", virtualChannelUpdateTime='" + virtualChannelUpdateTime + '\'' +
                ", virtualChannelFaultTime='" + virtualChannelFaultTime + '\'' +
                ", virtualChannelFaultCode=" + virtualChannelFaultCode +
                ", virtualChannelFault='" + virtualChannelFault + '\'' +
                ", virtualChannelModel='" + virtualChannelModel + '\'' +
                ", virtualChannelVendor='" + virtualChannelVendor + '\'' +
                '}';
    }
}
