package com.xlauncher.entity;

public class Info {

    private Integer infoId;
    private String infoContent;

    public Info() {
    }

    public Info(String infoContent) {
        this.infoContent = infoContent;
    }

    public Info(Integer infoId, String infoContent) {
        this.infoId = infoId;
        this.infoContent = infoContent;
    }

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public String getInfoContent() {
        return infoContent;
    }

    public void setInfoContent(String infoContent) {
        this.infoContent = infoContent;
    }

    @Override
    public String toString() {
        return "Info{" +
                "infoId=" + infoId +
                ", infoContent=" + infoContent +
                '}';
    }
}
