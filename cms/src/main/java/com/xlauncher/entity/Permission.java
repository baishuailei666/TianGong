package com.xlauncher.entity;

public class Permission {
    /**权限编号*/
    private Integer permissionId;
    /**权限名称*/
    private String permissionName;
    /**权限描述*/
    private String permissionNote;
    /**所属的高级权限*/
    private String permissionSuperiorName;

    public Permission() {
    }

    public Permission(String permissionName, String permissionNote, String permissionSuperiorName) {
        this.permissionName = permissionName;
        this.permissionNote = permissionNote;
        this.permissionSuperiorName = permissionSuperiorName;
    }

    public Permission(Integer permissionId, String permissionName, String permissionNote, String permissionSuperiorName) {
        this.permissionId = permissionId;
        this.permissionName = permissionName;
        this.permissionNote = permissionNote;
        this.permissionSuperiorName = permissionSuperiorName;
    }

    public Integer getpermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getpermissionNote() {
        return permissionNote;
    }

    public void setPermissionNote(String permissionNote) {
        this.permissionNote = permissionNote;
    }

    public String getPermissionSuperiorName() {
        return permissionSuperiorName;
    }

    public void setPermissionSuperiorName(String permissionSuperiorName) {
        this.permissionSuperiorName = permissionSuperiorName;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "permissionId=" + permissionId +
                ", permissionName='" + permissionName + '\'' +
                ", permissionNote='" + permissionNote + '\'' +
                ", permissionSuperiorName='" + permissionSuperiorName + '\'' +
                '}';
    }
}
