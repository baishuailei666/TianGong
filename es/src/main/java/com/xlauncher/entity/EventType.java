package com.xlauncher.entity;

/**
 * 功能：事件类型实体类
 * @author 白帅雷
 * @date 2018-02-02
 */

public class EventType {
    /**
     * 事件类型编号
     */
    private int typeId;

    /**
     * 事件类型描述
     */
    private String typeDescription;

    /**
     * 某告警事件类型推送事件段开始时间，
     */
    private String typeStartTime;
    /**
     * 某告警事件类型推送事件段结束时间，
     */
    private String typeEndTime;
    /**
     * 告警事件类型是否重复推送，
     */
    private String typePushStatus;
    /**
     * 告警事件类型是否删除，
     */
    private int typeDelete;

    public EventType() {

    }

    public int getTypeId() {
        return typeId;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public String getTypeStartTime() {
        return typeStartTime;
    }

    public void setTypeStartTime(String typeStartTime) {
        this.typeStartTime = typeStartTime;
    }

    public String getTypeEndTime() {
        return typeEndTime;
    }

    public void setTypeEndTime(String typeEndTime) {
        this.typeEndTime = typeEndTime;
    }

    public String getTypePushStatus() {
        return typePushStatus;
    }

    public void setTypePushStatus(String typePushStatus) {
        this.typePushStatus = typePushStatus;
    }

    public int getTypeDelete() {
        return typeDelete;
    }

    public void setTypeDelete(int typeDelete) {
        this.typeDelete = typeDelete;
    }

    @Override
    public String toString() {
        return "EventType{" +
                "typeId=" + typeId +
                ", typeDescription='" + typeDescription + '\'' +
                ", typeStartTime='" + typeStartTime + '\'' +
                ", typeEndTime='" + typeEndTime + '\'' +
                ", typePushStatus='" + typePushStatus + '\'' +
                ", typeDelete='" + typeDelete + '\'' +
                '}';
    }
}
