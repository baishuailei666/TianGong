package com.xlauncher.entity;

import java.util.List;

public class MailR {

    private Integer mailId;

    private String mailTitle;

    private String mailSender;

    private String mailReceiver;

    private String mailReceivers;

    private Integer mailInfoId;
    /**邮件的阅读状态，-1未读，1已读*/
    private Integer mailStatus;

    private String mailCreateTime;

    private String mailContent;

    public MailR() {
    }

    public MailR(String mailTitle, String mailSender, String mailReceiver, String mailReceivers, Integer mailInfoId, Integer mailStatus, String mailCreateTime, String mailContent) {
        this.mailTitle = mailTitle;
        this.mailSender = mailSender;
        this.mailReceiver = mailReceiver;
        this.mailReceivers = mailReceivers;
        this.mailInfoId = mailInfoId;
        this.mailStatus = mailStatus;
        this.mailCreateTime = mailCreateTime;
        this.mailContent = mailContent;
    }

    public MailR(Integer mailId, String mailTitle, String mailSender, String mailReceiver, String mailReceivers, Integer mailInfoId, Integer mailStatus, String mailCreateTime, String mailContent) {
        this.mailId = mailId;
        this.mailTitle = mailTitle;
        this.mailSender = mailSender;
        this.mailReceiver = mailReceiver;
        this.mailReceivers = mailReceivers;
        this.mailInfoId = mailInfoId;
        this.mailStatus = mailStatus;
        this.mailCreateTime = mailCreateTime;
        this.mailContent = mailContent;
    }

    public Integer getMailId() {
        return mailId;
    }

    public void setMailId(Integer mailId) {
        this.mailId = mailId;
    }

    public String getMailTitle() {
        return mailTitle;
    }

    public void setMailTitle(String mailTitle) {
        this.mailTitle = mailTitle;
    }

    public String getMailSender() {
        return mailSender;
    }

    public void setMailSender(String mailSender) {
        this.mailSender = mailSender;
    }

    public String getMailReceiver() {
        return mailReceiver;
    }

    public void setMailReceiver(String mailReceiver) {
        this.mailReceiver = mailReceiver;
    }

    public String getMailReceivers() {
        return mailReceivers;
    }

    public void setMailReceivers(String mailReceivers) {
        this.mailReceivers = mailReceivers;
    }

    public Integer getMailInfoId() {
        return mailInfoId;
    }

    public void setMailInfoId(Integer mailInfoId) {
        this.mailInfoId = mailInfoId;
    }

    public Integer getMailStatus() {
        return mailStatus;
    }

    public void setMailStatus(Integer mailStatus) {
        this.mailStatus = mailStatus;
    }

    public String getMailCreateTime() {
        return mailCreateTime;
    }

    public void setMailCreateTime(String mailCreateTime) {
        this.mailCreateTime = mailCreateTime;
    }

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }

    @Override
    public String toString() {
        return "MailR{" +
                "mailId=" + mailId +
                ", mailTitle='" + mailTitle + '\'' +
                ", mailSender='" + mailSender + '\'' +
                ", mailReceiver='" + mailReceiver + '\'' +
                ", mailReceivers=" + mailReceivers +
                ", mailInfoId=" + mailInfoId +
                ", mailStatus=" + mailStatus +
                ", mailCreateTime='" + mailCreateTime + '\'' +
                ", mailContent='" + mailContent + '\'' +
                '}';
    }
}
