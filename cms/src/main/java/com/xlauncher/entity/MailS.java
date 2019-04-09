package com.xlauncher.entity;

import java.util.List;

public class MailS {

    private Integer mailId;

    private String mailTitle;

    private String mailSender;

    private String mailReceiver;

    private Integer mailInfoId;
    /**邮件状态，为以后的草稿箱等服务*/
    private Integer mailStatus;

    private String mailCreateTime;

    private String mailContent;

    public MailS() {
    }

    public MailS(String mailTitle, String mailSender, String mailReceiver, Integer mailInfoId, Integer mailStatus, String mailCreateTime, String mailContent) {
        this.mailTitle = mailTitle;
        this.mailSender = mailSender;
        this.mailReceiver = mailReceiver;
        this.mailInfoId = mailInfoId;
        this.mailStatus = mailStatus;
        this.mailCreateTime = mailCreateTime;
        this.mailContent = mailContent;
    }

    public MailS(Integer mailId, String mailTitle, String mailSender, String mailReceiver, Integer mailInfoId, Integer mailStatus, String mailCreateTime, String mailContent) {
        this.mailId = mailId;
        this.mailTitle = mailTitle;
        this.mailSender = mailSender;
        this.mailReceiver = mailReceiver;
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
        return "MailS{" +
                "mailId=" + mailId +
                ", mailTitle='" + mailTitle + '\'' +
                ", mailSender='" + mailSender + '\'' +
                ", mailReceiver=" + mailReceiver +
                ", mailInfoId=" + mailInfoId +
                ", mailStatus=" + mailStatus +
                ", mailCreateTime='" + mailCreateTime + '\'' +
                ", mailContent='" + mailContent + '\'' +
                '}';
    }
}
