package com.xlauncher.web;

import com.xlauncher.entity.Channel;
import com.xlauncher.service.ChannelService;
import com.xlauncher.util.AuthUtil;
import com.xlauncher.util.CheckToken;
import com.xlauncher.util.userlogin.ActiveUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 真实通道Web层
 * @date 2018-05-08
 * @author 白帅雷
 */
@Controller
@RequestMapping(value = "/channel")
public class ChannelController {
    @Autowired
    private ChannelService channelService;
    private static Logger logger = Logger.getLogger(ChannelController.class);
    @Autowired
    ActiveUtil activeUtil;
    @Autowired
    CheckToken checkToken;
    @Autowired
    AuthUtil authUtil;
    private static final String DESC = "真实设备管理";
    /**
     * 添加通道信息
     *
     * @param channel 添加的通道信息
     * @return 添加通道的结果，非零为成功添加，0为添加失败
     */
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Map<String, Object> addChannel(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestBody @Param("channel") Channel channel) throws SQLException {
        activeUtil.check(request, response);
        if (!authUtil.isAuth(DESC, token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        logger.info("添加通道ChannelController addChannel: 用户id：" + checkToken.checkToken(token).getUserId() + ", 用户姓名：" + checkToken.checkToken(token).getUserName() + ",channel:" + channel);
        String message;
        int retAdd =this.channelService.insertChannel(channel, token);
        if (retAdd == 0) {
            response.setStatus(415);
            message="添加失败！";
        } else {
            message="添加成功！";
        }
        switch (retAdd) {
            case 107:message="添加失败！IPcamera类型的设备只能添加一个通道";
                logger.info("retAdd = 107 添加失败！IPcamera类型的设备只能添加一个通道");
                break;
            case 106:message="添加失败！通道序号必须小于设备的通道数且不能重复";
                logger.info("retAdd = 106 添加失败！通道序号必须小于设备的通道数且不能重复");
                break;
            case 105:message="添加失败！通道所在经纬度已经存在且不能重复";
                logger.info("retAdd = 105 添加失败！通道所在经纬度已经存在且不能重复");
                break;
            default:break;
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("status", retAdd);
        map.put("message",message);
        return map;
    }

    /**
     * 更新通道接口
     * @param response HTTP响应
     * @param channel 通道信息
     * @return 数据库操作影响行数
     */
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Map<String, Object> updateChannel(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestBody Channel channel) throws SQLException {
        activeUtil.check(request, response);
        if (!authUtil.isAuth(DESC, token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        logger.info("更新通道ChannelController updateDevice: 用户id：" + checkToken.checkToken(token).getUserId() + ", 用户姓名：" + checkToken.checkToken(token).getUserName() + ",channel:" + channel);
        int retUpd=this.channelService.updateChannel(channel, token);
        Map<String, Object> map = new HashMap<>(1);
        map.put("status", retUpd);
        if (retUpd == 0) {
            response.setStatus(415);
        }
        return map;
    }

    /**
     * 激活通道
     * @param response 通信的返回状态设置
     * @param token 用户令牌
     * @param channelId 通道编号
     * @return 数据库操作情况激活成功或失败
     */
    @ResponseBody
    @RequestMapping(value = "/activeChannel/{channelId}", method = RequestMethod.PUT)
    public Map<String, Object> activeChannel(HttpServletRequest request, HttpServletResponse response, @RequestHeader("token") String token
            , @PathVariable("channelId") String channelId) throws SQLException {
        activeUtil.check(request, response);
        if (!authUtil.isAuth(DESC, token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        logger.info("激活通道ChannelController activeDevice: 用户id：" + checkToken.checkToken(token).getUserId() + ", 用户姓名：" + checkToken.checkToken(token).getUserName() + ",channelId:" + channelId);
        int retActiveStatus = this.channelService.activeChannel(channelId, token);
        Map<String, Object> map = new HashMap<>(1);
        map.put("status", retActiveStatus);
        if (retActiveStatus == 0) {
            response.setStatus(415);
        }
        return map;
    }

    /**
     * 停用通道
     * @param response 通信的返回状态设置
     * @param token 用户令牌
     * @param channelId 通道编号
     * @return 数据库操作情况停用成功或失败
     */
    @ResponseBody
    @RequestMapping(value = "/disableChannel/{channelId}", method = RequestMethod.PUT)
    public Map<String, Object> disableChannel(HttpServletRequest request, HttpServletResponse response, @RequestHeader("token") String token
            , @PathVariable("channelId") String channelId) throws SQLException {
        activeUtil.check(request, response);
        if (!authUtil.isAuth(DESC, token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        logger.info("停用通道ChannelController disableChannel: 用户id：" + checkToken.checkToken(token).getUserId() + ", 用户姓名：" + checkToken.checkToken(token).getUserName() + ",channelId:" + channelId);
        int retDisableStatus = this.channelService.disableChannel(channelId, token);
        Map<String, Object> map = new HashMap<>(1);
        map.put("status", retDisableStatus);
        if (retDisableStatus == 0) {
            response.setStatus(415);
        }
        return map;
    }

    /**
     * 更新通道状态（dim模块使用）
     * @param channel 新的通道状态信息
     * @return 更新的结果，数据库操作影响行数
     */
    @ResponseBody
    @RequestMapping(value = "/channelStatus", method = RequestMethod.PUT)
    public Map<String, Object> updateChannelStatus(HttpServletResponse response, @RequestBody Channel channel) throws SQLException  {
        logger.info("DIM反馈通道状态ChannelController updateChannelStatus");
        int retUpdStatus =this.channelService.updateChannelStatus(channel);
        Map<String, Object> map = new HashMap<>(1);
        map.put("status", retUpdStatus);
        return map;
    }

    /**
     * 查询通道告警信息并分页展示
     *
     * @param upStartTime     查询条件开始时间
     * @param lowStartTime    查询条件结束时间
     * @param channelHandler 通道负责人
     * @param channelLocation 通道位置
     * @param channelName     通道名称
     * @param number          页码数
     * @return 数据库信息
     */
    @ResponseBody
    @RequestMapping(value = "/page/getStatus/{number}", method = RequestMethod.GET)
    public Map<String, Object> getChannelStatus(HttpServletRequest request, HttpServletResponse response, @RequestHeader("token") String token
            , @RequestParam("upStartTime") String upStartTime, @RequestParam("lowStartTime") String lowStartTime
            , @RequestParam("channelHandler") String channelHandler, @RequestParam("channelLocation") String channelLocation
            , @RequestParam("channelName") String channelName, @PathVariable("number") int number) throws SQLException {
        activeUtil.check(request, response);
        if (!authUtil.isAuth("真实设备告警", token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        logger.info("通道告警ChannelController getChannelStatus: 用户id：" + checkToken.checkToken(token).getUserId() + ", 用户姓名：" + checkToken.checkToken(token).getUserName());
        List<Channel> stringList = this.channelService.getRuntimeChannel(upStartTime, lowStartTime, channelHandler, channelLocation, channelName, (number-1)*10, token);
        if (stringList == null) {
            response.setStatus(415);
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("data", stringList);
        return map;
    }

    /**
     * 查询通道告警count数
     *
     * @param upStartTime     查询条件开始时间
     * @param lowStartTime    查询条件结束时间
     * @param channelHandler 通道负责人
     * @param channelLocation 通道位置
     * @param channelName     通道名称
     * @return 数据库信息
     */
    @ResponseBody
    @RequestMapping(value = "/countStatus", method = RequestMethod.GET)
    public Map<String, Object> countChannelStatus(HttpServletRequest request, HttpServletResponse response, @RequestHeader("token") String token
            , @RequestParam("upStartTime") String upStartTime, @RequestParam("lowStartTime") String lowStartTime
            , @RequestParam("channelHandler") String channelHandler, @RequestParam("channelLocation") String channelLocation
            , @RequestParam("channelName") String channelName) throws SQLException {
        int count = this.channelService.countRuntimeChannel(upStartTime, lowStartTime, channelHandler, channelLocation, channelName);
        Map<String, Object> map = new HashMap<>(1);
        map.put("count", count);
        return map;
    }

    /**
     * 删除通道
     * @param channelId 通道的唯一id
     * @return 删除通道的结果，非零为成功删除，0为删除失败
     */
    @ResponseBody
    @RequestMapping(value = "/{channelId}", method = RequestMethod.DELETE)
    public Map<String, Object> deleteChannel(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token,@PathVariable("channelId") String channelId) throws SQLException {
        activeUtil.check(request, response);
        if (!authUtil.isAuth(DESC, token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        logger.info("删除通道ChannelController deleteChannel: 用户id：" + checkToken.checkToken(token).getUserId() + ", 用户姓名：" + checkToken.checkToken(token).getUserName());
        int retDel = this.channelService.deleteChannel(channelId, token);
        Map<String, Object> map = new HashMap<>(1);
        if (retDel == 0) {
            response.setStatus(415);
        }
        map.put("status", retDel);
        return map;
    }

    /**
     * 根据通道编号查询通道信息
     *
     * @param channelId 通道编号
     * @return 通道信息
     */
    @ResponseBody
    @RequestMapping(value = "/{channelId}", method = RequestMethod.GET)
    public Map<String, Object> getChannelByChannelId(HttpServletRequest request,HttpServletResponse response
            ,@RequestHeader("token") String token,@PathVariable("channelId") String channelId) throws SQLException {
        activeUtil.check(request, response);
        if (!authUtil.isAuth(DESC, token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        logger.info("通道编号查询通道ChannelController getChannelByChannelId: 用户id：" + checkToken.checkToken(token).getUserId() + ", 用户姓名：" + checkToken.checkToken(token).getUserName());
        Channel channel = this.channelService.getChannelByChannelId(channelId, token);
        Map<String, Object> map = new HashMap<>(1);
        if (channel == null) {
            response.setStatus(415);
        }
        map.put("channel", channel);
        return map;
    }

    /**
     * 分页获取通道信息
     *
     * @param number 页码
     * @param channelName 通道名称
     * @param channelLocation 通道位置
     * @param channelStatus 通道状态
     * @return 一页十个通道数据
     */
    @ResponseBody
    @RequestMapping(value = "/page/{number}/{channelSourceId}", method = RequestMethod.GET)
    public Map<String, Object> queryChannel(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @PathVariable("number") int number, @PathVariable("channelSourceId") String channelSourceId, @RequestParam("channelName") String channelName
            , @RequestParam("channelLocation") String channelLocation, @RequestParam("channelStatus") String channelStatus) throws SQLException {
        activeUtil.check(request, response);
        if (!authUtil.isAuth(DESC, token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        logger.info("分页获取通道ChannelController queryChannel: 用户id：" + checkToken.checkToken(token).getUserId() + ", 用户姓名：" + checkToken.checkToken(token).getUserName());
        List<Channel> channelList =this.channelService.listChannel(channelName,channelLocation,channelStatus,(number-1)*10, channelSourceId, token);
        Map<String, Object> map = new HashMap<>(1);
        map.put("channelList", channelList);
        return map;
    }

    /**
     * 获取表的行数
     * @param channelName 通道名称
     * @param channelLocation 通道位置
     * @param channelStatus 通道状态
     * @return 满足条件的通道数
     */
    @ResponseBody
    @RequestMapping(value = "/countChannel/{channelSourceId}",method = RequestMethod.GET)
    public Map<String,Object> pageCountChannel(HttpServletRequest request,HttpServletResponse response
            , @RequestHeader("token") String token, @RequestParam("channelName") String channelName, @PathVariable("channelSourceId") String channelSourceId
            , @RequestParam("channelLocation") String channelLocation, @RequestParam("channelStatus") String channelStatus) throws SQLException {
        Map<String, Object> map = new HashMap<>(1);
        int count= this.channelService.countPageChannel(channelName,channelLocation,channelStatus,channelSourceId);
        map.put("count", count);
        return map;
    }

    /**
     * 获取所有摄像头及其地理信息以及是否有告警事件
     * @return 概览页所需数据
     */
    @ResponseBody
    @RequestMapping(value = "/overview",method = RequestMethod.GET)
    public List overview(HttpServletRequest request,HttpServletResponse response) throws SQLException {
        activeUtil.check(request, response);
        return this.channelService.overview();
    }

    /**
     * 获取所有摄像头及其地理信息以及是否有告警事件
     * @return 概览页所需数据
     */
    @ResponseBody
    @RequestMapping(value = "/channelview",method = RequestMethod.GET)
    public List overviewStatus(HttpServletRequest request,HttpServletResponse response) throws SQLException {
        activeUtil.check(request, response);
        return this.channelService.overviewStatus();
    }

    /**
     * 显示最新5条的设备告警信息
     *
     * @return 所需数据
     */
    @ResponseBody
    @RequestMapping(value = "/fault",method = RequestMethod.GET)
    public List<Channel> getChannelFault(HttpServletRequest request,HttpServletResponse response) throws SQLException {
        activeUtil.check(request, response);
        return this.channelService.getChannelFault();
    }

    /**
     * 饼状图、获取设备告警类型数量
     *
     * @return 所需数据
     */
    @ResponseBody
    @RequestMapping(value = "/faultCount",method = RequestMethod.GET)
    public Map<String, Object> getChannelFaultTypeAndCount(HttpServletRequest request,HttpServletResponse response) throws SQLException {
        activeUtil.check(request, response);
        return this.channelService.getChannelFaultTypeAndCount();
    }

    /**
     * 通道名称查重
     * @param channelName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/channelName",method = RequestMethod.POST)
    public Boolean countChannelName(@RequestBody @Param("channelName") String channelName){
        try {
            channelName = URLDecoder.decode(channelName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("字符编码错误:" + e);
        }
        logger.info("通道名称查重ChannelController countChannelName");
        String[] channelNames = channelName.split("&");
        logger.info("通道名称查重-接收到的参数：" + channelName);
        String[] sb = channelNames[0].split("=");
        logger.info("通道名称查重-接收到的参数：channelNumber:" + sb[1]);
        String[] cId = channelNames[1].split("=");
        logger.info("通道名称查重-接收到的参数：id:" + cId[1]);
        int status = channelService.countChannelName(sb[1],cId[1]);
        switch (status){
            // 1表示已经存在不能添加、修改
            case 1:return false;
            // 0表示不存在可以添加、修改
            case 0:return true;
            default:break;
        }
        return false;
    }

    /**
     * 通道序号查重
     * @param channelNumber
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/channelNumber",method = RequestMethod.POST)
    public Boolean countChannelNumber(@RequestBody @Param("channelNumber") String channelNumber){
        logger.info("通道序号查重ChannelController countChannelNumber");
        String[] channelNumbers = channelNumber.split("&");
        logger.info("通道序号查重-接收到的参数：" + channelNumber);
        String[] sb = channelNumbers[0].split("=");
        logger.info("通道序号查重-接收到的参数：channelNumber:" + sb[1]);
        String[] cId = channelNumbers[1].split("=");
        logger.info("通道序号查重-接收到的参数：id:" + cId[1]);
        int status = channelService.countChannelNumber(Integer.parseInt(sb[1]), cId[1]);
        switch (status){
            case 1:return false;
            case 0:return true;
            default:break;
        }
        return false;
    }

}
