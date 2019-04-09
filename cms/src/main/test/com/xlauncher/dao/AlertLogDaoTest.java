package com.xlauncher.dao;

import com.xlauncher.entity.AlertLog;
import com.xlauncher.util.DatetimeUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

/**
 * AlertLogDaoTest
 * @author baishuailei
 * @since 2018-05-14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml","classpath:spring-*.xml"})
@Transactional
public class AlertLogDaoTest {
    @Autowired
    AlertLogDao alertLogDao;
    private static AlertLog alertLog;
    @BeforeClass()
    public static void init() {
        alertLog = new AlertLog();
        alertLog.setAlertPriority("WARN");
        alertLog.setAlertTime(DatetimeUtil.getDate(System.currentTimeMillis()));
        alertLog.setAlertTimeSpan(15060);
        alertLog.setAlertThread("Thread");
        alertLog.setAlertLineNum("120");
        alertLog.setAlertFileName("AlertLogDaoTest");
        alertLog.setAlertClassName("AlertLogDaoTest.class");
        alertLog.setAlertMethodName("insertAlertLog");
        alertLog.setAlertMessage("Alert LogDao Test");
        alertLog.setAlertType("FileNotFoundException");
    }
    @Test
    public void insertAlertLog() throws SQLException {
        System.out.println("insertAlertLog: " + this.alertLogDao.insertAlertLog(alertLog));
    }

    @Test
    public void getAlertLogForExcel() throws SQLException {
        System.out.println("getAlertLogForExcel: " + this.alertLogDao.getAlertLogForExcel("undefined","undefined","undefined","undefined"));
    }

    @Test
    public void getAlertLog() throws SQLException {
        System.out.println("getAlertLog: " + this.alertLogDao.getAlertLog("undefined","undefined","undefined","undefined",1));
    }

    @Test
    public void countPage() throws SQLException {
        System.out.println("countPage: " + alertLogDao.countPage("undefined","undefined","undefined","undefined"));
    }

    @Test
    public void listFileName() throws SQLException {
        System.out.println("listFileName: " + this.alertLogDao.listFileName());
    }

    @Test
    public void getAlertLogById() throws SQLException {
        System.out.println("getAlertLogById: " + this.alertLogDao.getAlertLogById(1));
    }

}