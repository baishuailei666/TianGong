package com.xlauncher.dao;

import com.xlauncher.entity.Permission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色权限分配的Dao层，包括为角色添加权限，删除权限，查看权限列表
 * @author mao ye
 * @since 2018-02-10
 * Copyright: @V1.0
 * Company:   www.xlauncher.com
 */
@Service
public interface AuthorizationDao {

    /**
     * 为角色添加权限
     * @param roleId 角色编号
     * @param PermissionId 权限编号
     * @return 添加操作影响数据库的行数
     */
    int insertAuthorization(@Param("roleId") int roleId, @Param("PermissionId") int PermissionId);

    /**
     * 删除角色的某个权限
     * @param authorizeId 授权编号
     * @return 删除操作影响的数据库行数
     */
    int deleteAuthorization(@Param("authorizeId") int authorizeId);

    /**
     * 删除某个角色的所有权限
     * @param roleId 角色编号
     * @return 删除操作影响的数据库行数
     */
    int deleteAuthorizationByRole(int roleId);

    /**
     * 获取某个角色所有的权限列表
     * @param roleId 角色编号
     * @return 该编号角色所有的权限
     */
    List<Integer> listAuthorization(@Param("roleId") int roleId);

}
