package com.xlauncher.dao;

import com.xlauncher.entity.AlertLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 组件告警dao层
 * @author 白帅雷
 * @since 2018-05-16
 */
@Service
public interface AlertLogDao {

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
     * @return   告警日志信息
     */
    List<AlertLog> getAlertLogForExcel(@Param("upStartTime") String upStartTime, @Param("lowStartTime") String lowStartTime, @Param("alertPriority") String alertPriority, @Param("alertFileName") String alertFileName);

    /**
     * 根据参数查询告警日志
     *
     * @param upStartTime 开始时间
     * @param lowStartTime 结束时间
     * @param alertPriority 告警级别
     * @param alertFileName 告警发生的文件名
     * @param number 页码数
     * @return 告警日志信息
     */
    List<AlertLog> getAlertLog(@Param("upStartTime") String upStartTime, @Param("lowStartTime") String lowStartTime, @Param("alertPriority") String alertPriority, @Param("alertFileName") String alertFileName, @Param("number") int number);

    /**
     * 获取告警日志总数
     *
     * @param upStartTime 开始时间
     * @param lowStartTime 结束时间
     * @param alertPriority 告警级别
     * @param alertFileName 告警发生的文件名
     * @return 告警总数
     */
    int countPage(@Param("upStartTime") String upStartTime, @Param("lowStartTime") String lowStartTime, @Param("alertPriority") String alertPriority, @Param("alertFileName") String alertFileName);

    /**
     * 查询所有告警日志文件名
     *
     * @return 返回文件名的集合
     */
    List listFileName();

    /**
     * 根据编号查询告警日志
     *
     * @param id 告警编号
     * @return 告警日志信息
     */
    AlertLog getAlertLogById(int id);
}
