package com.xlauncher.entity;
/**
 * 虚拟设备实体类
 * @date 2018-05-09
 * @author 白帅雷
 */
public class VirtualDevice {
    /**主键id*/
    private Integer id;
    /**虚拟设备编号*/
    private String virtualDeviceId;
    /**虚拟设备名称*/
    private String virtualDeviceName;
    /**虚拟设备ip地址 */
    private String virtualDeviceIp;
    /**虚拟设备port端口*/
    private String virtualDevicePort;
    /**虚拟设备登录用户名*/
    private String virtualDeviceUserName;
    /**虚拟设备登录密码*/
    private String virtualDeviceUserPassword;
    /**虚拟设备类型*/
    private String virtualDeviceType;
    /**虚拟设备型号*/
    private String virtualDeviceModel;
    /**虚拟设备厂商*/
    private String virtualDeviceVendor;
    /**虚拟设备运行故障产生时间*/
    private String virtualDeviceFaultTime;
    /**虚拟设备故障编号*/
    private Integer virtualDeviceFaultCode;
    /**虚拟设备故障信息*/
    private String virtualDeviceFault;
    /**虚拟设备支持的通道总数*/
    private Integer virtualDeviceChannelCount;
    /**虚拟设备使用状态（在用，停用，删除）*/
    private String virtualDeviceStatus;
    /**虚拟设备所属组织编号*/
    private Integer virtualDeviceOrgId;
    /**虚拟设备所属行政区划编号*/
    private Integer virtualDeviceDivisionId;
    /**虚拟设备添加时间*/
    private String virtualDeviceCreateTime;
    /**虚拟设备更新时间*/
    private String virtualDeviceUpdateTime;

    /**虚拟设备所属组织名称*/
    private String orgName;
    /**虚拟设备所属行政区划名称*/
    private String adminDivisionName;

    public VirtualDevice() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVirtualDeviceId() {
        return virtualDeviceId;
    }

    public void setVirtualDeviceId(String virtualDeviceId) {
        this.virtualDeviceId = virtualDeviceId;
    }

    public String getVirtualDeviceName() {
        return virtualDeviceName;
    }

    public void setVirtualDeviceName(String virtualDeviceName) {
        this.virtualDeviceName = virtualDeviceName;
    }

    public String getVirtualDeviceIp() {
        return virtualDeviceIp;
    }

    public void setVirtualDeviceIp(String virtualDeviceIp) {
        this.virtualDeviceIp = virtualDeviceIp;
    }

    public String getVirtualDevicePort() {
        return virtualDevicePort;
    }

    public void setVirtualDevicePort(String virtualDevicePort) {
        this.virtualDevicePort = virtualDevicePort;
    }

    public String getVirtualDeviceUserName() {
        return virtualDeviceUserName;
    }

    public void setVirtualDeviceUserName(String virtualDeviceUserName) {
        this.virtualDeviceUserName = virtualDeviceUserName;
    }

    public String getVirtualDeviceUserPassword() {
        return virtualDeviceUserPassword;
    }

    public void setVirtualDeviceUserPassword(String virtualDeviceUserPassword) {
        this.virtualDeviceUserPassword = virtualDeviceUserPassword;
    }

    public String getVirtualDeviceType() {
        return virtualDeviceType;
    }

    public void setVirtualDeviceType(String virtualDeviceType) {
        this.virtualDeviceType = virtualDeviceType;
    }

    public String getVirtualDeviceModel() {
        return virtualDeviceModel;
    }

    public void setVirtualDeviceModel(String virtualDeviceModel) {
        this.virtualDeviceModel = virtualDeviceModel;
    }

    public Integer getVirtualDeviceFaultCode() {
        return virtualDeviceFaultCode;
    }

    public void setVirtualDeviceFaultCode(Integer virtualDeviceFaultCode) {
        this.virtualDeviceFaultCode = virtualDeviceFaultCode;
    }

    public String getVirtualDeviceFault() {
        return virtualDeviceFault;
    }

    public void setVirtualDeviceFault(String virtualDeviceFault) {
        this.virtualDeviceFault = virtualDeviceFault;
    }

