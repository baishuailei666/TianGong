package com.xlauncher.entity;

/**
 * 组件告警entity
 * @author 白帅雷
 * @since 2018-05-16
 */
public class AlertLog {

    /** 告警编号*/
    private int id;
    /** 告警级别*/
    private String alertPriority;
    /** 告警时间*/
    private String alertTime;
    /** 告警发生毫秒数*/
    private int alertTimeSpan;
    /** 告警发生的线程*/
    private String alertThread;
    /** 告警发生的代码行数*/
    private String alertLineNum;
    /** 告警发生的文件名*/
    private String alertFileName;
    /** 告警发生的类名*/
    private String alertClassName;
    /** 告警发生的方法名*/
    private String alertMethodName;
    /** 告警发生的信息*/
    private String alertMessage;
    /** 告警发生的异常类型*/
    private String alertType;

    public AlertLog() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlertPriority() {
        return alertPriority;
    }

    public void setAlertPriority(String alertPriority) {
        this.alertPriority = alertPriority;
    }

    public String getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(String alertTime) {
        this.alertTime = alertTime;
    }

    public int getAlertTimeSpan() {
        return alertTimeSpan;
    }

    public void setAlertTimeSpan(int alertTimeSpan) {
        this.alertTimeSpan = alertTimeSpan;
    }

    public String getAlertThread() {
        return alertThread;
    }

    public void setAlertThread(String alertThread) {
        this.alertThread = alertThread;
    }

    public String getAlertLineNum() {
        return alertLineNum;
    }

    public void setAlertLineNum(String alertLineNum) {
        this.alertLineNum = alertLineNum;
    }

    public String getAlertFileName() {
        return alertFileName;
    }

    public void setAlertFileName(String alertFileName) {
        this.alertFileName = alertFileName;
    }

    public String getAlertClassName() {
        return alertClassName;
    }

    public void setAlertClassName(String alertClassName) {
        this.alertClassName = alertClassName;
    }

    public String getAlertMethodName() {
        return alertMethodName;
    }

    public void setAlertMethodName(String alertMethodName) {
        this.alertMethodName = alertMethodName;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    @Override
    public String toString() {
        return "AlertLog{" +
                "id=" + id +
                ", alertPriority='" + alertPriority + '\'' +
                ", alertTime='" + alertTime + '\'' +
                ", alertTimeSpan=" + alertTimeSpan +
                ", alertThread='" + alertThread + '\'' +
                ", alertLineNum='" + alertLineNum + '\'' +
                ", alertFileName='" + alertFileName + '\'' +
                ", alertClassName='" + alertClassName + '\'' +
                ", alertMethodName='" + alertMethodName + '\'' +
                ", alertMessage='" + alertMessage + '\'' +
                ", alertType='" + alertType + '\'' +
                '}';
    }
}
