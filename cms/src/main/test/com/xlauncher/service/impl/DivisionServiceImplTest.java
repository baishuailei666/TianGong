package com.xlauncher.service.impl;

import com.xlauncher.dao.UserDao;
import com.xlauncher.entity.User;
import com.xlauncher.service.DivisionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
/**
 * DivisionServiceImplTest
 * @author baishuailei
 * @since 2018-07-25
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml","classpath:spring-*.xml"})
@Transactional
public class DivisionServiceImplTest {
    @Autowired
    DivisionService divisionService;
    @Autowired
    UserDao userDao;
    private static String token = "token is for test";
    @Before
    public void beTest() {
        User user = new User();
        user.setUserName("Test Case");
        user.setUserLoginName("DivisionServiceImplTest");
        user.setUserPassword("123456");
        user.setUserPhone("1234567890");
        userDao.insertUser(user);
        User userUp = new User();
        userUp.setUserId(user.getUserId());
        userUp.setToken("token is for test");
        userDao.updateUser(userUp);
    }
    @Test
    public void listDivisionB() throws Exception {
        System.out.println(divisionService.listDivisionB());
    }

}