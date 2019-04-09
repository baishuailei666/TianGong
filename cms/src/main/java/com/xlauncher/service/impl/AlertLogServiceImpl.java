package com.xlauncher.service.impl;

import com.xlauncher.dao.AlertLogDao;
import com.xlauncher.entity.AlertLog;
import com.xlauncher.service.AlertLogService;
import com.xlauncher.util.CheckToken;
import com.xlauncher.util.OperationLogUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 组件告警实现类
 * @author 白帅雷
 * @date 2018-05-16
 */
@Service(value = "alertLogService")
public class AlertLogServiceImpl implements AlertLogService {

    @Autowired
    private AlertLogDao alertLogDao;
    @Autowired
    private OperationLogUtil logUtil;
    @Autowired
    private CheckToken checkToken;
    private static final String MODULE = "组件告警";
    private static final String SYSTEM_MODULE = "组件管理";
    private static final String CATEGORY = "运维面";
    private int len = 10;
    private static Logger logger = Logger.getLogger(AlertLogServiceImpl.class);
    /**
     * 添加告警日志
     *
     * @param alertLog 告警日志信息
     * @return 数据库影响的行数
     */
    @Override
    public int insertAlertLog(AlertLog alertLog) {
        logger.debug("添加告警日志insertAlertLog:" + alertLog);
        return this.alertLogDao.insertAlertLog(alertLog);
    }

    /**
     * 根据参数查询告警日志并导出
     *
     * @param upStartTime   告警日志结束时间
     * @param lowStartTime  告警日志开始时间
     * @param alertPriority 告警日志级别
     * @param alertFileName 告警日志发生文件名
     * @param token
     * @return 告警日志信息
     */
    @Override
    public List<AlertLog> getAlertLogForExcel(String upStartTime, String lowStartTime, String alertPriority, String alertFileName, String token) {
        logger.debug("根据参数查询告警日志并导出getAlertLogForExcel");
        return this.alertLogDao.getAlertLogForExcel(upStartTime, lowStartTime, alertPriority, alertFileName);
    }

    /**
     * 根据参数查询告警日志
     *
     * @param upStartTime 开始时间
     * @param lowStartTime 结束时间
     * @param alertPriority 告警级别
     * @param alertFileName 告警发生的文件名
     * @param number 页码数
     * @param token 用户令牌
     * @return 告警日志信息
     */
    @Override
    public List<AlertLog> getAlertLog(String upStartTime, String lowStartTime, String alertPriority, String alertFileName, int number, String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询",MODULE,SYSTEM_MODULE,"根据索引条件查询" + (number + 1) + "到" + (number + 11) + "行的告警日志信息",CATEGORY);
        logger.debug("根据参数查询告警日志getAlertLog");
        if (lowStartTime.length() <= len) {
            lowStartTime = lowStartTime + "23:59:59";
        }
        List<AlertLog> logList = this.alertLogDao.getAlertLog(upStartTime,lowStartTime,alertPriority, alertFileName, number);
        if (logList.size() != 0 ) {
            for (AlertLog log : logList) {
                log.setAlertTime(log.getAlertTime().substring(0,19));
            }
        }
        return logList;
    }

    /**
     * 根据编号查询告警日志
     *
     * @param id 告警编号
     * @param token 用户令牌
     * @return 告警日志信息
     */
    @Override
    public AlertLog getAlertLogById(int id, String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询",MODULE,SYSTEM_MODULE,"查询编号为：" + id + "的告警日志信息",CATEGORY);
        logger.debug("根据编号查询告警日志getAlertLogById:" +id);
        AlertLog log = this.alertLogDao.getAlertLogById(id);
        if (log != null) {
            log.setAlertTime(log.getAlertTime().substring(0,19));
        }
        return log;
    }

    /**
     * 获取告警日志总数
     *
     * @param upStartTime   发生告警日志的开始时间
     * @param lowStartTime  发生告警日志的结束时间
     * @param alertPriority 发生告警日志的级别
     * @param alertFileName 发生告警日志的文件名
     * @param token         用户令牌
     * @return 告警日志总数
     */
    @Override
    public int countPage(String upStartTime,String lowStartTime,String alertPriority, String alertFileName, String token) {
        if (lowStartTime.length() <= len) {
            lowStartTime = lowStartTime + "23:59:59";
        }
        return this.alertLogDao.countPage(upStartTime,lowStartTime,alertPriority, alertFileName);
    }

    /**
     * 查询所有告警日志文件名
     *
     * @param token 用户令牌
     * @return 返回文件名的集合
     */
    @Override
    public List listFileName(String token) {
        logger.debug("查询所有告警日志文件名listFileName:");
        return this.alertLogDao.listFileName();
    }
}
