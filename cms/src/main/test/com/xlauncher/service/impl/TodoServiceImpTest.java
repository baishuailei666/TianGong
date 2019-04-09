package com.xlauncher.service.impl;

import com.xlauncher.dao.UserDao;
import com.xlauncher.entity.User;
import com.xlauncher.service.TodoService;
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
public class TodoServiceImpTest {
    @Autowired
    TodoService todoService;
    @Autowired
    UserDao userDao;
    private static String token = "token is for test";
    @Before
    public void beTest() {
        User user = new User();
        user.setUserName("Test Case");
        user.setUserLoginName("TodoServiceImpTest");
        user.setUserPassword("123456");
        user.setUserPhone("1234567890");
        userDao.insertUser(user);
        User userUp = new User();
        userUp.setUserId(user.getUserId());
        userUp.setToken("token is for test");
        userDao.updateUser(userUp);
    }
    @Test
    public void listRegisterTodo() throws Exception {
        todoService.listRegisterTodo(1,token);
    }

    @Test
    public void countRegisterTodo() throws Exception {
        todoService.countRegisterTodo(token);
    }

    @Test
    public void listResetCodeTode() throws Exception {
        todoService.listResetCodeTode(1,token);
    }

    @Test
    public void countResetCodetode() throws Exception {
        todoService.countResetCodetode(token);
    }

    @Test
    public void resetCode() throws Exception {
        todoService.resetCode(1,token);
    }

    @Test
    public void denyTodo() throws Exception {
        todoService.denyTodo(1,token);
    }

}