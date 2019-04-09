package com.xlauncher.util.alertlog;

import com.xlauncher.dao.*;
import com.xlauncher.entity.Permission;
import com.xlauncher.entity.Role;
import com.xlauncher.entity.User;
import com.xlauncher.util.MD5Util;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.math.BigInteger;
import java.util.List;

/**
 * Auto loading this class when initialized web application.
 * @author Xiaolong Zhang
 * @since 2018-02-13
 */
public class SocketServiceLoader implements ServletContextListener {

    private SocketThread socketThread = null;
    private Logger logger = Logger.getLogger(SocketServiceLoader.class);
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("***SocketServiceLoader-contextInitialized***" + servletContextEvent.toString());
        // 获取容器和相关的bean
        ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
        logger.info("TOMCAT初始化 - 获取容器和相关的bean：" + ac);
        UserDao userDao = (UserDao) ac.getBean("userDao");
        AssignmentDao assignmentDao = (AssignmentDao) ac.getBean("assignmentDao");
        RoleDao roleDao = (RoleDao) ac.getBean("roleDao");
        AuthorizationDao authorizationDao = (AuthorizationDao) ac.getBean("authorizationDao");
        PermissionDao permissionDao = (PermissionDao)ac.getBean("permissionDao");
        List<User> userCheck = userDao.rootCheck();
        if (userCheck.size() == 0) {
            Role role = new Role();
            role.setRoleId(1);
            role.setRoleName("超级管理员");
            role.setRoleDescription("超级管理员");
            role.setRoleStatus("1");
            roleDao.rootRole(role);
            logger.info("***添加角色 - 超级管理员***" + role);
            List<Permission> permissionList = permissionDao.listPermission();
            permissionList.forEach(permission -> {
                authorizationDao.insertAuthorization(role.getRoleId(), permission.getpermissionId());
            });
            logger.info("***添加权限认证 - 超级管理员***" + permissionList);
            User initUser = new User();
            initUser.setUserId(1);
            initUser.setUserCode("0001");
            initUser.setUserLoginName("admin");
            initUser.setUserPassword(MD5Util.getResult("123456"));
            initUser.setUserName("超级管理员");
            initUser.setUserPhone("12345678900");
            initUser.setAdminDivisionId(new BigInteger("11"));
            initUser.setUserQuestion(2);
            int status = userDao.rootUser(initUser);
            logger.info("***初始化用户-超级管理员***" + initUser + ", status=" + status);
            assignmentDao.insertAssign(1,1);
        } else {
            logger.info("***数据库未初始化, 用户信息***" + userCheck);
        }
        if (null == socketThread) {
            socketThread = new SocketThread(null);
            socketThread.start();
            logger.info("***SocketServiceLoader初始化，调用contextInitialized方法***");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        logger.info("***SocketServiceLoader-contextDestroyed***" + servletContextEvent.toString());
        if (null != socketThread && !socketThread.isInterrupted()) {
            logger.info("***SocketServiceLoader关闭，调用contextDestroyed方法:关闭socketServer同时中断线程***");
            socketThread.interrupt();
            socketThread.closeSocketServer();
        }
    }
}
