package com.xlauncher.entity;

/**
 * 告警事件实体类
 * @author 白帅雷
 * @since 2018-05-23
 */
public class EventAlert {
    /** 事件编号 */
    private int eventId;
    /** 事件发生对应摄像头编号 */
    private String channelId;
    /** 事件发生对应摄像头名称 */
    private String channelName;
    /** 事件发生时间 */
    private String eventStartTime;
    /** 事件所属类型 */
    private String typeDescription;
    /** 事件类型情况 */
    private String typeStatus;
    /** 事件类型校准 */
    private String typeRectify;
    /** 事件资源描述 */
    private String eventSource;
    /** 摄像头的负责人 */
    private String channelHandler;
    /** 负责人电话 */
    private String channelHandlerPhone;
    /** 摄像头位置 */
    private String channelLocation;
    /** 经度 */
    private String channelLongitude;
    /** 纬度 */
    private String channelLatitude;
    /** 所属组织 */
    private String channelOrg;
    /** 网格id */
    private String channelGridId;
    /** 事件复核状态 */
    private String eventCheck;
    /** 告警事件对应图片 */
    private byte[] eventData;
    /** 事件处理状态 */
    private String eventStatus;
    /** 事件处理时间 */
    private String eventEndTime;
    /** 事件复核人 */
    private String eventReviewer;

    public EventAlert() {

    }

    public EventAlert(int eventId, String channelId, String channelName, String eventStartTime, String typeDescription, String typeStatus
            , String typeRectify, String eventSource, String channelHandler, String channelHandlerPhone
            , String channelLocation, String channelLongitude, String channelLatitude, String channelOrg
            , String channelGridId, String eventCheck, byte[] eventData, String eventStatus, String eventEndTime, String eventReviewer) {

        this.eventId = eventId;
        this.channelId = channelId;
        this.channelName = channelName;
        this.eventStartTime = eventStartTime;
        this.typeDescription = typeDescription;
        this.typeStatus = typeStatus;
        this.typeRectify = typeRectify;
        this.eventSource = eventSource;
        this.channelHandler = channelHandler;
        this.channelHandlerPhone = channelHandlerPhone;
        this.channelLocation = channelLocation;
        this.channelLongitude = channelLongitude;
        this.channelLatitude = channelLatitude;
        this.channelOrg = channelOrg;
        this.channelGridId = channelGridId;
        this.eventCheck = eventCheck;
        this.eventStatus = eventStatus;
        this.eventEndTime = eventEndTime;
        this.eventReviewer = eventReviewer;
        if (eventData != null) {
            this.eventData = eventData.clone();
        } else {
            this.eventData = null;
        }
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
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

    public String getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(String eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public String getTypeStatus() {
        return typeStatus;
    }

    public void setTypeStatus(String typeStatus) {
        this.typeStatus = typeStatus;
    }

    public String getTypeRectify() {
        return typeRectify;
    }

    public void setTypeRectify(String typeRectify) {
        this.typeRectify = typeRectify;
    }

    public String getEventSource() {
        return eventSource;
    }

    public void setEventSource(String eventSource) {
        this.eventSource = eventSource;
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

    public String getChannelOrg() {
        return channelOrg;
    }

    public void setChannelOrg(String channelOrg) {
        this.channelOrg = channelOrg;
    }

    public String getChannelGridId() {
        return channelGridId;
    }

    public void setChannelGridId(String channelGridId) {
        this.channelGridId = channelGridId;
    }

    public String getEventCheck() {
        return eventCheck;
    }

    public void setEventCheck(String eventCheck) {
        this.eventCheck = eventCheck;
    }

    public byte[] getEventData() {
        if (eventData != null) {
            return eventData.clone();
        }
        return null;
    }

    public void setEventData(byte[] eventData) {
        if (eventData != null) {
            this.eventData = eventData.clone();
        } else {
            this.eventData = null;
        }
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    public String getEventEndTime() {
        return eventEndTime;
    }

    public void setEventEndTime(String eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public String getEventReviewer() {
        return eventReviewer;
    }

    public void setEventReviewer(String eventReviewer) {
        this.eventReviewer = eventReviewer;
    }

    @Override
    public String toString() {
        return "EventAlert{" +
                "eventId=" + eventId +
                ", channelId=" + channelId +
                ", channelName='" + channelName + '\'' +
                ", eventStartTime='" + eventStartTime + '\'' +
                ", typeDescription='" + typeDescription + '\'' +
                ", typeStatus='" + typeStatus + '\'' +
                ", typeRectify='" + typeRectify + '\'' +
                ", eventSource='" + eventSource + '\'' +
                ", channelHandler='" + channelHandler + '\'' +
                ", channelHandlerPhone='" + channelHandlerPhone + '\'' +
                ", channelLocation='" + channelLocation + '\'' +
                ", channelLongitude='" + channelLongitude + '\'' +
                ", channelLatitude='" + channelLatitude + '\'' +
                ", channelOrg='" + channelOrg + '\'' +
                ", channelGridId='" + channelGridId + '\'' +
                ", eventCheck='" + eventCheck + '\'' +
                ", eventStatus='" + eventStatus + '\'' +
                ", eventEndTime='" + eventEndTime + '\'' +
                ", eventReviewer='" + eventReviewer + '\'' +
                '}';
    }
}
