package com.xlauncher.dao;

import com.xlauncher.entity.Permission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PermissionDao {

    /**
     * 添加权限
     * @param permission 权限信息
     * @return 添加操作影响的数据库行数
     */
    int insertPermission(Permission permission);

    /**
     * 删除权限
     * @param permissionId 权限编号
     * @return 删除操作影响的数据库行数
     */
    int deletePermission(@Param("permissionId") int permissionId);

    /**
     * 更新权限
     * @param permission 更新的权限信息
     * @return 更新操作影响的数据库行数
     */
    int updatePermission(Permission permission);

    /**
     * 权限功能检索（具体内容待定）
     * @return 满足条件的权限列表
     */
    List<Permission> listPermission();

    /**
     * 根据权限编号获取权限信息
     * @param permissionId 权限编号
     * @return 该编号权限的所有信息
     */
    Permission getPermission(int permissionId);

    /**
     * 根据权限编号获取权限信息
     * @param permissionId 权限编号
     * @return 该编号权限的所有信息
     */
    String getPermissionName(int permissionId);
}
