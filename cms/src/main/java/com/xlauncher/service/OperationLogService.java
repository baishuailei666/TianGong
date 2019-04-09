package com.xlauncher.service;

import com.xlauncher.entity.OperationLog;
import com.xlauncher.entity.RecordLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 操作日志Service层
 * @date 2018-05-11
 * @author 白帅雷
 */
@Service
public interface OperationLogService {

    /**
     * 删除用户操作日志
     * @param id 操作日志的id
     * @param token 用户令牌
     * @return 删除操作日期影响的数据库行数
     */
    int deleteLog(int id, String token);

    /**
     * 获取操作日志并导出
     *
     * @param upStartTime 查询的起始时间
     * @param lowStartTime 查询的结束时间
     * @param opPerson 操作用户
     * @param opType 操作类型
     * @param opModule 操作子模块
     * @param token 用户令牌
     * @param opCategory 操作类别
     * @param opSystemModule 操作子系统模块
     * @return 操作日志列表
     */
    List<OperationLog> listLogForExcel(@Param("upStartTime") String upStartTime, @Param("lowStartTime") String lowStartTime
            , @Param("opPerson") String opPerson, @Param("opType") String opType
            , @Param("opModule") String opModule, @Param("token") String token
            , @Param("opCategory") String opCategory, @Param("opSystemModule") String opSystemModule);

    /**
     * 获得所有操作日志，分页展示，条件查询
     *
     * @param upStartTime 查询的起始时间
     * @param lowStartTime 查询的结束时间
     * @param opPerson 操作用户
     * @param opType 操作类型
     * @param opModule 操作模块
     * @param number 页码数
     * @param token 用户令牌
     * @param opCategory 操作类别
     * @param opSystemModule 操作子系统模块
     * @return 满足条件的操作日志列表
     */
    List<OperationLog> listLog(@Param("upStartTime") String upStartTime, @Param("lowStartTime") String lowStartTime
            , @Param("opPerson") String opPerson, @Param("opType") String opType
            , @Param("opModule") String opModule, @Param("number") int number
            , @Param("token") String token, @Param("opCategory") String opCategory, @Param("opSystemModule") String opSystemModule);

    /**
     * 获得count数，分页展示，条件查询
     *
     * @param upStartTime 查询的起始时间
     * @param lowStartTime 查询的结束时间
     * @param opPerson 操作用户
     * @param opType 操作类型
     * @param opModule 操作模块
     * @param opCategory 日志类别
     * @param opSystemModule 操作子系统模块
     * @return 满足条件的操作日志行数
     */
    int countPage(@Param("upStartTime") String upStartTime, @Param("lowStartTime") String lowStartTime
            , @Param("opPerson") String opPerson, @Param("opType") String opType
            , @Param("opModule") String opModule, @Param("opCategory") String opCategory, @Param("opSystemModule") String opSystemModule);

    /**
     * 根据编号查询运维操作日志
     *
     * @param id 告警编号
     * @param token 用户令牌
     * @return 告警日志信息
     */
    OperationLog getOperationLogById(int id, String token);

    /**
     * 根据编号查询运营操作日志
     *
     * @param id 告警编号
     * @param token 用户令牌
     * @return 告警日志信息
     */
    OperationLog getOperatingLogById(int id, String token);

    /**
     * 根据编号查询事件操作日志
     *
     * @param id 告警编号
     * @param token 用户令牌
     * @return 告警日志信息
     */
    OperationLog getEventLogById(int id, String token);

    /**
     * 得到日志记录配置子模块
     *
     * @param recordCategory 系统模块
     * @param recordSystemModule 子系统模块
     * @return 日志记录配置信息
     */
    List<String> getRecordModule(@Param("recordCategory") String recordCategory, @Param("recordSystemModule") String recordSystemModule);

    /**
     * 得到日志记录配置子系统模块
     *
     * @param recordCategory 系统模块
     * @return 日志记录配置信息
     */
    List<String> getRecordSystemModule(@Param("recordCategory") String recordCategory);
}
