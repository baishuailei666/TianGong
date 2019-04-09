package com.xlauncher.service.impl;

import com.xlauncher.dao.UserDao;
import com.xlauncher.entity.Permission;
import com.xlauncher.entity.User;
import com.xlauncher.service.PermissionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml","classpath:spring-*.xml"})
@Transactional
public class PermissionServiceImpTest {
    @Autowired
    PermissionService permissionService;
    @Autowired
    UserDao userDao;
    private static String token = "token is for test";
    @Before
    public void beTest() {
        User user = new User();
        user.setUserName("Test Case");
        user.setUserLoginName("PermissionServiceImpTest");
        user.setUserPassword("123456");
        user.setUserPhone("1234567890");
        userDao.insertUser(user);
        User userUp = new User();
        userUp.setUserId(user.getUserId());
        userUp.setToken("token is for test");
        userDao.updateUser(userUp);
    }
    @Test
    public void insertPermission() throws Exception {
        Permission permission = new Permission();
        permission.setPermissionName("测试");
        permission.setPermissionNote("测试权限");
        permissionService.insertPermission(permission,token);
    }

    @Test
    public void deletePermission() throws Exception {
        permissionService.deletePermission(1,token);
    }

    @Test
    public void updatePermission() throws Exception {
//        Permission permission = new Permission();
//        permission.setpermissionId(1);
//        permissionService.updatePermission(permission,"token");
    }

    @Test
    public void listPermission() throws Exception {
        System.out.println(permissionService.listPermission(token));
    }

}