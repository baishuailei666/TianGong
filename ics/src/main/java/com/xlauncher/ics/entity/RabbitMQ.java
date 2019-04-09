package com.xlauncher.ics.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author :baisl
 * @Email :baishuailei@xlauncher.io
 * @Date :2018/12/19 0019
 * @Desc :RabbitMQ 消息队列参数实体对象
 **/
@Configuration
@PropertySource("classpath:service.properties")
public class RabbitMQ {
    /**
     * rabbitMQ
     */
    @Value("${mq.ip}")
    private String mqIp;
    @Value("${mq.port}")
    private String mqPort;
    @Value("${mq.userName}")
    private String mqUserName;
    @Value("${mq.password}")
    private String mqPassword;
    @Value("${mq.queue_img}")
    private String mqQueueImg;
    @Value("${mq.queue_channel}")
    private String mqQueueChannel;

    @Value("${detectorModelPath}")
    private String detectorModelPath;
    @Value("${predictorModelPath}")
    private String predictorModelPath;
    @Value("${classifierModelPath}")
    private String classifierModelPath;

    public String getMqIp() {
        return mqIp;
    }

    public void setMqIp(String mqIp) {
        this.mqIp = mqIp;
    }

    public String getMqPort() {
        return mqPort;
    }

    public void setMqPort(String mqPort) {
        this.mqPort = mqPort;
    }

    public String getMqUserName() {
        return mqUserName;
    }

    public void setMqUserName(String mqUserName) {
        this.mqUserName = mqUserName;
    }

    public String getMqPassword() {
        return mqPassword;
    }

    public void setMqPassword(String mqPassword) {
        this.mqPassword = mqPassword;
    }

    public String getMqQueueImg() {
        return mqQueueImg;
    }

    public void setMqQueueImg(String mqQueueImg) {
        this.mqQueueImg = mqQueueImg;
    }

    public String getMqQueueChannel() {
        return mqQueueChannel;
    }

    public void setMqQueueChannel(String mqQueueChannel) {
        this.mqQueueChannel = mqQueueChannel;
    }

    public String getDetectorModelPath() {
        return detectorModelPath;
    }

    public void setDetectorModelPath(String detectorModelPath) {
        this.detectorModelPath = detectorModelPath;
    }

    public String getPredictorModelPath() {
        return predictorModelPath;
    }

    public void setPredictorModelPath(String predictorModelPath) {
        this.predictorModelPath = predictorModelPath;
    }

    public String getClassifierModelPath() {
        return classifierModelPath;
    }

    public void setClassifierModelPath(String classifierModelPath) {
        this.classifierModelPath = classifierModelPath;
    }

    @Override
    public String toString() {
        return "RabbitMQ{" +
                "mqIp='" + mqIp + '\'' +
                ", mqPort=" + mqPort +
                ", mqUserName='" + mqUserName + '\'' +
                ", mqPassword='" + mqPassword + '\'' +
                ", mqQueueImg='" + mqQueueImg + '\'' +
                ", mqQueueChannel='" + mqQueueChannel + '\'' +
                '}';
    }
}
