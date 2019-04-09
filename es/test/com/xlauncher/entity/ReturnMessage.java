package com.xlauncher.entity;

public class ReturnMessage {
    private String resCode;
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
