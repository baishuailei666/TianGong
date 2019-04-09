package com.xlauncher.service;

import com.xlauncher.dao.UserDao;
import com.xlauncher.entity.OperationLog;
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
 * OperationLogServiceTest
 * @author baishuailei
 * @since 2018-05-11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml","classpath:spring-*.xml"})
@Transactional
public class OperationLogServiceTest {
    @Autowired
    OperationLogService logService;
    @Autowired
    UserDao userDao;
    private static OperationLog log;
    private static String token = "token is for test";
    @BeforeClass()
    public static void init() {
        log = new OperationLog();
        log.setOperationPerson("OperationLogServiceTest");
        log.setOperationTime(DatetimeUtil.getDate(System.currentTimeMillis()));
        log.setOperationType("OperationLogServiceTest");
        log.setOperationModule("OperationLogServiceTest");
        log.setOperationDescription("2018-05-11 OperationLogServiceTest");
        log.setOperationCategory("运营");
        log.setOperationSystemModule("日志管理");
    }
    @Before
    public void beTest() {
        User user = new User();
        user.setUserName("Test Case");
        user.setUserLoginName("OperationLogServiceTest");
        user.setUserPassword("123456");
        user.setUserPhone("1234567890");
        userDao.insertUser(user);
        User userUp = new User();
        userUp.setUserId(user.getUserId());
        userUp.setToken("token is for test");
        userDao.updateUser(userUp);
    }

    @Test
    public void deleteLog() throws SQLException {
        System.out.println("deleteLog: " + this.logService.deleteLog(1, token));
    }

    @Test
    public void listLogForExcel() throws SQLException {
        System.out.println("listLogForExcel: " + this.logService.listLogForExcel("undefined","undefined","undefined","undefined","undefined", token,"undefined","undefined"));
    }

    @Test
    public void listLog() throws SQLException {
        System.out.println("listLog: " + this.logService.listLog("undefined","undefined","undefined","undefined","undefined",1, token,"undefined","undefined"));
    }

    @Test
    public void countPage() throws SQLException {
        System.out.println("countPage: " + this.logService.countPage("undefined","undefined","undefined","undefined","undefined","undefined","undefined"));
    }

    @Test
    public void getOperationLogById() throws SQLException {
        System.out.println("getOperationLogById: " + this.logService.getOperationLogById(1,token));
    }

    @Test
    public void getOperatingLogById() throws SQLException {
        System.out.println("getOperatingLogById: " + this.logService.getOperatingLogById(1,token));
    }

    @Test
    public void getEventLogById() throws SQLException {
        System.out.println("getEventLogById: " + this.logService.getEventLogById(1,token));
    }

    @Test
    public void getRecordModule() throws SQLException {
        System.out.println("getRecordModule: " + this.logService.getRecordModule("undefined","undefined"));
    }

    @Test
    public void getRecordSystemModule() throws SQLException {
        System.out.println("getRecordSystemModule: " + this.logService.getRecordSystemModule("undefined"));
    }
}