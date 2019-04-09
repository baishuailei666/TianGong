package com.xlauncher.service.impl;

import com.xlauncher.dao.UserDao;
import com.xlauncher.entity.Question;
import com.xlauncher.entity.User;
import com.xlauncher.service.UserService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml","classpath:spring-*.xml"})
@Transactional
public class UserServiceImpTest {
    @Autowired
    UserService userService;
    private static User user;
    @Autowired
    UserDao userDao;
    private static String token = "token is for test";
    @Before
    public void beTest() {
        User user = new User();
        user.setUserName("Test Case");
        user.setUserLoginName("UserServiceImpTest");
        user.setUserPassword("123456");
        user.setUserPhone("1234567890");
        userDao.insertUser(user);
        User userUp = new User();
        userUp.setUserId(user.getUserId());
        userUp.setToken("token is for test");
        userDao.updateUser(userUp);
    }
    @BeforeClass
    public static void init() throws  Exception{
        user = new User();
        user.setUserLoginName("test");
        user.setUserPassword("123456");
        user.setUserName("testName");
        user.setUserPhone("15912345678");
        user.setUserEmail("test@test.com");
    }

    @Test
    public void register() throws Exception{
        userService.register(user);
    }

    @Test
    public void login() throws Exception {
//        userService.register(user);
//        Map<String,Object> map = new HashMap<>();
//        map.put("userLoginName","test");
//        map.put("userPassword","123456");
//        userService.login(map);
    }

    @Test
    public void userInfo() throws Exception {
        userService.register(user);
        userService.userInfo(token);
     }

    @Test
    public void getUserById() throws Exception {
        System.out.println(userService.getUserById(53,token));
    }

    @Test
    public void listUser() throws Exception {
        System.out.println(userService.listUser("undefined","undefined","undefined",1,"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1MzQyNjEzNzE3NzUsInBheWxvYWQiOiJ7XCJ1c2VyTG9naW5OYW1lXCI6XCJ0ZXN0b25lXCJ9In0.9dVoPYrTpncRgN7coPjUL5PpSoeXOSWsGSp5TsvTJ-Y"));
    }

    @Test
    public void countPage() throws Exception {
        userService.countPage(null,null,null,token);
    }

    @Test
    public void countUserByLoginName() throws Exception {
        userService.countUserByLoginName("test");
    }

    @Test
    public void countUserCode() throws Exception {
        System.out.println("countUserCode:" + userService.countUserCode("1001","1"));
    }

    @Test
    public void insertUser() throws Exception {
    }

    @Test
    public void updateUser() throws Exception {
        Map<String,Object> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("1");
        map.put("userId",123);
        BigInteger bi = new BigInteger("1");
        map.put("adminDivisionId",bi);
        map.put("userOrgId",1);
        map.put("assign",list);
        userService.updateUser(map,token);
    }

    @Test
    public void updatePassword() throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("userId","1");
        map.put("userPassword","123456");
        userService.updatePassword(map);
    }

    @Test
    public void assignList() throws Exception {
        userService.assignList(1,token);
    }

    @Test
    public void deleteUser() throws Exception {
        userService.deleteUser(1,token);
    }

    @Test
    public void insertQuestion() throws Exception {

    }

    @Test
    public void deleteQuestionByUser() throws Exception {
//        userService.deleteQuestionByUser(1,token);
    }

    @Test
    public void updateQuestion() throws Exception {
//        userService.updateQuestion();
    }

    @Test
    public void listQuestion() throws Exception {
        userService.listQuestion(1);
    }

    @Test
    public void questionCheck() throws Exception {
//        userService.questionCheck()
    }

    @Test
    public void infoCheck() throws Exception {
    }

    @Test
    public void checkInfo() throws Exception {
    }

    @Test
    public void mailUser() throws Exception {
        System.out.println("mailUser:" + userService.mailUser(token,"undefined"));
    }

}