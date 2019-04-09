package com.xlauncher.dao;

import com.xlauncher.entity.Permission;
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
public class PermissionDaoTest {
    @Autowired
    PermissionDao permissionDao;

    @Test
    public void insertPermission() throws Exception {
//        Permission permission = new Permission();
//        permission.setPermissionName("测试权限");
//        permission.setpermissionNote("测试权限");
//        permission.setPermissionSuperiorName("测试模块");
//        permissionDao.insertPermission(new Permission());
    }

    @Test
    public void deletePermission() throws Exception {
        permissionDao.deletePermission(1);
    }

    @Test
    public void updatePermission() throws Exception {
//        Permission permission = new Permission();
//        permission.setpermissionId(1);
//        permission.setPermissionName("aaa");
//        permissionDao.updatePermission(permission);
    }

    @Test
    public void listPermission() throws Exception {
        permissionDao.listPermission();
    }

    @Test
    public void getPermission() throws Exception {
        permissionDao.getPermission(1);
    }

    @Test
    public void getPermissionName() throws Exception {
        permissionDao.getPermissionName(1);
    }

}