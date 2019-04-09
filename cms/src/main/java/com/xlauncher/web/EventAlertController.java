package com.xlauncher.web;

import com.xlauncher.entity.EventAlert;
import com.xlauncher.service.EventAlertService;
import com.xlauncher.util.AuthUtil;
import com.xlauncher.util.CheckToken;
import com.xlauncher.util.userlogin.ActiveUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 告警事件web层
 * @author 白帅雷
 * @date  2018-05-23
 */
@Controller
@RequestMapping(value = "/eventAlert")
public class EventAlertController {
    @Autowired
    EventAlertService eventAlertService;
    private Logger logger = Logger.getLogger(EventAlertController.class);
    @Autowired
    CheckToken checkToken;
    @Autowired
    ActiveUtil activeUtil;
    @Autowired
    AuthUtil authUtil;
    private static final String DESC = "事件告警复核";
    /**
     * 根据索引条件查询分页告警事件未复核信息
     *
     * @param upStartTime 查询的起始时间
     * @param lowStartTime 查询的结束时间
     * @param typeDescription 事件类型
     * @param channelLocation 通道位置
     * @param channelHandler  事件负责人
     * @param channelOrg   通道所属组织
     * @param number 页码
     * @param token 用户令牌
     * @return 告警事件event列表
     */
    @ResponseBody
    @RequestMapping(value = "/notCheck/page/{number}", method = RequestMethod.GET)
    public Map<String, Object> listNotCheckAlert(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestParam("upStartTime")String upStartTime
            , @RequestParam("lowStartTime")String lowStartTime, @RequestParam("typeDescription") String typeDescription
            , @RequestParam("channelLocation") String channelLocation, @RequestParam("channelHandler") String channelHandler
            , @RequestParam("channelOrg") String channelOrg, @PathVariable("number") int number) throws SQLException {
        logger.info("分页告警事件 未复核信息：用户id:" + checkToken.checkToken(token).getUserId() + ",用户姓名:" + checkToken.checkToken(token).getUserName());
        activeUtil.check(request,response);
        if (!authUtil.isAuth(DESC, token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        List<EventAlert> eventAlertList = this.eventAlertService.listNotCheckAlert(upStartTime, lowStartTime, typeDescription, channelLocation, channelHandler, channelOrg, (number-1)*10, token);
        Map<String, Object> map = new HashMap<>(1);
        map.put("eventAlertList", eventAlertList);
        return map;
    }

    /**
     * 获得未复核事件的总数
     *
     * @param upStartTime 查询的起始时间
     * @param lowStartTime 查询的结束时间
     * @param typeDescription 事件类型
     * @param channelLocation 通道位置
     * @param channelHandler  事件负责人
     * @param channelOrg   通道所属组织
     * @return 告警事件event列表
     */
    @ResponseBody
    @RequestMapping(value = "/notCheck/count",method = RequestMethod.GET)
    public Map<String,Object> pageNotCheckCount(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestParam("upStartTime")String upStartTime
            , @RequestParam("lowStartTime")String lowStartTime, @RequestParam("typeDescription") String typeDescription
            , @RequestParam("channelLocation") String channelLocation, @RequestParam("channelHandler") String channelHandler
            , @RequestParam("channelOrg") String channelOrg) throws SQLException {
        activeUtil.check(request,response);
        if (!authUtil.isAuth(DESC, token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        Map<String, Object> map = new HashMap<>(1);
        int count= this.eventAlertService.pageNotCheckCount(upStartTime, lowStartTime, typeDescription, channelLocation, channelHandler, channelOrg);
        map.put("count", count);
        return map;
    }

    /**
     * 根据索引条件查询分页告警事件已复核信息
     *
     * @param upStartTime 查询的起始时间
     * @param lowStartTime 查询的结束时间
     * @param upCheckStartTime 复核的起始时间
     * @param lowCheckStartTime 复核的结束时间
     * @param typeDescription 事件类型
     * @param typeStatus 告警状态
     * @param channelLocation 通道位置
     * @param channelHandler  事件负责人
     * @param channelOrg   通道所属组织
     * @param eventReviewer 事件复核人
     * @param typeRectify 事件复核类型
     * @param number 页码
     * @param token 用户令牌
     * @return 告警事件event列表
     */
    @ResponseBody
    @RequestMapping(value = "/check/page/{number}", method = RequestMethod.GET)
    public Map<String, Object> listCheckAlert(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestParam("upStartTime")String upStartTime
            , @RequestParam("lowStartTime")String lowStartTime, @RequestParam("upCheckStartTime")String upCheckStartTime
            , @RequestParam("lowCheckStartTime")String lowCheckStartTime, @RequestParam("typeDescription") String typeDescription
            , @RequestParam("typeStatus") String typeStatus, @RequestParam("channelLocation") String channelLocation
            , @RequestParam("channelHandler") String channelHandler, @RequestParam("channelOrg") String channelOrg
            , @RequestParam("eventReviewer") String eventReviewer, @RequestParam("typeRectify") String typeRectify, @PathVariable("number") int number) throws SQLException {
        logger.info("分页告警事件 已复核信息：用户id:" + checkToken.checkToken(token).getUserId() + ", 用户姓名:" + checkToken.checkToken(token).getUserName());
        activeUtil.check(request,response);
        if (!authUtil.isAuth(DESC, token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        List<EventAlert> eventAlertList = this.eventAlertService.listCheckAlert(upStartTime, lowStartTime, upCheckStartTime, lowCheckStartTime, typeDescription, typeStatus, channelLocation, channelHandler, channelOrg, eventReviewer, typeRectify, (number-1)*10 , token);
        Map<String, Object> map = new HashMap<>(1);
        map.put("eventAlertList", eventAlertList);
        return map;
    }

    /**
     * 获得已复核事件的总数
     *
     * @param upStartTime 查询的起始时间
     * @param lowStartTime 查询的结束时间
     * @param upCheckStartTime 复核的起始时间
     * @param lowCheckStartTime 复核的结束时间
     * @param typeDescription 事件类型
     * @param typeStatus 告警状态
     * @param channelLocation 通道位置
     * @param channelHandler  事件负责人
     * @param channelOrg   通道所属组织
     * @param eventReviewer 事件复核人
     * @param typeRectify 事件复核类型
     * @return 告警事件event列表
     */
    @ResponseBody
    @RequestMapping(value = "/check/count",method = RequestMethod.GET)
    public Map<String,Object> pageCheckCount(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestParam("upStartTime")String upStartTime
            , @RequestParam("lowStartTime")String lowStartTime, @RequestParam("upCheckStartTime")String upCheckStartTime
            , @RequestParam("lowCheckStartTime")String lowCheckStartTime, @RequestParam("typeDescription") String typeDescription
            , @RequestParam("typeStatus") String typeStatus, @RequestParam("channelLocation") String channelLocation
            , @RequestParam("channelHandler") String channelHandler, @RequestParam("channelOrg") String channelOrg
            , @RequestParam("eventReviewer") String eventReviewer, @RequestParam("typeRectify") String typeRectify) throws SQLException {
        activeUtil.check(request,response);
        if (!authUtil.isAuth(DESC, token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        Map<String, Object> map = new HashMap<>(1);
        int count= this.eventAlertService.pageCheckCount(upStartTime, lowStartTime, upCheckStartTime, lowCheckStartTime, typeDescription, typeStatus, channelLocation, channelHandler, channelOrg, eventReviewer, typeRectify);
        map.put("count", count);
        return map;
    }


    /**
     * 从ES模块接收告警事件的接口
     *
     * @param eventAlert 告警事件的重要信息
     * @return 接收告警事件成功返回1，失败返回0
     */
    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.POST)
    public Map<String,Object> receive(@RequestBody EventAlert eventAlert){
        logger.info("从ES模块接收告警事件的接口" + eventAlert);
        Map<String, Object> map = new HashMap<>(1);
        int retPut = this.eventAlertService.insertAlert(eventAlert);
        map.put("status", retPut);
        return map;
    }

    /**
     * 根据告警事件ID获取告警事件
     *
     * @param eventId 告警事件ID
     * @param response 返回给前端的数据载体
     * @return eventAlert
     */
    @ResponseBody
    @RequestMapping(value = "/{eventId}", method = RequestMethod.GET)
    public Map<String, Object> getAlertByEventId(@PathVariable int eventId, HttpServletResponse response, HttpServletRequest request, @RequestHeader("token") String token) {
        logger.info("根据告警事件ID获取告警事件：用户id:" + checkToken.checkToken(token).getUserId() + ", 用户姓名:" + checkToken.checkToken(token).getUserName() + ",eventId:" + eventId);
        if (!authUtil.isAuth(DESC, token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        EventAlert eventAlert = this.eventAlertService.getAlertByEventId(eventId, token);
        Map<String, Object> map = new HashMap<>(1);
        map.put("getAlertByEventId", eventAlert);
        return map;
    }

    /**
     * 根据告警事件ID获取图片
     *
     * @param eventId 告警事件ID
     * @param httpServletResponse 返回给前端的数据载体
     * @return 获取图片状态的map和HttpServletResponse
     */
    @ResponseBody
    @RequestMapping(value = "/image/{eventId}", method = RequestMethod.GET)
    public Map<String, Object> getImgByEventId(@PathVariable int eventId, HttpServletResponse httpServletResponse) {
        int ret;
        logger.info("根据告警事件ID获取图片" + eventId);
        httpServletResponse.setContentType("image/jpg");
        httpServletResponse.setBufferSize(1024);
        try {
            logger.info("OutputStream outputStream = httpServletResponse.getOutputStream()");
            OutputStream outputStream = httpServletResponse.getOutputStream();
            byte[] imgData = this.eventAlertService.getImgData(eventId);
            logger.info("根据告警事件ID获取告警事件数据imgData.length:" + imgData.length);
//            //将从服务器上拉取的图片字节流存入本地文件
//            logger.info("将从服务器上拉取的图片字节流存入本地文件" + imgData.length);
//            File file = new File("/tmp/test.jpg");
//            FileOutputStream fo = new FileOutputStream(file);
//            fo.write(imgData);
//            fo.flush();
//            fo.close();

            logger.info("OutputStream outputStream = httpServletResponse.getOutputStream() , imgData.length:" + imgData.length);
            outputStream.write(imgData);
            outputStream.flush();
            outputStream.close();
            ret = 1;
        } catch (IOException e) {
            logger.error("获取告警图片失败！" + e.getMessage());
            ret = 0;
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("status", ret);
        return map;
    }

    /**
     * 查询告警事件的类型，同步给ES模块
     *
     * @param typeDescription 告警事件类型
     * @return 告警类型
     */
    @ResponseBody
    @RequestMapping(value = "/eventType", method = RequestMethod.GET)
    public Map getEventType(HttpServletRequest request, HttpServletResponse response, @RequestHeader("token") String token, @RequestParam("typeDescription") String typeDescription){
        logger.info("查询告警事件的类型，同步给ES模块：用户id:" + checkToken.checkToken(token).getUserId() + ",用户姓名:" + checkToken.checkToken(token).getUserName());
        activeUtil.check(request,response);
        if (this.eventAlertService.getEventType(token, typeDescription) != null) {
            return this.eventAlertService.getEventType(token, typeDescription);
        } else {
            Map<String, String> map = new HashMap<>(1);
            map.put("error","ES组件不稳定，无法获得数据");
            return map;
        }
    }

    /**
     * 查询告警事件的类型，同步给ES模块
     *
     * @return 告警类型
     */
    @ResponseBody
    @RequestMapping(value = "/eventType/{id}", method = RequestMethod.GET)
    public Map getEventTypeById(HttpServletRequest request, HttpServletResponse response, @RequestHeader("token") String token, @PathVariable("id") String id){
        logger.info("查询告警事件的类型，同步给ES模块：用户id:" + checkToken.checkToken(token).getUserId() + ",用户姓名:" + checkToken.checkToken(token).getUserName() + ",id:" + id);
        activeUtil.check(request,response);
        if (!authUtil.isAuth("事件配置管理", token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        if (this.eventAlertService.getEventTypeById(id) != null) {
            return this.eventAlertService.getEventTypeById(id);
        } else {
            Map<String, String> map = new HashMap<>(1);
            map.put("error","ES组件不稳定，无法获得数据");
            return map;
        }
    }

    /**
     * 添加告警事件的类型，同步给ES模块
     *
     * @return 200
     */
    @ResponseBody
    @RequestMapping(value = "/eventType", method = RequestMethod.POST)
    public Map<String, Object> addEventType(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestBody Map<String, Object> addMap){
        logger.info("添加告警事件的类型，同步给ES模块：用户id:" + checkToken.checkToken(token).getUserId() + ",用户姓名:" + checkToken.checkToken(token).getUserName() + "addMap:" + addMap);
        activeUtil.check(request,response);
        if (!authUtil.isAuth("事件配置管理", token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        Map<String, Object> map = new HashMap<>(1);
        String message = "";
        int status = this.eventAlertService.addEventType(addMap, token);
        switch (status) {
            case 1:message="添加成功!";
                break;
            case 0:message="添加失败!";
                break;
            case -1:message="告警类型已存在!";
                break;
            default:break;
        }
        map.put("status",status);
        map.put("message",message);
        return map;
    }

    /**
     * 添加告警事件的时间配置，同步给ES模块
     *
     * @return 200
     */
    @ResponseBody
    @RequestMapping(value = "/eventTime", method = RequestMethod.POST)
    public Map<String, Object> addEventTime(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestBody Map<String, Object> eventTime){
        activeUtil.check(request,response);
        Map<String, Object> map = new HashMap<>(1);
        int value = this.eventAlertService.addEventTime(eventTime, token);
        map.put("addStatus",value);
        return map;
    }

    /**
     * 删除告警事件的类型，同步给ES模块
     *
     * @return 200
     */
    @ResponseBody
    @RequestMapping(value = "/eventType/{id}", method = RequestMethod.DELETE)
    public Map<String, Object> deleteEventType(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @PathVariable("id") int id){
        logger.info("删除告警事件的类型，同步给ES模块：用户id:" + checkToken.checkToken(token).getUserId() + ",用户姓名:" + checkToken.checkToken(token).getUserName() + "id:" + id);
        activeUtil.check(request,response);
        if (!authUtil.isAuth("事件配置管理", token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        Map<String, Object> deleteMap = new HashMap<>(1);
        deleteMap.put("id",id);
        int value = this.eventAlertService.deleteEventType(deleteMap, token);
        Map<String, Object> map = new HashMap<>(1);
        map.put("deleteStatus",value);
        return map;
    }

    /**
     * 修改告警事件的类型，同步给ES模块
     *
     * @return 200
     */
    @ResponseBody
    @RequestMapping(value = "/eventType", method = RequestMethod.PUT)
    public Map<String, Object> putEventType(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token , @RequestBody Map<String, Object> putMap){
        logger.info("修改告警事件的类型，同步给ES模块：用户id:" + checkToken.checkToken(token).getUserId() + ",用户姓名:" + checkToken.checkToken(token).getUserName() + "putMap:" + putMap);
        activeUtil.check(request,response);
        if (!authUtil.isAuth("事件配置管理", token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        Map<String, Object> map = new HashMap<>(1);
        int value = this.eventAlertService.putEventType(putMap, token);
        map.put("putStatus",value);
        return map;
    }

    /**
     * 更新告警事件的状态，获取告警事件处理事件
     *
     * @param eventAlert 告警事件
     * @param token 用户令牌
     * @return 成功返回1；失败返回0
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public Map<String,Object> updateEventCheck(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestBody EventAlert eventAlert){
        logger.info("更新告警事件的状态，获取告警事件处理事件：用户id:" + checkToken.checkToken(token).getUserId() + ",用户姓名:" + checkToken.checkToken(token).getUserName() + "eventAlert:" + eventAlert);
        activeUtil.check(request,response);
        if (!authUtil.isAuth(DESC, token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        int retUp = this.eventAlertService.updateEventCheck(eventAlert, token);
        if (retUp == 0) {
            response.setStatus(415);
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("upStatus", retUp);
        return map;
    }

    /**
     * 查询已复核告警事件数量
     *
     * @param typeStatus 事件的正确状态
     * @param time 查询条件（年、季、月、周）
     * @return count数
     */
    @ResponseBody
    @RequestMapping(value = "/checkCount", method = RequestMethod.GET)
    public Map<String,Object> getCheckTypeStatusCount(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestParam("typeStatus")String typeStatus, @RequestParam("time") String time){
        activeUtil.check(request,response);
        if (!authUtil.isAuth("事件告警监控中心", token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        logger.info("查询已复核告警事件数量getCheckTypeStatusCount：用户id:" + checkToken.checkToken(token).getUserId() + ",用户姓名:" + checkToken.checkToken(token).getUserName());
        logger.info("请求的参数typeStatus：" + typeStatus + ", time: " + time);
        Map<String,Object> map = new HashMap<>(1);
        String undefined = "different";
        if (Objects.equals(typeStatus, undefined)) {
            String[] type = {"正确告警","错误识别","信息误报"};
            Map<String,Object> correctMap = this.eventAlertService.getCheckTypeStatusCount(type[0],time,token);
            Map<String,Object> errorMap = this.eventAlertService.getCheckTypeStatusCount(type[1],time,token);
            Map<String,Object> infoMap = this.eventAlertService.getCheckTypeStatusCount(type[2],time,token);
            map.put("time",correctMap.get("time"));
            map.put("correctData",correctMap.get("data"));
            map.put("errorData",errorMap.get("data"));
            map.put("infoData",infoMap.get("data"));
        } else {
            map = this.eventAlertService.getCheckTypeStatusCount(typeStatus,time,token);
        }
        return map;
    }

    /**
     * 得到告警类型数量
     *
     * @return stringList
     */
    @ResponseBody
    @RequestMapping(value = "/getTypeCount", method = RequestMethod.GET)
    public List< Map<String, Object>> getTypeDescriptionCount(HttpServletRequest request, HttpServletResponse response, @RequestHeader("token") String token, @RequestParam("typeDescription") String typeDescription){
        activeUtil.check(request,response);
        return this.eventAlertService.getTypeDescriptionCount(typeDescription,token);
    }

    /**
     * 获取未复核告警事件总数
     *
     * @return count数
     */
    @ResponseBody
    @RequestMapping(value = "/notCheckCount", method = RequestMethod.GET)
    public Map<String,Object> getNotCheckCount(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token){
        activeUtil.check(request,response);
        if (!authUtil.isAuth("事件告警监控中心", token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        int count = this.eventAlertService.getNotCheckCount(token);
        Map<String, Object> map = new HashMap<>(1);
        map.put("count", count);
        return map;
    }

    /**
     * 查询最新的未复核告警事件（5条显示）
     * @return eventAlert
     */
    @ResponseBody
    @RequestMapping(value = "/notCheckEvent", method = RequestMethod.GET)
    public Map<String,Object> getNotCheckEvent(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token){
        if (!authUtil.isAuth("事件告警监控中心", token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        List<EventAlert> notCheckEventList = this.eventAlertService.getNotCheckEvent(token);
        Map<String, Object> map = new HashMap<>(1);
        map.put("notCheckEventList", notCheckEventList);
        return map;
    }
}
