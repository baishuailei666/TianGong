package com.xlauncher.dao;

import com.xlauncher.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * 角色Dao层，包括角色添加，角色修改，角色删除，角色信息检索
 * @author mao ye
 * @since 2018-05-07
 * Copyright: @V0.5
 * Company:   www.xlauncher.com
 */
@Service
public interface RoleDao {

    /**
     * 添加角色
     * @param role 完整的角色信息
     * @return  添加操作影响的数据库行数
     */
    int insertRole(Role role);
    /**
     * 初始化用户-超级管理员
     * @param role 完整的角色信息
     * @return  添加操作影响的数据库行数
     */
    int rootRole(Role role);
    /**
     * 角色名称查重
     * @param roleName 角色名
     * @return 已存在返回1，可用返回0
     */
    int countRoleName(@Param("roleName") String roleName);

    /**
     * 删除角色
     * @param roleId 需要删除的角色的编号
     * @return 删除操作影响的数据库行数
     */
    int deleteRole(int roleId);

    /**
     * 更新角色信息
     * @param role 更新的角色的信息
     * @return  更新操作影响的数据库行数
     */
    int updateRole(Role role);

    /**
     * 角色信息检索（具体条件待定）
     * @return 满足条件的一个角色信息列表
     */
    List<Role> listRole();

    /**
     * 通过角色编号查询角色信息
     * @param roleId 角色编号
     * @return 角色的完整信息
     */
    Role getRoleById(int roleId);

    /**
     * 通过角色编号查询角色名称
     * @param roleId 角色编号
     * @return 角色的完整信息
     */
    String getRoleNameById(int roleId);
}
