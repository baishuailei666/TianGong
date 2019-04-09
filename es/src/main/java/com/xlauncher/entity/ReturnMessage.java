package com.xlauncher.entity;

/**
 * 正阳科技返回信息实体类
 * @author 张霄龙
 * @since 2018-03-22
 */
public class ReturnMessage {
    /**
     * 返回的编码
     */
    private String resCode;

    /**
     * 编码对应的信息描述
     */
    private String resMessage;

    public String getResCode() {
        return resCode;
    }

    public String getResMessage() {
        return resMessage;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public void setResMessage(String resMessage) {
        this.resMessage = resMessage;
    }

    @Override
    public String toString() {
        return "ReturnMessage{" +
                "resCode='" + resCode + '\'' +
                ", resMessage='" + resMessage + '\'' +
                '}';
    }
}
