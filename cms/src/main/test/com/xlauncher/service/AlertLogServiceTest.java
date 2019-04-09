package com.xlauncher.service;

import com.xlauncher.dao.UserDao;
import com.xlauncher.entity.AlertLog;
import com.xlauncher.entity.User;
import com.xlauncher.util.DatetimeUtil;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

/**
 * AlertLogServiceTest
 * @author baishuailei
 * @since 2018-05-14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml","classpath:spring-*.xml"})
@Transactional
public class AlertLogServiceTest {
    @Autowired
    AlertLogService alertLogService;
    @Autowired
    UserDao userDao;
    private static AlertLog alertLog;
    private static String token = "token is for test";
    @BeforeClass()
    public static void init() {
        alertLog = new AlertLog();
        alertLog.setAlertPriority("DEBUG");
        alertLog.setAlertTime(DatetimeUtil.getDate(System.currentTimeMillis()));
        alertLog.setAlertTimeSpan(150);
        alertLog.setAlertThread("Thread 10");
        alertLog.setAlertLineNum("150");
        alertLog.setAlertFileName("AlertLogServiceTest");
        alertLog.setAlertClassName("AlertLogServiceTest.class");
        alertLog.setAlertMethodName("insertAlertLog");
        alertLog.setAlertMessage("Alert Service Test");
        alertLog.setAlertType("FileNotFoundException");
    }
    @Before
    public void beTest() {
        User user = new User();
        user.setUserName("Test Case");
        user.setUserLoginName("Test Case");
        user.setUserPassword("123456");
        user.setUserPhone("1234567890");
        userDao.insertUser(user);
        User userUp = new User();
        userUp.setUserId(user.getUserId());
        userUp.setToken("token is for test");
        userDao.updateUser(userUp);
    }
    @Test
    public void insertAlertLog() throws SQLException {
        System.out.println("insertAlertLog: " + this.alertLogService.insertAlertLog(alertLog));
    }

    @Test
    public void getAlertLogForExcel() throws SQLException {
        System.out.println("getAlertLogForExcel: " + this.alertLogService.getAlertLogForExcel("undefined","undefined","undefined","undefined",token));
    }

    @Test
    public void getAlertLog() throws SQLException {
        System.out.println("getAlertLog: " + this.alertLogService.getAlertLog("undefined","undefined","undefined","undefined",1,token));
    }

    @Test
    public void countPage() throws SQLException {
        System.out.println("countPage: " + this.alertLogService.countPage("undefined","undefined","undefined","undefined",token));
    }

    @Test
    public void listFileName() throws SQLException {
        System.out.println("listFileName: " + this.alertLogService.listFileName(token));
    }

    @Test
    public void getAlertLogById() throws SQLException {
        System.out.println("getAlertLogById: " + this.alertLogService.getAlertLogById(1,token));
    }

}