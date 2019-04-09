package com.xlauncher.util;

import com.xlauncher.dao.OperationLogDao;
import com.xlauncher.entity.OperationLog;
import com.xlauncher.entity.RecordLog;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 操作日志工具类
 * @date 2018-05-11
 * @author 白帅雷
 */
@Component
public class OperationLogUtil {
    @Autowired
    private OperationLogDao operationLogDao;
    private OperationLog opLog = new OperationLog();
    private static Logger logger = Logger.getLogger(OperationLogUtil.class);
    /**
     * 操作日志工具类
     *
     * @param userName 操作人姓名
     * @param type 操作类型
     * @param module 操作模块
     * @param systemModule 系统模块
     * @param description 操作描述
     * @param category 操作类别
     */
    public void opLog(String userName, String type, String module, String systemModule, String description, String category){
        logger.info("用户操作信息写入数据库：" + "用户姓名：" + userName + "操作类型：" + type + "操作模块：" + module + "操作详情描述：" + description);
        opLog.setOperationPerson(userName);
        opLog.setOperationTime(DatetimeUtil.getDate(System.currentTimeMillis()));
        opLog.setOperationType(type);
        opLog.setOperationModule(module);
        opLog.setOperationSystemModule(systemModule);
        opLog.setOperationDescription(description);
        opLog.setOperationCategory(category);
        operationLogDao.insertLog(opLog);
    }
}
