package com.xlauncher.util;

import com.xlauncher.dao.AssignmentDao;
import com.xlauncher.dao.AuthorizationDao;
import com.xlauncher.dao.PermissionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据userId获取用户相应权限信息
 * @author baisl
 * @Email :baishuailei@xlauncher.io
 * @Date :2018/9/20 0020
 * @Desc :
 **/
@Component
public class PermissionUtil {
    @Autowired
    AssignmentDao assignmentDao;
    @Autowired
    AuthorizationDao authorizationDao;
    @Autowired
    PermissionDao permissionDao;

    public List<String> getPermission(int userId) {
        List<Integer> roleIdList = assignmentDao.listAssign(userId);
        /**权限编号表，利用遍历角色编号表从角色权限关系表中获取所有的权限编号*/
        List<Integer> permissionIdList = new ArrayList<>();
        /**权限名称表，前端需求内容*/
        List<String> permissionNameList = new ArrayList<>();
        /**遍历角色编号表*/
        if (roleIdList.size() != 0) {
            roleIdList.forEach(integer -> {
                List<Integer> permissionIdMidList = authorizationDao.listAuthorization(integer);
                if (permissionIdMidList.size() != 0) {
                    permissionIdList.addAll(permissionIdMidList);
                }
            });
        }
        if (permissionIdList.size() != 0) {
            permissionIdList.forEach(integer -> {
                permissionNameList.add(permissionDao.getPermissionName(integer));
            });
        }
        return permissionNameList;
    }
}
