package com.xlauncher.service.impl;

import com.xlauncher.dao.OperationLogDao;
import com.xlauncher.entity.OperationLog;
import com.xlauncher.entity.RecordLog;
import com.xlauncher.service.OperationLogService;
import com.xlauncher.util.CheckToken;
import com.xlauncher.util.OperationLogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 操作日志ServiceImpl层
 * @date 2018-05-11
 * @author 白帅雷
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {
    @Autowired
    OperationLogDao operationLogDao;
    @Autowired
    private OperationLogUtil logUtil;
    @Autowired
    private CheckToken checkToken;
    private static final String MODULE = "日志配置管理";
    private static final String OPERATION = "运维日志";
    private static final String OPERATING = "运营日志";
    private static final String EVENT = "告警事件日志";
    private static final String SYSTEM_MODULE = "日志管理";
    private static final String CATEGORY = "运维面";
    private static final int LEN = 10;
    /**
     * 删除用户操作日志
     *
     * @param id 操作日志的id
     * @param token 用户令牌
     * @return 删除操作日期影响的数据库行数
     */
    @Override
    public int deleteLog(int id, String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"删除",MODULE,SYSTEM_MODULE,"删除编号为：" + id + "的操作日志",CATEGORY);
        return operationLogDao.deleteLog(id);
    }

    /**
     * 获取操作日志并导出
     *
     * @param upStartTime    查询的起始时间
     * @param lowStartTime   查询的结束时间
     * @param opPerson       操作用户
     * @param opType         操作类型
     * @param opModule       操作子模块
     * @param token          用户令牌
     * @param opCategory     操作类别
     * @param opSystemModule 操作子系统模块
     * @return 操作日志列表
     */
    @Override
    public List<OperationLog> listLogForExcel(String upStartTime, String lowStartTime, String opPerson, String opType, String opModule, String token, String opCategory, String opSystemModule) {
        return this.operationLogDao.listLogForExcel(upStartTime, lowStartTime, opPerson, opType, opModule, opCategory, opSystemModule);
    }

    /**
     * 获得所有操作日志，分页展示，条件查询
     *
     * @param upStartTime  查询的起始时间
     * @param lowStartTime 查询的结束时间
     * @param opPerson     操作用户
     * @param opType       操作类型
     * @param opModule     操作模块
     * @param number       页码数
     * @param token 用户令牌
     * @param opCategory 日志类别
     * @param opSystemModule 操作子系统模块
     * @return 满足条件的操作日志列表
     */
    @Override
    public List<OperationLog> listLog(String upStartTime, String lowStartTime, String opPerson, String opType, String opModule, int number, String token, String opCategory, String opSystemModule) {
        if (Objects.equals(opModule, OPERATION)) {
            logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询",OPERATION,SYSTEM_MODULE,"根据条件开始时间,结束时间,操作人员,操作模块查询" + (number + 1) + "到" + (number + 11) + "行操作日志信息",CATEGORY);
        }
        if (Objects.equals(opModule, OPERATING)) {
            logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询",OPERATING,SYSTEM_MODULE,"根据条件开始时间,结束时间,操作人员,操作模块查询" + (number + 1) + "到" + (number + 11) + "行操作日志信息",CATEGORY);
        }
        if (Objects.equals(opModule, EVENT)) {
            logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询",EVENT,SYSTEM_MODULE,"根据条件开始时间,结束时间,操作人员,操作模块查询" + (number + 1) + "到" + (number + 11) + "行操作日志信息",CATEGORY);
        }
        if (lowStartTime.length() <= LEN) {
            lowStartTime = lowStartTime + "23:59:59";
        }
        List<OperationLog> logList = operationLogDao.listLog(upStartTime,lowStartTime,opPerson,opType,opModule,number,opCategory,opSystemModule);
        if (logList.size() != 0) {
            for (OperationLog log : logList) {
                log.setOperationTime(log.getOperationTime().substring(0,19));
            }
        }
        return logList;
    }

    /**
     * 获得count数，分页展示，条件查询
     *
     * @param upStartTime  查询的起始时间
     * @param lowStartTime 查询的结束时间
     * @param opPerson     操作用户
     * @param opType       操作类型
     * @param opModule     操作模块
     * @param opCategory 日志类别
     * @param opSystemModule 操作子系统模块
     * @return 满足条件的操作日志行数
     */
    @Override
    public int countPage(String upStartTime, String lowStartTime, String opPerson, String opType, String opModule, String opCategory, String opSystemModule) {
        return operationLogDao.countPage(upStartTime,lowStartTime,opPerson,opType,opModule,opCategory,opSystemModule);
    }

    /**
     * 根据编号查询运维操作日志
     *
     * @param id 告警编号
     * @param token 用户令牌
     * @return 告警日志信息
     */
    @Override
    public OperationLog getOperationLogById(int id, String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询",OPERATION,SYSTEM_MODULE,"查询编号为：" + id + "的运维操作日志",CATEGORY);
        return this.operationLogDao.getOperationLogById(id);
    }

    /**
     * 根据编号查询运营操作日志
     *
     * @param id 告警编号
     * @param token 用户令牌
     * @return 告警日志信息
     */
    @Override
    public OperationLog getOperatingLogById(int id, String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询",OPERATING,SYSTEM_MODULE,"查询编号为：" + id + "的运营操作日志",CATEGORY);
        return this.operationLogDao.getOperatingLogById(id);
    }

    /**
     * 根据编号查询事件操作日志
     *
     * @param id 告警编号
     * @param token 用户令牌
     * @return 告警日志信息
     */
    @Override
    public OperationLog getEventLogById(int id, String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询",EVENT,SYSTEM_MODULE,"查询编号为：" + id + "的事件操作日志",CATEGORY);
        return this.operationLogDao.getEventLogById(id);
    }

    /**
     * 得到日志记录配置子模块
     *
     * @param recordCategory 系统模块
     * @param recordSystemModule 子系统模块
     * @return 日志记录配置信息
     */
    @Override
    public List<String> getRecordModule(String recordSystemModule, String recordCategory) {
        return this.operationLogDao.getRecordModule(recordSystemModule, recordCategory);
    }

    /**
     * 得到日志记录配置子系统模块
     *
     * @param recordCategory 系统模块
     * @return 日志记录配置信息
     */
    @Override
    public List<String> getRecordSystemModule(String recordCategory) {
        return this.operationLogDao.getRecordSystemModule(recordCategory);
    }
}
