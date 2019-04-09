package com.xlauncher.entity;

/**
 * 功能：事件实体类
 * @author 白帅雷
 * @date 2018-02-02
 */

public class AlertEvent {

    /**
     * 实体类的属性和表中字段一致
     */
    /**
     * 告警事件编号
     */
    private int eventId;

    /**
     * 告警事件发生时间
     */
    private String eventStartTime;

    /**
     * 告警事件发生对应通道编号
     */
    private String channelId;

    /**
     * 告警事件所属类型编号
     */
    private int typeId;

    /**
     * 告警事件类型描述
     */
    private String typeDescription;

    /**
     * 告警事件资源描述（图片）
     */
    private String eventSource;

    /**
     * 告警事件推送状态（已推送、未推送）
     */
    private String eventPushStatus;

    /**
     * 告警事件推送正阳科技状态（已推送、未推送）
     */
    private String eventPushSuntecStatus;

    /**
     * 正阳科技返回的告警事件的orderId
     */
    private String orderId;

    public AlertEvent() {

    }

    public AlertEvent(int eventId , String eventStartTime , String channelId , int typeId
            , String eventSource , String eventPushStatus , String typeDescription) {
        this.eventId = eventId;
        this.eventStartTime = eventStartTime;
        this.channelId = channelId;
        this.typeId = typeId;
        this.eventSource = eventSource;
        this.typeDescription = typeDescription;
        this.eventPushStatus = eventPushStatus;

    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(String eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public String getEventSource() {
        return eventSource;
    }

    public void setEventSource(String eventSource) {
        this.eventSource = eventSource;
    }


    public String getEventPushStatus() {
        return eventPushStatus;
    }

    public void setEventPushStatus(String eventPushStatus) {
        this.eventPushStatus = eventPushStatus;
    }

    public String getEventPushSuntecStatus() {
        return eventPushSuntecStatus;
    }

    public void setEventPushSuntecStatus(String eventPushSuntecStatus) {
        this.eventPushSuntecStatus = eventPushSuntecStatus;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    @Override
    public String toString() {
        return "AlertEvent {" +
                "eventId=" + eventId +
                ", eventStartTime='" + eventStartTime + '\'' +
                ", channelId='" + channelId + '\'' +
                ", typeId=" + typeId +
                ", typeDescription='" + typeDescription + '\'' +
                ", eventSource='" + eventSource + '\'' +
                ", eventPushStatus='" + eventPushStatus + '\'' +
                ", eventPushSuntecStatus='" + eventPushSuntecStatus + '\'' +
                ", orderId='" + orderId + '\'' +
                '}';
    }

}
