package com.xlauncher.web;


import com.xlauncher.dao.ComponentDao;
import com.xlauncher.entity.Component;
import com.xlauncher.util.*;
import com.xlauncher.util.synsunnyitec.SynSunnyintecProperties;
import com.xlauncher.util.userlogin.ActiveUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志配置管理：日志是否记录配置
 * @date 2018-05-19
 * @author 白帅雷
 */
@Controller
@RequestMapping(value = "/logConf")
public class LogConfigurationController {
    @Autowired
    SynSunnyintecProperties logConfProperties;
    @Autowired
    ComponentDao componentDao;
    @Autowired
    OperationLogUtil logUtil;
    @Autowired
    CheckToken checkToken;
    @Autowired
    ActiveUtil activeUtil;
    @Autowired
    AuthUtil authUtil;
    private static final String DESC = "日志配置管理";
    private LogConfigurationUtil configurationUtil;
    private Logger logger = Logger.getLogger(LogConfigurationController.class);

    public LogConfigurationController(LogConfigurationUtil configurationUtil) {
        this.configurationUtil = configurationUtil;
    }

    /**
     * 日志是否记录
     *
     * @param request 发送请求
     * @param response 返回结果
     * @param token 用户令牌
     * @param recordConfMap 日志记录配置
     */
    @ResponseBody
    @RequestMapping(value = "/record", method = RequestMethod.POST)
    public Map<String, List<String>> logConf(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestBody Map<String, List<String>> recordConfMap) {
        activeUtil.check(request,response);
        List<String> recordLists = recordConfMap.get("recordId");
        System.out.println(" ****日志是否记录****** " + recordConfMap);
        configurationUtil.configuration(recordLists, token);
        Map<String, List<String>> map = new HashMap<>(1);
        map.put("record",recordLists);
        return map;
    }

    /**
     * 查询日志是否记录子模块
     *
     * @param request 发送请求
     * @param response 返回结果
     * @param token 用户令牌
     */
    @ResponseBody
    @RequestMapping(value = "/getRecord", method = RequestMethod.GET)
    public Map<String, Object> getLogConf(HttpServletRequest request, HttpServletResponse response, @RequestHeader("token") String token) {
        activeUtil.check(request,response);
        return configurationUtil.getRecordModule();
    }

    /**
     * checkModule
     * @param request
     * @param response
     * @param token
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/checkModule", method = RequestMethod.GET)
    public List<Integer> checkModule(HttpServletRequest request, HttpServletResponse response, @RequestHeader("token") String token) {
        activeUtil.check(request,response);
        return configurationUtil.checkModule();
    }

    /**
     * 提供日志的记录状态
     * @param request 发送请求
     * @param response 返回结果
     * @param token 用户令牌
     */
    @ResponseBody
    @RequestMapping(value = "/getRecordStatus", method = RequestMethod.GET)
    public List<Object> getRecordStatus(HttpServletRequest request, HttpServletResponse response, @RequestHeader("token") String token) {
        activeUtil.check(request,response);
        return configurationUtil.getRecordStatus();
    }

