package com.xlauncher.util;

import com.xlauncher.dao.UserDao;
import com.xlauncher.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * CheckTokenTest
 * @author baishuailei
 * @since 2018-05-18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml","classpath:spring-*.xml"})
@Transactional
public class CheckTokenTest {
    @Autowired
    UserDao userDao;
    @Autowired
    CheckToken checkToken;
    @Before
    public void beTest() {
        User user = new User();
        user.setUserName("Test Case");
        user.setUserLoginName("CheckTokenTest");
        user.setUserPassword("123456");
        user.setUserPhone("1234567890");
        userDao.insertUser(user);
        User userUp = new User();
        userUp.setUserId(user.getUserId());
        userUp.setToken("token is for test");
        userDao.updateUser(userUp);
    }
    @Test
    public void checkToken() throws Exception {
//        User user = this.userDao.checkToken("token is for test");
//        if (user != null) {
//            System.out.println("checkToken: " + user.getUserName());
//        } else {
//            System.out.println("user is null");
//        }
        System.out.println(checkToken.checkToken("1235"));
    }
}