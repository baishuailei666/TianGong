package com.xlauncher.service;

import com.xlauncher.entity.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionService {
    /**
     * 添加权限
     * @param permission 权限信息
     * @param token 令牌
     * @return 添加操作影响的数据库行数
     */
    int insertPermission(@Param("permission") Permission permission, @Param("token") String token);

    /**
     * 删除权限
     * @param permissionId 权限编号
     * @param token 令牌
     * @return 删除操作影响的数据库行数
     */
    int deletePermission(@Param("permissionId") int permissionId,@Param("token") String token);

    /**
     * 更新权限
     * @param permission 更新的权限信息
     * @param token 令牌
     * @return 更新操作影响的数据库行数
     */
    int updatePermission(@Param("permission") Permission permission,@Param("token") String token);

    /**
     * 权限功能检索（具体内容待定）
     * @param token 令牌
     * @return 满足条件的权限列表
     */
    List<Permission> listPermission(@Param("token") String token);
}
