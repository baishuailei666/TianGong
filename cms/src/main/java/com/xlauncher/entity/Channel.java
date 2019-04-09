package com.xlauncher.entity;
/**
 * 真实通道实体类
 * @date 2018-05-08
 * @author 白帅雷
 */
public class Channel {
    /** 主键id*/
    private Integer id;
    /** 通道编号*/
    private String channelId;
    /** 通道名称*/
    private String channelName;
    /** 通道资源编号*/
    private String channelSourceId;
    /** 设备取流使用的通道序号*/
    private Integer channelNumber;
    /** 网格编号*/
    private String channelGridId;
    /** 通道行政地理位置信息*/
    private String channelLocation;
    /** GIS信息经度*/
    private String channelLongitude;
    /** GIS信息维度*/
    private String channelLatitude;
    /** 通道负责人*/
    private String channelHandler;
    /** 通道负责人联系电话*/
    private String channelHandlerPhone;
    /** 通道连接Pod状态*/
    private Integer channelPodStatus;
    /** 通道使用状态*/
    private String channelStatus;
    /** 通道添加时间*/
    private String channelCreateTime;
    /** 通道更新时间*/
    private String channelUpdateTime;
    /** 通道故障产生时间*/
    private String channelFaultTime;
    /** 通道故障编码*/
    private Integer channelFaultCode;
    /** 通道故障信息*/
    private String channelFault;
    /** 通道型号*/
    private String channelModel;
    /** 通道厂商*/
    private String channelVendor;
    /**设备名称*/
    private String deviceName;

    public Channel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelSourceId() {
        return channelSourceId;
    }

    public void setChannelSourceId(String channelSourceId) {
        this.channelSourceId = channelSourceId;
    }

    public Integer getChannelNumber() {
        return channelNumber;
    }

    public void setChannelNumber(Integer channelNumber) {
        this.channelNumber = channelNumber;
    }

    public String getChannelGridId() {
        return channelGridId;
    }

    public void setChannelGridId(String channelGridId) {
        this.channelGridId = channelGridId;
    }

    public String getChannelLocation() {
        return channelLocation;
    }

    public void setChannelLocation(String channelLocation) {
        this.channelLocation = channelLocation;
    }

    public String getChannelLongitude() {
        return channelLongitude;
    }

    public void setChannelLongitude(String channelLongitude) {
        this.channelLongitude = channelLongitude;
    }

    public String getChannelLatitude() {
        return channelLatitude;
    }

    public void setChannelLatitude(String channelLatitude) {
        this.channelLatitude = channelLatitude;
    }

    public String getChannelHandler() {
        return channelHandler;
    }

    public void setChannelHandler(String channelHandler) {
        this.channelHandler = channelHandler;
    }

    public String getChannelHandlerPhone() {
        return channelHandlerPhone;
    }

    public void setChannelHandlerPhone(String channelHandlerPhone) {
        this.channelHandlerPhone = channelHandlerPhone;
    }

    public Integer getChannelPodStatus() {
        return channelPodStatus;
    }

    public void setChannelPodStatus(Integer channelPodStatus) {
        this.channelPodStatus = channelPodStatus;
    }

    public String getChannelStatus() {
        return channelStatus;
    }

    public void setChannelStatus(String channelStatus) {
        this.channelStatus = channelStatus;
    }

    public String getChannelCreateTime() {
        return channelCreateTime;
    }

    public void setChannelCreateTime(String channelCreateTime) {
        this.channelCreateTime = channelCreateTime;
    }

    public String getChannelUpdateTime() {
        return channelUpdateTime;
    }

    public void setChannelUpdateTime(String channelUpdateTime) {
        this.channelUpdateTime = channelUpdateTime;
    }

    public String getChannelFaultTime() {
        return channelFaultTime;
    }

    public void setChannelFaultTime(String channelFaultTime) {
        this.channelFaultTime = channelFaultTime;
    }

    public Integer getChannelFaultCode() {
        return channelFaultCode;
    }

    public void setChannelFaultCode(Integer channelFaultCode) {
        this.channelFaultCode = channelFaultCode;
    }

    public String getChannelFault() {
        return channelFault;
    }

    public void setChannelFault(String channelFault) {
        this.channelFault = channelFault;
    }

    public String getChannelModel() {
        return channelModel;
    }

    public void setChannelModel(String channelModel) {
        this.channelModel = channelModel;
    }

    public String getChannelVendor() {
        return channelVendor;
    }

    public void setChannelVendor(String channelVendor) {
        this.channelVendor = channelVendor;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "id=" + id +
                ", channelId='" + channelId + '\'' +
                ", channelName='" + channelName + '\'' +
                ", channelSourceId='" + channelSourceId + '\'' +
                ", channelNumber=" + channelNumber +
                ", channelGridId='" + channelGridId + '\'' +
                ", channelLocation='" + channelLocation + '\'' +
                ", channelLongitude='" + channelLongitude + '\'' +
                ", channelLatitude='" + channelLatitude + '\'' +
                ", channelHandler='" + channelHandler + '\'' +
                ", channelHandlerPhone='" + channelHandlerPhone + '\'' +
                ", channelPodStatus=" + channelPodStatus +
                ", channelStatus='" + channelStatus + '\'' +
                ", channelCreateTime='" + channelCreateTime + '\'' +
                ", channelUpdateTime='" + channelUpdateTime + '\'' +
                ", channelFaultTime='" + channelFaultTime + '\'' +
                ", channelFaultCode=" + channelFaultCode +
                ", channelFault='" + channelFault + '\'' +
                ", channelModel='" + channelModel + '\'' +
                ", channelVendor='" + channelVendor + '\'' +
                ", deviceName='" + deviceName + '\'' +
                '}';
    }
}
