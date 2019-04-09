package com.xlauncher.service.impl;

import com.xlauncher.dao.UserDao;
import com.xlauncher.entity.User;
import com.xlauncher.service.RoleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml","classpath:spring-*.xml"})
@Transactional
public class RoleServiceImpTest {
    @Autowired
    RoleService roleService;
    @Autowired
    UserDao userDao;
    private static String token = "token is for test";
    @Before
    public void beTest() {
        User user = new User();
        user.setUserName("Test Case");
        user.setUserLoginName("RoleServiceImpTest");
        user.setUserPassword("123456");
        user.setUserPhone("1234567890");
        userDao.insertUser(user);
        User userUp = new User();
        userUp.setUserId(user.getUserId());
        userUp.setToken("token is for test");
        userDao.updateUser(userUp);
    }
    @Test
    public void insertRole() throws Exception {
        Map<String,Object> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        map.put("roleName","testName");
        map.put("roleDescription","testRole");
        map.put("roleStatus","1");
        map.put("authorization",list);
        System.out.println(roleService.insertRole(map,token));
    }

    @Test
    public void countRoleName() throws Exception {
        roleService.countRoleName("test");
    }

    @Test
    public void deleteRole() throws Exception {
        roleService.deleteRole(1,token);
    }

    @Test
    public void updateRole() throws Exception {
//        Map<String,Object> map = new HashMap<>();
//        map.put("roleId","1");
//        roleService.updateRole(map,token);
    }

    @Test
    public void listRole() throws Exception {
        roleService.listRole(token);
    }

    @Test
    public void listAuthorization() throws Exception {
        roleService.listAuthorization(1,token);
    }

    @Test
    public void getRoleById() throws Exception {
        roleService.getRoleById(1,token);
    }

}