    /**
     * 存储配置
     *
     * @param request 发送请求
     * @param response 返回结果
     * @param token 用户令牌
     * @param logConfMap 存储路径配置
     */
    @ResponseBody
    @RequestMapping(value = "/dump", method = RequestMethod.PUT)
    public Map<String, Object> logConfDump(HttpServletRequest request, HttpServletResponse response, @RequestHeader("token") String token, @RequestBody Map<String, String> logConfMap) {
        activeUtil.check(request,response);
        String ftpUsername = logConfMap.get("ftpUsername");
        String ftpPassword = logConfMap.get("ftpPassword");
        String ftpIp =  logConfMap.get("ftpIp");
        String ftpTime = logConfMap.get("ftpTime");
        String ftpLogTime = logConfMap.get("ftpLogTime");
        String ftpImagePath =  logConfMap.get("ftpImagePath");
        String ftpLogPath = logConfMap.get("ftpLogPath");
        String ftpPort = logConfMap.get("ftpPort");
        logger.info("视频图片/日志存储配置logConfDump：" + logConfMap);
        Map<String, Object> map = new HashMap<>(1);
        if (ftpTime == null) {
            Map<String, Object> ftpMap = new HashMap<>(1);
            ftpMap.put("userName",ftpUsername);
            ftpMap.put("password",ftpPassword);
            Component ftp = this.componentDao.getComponentByAbbr("ftp");
            if (ftpImagePath != null) {
                if (!authUtil.isAuth("视频图片存储", token, request)) {
                    response.setStatus(409);
                    return map;
                }
                ftpMap.put("store_path",ftpImagePath);
                ftpMap.put("log_path",ftp.getComponentConfiguration().get("log_path"));
                ftpMap.put("log_time",ftp.getComponentConfiguration().get("log_time"));
                logUtil.opLog(checkToken.checkToken(token).getUserName(),"更新","视频图片存储","设备管理","视频图片存储信息修改, 存储路径:" + ftpImagePath
                        + ", 服务器用户名:" + ftpUsername + ", 服务器密码:" + ftpPassword + ", 服务器地址:" + ftpIp + ", 服务器端口号:" + ftpPort,"运维面");

            } else {
                if (!authUtil.isAuth(DESC, token, request)) {
                    response.setStatus(409);
                    return map;
                }
                ftpMap.put("store_path",ftp.getComponentConfiguration().get("store_path"));
                ftpMap.put("log_path",ftpLogPath);
                ftpMap.put("log_time",ftpLogTime);
                logUtil.opLog(checkToken.checkToken(token).getUserName(),"更新",DESC,"设备管理","视频图片存储信息修改, 存储路径:" + ftpLogPath
                        + ", 服务器用户名:" + ftpUsername + ", 服务器密码:" + ftpPassword + ", 服务器地址:" + ftpIp + ", 服务器端口号:" + ftpPort,"运维面");
            }
            com.xlauncher.entity.Component component = new com.xlauncher.entity.Component();
            // ftp
            component.setId(6);
            component.setComponentIp(ftpIp);
            component.setComponentPort(ftpPort);
            component.setComponentConfiguration(ftpMap);
            logger.info("FtpUtil.connect: ftpIp:" + ftpIp + ",ftpPort:" + ftpPort + ",ftpUsername:" + ftpUsername + ",ftpPassword:" + ftpPassword);
            boolean isLogin = FtpUtil.connect("",ftpIp, Integer.parseInt(ftpPort),ftpUsername,ftpPassword);
            if (isLogin) {
                logger.info("ftp服务器配置写入数据库-ftp模块:" + component);
                int status = this.componentDao.updateComponent(component);
                if (status ==  1) {
                    map.put("isLogin",true);
                    map.put("status", status + "成功");
                    return map;
                } else {
                    map.put("status", status + "失败");
                    return map;
                }
            } else {
                map.put("isLogin",false);
                return map;
            }
        }
        if (ftpLogTime == null) {
            Map<String, Object> ftpMap = new HashMap<>(1);
            ftpMap.put("time",ftpTime);
            com.xlauncher.entity.Component component = new com.xlauncher.entity.Component();
            // es
            component.setId(4);
            component.setComponentConfiguration(ftpMap);
            int status = this.componentDao.updateComponent(component);
            if (status == 1) {
                map.put("status","成功");
            }
            logger.info("ftp服务器配置写入数据库-es模块:" + component);
        }
        logger.info("ftp服务器存储配置:" + logConfMap);
        Thread pushThread = new Thread(new SqlDumpUtil());
        pushThread.start();
        return map;
    }

    /**
     * 给前端提供查询ftp配置信息接口
     *
     * @param request 发送请求
     * @param response 返回结果
     * @param token 用户令牌
     */
    @ResponseBody
    @RequestMapping(value = "/getDump", method = RequestMethod.GET)
    public Component getLogConfDump(HttpServletRequest request, HttpServletResponse response, @RequestHeader("token") String token) {
        activeUtil.check(request,response);
        return this.componentDao.getComponentByAbbr("ftp");
    }
}
