package com.xlauncher.entity;

import java.math.BigInteger;

/**
 * 设备实体类（IPCamera和DVR）
 * @author YangDengcheng
 * @date 2018/2/27 14:10
 */
public class Device {
    /**主键id*/
    private Integer id;
    /**设备编号*/
    private String deviceId;
    /**设备名称*/
    private String deviceName;
    /**设备ip地址 */
    private String deviceIp;
    /**设备port端口*/
    private String devicePort;
    /**设备登录用户名*/
    private String deviceUserName;
    /**设备登录密码*/
    private String deviceUserPassword;
    /**设备类型*/
    private String deviceType;
    /**设备型号*/
    private String deviceModel;
    /**设备厂商*/
    private String deviceVendor;
    /**设备运行故障产生时间*/
    private String deviceFaultTime;
    /**设备故障编号*/
    private Integer deviceFaultCode;
    /**设备故障信息*/
    private String deviceFault;
    /**设备支持的通道总数*/
    private Integer deviceChannelCount;
    /**设备使用状态（在用，停用，删除）*/
    private String deviceStatus;
    /**设备所属组织编号*/
    private String deviceOrgId;
    /**设备所属行政区划编号*/
    private BigInteger deviceDivisionId;
    /**设备添加时间*/
    private String deviceCreateTime;
    /**设备更新时间*/
    private String deviceUpdateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    public String getDevicePort() {
        return devicePort;
    }

    public void setDevicePort(String devicePort) {
        this.devicePort = devicePort;
    }

    public String getDeviceUserName() {
        return deviceUserName;
    }

    public void setDeviceUserName(String deviceUserName) {
        this.deviceUserName = deviceUserName;
    }

    public String getDeviceUserPassword() {
        return deviceUserPassword;
    }

    public void setDeviceUserPassword(String deviceUserPassword) {
        this.deviceUserPassword = deviceUserPassword;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceVendor() {
        return deviceVendor;
    }

    public void setDeviceVendor(String deviceVendor) {
        this.deviceVendor = deviceVendor;
    }

    public String getDeviceFaultTime() {
        return deviceFaultTime;
    }

    public void setDeviceFaultTime(String deviceFaultTime) {
        this.deviceFaultTime = deviceFaultTime;
    }

    public Integer getDeviceFaultCode() {
        return deviceFaultCode;
    }

    public void setDeviceFaultCode(Integer deviceFaultCode) {
        this.deviceFaultCode = deviceFaultCode;
    }

    public String getDeviceFault() {
        return deviceFault;
    }

    public void setDeviceFault(String deviceFault) {
        this.deviceFault = deviceFault;
    }

    public Integer getDeviceChannelCount() {
        return deviceChannelCount;
    }

    public void setDeviceChannelCount(Integer deviceChannelCount) {
        this.deviceChannelCount = deviceChannelCount;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getDeviceOrgId() {
        return deviceOrgId;
    }

    public void setDeviceOrgId(String deviceOrgId) {
        this.deviceOrgId = deviceOrgId;
    }

    public BigInteger getDeviceDivisionId() {
        return deviceDivisionId;
    }

    public void setDeviceDivisionId(BigInteger deviceDivisionId) {
        this.deviceDivisionId = deviceDivisionId;
    }

    public String getDeviceCreateTime() {
        return deviceCreateTime;
    }

    public void setDeviceCreateTime(String deviceCreateTime) {
        this.deviceCreateTime = deviceCreateTime;
    }

    public String getDeviceUpdateTime() {
        return deviceUpdateTime;
    }

    public void setDeviceUpdateTime(String deviceUpdateTime) {
        this.deviceUpdateTime = deviceUpdateTime;
    }

    public Device() {

    }

    public Device(String deviceName, String deviceIp, String devicePort, String deviceUserName, String deviceUserPassword, String deviceType, Integer deviceChannelCount, String deviceStatus) {
        this.deviceName = deviceName;
        this.deviceIp = deviceIp;
        this.devicePort = devicePort;
        this.deviceUserName = deviceUserName;
        this.deviceUserPassword = deviceUserPassword;
        this.deviceType = deviceType;
        this.deviceChannelCount = deviceChannelCount;
        this.deviceStatus = deviceStatus;
    }

    public Device(String deviceId, String deviceName, String deviceIp, String devicePort, String deviceUserName, String deviceUserPassword, String deviceType, Integer deviceChannelCount, String deviceStatus) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.deviceIp = deviceIp;
        this.devicePort = devicePort;
        this.deviceUserName = deviceUserName;
        this.deviceUserPassword = deviceUserPassword;
        this.deviceType = deviceType;
        this.deviceChannelCount = deviceChannelCount;
        this.deviceStatus = deviceStatus;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", deviceId='" + deviceId + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", deviceIp='" + deviceIp + '\'' +
                ", devicePort='" + devicePort + '\'' +
                ", deviceUserName='" + deviceUserName + '\'' +
                ", deviceUserPassword='" + deviceUserPassword + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", deviceModel='" + deviceModel + '\'' +
                ", deviceVendor='" + deviceVendor + '\'' +
                ", deviceFaultTime='" + deviceFaultTime + '\'' +
                ", deviceFaultCode=" + deviceFaultCode +
                ", deviceFault='" + deviceFault + '\'' +
                ", deviceChannelCount=" + deviceChannelCount +
                ", deviceStatus='" + deviceStatus + '\'' +
                ", deviceOrgId=" + deviceOrgId +
                ", deviceDivisionId=" + deviceDivisionId +
                ", deviceCreateTime='" + deviceCreateTime + '\'' +
                ", deviceUpdateTime='" + deviceUpdateTime + '\'' +
                '}';
    }
}
