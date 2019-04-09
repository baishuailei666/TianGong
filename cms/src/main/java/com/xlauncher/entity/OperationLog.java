package com.xlauncher.entity;

/**
 * 操作日志实体类
 * @date 2018-05-11
 * @author 白帅雷
 */
public class OperationLog {
    /**主键id*/
    private Integer id;
    /** 操作人*/
    private String operationPerson;
    /** 操作时间*/
    private String operationTime;
    /** 操作类型*/
    private String operationType;
    /** 操作描述*/
    private String operationDescription;
    /** 操作模块*/
    private String operationModule;
    /** 操作类别*/
    private String operationCategory;
    /** 操作子系统模块*/
    private String operationSystemModule;

    public OperationLog() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperationPerson() {
        return operationPerson;
    }

    public void setOperationPerson(String operationPerson) {
        this.operationPerson = operationPerson;
    }

    public String getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getOperationDescription() {
        return operationDescription;
    }

    public void setOperationDescription(String operationDescription) {
        this.operationDescription = operationDescription;
    }

    public String getOperationModule() {
        return operationModule;
    }

    public void setOperationModule(String operationModule) {
        this.operationModule = operationModule;
    }

    public String getOperationCategory() {
        return operationCategory;
    }

    public void setOperationCategory(String operationCategory) {
        this.operationCategory = operationCategory;
    }

    public String getOperationSystemModule() {
        return operationSystemModule;
    }

    public void setOperationSystemModule(String operationSystemModule) {
        this.operationSystemModule = operationSystemModule;
    }

    @Override
    public String toString() {
        return "OperationLog{" +
                "id=" + id +
                ", operationPerson='" + operationPerson + '\'' +
                ", operationTime='" + operationTime + '\'' +
                ", operationType='" + operationType + '\'' +
                ", operationDescription='" + operationDescription + '\'' +
                ", operationModule='" + operationModule + '\'' +
                ", operationCategory='" + operationCategory + '\'' +
                ", operationSystemModule='" + operationSystemModule + '\'' +
                '}';
    }
}
