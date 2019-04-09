package com.xlauncher.service;

import com.xlauncher.entity.AlertLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 组件告警service层
 * @author 白帅雷
 * @since 2018-05-16
 */
@Service
public interface AlertLogService {

    /**
     * 添加告警日志
     *
     * @param alertLog 告警日志信息
     * @return 数据库影响的行数
     */
    int insertAlertLog(AlertLog alertLog);

    /**
     * 根据参数查询告警日志并导出
     *
     * @param alertPriority 告警日志级别
     * @param alertFileName 告警日志发生文件名
     * @param lowStartTime 告警日志开始时间
     * @param upStartTime 告警日志结束时间
     * @param token 用户令牌
     * @return   告警日志信息
     */
    List<AlertLog> getAlertLogForExcel(@Param("upStartTime") String upStartTime, @Param("lowStartTime") String lowStartTime, @Param("alertPriority") String alertPriority, @Param("alertFileName") String alertFileName, @Param("token") String token);

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
    List<AlertLog> getAlertLog(@Param("upStartTime") String upStartTime, @Param("lowStartTime") String lowStartTime, @Param("alertPriority") String alertPriority, @Param("alertFileName") String alertFileName, @Param("number") int number, @Param("token") String token);

    /**
     * 获取告警日志总数
     *
     * @param upStartTime 发生告警日志的开始时间
     * @param lowStartTime 发生告警日志的结束时间
     * @param alertPriority 发生告警日志的级别
     * @param alertFileName 发生告警日志的文件名
     * @param token 用户令牌
     * @return 告警日志总数
     */
    int countPage(@Param("upStartTime") String upStartTime, @Param("lowStartTime") String lowStartTime, @Param("alertPriority") String alertPriority, @Param("alertFileName") String alertFileName, @Param("token") String token);

    /**
     * 查询所有告警日志文件名
     *
     * @param token 用户令牌
     * @return 返回文件名的集合
     */
    List listFileName(String token);

    /**
     * 根据编号查询告警日志
     *
     * @param id 告警编号
     * @param token 用户令牌
     * @return 告警日志信息
     */
    AlertLog getAlertLogById(@Param("id") int id, @Param("token") String token);

}
