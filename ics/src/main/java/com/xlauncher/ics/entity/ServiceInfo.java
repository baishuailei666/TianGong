package com.xlauncher.ics.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author :baisl
 * @Email :baishuailei@xlauncher.io
 * @Date :2018/12/19 0019
 * @Desc :组件服务参数对象
 **/
@Configuration
@PropertySource("classpath:service.properties")
public class ServiceInfo {

    /**
     * es
     */
    @Value("${es.ip}")
    private String esIp;
    @Value("${es.port}")
    private String esPort;
    @Value("${es.name}")
    private String esName;
    @Value("${es.detect_thresh}")
    private float detectThresh;
    @Value("${es.class_thresh}")
    private float classThresh;

    /**
     * ftp
     */
    @Value("${ftp.ip}")
    private String ftpIp;
    @Value("${ftp.port}")
    private String ftpPort;
    @Value("${ftp.userName}")
    private String ftpUserName;
    @Value("${ftp.password}")
    private String ftpPassword;
    @Value("${ftp.store_path}")
    private String ftpStorePath;

    public String getEsIp() {
        return esIp;
    }

    public void setEsIp(String esIp) {
        this.esIp = esIp;
    }

    public String getEsPort() {
        return esPort;
    }

    public void setEsPort(String esPort) {
        this.esPort = esPort;
    }

    public String getEsName() {
        return esName;
    }

    public void setEsName(String esName) {
        this.esName = esName;
    }

    public float getDetectThresh() {
        return detectThresh;
    }

    public void setDetectThresh(float detectThresh) {
        this.detectThresh = detectThresh;
    }

    public float getClassThresh() {
        return classThresh;
    }

    public void setClassThresh(float classThresh) {
        this.classThresh = classThresh;
    }

    public String getFtpIp() {
        return ftpIp;
    }

    public void setFtpIp(String ftpIp) {
        this.ftpIp = ftpIp;
    }

    public String getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(String ftpPort) {
        this.ftpPort = ftpPort;
    }

    public String getFtpUserName() {
        return ftpUserName;
    }

    public void setFtpUserName(String ftpUserName) {
        this.ftpUserName = ftpUserName;
    }

    public String getFtpPassword() {
        return ftpPassword;
    }

    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }

    public String getFtpStorePath() {
        return ftpStorePath;
    }

    public void setFtpStorePath(String ftpStorePath) {
        this.ftpStorePath = ftpStorePath;
    }

    @Override
    public String toString() {
        return "Service{" +
                "ES [" +
                "esIp='" + esIp + '\'' +
                ", esPort=" + esPort +
                ", esName='" + esName + '\'' +
                ", detectThresh=" + detectThresh +
                ", classThresh=" + classThresh +
                "], FTP [" +
                ", ftpIp='" + ftpIp + '\'' +
                ", ftpPort=" + ftpPort +
                ", ftpUserName='" + ftpUserName + '\'' +
                ", ftpPassword='" + ftpPassword + '\'' +
                ", ftpStorePath='" + ftpStorePath + '\'' +
                "]" +
                '}';
    }
}
