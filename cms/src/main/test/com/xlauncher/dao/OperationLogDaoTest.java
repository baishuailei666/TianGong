package com.xlauncher.dao;

import com.xlauncher.entity.OperationLog;
import com.xlauncher.util.DatetimeUtil;
import com.xlauncher.util.LogConfigurationUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

/**
 * OperationLogDaoTest
 * @author baishuailei
 * @since 2018-05-11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml","classpath:spring-*.xml"})
@Transactional
public class OperationLogDaoTest {
    @Autowired
    OperationLogDao logDao;
    private static OperationLog log;
    @BeforeClass()
    public static void init() {
        log = new OperationLog();
        log.setOperationPerson("launcher");
        log.setOperationTime(DatetimeUtil.getDate(System.currentTimeMillis()));
        log.setOperationType("查询");
        log.setOperationModule("真实设备管理");
        log.setOperationDescription("2018-05-14 OperationLogDaoTest");
        log.setOperationCategory("运维");
        log.setOperationSystemModule("设备管理");
    }
    @Test
    public void insertLog() throws SQLException {
        this.logDao.insertLog(log);
    }

    @Test
    public void deleteLog() throws SQLException {
        System.out.println("deleteLog: " + this.logDao.deleteLog(1));
    }

    @Test
    public void listLogForExcel() throws SQLException {
        System.out.println("listLogForExcel: " + this.logDao.listLogForExcel("undefined","undefined","undefined","undefined","undefined","undefined","undefined"));
    }

    @Test
    public void listLog() throws SQLException {
        System.out.println("listLog: " + this.logDao.listLog("undefined","undefined","undefined","undefined","undefined",1,"undefined","undefined"));
    }

    @Test
    public void countPage() throws SQLException {
        System.out.println("countPage: " + this.logDao.countPage("undefined","undefined","undefined","undefined","undefined","undefined","undefined"));
    }

    @Test
    public void getOperationLogById() throws SQLException {
        System.out.println("getOperationLogById: " + this.logDao.getOperationLogById(10));
    }

    @Test
    public void getOperatingLogById() throws SQLException {
        System.out.println("getOperatingLogById: " + this.logDao.getOperatingLogById(1));
    }

    @Test
    public void getEventLogById() throws SQLException {
        System.out.println("getEventLogById: " + this.logDao.getEventLogById(5));
    }

    @Test
    public void getRecordModuleById() throws SQLException {
        System.out.println("getRecordModuleById: " + this.logDao.getRecordModuleById("010101"));
    }
    @Test
    public void getRecordModuleBySysModule() throws SQLException {
        System.out.println("getRecordModuleBySysModule: " + this.logDao.getRecordModuleBySysModule("事件管理"));
    }
    @Test
    public void upRecordStatus() throws SQLException {

        System.out.println("upRecordStatus: " + this.logDao.upRecordStatus("010101"));
    }

    @Test
    public void getRecordStatus() throws SQLException {
        System.out.println("getRecordStatus: " + this.logDao.getRecordStatus("用户管理"));
    }
    @Test
    public void getRecordModule() throws SQLException {
        System.out.println("getRecordModule: " + this.logDao.getRecordModule("undefined","undefined"));
    }

    @Test
    public void getRecordSystemModule() throws SQLException {
        System.out.println("getRecordSystemModule: " + this.logDao.getRecordSystemModule("undefined"));
    }
}