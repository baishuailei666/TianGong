package com.xlauncher.ics.entity;

/**
 * @author :baisl
 * @Email :baishuailei@xlauncher.io
 * @Date :2018/12/19 0019
 * @Desc :告警事件对象
 **/
public class AlertEvent {

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
    private Integer typeId;

    /**
     * 告警事件资源描述（图片）
     */
    private String eventSource;

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

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getEventSource() {
        return eventSource;
    }

    public void setEventSource(String eventSource) {
        this.eventSource = eventSource;
    }

    @Override
    public String toString() {
        return "AlertEvent{" +
                "eventStartTime='" + eventStartTime + '\'' +
                ", channelId='" + channelId + '\'' +
                ", typeId=" + typeId +
                ", eventSource='" + eventSource + '\'' +
                '}';
    }
}
