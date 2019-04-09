package com.xlauncher.service;

import com.xlauncher.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RoleService {
    /**
     * 添加角色
     * @param role 完整的角色信息
     * @param token 令牌
     * @return  添加操作影响的数据库行数
     */
    int insertRole(Map<String,Object> role, String token);

    /**
     * 角色名称查重
     * @param roleName 角色名称
     * @return 角色名称已存在则返回1，否则返回0
     */
    int countRoleName(String roleName);

    /**
     * 删除角色
     * @param roleId 需要删除的角色的编号
     * @param token 令牌
     * @return 删除操作影响的数据库行数
     */
    int deleteRole(int roleId, String token);

    /**
     * 更新角色信息
     * @param role 更新的角色的信息
     * @param token 令牌
     * @return  更新操作影响的数据库行数
     */
    int updateRole(Map<String,Object> role, String token);

    /**
     * 角色信息检索（具体条件待定）
     * @param token 令牌
     * @return
     */
    List<Role> listRole(String token);

    /**
     * 更新页面角色的权限打钩选项
     * @param roleId 角色编号
     * @param token 令牌
     * @return 权限编号列表
     */
    List<Integer> listAuthorization(int roleId,String token);

    /**
     * 利用角色编号获取角色信息
     * @param roleId 用户编号
     * @param token 令牌
     * @return 角色信息
     */
    Role getRoleById(int roleId,String token);

}
