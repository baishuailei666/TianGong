package com.xlauncher.dao;

import com.xlauncher.entity.User;
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
public class UserDaoTest {
    @Autowired
    UserDao userDao;

    @Test
    public void insertUser() throws Exception {
        User user = new User();
        user.setUserLoginName("测试人员");
        user.setUserPassword("123");
        user.setUserName("姓名");
        user.setUserPhone("555");
        userDao.insertUser(user);
    }

    @Test
    public void getUserById() throws Exception {
        System.out.println(userDao.getUserById(1));
    }

    @Test
    public void getUserByLoginName() throws Exception {
        userDao.getUserByLoginName("login");
    }

    @Test
    public void countUserByLoginName() throws Exception {
        userDao.countUserByLoginName("login");
    }

    @Test
    public void userCheck() throws Exception {
        userDao.userCheck("login","name","cardId","phone");
    }

    @Test
    public void listUser() throws Exception {
        System.out.println(userDao.listUser("undefined","undefined",1));
    }

    @Test
    public void countPage() throws Exception {
        userDao.countPage("undefined","undefined","undefined");
    }

    @Test
    public void deleteUser() throws Exception {
        userDao.deleteUser(1);
    }

    @Test
    public void updateUser() throws Exception {
//        User user =new User();
//        user.setUserId(1);
//        userDao.updateUser(user);
    }

    @Test
    public void checkToken() throws Exception {
        userDao.checkToken("token");
    }

    @Test
    public void deleteToken() throws Exception {
        userDao.deleteToken("token");
    }

    @Test
    public void getToken() throws Exception {
        userDao.getToken("login");
    }

    @Test
    public void countUserCode() throws Exception {
        System.out.println("countUserCode:" + userDao.countUserCode("1001","56"));
    }
    @Test
    public void mailUser() throws Exception {
        System.out.println("mailUser:" + userDao.mailUser("undefined"));
    }

}