package com.xlauncher.entity;

public class Organization {

    private String orgId;

    private String name;

    private String orgLeader;

    private String orgDuty;

    private String orgPhone;

    private String orgLocation;

    private String orgEmail;

    private String orgSuperiorId;

    public Organization() {
    }

    public Organization(String name, String orgLeader, String orgDuty, String orgPhone, String orgLocation, String orgEmail, String orgSuperiorId) {
        this.name = name;
        this.orgLeader = orgLeader;
        this.orgDuty = orgDuty;
        this.orgPhone = orgPhone;
        this.orgLocation = orgLocation;
        this.orgEmail = orgEmail;
        this.orgSuperiorId = orgSuperiorId;
    }

    public Organization(String orgId, String name, String orgLeader, String orgDuty, String orgPhone, String orgLocation, String orgEmail, String orgSuperiorId) {
        this.orgId = orgId;
        this.name = name;
        this.orgLeader = orgLeader;
        this.orgDuty = orgDuty;
        this.orgPhone = orgPhone;
        this.orgLocation = orgLocation;
        this.orgEmail = orgEmail;
        this.orgSuperiorId = orgSuperiorId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgDuty() {
        return orgDuty;
    }

    public void setOrgDuty(String orgDuty) {
        this.orgDuty = orgDuty;
    }

    public String getOrgLeader() {
        return orgLeader;
    }

    public void setOrgLeader(String orgLeader) {
        this.orgLeader = orgLeader;
    }

    public String getOrgPhone() {
        return orgPhone;
    }

    public void setOrgPhone(String orgPhone) {
        this.orgPhone = orgPhone;
    }

    public String getOrgLocation() {
        return orgLocation;
    }

    public void setOrgLocation(String orgLocation) {
        this.orgLocation = orgLocation;
    }

    public String getOrgEmail() {
        return orgEmail;
    }

    public void setOrgEmail(String orgEmail) {
        this.orgEmail = orgEmail;
    }

    public String getOrgSuperiorId() {
        return orgSuperiorId;
    }

    public void setOrgSuperiorId(String orgSuperiorId) {
        this.orgSuperiorId = orgSuperiorId;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "orgId=" + orgId +
                ", name='" + name + '\'' +
                ", orgLeader='" + orgLeader + '\'' +
                ", orgDuty='" + orgDuty + '\'' +
                ", orgPhone='" + orgPhone + '\'' +
                ", orgLocation='" + orgLocation + '\'' +
                ", orgEmail='" + orgEmail + '\'' +
                ", orgSuperiorId=" + orgSuperiorId +
                '}';
    }
}
