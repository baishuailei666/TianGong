package com.xlauncher.entity;

import java.math.BigInteger;

public class Division {

    private Integer id;

    private BigInteger divisionSuperiorId;

    private BigInteger divisionId;

    private String name;

    public Division() {
    }

    public Division(BigInteger divisionSuperiorId, BigInteger divisionId, String name) {
        this.divisionSuperiorId = divisionSuperiorId;
        this.divisionId = divisionId;
        this.name = name;
    }

    public Division(Integer id, BigInteger divisionSuperiorId, BigInteger divisionId, String divisionName) {
        this.id = id;
        this.divisionSuperiorId = divisionSuperiorId;
        this.divisionId = divisionId;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigInteger getDivisionSuperiorId() {
        return divisionSuperiorId;
    }

    public void setDivisionSuperiorId(BigInteger divisionSuperiorId) {
        this.divisionSuperiorId = divisionSuperiorId;
    }

    public BigInteger getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(BigInteger divisionId) {
        this.divisionId = divisionId;
    }

    public String getName () {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Division{" +
                "id=" + id +
                ", divisionSuperiorId=" + divisionSuperiorId +
                ", divisionId=" + divisionId +
                ", name='" + name + '\'' +
                '}';
    }
}
