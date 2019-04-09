package com.xlauncher.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface AssignmentDao {

    /**
     * 为用户分配角色
     * @param userId 用户编号
     * @param roleId 角色编号
     * @return 添加操作影响的数据库行数
     */
    int insertAssign(@Param("userId") int userId, @Param("roleId") int roleId);

    /**
     * 删除用户已有的角色
     * @param assignId 用户角色关系编号
     * @return 删除操作影响的数据库行数
     */
    int deleteAssign(@Param("assignId") int assignId);

    /**
     * 查询用户的角色编号
     * @param userId 用户编号
     * @return 用户所有角色编号的列表
     */
    List<Integer> listAssign(int userId);

    /**
     * 删除某用户的所有角色关系
     * @param userId 用户编号
     * @return 删除操作影响的数据库行数
     */
    int deleteAssignByUser(int userId);

    /**
     * 删除某用户的所有角色关系
     * @param roleId 角色编号
     * @return 删除操作影响的数据库行数
     */
    int deleteAssignByRole(int roleId);

}