    public Integer getVirtualDeviceChannelCount() {
        return virtualDeviceChannelCount;
    }

    public void setVirtualDeviceChannelCount(Integer virtualDeviceChannelCount) {
        this.virtualDeviceChannelCount = virtualDeviceChannelCount;
    }

    public Integer getVirtualDeviceOrgId() {
        return virtualDeviceOrgId;
    }

    public void setVirtualDeviceOrgId(Integer virtualDeviceOrgId) {
        this.virtualDeviceOrgId = virtualDeviceOrgId;
    }

    public Integer getVirtualDeviceDivisionId() {
        return virtualDeviceDivisionId;
    }

    public void setVirtualDeviceDivisionId(Integer virtualDeviceDivisionId) {
        this.virtualDeviceDivisionId = virtualDeviceDivisionId;
    }

    public String getVirtualDeviceCreateTime() {
        return virtualDeviceCreateTime;
    }

    public void setVirtualDeviceCreateTime(String virtualDeviceCreateTime) {
        this.virtualDeviceCreateTime = virtualDeviceCreateTime;
    }

    public String getVirtualDeviceUpdateTime() {
        return virtualDeviceUpdateTime;
    }

    public void setVirtualDeviceUpdateTime(String virtualDeviceUpdateTime) {
        this.virtualDeviceUpdateTime = virtualDeviceUpdateTime;
    }

    public String getVirtualDeviceVendor() {
        return virtualDeviceVendor;
    }

    public void setVirtualDeviceVendor(String virtualDeviceVendor) {
        this.virtualDeviceVendor = virtualDeviceVendor;
    }

    public String getVirtualDeviceFaultTime() {
        return virtualDeviceFaultTime;
    }

    public void setVirtualDeviceFaultTime(String virtualDeviceFaultTime) {
        this.virtualDeviceFaultTime = virtualDeviceFaultTime;
    }

    public String getVirtualDeviceStatus() {
        return virtualDeviceStatus;
    }

    public void setVirtualDeviceStatus(String virtualDeviceStatus) {
        this.virtualDeviceStatus = virtualDeviceStatus;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getAdminDivisionName() {
        return adminDivisionName;
    }

    public void setAdminDivisionName(String adminDivisionName) {
        this.adminDivisionName = adminDivisionName;
    }

    @Override
    public String toString() {
        return "VirtualDevice{" +
                "id=" + id +
                ", virtualDeviceId='" + virtualDeviceId + '\'' +
                ", virtualDeviceName='" + virtualDeviceName + '\'' +
                ", virtualDeviceIp='" + virtualDeviceIp + '\'' +
                ", virtualDevicePort='" + virtualDevicePort + '\'' +
                ", virtualDeviceUserName='" + virtualDeviceUserName + '\'' +
                ", virtualDeviceUserPassword='" + virtualDeviceUserPassword + '\'' +
                ", virtualDeviceType='" + virtualDeviceType + '\'' +
                ", virtualDeviceModel='" + virtualDeviceModel + '\'' +
                ", virtualDeviceVendor='" + virtualDeviceVendor + '\'' +
                ", virtualDeviceFaultTime='" + virtualDeviceFaultTime + '\'' +
                ", virtualDeviceFaultCode=" + virtualDeviceFaultCode +
                ", virtualDeviceFault='" + virtualDeviceFault + '\'' +
                ", virtualDeviceChannelCount=" + virtualDeviceChannelCount +
                ", virtualDeviceStatus='" + virtualDeviceStatus + '\'' +
                ", virtualDeviceOrgId=" + virtualDeviceOrgId +
                ", virtualDeviceDivisionId=" + virtualDeviceDivisionId +
                ", virtualDeviceCreateTime='" + virtualDeviceCreateTime + '\'' +
                ", virtualDeviceUpdateTime='" + virtualDeviceUpdateTime + '\'' +
                ", orgName='" + orgName + '\'' +
                ", adminDivisionName='" + adminDivisionName + '\'' +
                '}';
    }
}
