package com.xlauncher.entity;

import java.util.Arrays;
import java.util.Calendar;


/**
 * 正阳告警事件接口实体类
 * @author 张霄龙
 * @since 2018-03-19
 */
public class EventDto {
    /**
     * 问题大类
     * 默认：33
     */
    private String type;

    /**
     * 问题小类
     * 告警事件：typeId，3301,3302,3303
     */
    private String typeDtl;

    /**
     * 部件类案件对于的部件编码
     */
    private String componentId;

    /**
     * 事件发生地
     * 默认：密云区
     */
    private String occurPlace;

    /**
     * 事件发生时间
     * 告警事件：eventStartTime，未来之瞳系统传输格式为：yyyy-MM-dd HH:mm:ss,需要转换SimpleDateFormat.parse
     */
    private Calendar occurDate;

    /**
     * 上报人
     * 默认：xlauncher
     */
    private String reporter;

    /**
     * 事件发生的网格编号
     * 默认：1
     */
    private String gridCode;

    /**
     * 事件发生的网格名称
     * 默认：密云区
     */
    private String gridName;

    /**
     * 案发照片(base64编码)
     */
    private String[] image1;

    /**
     * 事件主题
     * 告警事件：typeDescription
     */
    private String subject;

    /**
     * 事件描述
     * 默认：发现+subject
     */
    private String description;

    /**
     * 外联渠道
     * 默认：Channel
     */
    private String refEntity;

    /**
     * 外联渠道ID
     * 告警事件：channelId
     */
    private String refId;


    /**
     * MD5加密后字符串
     * description{0}gridCode{1}gridName{2}occurDate{3}occurPlace{4}reporter{5}subject{6}type{7}typeDtl{8}
     */
    private String sign;

    public String getType() {
        return type;
    }

    public String getTypeDtl() {
        return typeDtl;
    }

    public String getComponentId() {
        return componentId;
    }

    public String getOccurPlace() {
        return occurPlace;
    }

    public String getReporter() {
        return reporter;
    }

    public String getGridCode() {
        return gridCode;
    }

    public String getGridName() {
        return gridName;
    }

    public String[] getImage1() {
        if (image1 != null) {
            return image1.clone();
        }
        return null;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public Calendar getOccurDate() {
        return occurDate;
    }

    public String getRefEntity() {
        return refEntity;
    }

    public String getRefId() {
        return refId;
    }

    public String getSign() {
        return sign;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTypeDtl(String typeDtl) {
        this.typeDtl = typeDtl;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public void setOccurPlace(String occurPlace) {
        this.occurPlace = occurPlace;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public void setGridCode(String gridCode) {
        this.gridCode = gridCode;
    }

    public void setGridName(String gridName) {
        this.gridName = gridName;
    }

    public void setImage1(String[] imgPath) {
        if (imgPath != null) {
            this.image1 = imgPath.clone();
        } else {
            this.image1 = null;
        }
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOccurDate(Calendar occurDate) {
        this.occurDate = occurDate;
    }

    public void setRefEntity(String refEntity) {
        this.refEntity = refEntity;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "EventDto{" +
                "type='" + type + '\'' +
                ", typeDtl='" + typeDtl + '\'' +
                ", componentId='" + componentId + '\'' +
                ", occurPlace='" + occurPlace + '\'' +
                ", occurDate=" + occurDate +
                ", reporter='" + reporter + '\'' +
                ", gridCode='" + gridCode + '\'' +
                ", gridName='" + gridName + '\'' +
                ", image1='" + Arrays.toString(image1) + '\'' +
                ", subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                ", refEntity='" + refEntity + '\'' +
                ", refId='" + refId + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
