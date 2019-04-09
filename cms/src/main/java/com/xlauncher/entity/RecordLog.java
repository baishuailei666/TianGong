package com.xlauncher.entity;

/**
 * 日志记录配置实体类
 * @date 2018-05-30
 * @author 白帅雷
 */
public class RecordLog {
    /** 主键id*/
    private String recordId;
    /** 日志记录配置系统模块*/
    private String recordCategory;
    /** 日志记录配置子系统模块*/
    private String recordSystemModule;
    /** 日志记录配置子模块*/
    private String recordModule;
    /** 日志记录配置子模块记录状态*/
    private Integer recordStatus;

    public RecordLog() {
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getRecordCategory() {
        return recordCategory;
    }

    public void setRecordCategory(String recordCategory) {
        this.recordCategory = recordCategory;
    }

    public String getRecordSystemModule() {
        return recordSystemModule;
    }

    public void setRecordSystemModule(String recordSystemModule) {
        this.recordSystemModule = recordSystemModule;
    }

    public String getRecordModule() {
        return recordModule;
    }

    public void setRecordModule(String recordModule) {
        this.recordModule = recordModule;
    }

    public Integer getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }

    @Override
    public String toString() {
        return "RecordLog{" +
                "recordId='" + recordId + '\'' +
                ", recordCategory='" + recordCategory + '\'' +
                ", recordSystemModule='" + recordSystemModule + '\'' +
                ", recordModule='" + recordModule + '\'' +
                ", recordStatus='" + recordStatus + '\'' +
                '}';
    }
}
