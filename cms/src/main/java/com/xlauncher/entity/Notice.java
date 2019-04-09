package com.xlauncher.entity;

import javax.xml.soap.Text;
import java.util.List;

public class Notice {

    private Integer noticeId;

    private String noticeTitle;

    private Integer noticeInfoId;

    private String noticeAuthor;
    /**公告公开的角色编号*/
    private List<Integer> noticeRole;

    private String noticeCreateTime;

    private Integer noticeCount;

    private String infoContent;

    public Notice() {
    }

    public Notice(String noticeTitle, Integer noticeInfoId, String noticeAuthor, List<Integer> noticeRole, String noticeCreateTime, Integer noticeCount, String infoContent) {
        this.noticeTitle = noticeTitle;
        this.noticeInfoId = noticeInfoId;
        this.noticeAuthor = noticeAuthor;
        this.noticeRole = noticeRole;
        this.noticeCreateTime = noticeCreateTime;
        this.noticeCount = noticeCount;
        this.infoContent = infoContent;
    }

    public Notice(Integer noticeId, String noticeTitle, Integer noticeInfoId, String noticeAuthor, List<Integer> noticeRole, String noticeCreateTime, Integer noticeCount, String infoContent) {
        this.noticeId = noticeId;
        this.noticeTitle = noticeTitle;
        this.noticeInfoId = noticeInfoId;
        this.noticeAuthor = noticeAuthor;
        this.noticeRole = noticeRole;
        this.noticeCreateTime = noticeCreateTime;
        this.noticeCount = noticeCount;
        this.infoContent = infoContent;
    }

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public String getnoticeTitle() {
        return noticeTitle;
    }

    public void setnoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public Integer getNoticeInfoId() {
        return noticeInfoId;
    }

    public void setNoticeInfoId(Integer noticeInfoId) {
        this.noticeInfoId = noticeInfoId;
    }

    public String getNoticeAuthor() {
        return noticeAuthor;
    }

    public void setNoticeAuthor(String noticeAuthor) {
        this.noticeAuthor = noticeAuthor;
    }

    public List<Integer> getNoticeRole() {
        return noticeRole;
    }

    public void setNoticeRole(List<Integer> noticeRole) {
        this.noticeRole = noticeRole;
    }

    public String getNoticeCreateTime() {
        return noticeCreateTime;
    }

    public void setNoticeCreateTime(String noticeCreateTime) {
        this.noticeCreateTime = noticeCreateTime;
    }

    public Integer getNoticeCount() {
        return noticeCount;
    }

    public void setNoticeCount(Integer noticeCount) {
        this.noticeCount = noticeCount;
    }

    public String getInfoContent() {
        return infoContent;
    }

    public void setInfoContent(String infoContent) {
        this.infoContent = infoContent;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "noticeId=" + noticeId +
                ", noticeTitle='" + noticeTitle + '\'' +
                ", noticeInfoId=" + noticeInfoId +
                ", noticeAuthor='" + noticeAuthor + '\'' +
                ", noticeRole=" + noticeRole +
                ", noticeCreateTime='" + noticeCreateTime + '\'' +
                ", noticeCount=" + noticeCount +
                ", infoContent=" + infoContent +
                '}';
    }
}
