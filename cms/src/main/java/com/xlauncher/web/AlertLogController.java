package com.xlauncher.web;

import com.xlauncher.entity.AlertLog;
import com.xlauncher.service.AlertLogService;
import com.xlauncher.util.AuthUtil;
import com.xlauncher.util.userlogin.ActiveUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组件告警控制层
 * @author 白帅雷
 * @since 2018-05-16
 */
@Controller
@RequestMapping(value = "/alertLog")
public class AlertLogController {

    @Autowired
    private AlertLogService alertLogService;
    @Autowired
    ActiveUtil activeUtil;
    @Autowired
    AuthUtil authUtil;
    private static final String DESC = "组件告警";
    /**
     * 根据参数查询告警日志
     *
     * @param number 页码数
     * @param alertPriority 告警日志级别
     * @param alertFileName 告警日志发生文件名
     * @param lowStartTime 告警日志开始时间
     * @param upStartTime 告警日志结束时间
     * @return   告警日志信息
     */
    @ResponseBody
    @RequestMapping(value = "/page/{number}", method = RequestMethod.GET)
    public Map<String, Object> getAlertLog(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestParam("upStartTime")String upStartTime
            , @RequestParam("lowStartTime")String lowStartTime, @RequestParam("alertPriority") String alertPriority
            , @RequestParam("alertFileName") String alertFileName, @PathVariable("number") int number) throws SQLException {
        activeUtil.check(request, response);
        if (!authUtil.isAuth(DESC, token, request)) {
            response.setStatus(409);
            Map<String, Object> map = new HashMap<>(1);
            map.put("alertLogList", "");
            return map;
        }
        List<AlertLog> alertLogList = this.alertLogService.getAlertLog(upStartTime,lowStartTime,alertPriority, alertFileName,(number-1)*10,token);
        Map<String, Object> map = new HashMap<>(1);
        map.put("alertLogList", alertLogList);
        return map;
    }

    /**
     * 获取表的行数
     *
     * @param alertPriority 告警日志级别
     * @param alertFileName 告警日志发生文件名
     * @param lowStartTime 告警日志开始时间
     * @param upStartTime 告警日志结束时间
     * @return 满足条件的表的行数
     */
    @ResponseBody
    @RequestMapping(value = "/count",method = RequestMethod.GET)
    public Map<String,Object> pageCount(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestParam("upStartTime")String upStartTime
            , @RequestParam("lowStartTime")String lowStartTime, @RequestParam("alertPriority") String alertPriority
            , @RequestParam("alertFileName") String alertFileName) throws SQLException {
        activeUtil.check(request, response);
        Map<String, Object> map = new HashMap<>(1);
        if (!authUtil.isAuth(DESC, token, request)) {
            response.setStatus(409);
            return map;
        }
        int count= this.alertLogService.countPage(upStartTime,lowStartTime,alertPriority,alertFileName,token);
        map.put("count", count);
        return map;
    }

    /**
     * 查询所有告警日志文件名
     *
     * @return 告警日志信息
     */
    @ResponseBody
    @RequestMapping(value = "/alertFileName",method = RequestMethod.GET)
    public Map<String,Object> getFileName(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token) throws SQLException {
        activeUtil.check(request, response);
        Map<String, Object> map = new HashMap<>(1);
        if (!authUtil.isAuth(DESC, token, request)) {
            response.setStatus(409);
            return map;
        }
        List fileNameList = this.alertLogService.listFileName(token);
        map.put("alertFileName",fileNameList);
        return map;
    }


    /**
     * 根据编号查询告警日志
     *
     * @param id　告警日志的编号
     * @return 告警日志信息
     */
    @ResponseBody
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Map<String, Object> getAlertLogById(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @PathVariable("id") int id) throws SQLException {
        activeUtil.check(request, response);
        Map<String, Object> map = new HashMap<>(1);
        if (!authUtil.isAuth(DESC, token, request)) {
            response.setStatus(409);
            return map;
        }
        AlertLog alertLog = this.alertLogService.getAlertLogById(id,token);
        String message = "组件告警详细信息 - 告警发生时间：" + alertLog.getAlertTime() + " , 告警发生的毫秒数：" + alertLog.getAlertTimeSpan() + " , 告警发生的线程：" + alertLog.getAlertThread()
                + " , 告警发生的文件名：" + alertLog.getAlertFileName() + " , 告警发生的类名：" + alertLog.getAlertClassName() + " , 告警发生的方法名：" + alertLog.getAlertMethodName()
                + " , 告警发生的行数：" + alertLog.getAlertLineNum() + " , 告警发生的错误信息：" + alertLog.getAlertMessage() ;
        map.put("alertLog", message);
        return map;
    }
}
