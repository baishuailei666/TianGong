package com.xlauncher.service.impl;

import com.xlauncher.dao.PermissionDao;
import com.xlauncher.entity.Permission;
import com.xlauncher.service.PermissionService;
import com.xlauncher.util.CheckToken;
import com.xlauncher.util.OperationLogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImp implements PermissionService {

    @Autowired
    PermissionDao permissionDao;
    @Autowired
    private OperationLogUtil logUtil;
    @Autowired
    private CheckToken checkToken;
    private static final String MODULE = "权限管理";
    private static final String SYSTEM_MODULE = "系统管理";
    private static final String CATEGORY = "运营面";
    @Override
    public int insertPermission(Permission permission, String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"添加",MODULE,SYSTEM_MODULE,"添加权限信息:" + permission.getPermissionName(),CATEGORY);
        return permissionDao.insertPermission(permission);
    }

    @Override
    public int deletePermission(int permissionId, String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"删除",MODULE,SYSTEM_MODULE,"删除编号为：" + permissionId + "的权限信息",CATEGORY);
        return permissionDao.deletePermission(permissionId);
    }

    @Override
    public int updatePermission(Permission permission, String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"更新",MODULE,SYSTEM_MODULE,"更新权限信息:" + permission.getPermissionName(),CATEGORY);
        return permissionDao.updatePermission(permission);
    }

    @Override
    public List<Permission> listPermission(String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询",MODULE,SYSTEM_MODULE,"查询权限信息",CATEGORY);
        List<Permission> lists = permissionDao.listPermission();
        lists.removeIf(permission -> permission.getpermissionId() == 6);
        lists.removeIf(permission -> permission.getpermissionId() == 7);
        return lists;
    }
}
