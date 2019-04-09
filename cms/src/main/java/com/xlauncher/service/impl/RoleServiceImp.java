package com.xlauncher.service.impl;

import com.xlauncher.dao.AssignmentDao;
import com.xlauncher.dao.AuthorizationDao;
import com.xlauncher.dao.PermissionDao;
import com.xlauncher.dao.RoleDao;
import com.xlauncher.entity.Permission;
import com.xlauncher.entity.Role;
import com.xlauncher.service.RoleService;
import com.xlauncher.util.CheckToken;
import com.xlauncher.util.OperationLogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImp implements RoleService {

    @Autowired
    RoleDao roleDao;

    @Autowired
    AssignmentDao assignmentDao;

    @Autowired
    AuthorizationDao authorizationDao;

    @Autowired
    PermissionDao permissionDao;
    @Autowired
    private OperationLogUtil logUtil;
    @Autowired
    private CheckToken checkToken;
    private static final String MODULE = "角色管理";
    private static final String SYSTEM_MODULE = "系统管理";
    private static final String CATEGORY = "运营面";

    @Override
    public int insertRole(Map<String,Object> roleInfo, String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"添加",MODULE,SYSTEM_MODULE,"添加角色信息",CATEGORY);
        try {
            Role role = new Role();
            role.setRoleName((String) roleInfo.get("roleName"));
            role.setRoleDescription((String) roleInfo.get("roleDescription"));
            String roleStatus = String.valueOf(roleInfo.get("roleStatus"));
            role.setRoleStatus(roleStatus);
            List<String> permissionList =(List<String>) roleInfo.get("authorization");
            int status = roleDao.insertRole(role);
            if(permissionList.size()!=0){
                for (String permission:
                        permissionList) {
                    authorizationDao.insertAuthorization(role.getRoleId(),Integer.parseInt(permission));
                }
            }
            return status;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int countRoleName(String roleName) {
        return roleDao.countRoleName(roleName);
    }

    @Override
    public int deleteRole(int roleId, String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"删除",MODULE,SYSTEM_MODULE,"删除编号为：" + roleId +"的角色信息",CATEGORY);
        assignmentDao.deleteAssignByRole(roleId);
        authorizationDao.deleteAuthorizationByRole(roleId);
        return roleDao.deleteRole(roleId);
    }

    @Override
    public int updateRole(Map<String,Object> roleInfo, String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"更新",MODULE,SYSTEM_MODULE,"更新角色信息",CATEGORY);
        Role role = new Role();
        role.setRoleId(Integer.parseInt((String)roleInfo.get("roleId")));
        role.setRoleName((String)roleInfo.get("roleName"));
        role.setRoleDescription((String)roleInfo.get("roleDescription"));
        String roleStatus = String.valueOf(roleInfo.get("roleStatus"));
        role.setRoleStatus(roleStatus);
        int status = roleDao.updateRole(role);
        List<String > permissionList = (List<String>) roleInfo.get("authorization");
        if(!(permissionList.containsAll(authorizationDao.listAuthorization(role.getRoleId()))&&authorizationDao.listAuthorization(role.getRoleId()).containsAll(permissionList))){
            authorizationDao.deleteAuthorizationByRole(role.getRoleId());
            for (String permission:
                    permissionList) {
                authorizationDao.insertAuthorization(role.getRoleId(),Integer.parseInt(permission));
            }
        }
        return status;
    }

    @Override
    public List<Role> listRole(String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询",MODULE,SYSTEM_MODULE,"查询角色信息",CATEGORY);
        List<Role> roleList = roleDao.listRole();
        if (roleList.size()!=0){
            for (Role role:
                 roleList) {
                List<Integer> perList = authorizationDao.listAuthorization(role.getRoleId());
                if (perList != null) {
                    String perName="";
                    for (int permissionId:
                            perList) {
                        perName=perName+permissionDao.getPermissionName(permissionId)+",";
                    }
                    try {
                        perName = perName.substring(0, perName.length()-1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    role.setRolePermission(perName);
                }
            }
        }
        return roleList;
    }

    @Override
    public List<Integer> listAuthorization(int roleId,String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询",MODULE,SYSTEM_MODULE,"查询角色授权信息",CATEGORY);
        return authorizationDao.listAuthorization(roleId);
    }

    @Override
    public Role getRoleById(int roleId, String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询",MODULE,SYSTEM_MODULE,"查询编号为：" + roleId +"的角色信息",CATEGORY);
        return roleDao.getRoleById(roleId);
    }
}
