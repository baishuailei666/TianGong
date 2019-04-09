package com.xlauncher.util;

import com.xlauncher.dao.*;
import com.xlauncher.entity.Role;
import com.xlauncher.entity.User;
import com.xlauncher.util.userlogin.SessionManageListener;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限认证
 * @author 白帅雷
 * @date 2018-08-21
 */
@Component
public class AuthUtil {
    @Autowired
    UserDao userDao;
    @Autowired
    RoleDao roleDao;
    @Autowired
    AssignmentDao assignmentDao;
    @Autowired
    AuthorizationDao authorizationDao;
    @Autowired
    PermissionDao permissionDao;
    @Autowired
    CheckToken checkToken;
    private Logger logger = Logger.getLogger(AuthUtil.class);

    /**
     * 权限验证避免篡改页面
     *
     * @param desc
     * @param token
     * @return
     */
    public boolean isAuth(String desc, String token, HttpServletRequest request) {
        User user = userDao.checkToken(token);
        if (user != null) {
            List<Integer> roleIdList = assignmentDao.listAssign(user.getUserId());
            /**权限编号表，利用遍历角色编号表从角色权限关系表中获取所有的权限编号*/
            List<Integer> permissionIdList = new ArrayList<>();
            /**权限名称表，前端需求内容*/
            List<String> permissionNameList = new ArrayList<>();
            /**遍历角色编号表*/
            if (roleIdList.size() != 0) {
                for (int roleId :
                        roleIdList) {
                    Role role = roleDao.getRoleById(roleId);
                    String roleStatus = role.getRoleStatus();
                    if (!"0".equals(roleStatus)) {
                        List<Integer> permissionIdMidList = authorizationDao.listAuthorization(roleId);
                        if (permissionIdMidList.size() != 0) {
                            for (int permissionMidId :
                                    permissionIdMidList) {
                                permissionIdList.add(permissionMidId);
                            }
                        }
                    }
                }
            }
            if (permissionIdList.size() != 0) {
                for (int permissionId :
                        permissionIdList) {
                    permissionNameList.add(permissionDao.getPermissionName(permissionId));
                }
            }
            logger.info("permissionNameList:" + permissionNameList);
            for (String name : permissionNameList) {
                if (desc.equals(name)) {
                    logger.info("[AuthUtil isAuth true] - User：" + user);
                    return true;
                }
            }
            logger.info("[AuthUtil isAuth false] - User：" + user);
            SessionManageListener.removeUserSession(checkToken.checkToken(token).getUserLoginName());
            SessionManageListener.removeSession(request.getSession().getId());
        } else {
            logger.info("[AuthUtil isAuth false]");
            return false;
        }
        return false;
    }

}
