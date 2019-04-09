package com.xlauncher.entity;

public class Role {
    /**角色编号*/
    private Integer roleId;
    /**角色名称*/
    private String roleName;
    /**角色权限*/
    private String rolePermission;
    /**角色描述*/
    private String roleDescription;
    /**角色状态*/
    private String roleStatus;

    public Role() {
    }

    public Role(String roleName, String rolePermission, String roleDescription, String roleStatus) {
        this.roleName = roleName;
        this.rolePermission = rolePermission;
        this.roleDescription = roleDescription;
        this.roleStatus = roleStatus;
    }

    public Role(Integer roleId, String roleName, String rolePermission, String roleDescription, String roleStatus) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.rolePermission = rolePermission;
        this.roleDescription = roleDescription;
        this.roleStatus = roleStatus;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRolePermission() {
        return rolePermission;
    }

    public void setRolePermission(String rolePermission) {
        this.rolePermission = rolePermission;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public String getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(String roleStatus) {
        this.roleStatus = roleStatus;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", rolePermission='" + rolePermission + '\'' +
                ", roleDescription='" + roleDescription + '\'' +
                ", roleStatus=" + roleStatus +
                '}';
    }
}
