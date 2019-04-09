package com.xlauncher.dao;

import com.xlauncher.entity.Role;
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
public class RoleDaoTest {
    @Autowired
    RoleDao roleDao;

    @Test
    public void insertRole() throws Exception {
        Role role = new Role();
        role.setRoleId(1001);
        role.setRoleName("测试用例");
        role.setRoleDescription("eses");
        role.setRoleStatus("1");
        System.out.println(roleDao.insertRole(role));
    }

    @Test
    public void countRoleName() throws Exception {
        roleDao.countRoleName("s");
    }

    @Test
    public void deleteRole() throws Exception {
        roleDao.deleteRole(1);
    }

    @Test
    public void updateRole() throws Exception {
//        Role role = new Role();
//        role.setRoleId(1);
//        roleDao.updateRole(role);
    }

    @Test
    public void listRole() throws Exception {
        roleDao.listRole();
    }

    @Test
    public void getRoleById() throws Exception {
        roleDao.getRoleById(1);
    }

    @Test
    public void getRoleNameById() throws Exception {
        roleDao.getRoleNameById(1);
    }

}