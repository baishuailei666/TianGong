package com.xlauncher.entity;

import java.math.BigInteger;

public class User {
    /**用户编号*/
    private Integer userId;
    /**用户工号*/
    private String userCode;
    /**用户登录名*/
    private String userLoginName;
    /**用户登录密码*/
    private String userPassword;
    /**用户真实姓名*/
    private String userName;
    /**用户身份证*/
    private String userCardId;
    /**用户联系方式*/
    private String userPhone;
    /**用户邮箱*/
    private String userEmail;
    /**用户角色*/
    private String userRole;
    /**用户行政区划编号*/
    private BigInteger adminDivisionId;
    /**用户所属组织编号*/
    private String userOrgId;
    /**用户登录次数*/
    private Integer userLoginCount;
    /**用户在线状态*/
    private Integer userStatus;
    /**用户密保问题填写情况*/
    private Integer userQuestion;
    /**用户上次登录时间*/
    private String userLastLogin;
    /**用户专属令牌*/
    private String token;
    /**用户所属组织名称*/
    private String orgName;
    /**用户所属行政区划名称*/
    private String divisionName;

    public User() {
    }

    public User(String userCode, String userLoginName, String userPassword, String userName, String userCardId, String userPhone, String userEmail, String userRole, BigInteger adminDivisionId, String userOrgId, Integer userLoginCount, Integer userStatus, Integer userQuestion, String userLastLogin, String token) {
        this.userCode = userCode;
        this.userLoginName = userLoginName;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userCardId = userCardId;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userRole = userRole;
        this.adminDivisionId = adminDivisionId;
        this.userOrgId = userOrgId;
        this.userLoginCount = userLoginCount;
        this.userStatus = userStatus;
        this.userQuestion = userQuestion;
        this.userLastLogin = userLastLogin;
        this.token = token;
    }

    public User(Integer userId, String userCode, String userLoginName, String userPassword, String userName, String userCardId, String userPhone, String userEmail, String userRole, BigInteger adminDivisionId, String userOrgId, Integer userLoginCount, Integer userStatus, Integer userQuestion, String userLastLogin, String token) {
        this.userId = userId;
        this.userCode = userCode;
        this.userLoginName = userLoginName;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userCardId = userCardId;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userRole = userRole;
        this.adminDivisionId = adminDivisionId;
        this.userOrgId = userOrgId;
        this.userLoginCount = userLoginCount;
        this.userStatus = userStatus;
        this.userQuestion = userQuestion;
        this.userLastLogin = userLastLogin;
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCardId() {
        return userCardId;
    }

    public void setUserCardId(String userCardId) {
        this.userCardId = userCardId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public BigInteger getAdminDivisionId() {
        return adminDivisionId;
    }

    public void setAdminDivisionId(BigInteger adminDivisionId) {
        this.adminDivisionId = adminDivisionId;
    }

    public String getUserOrgId() {
        return userOrgId;
    }

    public void setUserOrgId(String userOrgId) {
        this.userOrgId = userOrgId;
    }

    public Integer getUserLoginCount() {
        return userLoginCount;
    }

    public void setUserLoginCount(Integer userLoginCount) {
        this.userLoginCount = userLoginCount;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getUserQuestion() {
        return userQuestion;
    }

    public void setUserQuestion(Integer userQuestion) {
        this.userQuestion = userQuestion;
    }

    public String getUserLastLogin() {
        return userLastLogin;
    }

    public void setUserLastLogin(String userLastLogin) {
        this.userLastLogin = userLastLogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userCode='" + userCode + '\'' +
                ", userLoginName='" + userLoginName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userName='" + userName + '\'' +
                ", userCardId='" + userCardId + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userRole='" + userRole + '\'' +
                ", adminDivisionId='" + adminDivisionId + '\'' +
                ", userOrgId=" + userOrgId +
                ", userLoginCount=" + userLoginCount +
                ", userStatus=" + userStatus +
                ", userQuestion=" + userQuestion +
                ", userLastLogin='" + userLastLogin + '\'' +
                ", divisionName='" + divisionName + '\'' +
                ", orgName='" + orgName + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
