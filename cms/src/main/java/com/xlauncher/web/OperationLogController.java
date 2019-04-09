package com.xlauncher.web;

import com.xlauncher.entity.OperationLog;
import com.xlauncher.service.OperationLogService;
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
 * 操作日志web层
 * @date 2018-05-11
 * @author 白帅雷
 */
@Controller
@RequestMapping(value = "/opLog")
public class OperationLogController {
    @Autowired
    OperationLogService operationLogService;
    @Autowired
    ActiveUtil activeUtil;
    @Autowired
    AuthUtil authUtil;
    private static final String DESC = "运维日志";
    /**
     * 根据编号删除操作日志
     *
     * @param id 编号
     * @param token 用户令牌
     * @return 删除操作日志成功与否
     */
    @ResponseBody
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Map<String,Object> deleteLog(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @PathVariable("id") int id) throws SQLException {
        activeUtil.check(request,response);
        int deleteRet = this.operationLogService.deleteLog(id, token);
        if (deleteRet == 0) {
            response.setStatus(415);
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("deleteRet", deleteRet);
        return map;
    }

    /**
     * 分页获取运维操作日志
     *
     * @param upStartTime 查询的起始时间
     * @param lowStartTime 查询的结束时间
     * @param response 通信响应体
     * @param opPerson 操作用户
     * @param opType 操作类型
     * @param opModule 操作子模块
     * @param number 页码
     * @param token 用户令牌
     * @param opCategory 操作类别
     * @param opSystemModule 操作子系统模块
     * @return 操作日志列表
     */
    @ResponseBody
    @RequestMapping(value = "/operation/page/{number}",method = RequestMethod.GET)
    public Map<String,Object> queryAllOperationLog(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestParam("upStartTime")String upStartTime
            , @RequestParam("lowStartTime")String lowStartTime, @RequestParam("opPerson")String opPerson
            , @RequestParam("opType")String opType, @RequestParam("opModule")String opModule
            , @PathVariable("number") int number, @RequestParam("opCategory")String opCategory
            , @RequestParam("opSystemModule")String opSystemModule) throws SQLException {
        activeUtil.check(request,response);
        if (!authUtil.isAuth(DESC, token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        List<OperationLog> operationLogList =this.operationLogService.listLog(upStartTime, lowStartTime, opPerson, opType, opModule, (number-1)*10, token, opCategory,opSystemModule);
        if (operationLogList == null) {
            response.setStatus(415);
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("operationLogList", operationLogList);
        return map;
    }

    /**
     * 分页获取运营操作日志
     *
     * @param upStartTime 查询的起始时间
     * @param lowStartTime 查询的结束时间
     * @param response 通信响应体
     * @param opPerson 操作用户
     * @param opType 操作类型
     * @param opModule 操作子模块
     * @param number 页码
     * @param token 用户令牌
     * @param opCategory 操作类别
     * @param opSystemModule 操作子系统模块
     * @return 操作日志列表
     */
    @ResponseBody
    @RequestMapping(value = "/operating/page/{number}",method = RequestMethod.GET)
    public Map<String,Object> queryAllOperatingLog(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestParam("upStartTime")String upStartTime
            , @RequestParam("lowStartTime")String lowStartTime, @RequestParam("opPerson")String opPerson
            , @RequestParam("opType")String opType, @RequestParam("opModule")String opModule
            , @PathVariable("number") int number, @RequestParam("opCategory")String opCategory
            , @RequestParam("opSystemModule")String opSystemModule) throws SQLException {
        activeUtil.check(request,response);
        if (!authUtil.isAuth("运营日志", token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        List<OperationLog> operatingLogList =this.operationLogService.listLog(upStartTime, lowStartTime, opPerson, opType, opModule, (number-1)*10, token, opCategory,opSystemModule);
        if (operatingLogList == null) {
            response.setStatus(415);
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("operatingLogList", operatingLogList);
        return map;
    }

    /**
     * 获取操作日志总数
     *
     * @param upStartTime 查询的起始时间
     * @param lowStartTime 查询的结束时间
     * @param opPerson 操作用户
     * @param opType 操作类型
     * @param opModule 操作子模块
     * @param opCategory 日志类别
     * @param opSystemModule 操作子系统模块
     * @return 操作日志count数
     */
    @ResponseBody
    @RequestMapping(value = "/count",method = RequestMethod.GET)
    public Map<String,Object> pageCount(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestParam("upStartTime")String upStartTime
            , @RequestParam("lowStartTime")String lowStartTime, @RequestParam("opPerson")String opPerson
            ,@RequestParam("opType")String opType, @RequestParam("opCategory")String opCategory
            , @RequestParam("opSystemModule")String opSystemModule, @RequestParam("opModule")String opModule) throws SQLException {
        activeUtil.check(request,response);
        Map<String, Object> map = new HashMap<>(1);
        int count= this.operationLogService.countPage(upStartTime,lowStartTime,opPerson,opType,opModule,opCategory,opSystemModule);
        map.put("count", count);
        return map;
    }

    /**
     * 根据编号查询运维操作日志
     *
     * @param id　告警日志的编号
     * @return 告警日志信息
     */
    @ResponseBody
    @RequestMapping(value = "/operation/{id}",method = RequestMethod.GET)
    public OperationLog getOperationLogById(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @PathVariable("id") int id) throws SQLException {
        activeUtil.check(request,response);
        if (!authUtil.isAuth(DESC, token, request)) {
            response.setStatus(409);
        }
        return this.operationLogService.getOperationLogById(id, token);
    }

    /**
     * 根据编号查询运营操作日志
     *
     * @param id　告警日志的编号
     * @return 告警日志信息
     */
    @ResponseBody
    @RequestMapping(value = "/operating/{id}",method = RequestMethod.GET)
    public OperationLog getOperatingLogById(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @PathVariable("id") int id) throws SQLException {
        activeUtil.check(request,response);
        if (!authUtil.isAuth("运营日志", token, request)) {
            response.setStatus(409);
        }
        return this.operationLogService.getOperatingLogById(id, token);
    }

    /**
     * 根据编号查询事件操作日志
     *
     * @param id　告警日志的编号
     * @return 告警日志信息
     */
    @ResponseBody
    @RequestMapping(value = "/event/{id}",method = RequestMethod.GET)
    public OperationLog getEventLogById(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @PathVariable("id") int id) throws SQLException {
        activeUtil.check(request,response);
        if (!authUtil.isAuth(DESC, token, request)) {
            response.setStatus(409);
        }
        return this.operationLogService.getEventLogById(id, token);
    }

    /**
     * 得到日志记录配置子模块
     *
     * @return 日志记录配置信息
     */
    @ResponseBody
    @RequestMapping(value = "/recordModule",method = RequestMethod.GET)
    public List<String> getRecordModule(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestParam("recordCategory") String recordCategory, @RequestParam("recordSystemModule") String recordSystemModule) throws SQLException {
        activeUtil.check(request,response);
        return this.operationLogService.getRecordModule(recordCategory,recordSystemModule);
    }

    /**
     * 得到日志记录配置子系统模块
     *
     * @return 日志记录配置信息
     */
    @ResponseBody
    @RequestMapping(value = "/recordSystemModule",method = RequestMethod.GET)
    public List<String> getRecordSystemModule(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestParam("recordCategory") String recordCategory) throws SQLException {
        activeUtil.check(request,response);
        return this.operationLogService.getRecordSystemModule(recordCategory);
    }
}
