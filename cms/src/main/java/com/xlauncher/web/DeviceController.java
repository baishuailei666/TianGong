package com.xlauncher.web;

import com.xlauncher.entity.Device;
import com.xlauncher.service.DeviceService;
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
 * 真实设备Web层
 * @date 2018-05-08
 * @author 白帅雷
 */
@Controller
@RequestMapping(value = "/device")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;
    private static Logger logger = Logger.getLogger(DeviceController.class);
    @Autowired
    ActiveUtil activeUtil;
    @Autowired
    CheckToken checkToken;
    @Autowired
    AuthUtil authUtil;
    private static final String DESC = "真实设备管理";
    /**
     * 添加设备信息
     * @param device 设备信息
     * @return  添加设备信息的结果
     */
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Map<String, Object> addDevice(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestBody Device device) throws SQLException {
        activeUtil.check(request, response);
        if (!authUtil.isAuth(DESC, token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        logger.info("添加设备DeviceController addDevice: 用户id：" + checkToken.checkToken(token).getUserId() + "用户姓名：" + checkToken.checkToken(token).getUserName() + ",device:" + device);
        int retAdd = this.deviceService.insertDevice(device, token);
        String message = "";
        if (retAdd == 0) {
            response.setStatus(415);
            message = "添加成功！";
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("status", retAdd);
        map.put("message", message);
        return map;
    }

    /**
     * 删除设备
     * @param deviceId　设备编号
     * @return  删除的结果
     */
    @ResponseBody
    @RequestMapping(value = "/{deviceId}", method = RequestMethod.DELETE)
    public Map<String, Object> deleteDevice(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @PathVariable("deviceId") String deviceId) throws SQLException {
        activeUtil.check(request, response);
        if (!authUtil.isAuth(DESC, token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        logger.info("删除设备DeviceController deleteDevice: 用户id：" + checkToken.checkToken(token).getUserId() + "用户姓名：" + checkToken.checkToken(token).getUserName() + ",deleteId:" + deviceId);
        int retDel = this.deviceService.deleteDevice(deviceId, token);
        String message;
        Map<String, Object> map = new HashMap<>(1);
        if (retDel == 0) {
            response.setStatus(415);
            message="删除失败！";
        } else {
            message="删除成功！";
        }
        map.put("status", retDel);
        map.put("message",message);
        return map;
    }

    /**
     * 修改设备
     * @param response 通信的返回状态设置
     * @param device 修改的设备信息
     * @return 数据库的操作情况
     */
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Map<String, Object> updateDevice(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestBody Device device) throws SQLException {
        activeUtil.check(request, response);
        if (!authUtil.isAuth(DESC, token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        logger.info("修改设备DeviceController updateDevice: 用户id：" + checkToken.checkToken(token).getUserId() + ", 用户姓名：" + checkToken.checkToken(token).getUserName() + ",device:" + device);
        int retUpdStatus = this.deviceService.updateDevice(device, token);
        String message;
        Map<String, Object> map = new HashMap<>(1);
        if (retUpdStatus == 0) {
            response.setStatus(415);
            message="修改失败！";
        } else {
            message="修改成功！";
        }
        switch (retUpdStatus) {
            case 10:message="修改失败！IPCamera通道数不能修改！";
                response.setStatus(406);
                break;
            case 11:message="修改设备成功！ERROR:同步设备DIM服务异常！";
                break;
            default:break;
        }
        map.put("status", retUpdStatus);
        map.put("message",message);
        return map;
    }

    /**
     * 更新设备运行状态（dim使用）
     *
     * @param device 包含更改设备状态的设备信息
     * @return 数据库的操作结果
     */
    @ResponseBody
    @RequestMapping(value = "/runTimeStatus", method = RequestMethod.PUT)
    public Map<String, Object> updateDeviceStatus(HttpServletResponse response, @RequestBody Device device) throws SQLException {
        logger.info("DIM同步反馈设备状态DeviceController updateDeviceStatus");
        int retUpd = this.deviceService.updateRuntimeDevice(device);
        Map<String, Object> map = new HashMap<>(1);
        map.put("status", retUpd);
        return map;
    }

    /**
     * 查询设备告警信息并分页展示
     *
     * @param upStartTime 查询条件开始时间
     * @param lowStartTime 查询条件结束时间
     * @param deviceType 设备类型
     * @param deviceName 设备名称
     * @param deviceFault 设备故障信息
     * @param deviceUserName 设备用户名
     * @param deviceStatus 设备状态
     * @param number 页码数
     * @return 数据库信息
     */
    @ResponseBody
    @RequestMapping(value = "/page/getStatus/{number}", method = RequestMethod.GET)
    public Map<String, Object> getDeviceStatus(HttpServletRequest request, HttpServletResponse response, @RequestHeader("token") String token
            , @RequestParam("upStartTime") String upStartTime, @RequestParam("lowStartTime") String lowStartTime
            , @RequestParam("deviceType") String deviceType, @RequestParam("deviceName") String deviceName, @RequestParam("deviceFault") String deviceFault
            , @RequestParam("deviceUserName") String deviceUserName, @RequestParam("deviceStatus") String deviceStatus
            , @PathVariable("number") int number) throws SQLException {
        activeUtil.check(request, response);
        if (!authUtil.isAuth("真实设备告警", token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        logger.info("设备告警DeviceController getDeviceStatus: 用户id：" + checkToken.checkToken(token).getUserId() + ", 用户姓名：" + checkToken.checkToken(token).getUserName());
        List<Device> stringList = this.deviceService.getRuntimeDevice(upStartTime, lowStartTime, deviceType, deviceName, deviceFault, deviceUserName, deviceStatus, (number-1)*10, token);
        if (stringList == null) {
            response.setStatus(415);
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("data", stringList);
        return map;
    }

    /**
     * 查询设备告警count数
     *
     * @param upStartTime 查询条件开始时间
     * @param lowStartTime 查询条件结束时间
     * @param deviceType 设备类型
     * @param deviceName 设备名称
     * @param deviceFault 设备故障信息
     * @param deviceUserName 设备用户名
     * @param deviceStatus 设备状态
     * @return 数据库信息
     */
    @ResponseBody
    @RequestMapping(value = "/countStatus", method = RequestMethod.GET)
    public Map<String, Object> countDeviceStatus(HttpServletRequest request, HttpServletResponse response, @RequestHeader("token") String token
            , @RequestParam("upStartTime") String upStartTime, @RequestParam("lowStartTime") String lowStartTime
            , @RequestParam("deviceType") String deviceType
            , @RequestParam("deviceName") String deviceName, @RequestParam("deviceFault") String deviceFault
            , @RequestParam("deviceUserName") String deviceUserName, @RequestParam("deviceStatus") String deviceStatus) throws SQLException {
        activeUtil.check(request, response);
        if (!authUtil.isAuth("真实设备告警", token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        int count = this.deviceService.countRuntimeDevice(upStartTime, lowStartTime, deviceType, deviceName, deviceFault, deviceUserName, deviceStatus);
        Map<String, Object> map = new HashMap<>(1);
        map.put("count", count);
        return map;
    }

    /**
     * 显示最新5条的设备告警信息
     *
     * @return 所需数据
     */
    @ResponseBody
    @RequestMapping(value = "/fault",method = RequestMethod.GET)
    public List<Device> getDeviceFault(HttpServletRequest request,HttpServletResponse response
            , @RequestHeader("token") String token) throws SQLException {
        logger.info("显示最新5条的设备告警信息DeviceController getDeviceFault: 用户id：" + checkToken.checkToken(token).getUserId() + ", 用户姓名：" + checkToken.checkToken(token).getUserName());
        activeUtil.check(request, response);
        return this.deviceService.getDeviceFault();
    }

    /**
     * 饼状图、获取设备告警类型数量
     *
     * @return 所需数据
     */
    @ResponseBody
    @RequestMapping(value = "/faultCount",method = RequestMethod.GET)
    public Map<String, Object> getDeviceFaultTypeAndCount(HttpServletRequest request,HttpServletResponse response
            , @RequestHeader("token") String token) throws SQLException {
        logger.info("饼状图、获取设备告警类型数量DeviceController getDeviceFaultTypeAndCount: 用户id：" + checkToken.checkToken(token).getUserId() + ", 用户姓名：" + checkToken.checkToken(token).getUserName());
        activeUtil.check(request, response);
        return this.deviceService.getDeviceFaultTypeAndCount();
    }

    /**
     * 激活设备
     * @param response 通信的返回状态设置
     * @param token 用户令牌
     * @param deviceId 设备编号
     * @return 数据库操作情况激活成功或失败
     */
    @ResponseBody
    @RequestMapping(value = "/activeDevice/{deviceId}", method = RequestMethod.PUT)
    public Map<String, Object> activeDevice(HttpServletRequest request, HttpServletResponse response, @RequestHeader("token") String token
            , @PathVariable("deviceId") String deviceId) throws SQLException {
        activeUtil.check(request, response);
        if (!authUtil.isAuth(DESC, token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        logger.info("激活设备DeviceController activeDevice: 用户id：" + checkToken.checkToken(token).getUserId() + ", 用户姓名：" + checkToken.checkToken(token).getUserName() + ",deviceId:" + deviceId);
        int retActiveStatus = this.deviceService.activeDevice(deviceId, token);
        Map<String, Object> map = new HashMap<>(1);
        map.put("status", retActiveStatus);
        if (retActiveStatus == 0) {
            response.setStatus(415);
        }
        return map;
    }

    /**
     * 停用设备
     * @param response 通信的返回状态设置
     * @param token 用户令牌
     * @param deviceId 设备编号
     * @return 数据库操作情况停用成功或失败
     */
    @ResponseBody
    @RequestMapping(value = "/disableDevice/{deviceId}", method = RequestMethod.PUT)
    public Map<String, Object> disableDevice(HttpServletRequest request, HttpServletResponse response, @RequestHeader("token") String token
            , @PathVariable("deviceId") String deviceId) throws SQLException {
        activeUtil.check(request, response);
        if (!authUtil.isAuth(DESC, token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        logger.info("停用设备DeviceController disableDevice: 用户id：" + checkToken.checkToken(token).getUserId() + ", 用户姓名：" + checkToken.checkToken(token).getUserName() + ",deviceId:" +deviceId);
        int retDisableStatus = this.deviceService.disableDevice(deviceId, token);
        Map<String, Object> map = new HashMap<>(1);
        map.put("status", retDisableStatus);
        if (retDisableStatus == 0) {
            response.setStatus(415);
        }
        return map;
    }

    /**
     * 设备名称查重
     * @param deviceName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deviceName",method = RequestMethod.POST)
    public Boolean countDeviceName(@RequestBody @Param("deviceName") String deviceName){
        try {
            deviceName = URLDecoder.decode(deviceName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("字符编码错误:" + e);
        }
        String[] deviceNames = deviceName.split("&");
        logger.info("设备名称查重-接收到的参数：" + deviceName);
        String[] sb = deviceNames[0].split("=");
        logger.info("设备名称查重-接收到的参数：deviceName" + sb[1]);
        String[] dId = deviceNames[1].split("=");
        logger.info("设备名称查重-接收到的参数：deviceName" + dId[1]);
        int status = deviceService.countDeviceName(sb[1], dId[1]);
        switch (status){
            case 1:return false;
            case 0:return true;
            default:break;
        }
        return false;
    }
    /**
     * 获取单个设备信息
     * @param deviceId 设备编号
     * @return 数据库结果
     */
    @ResponseBody
    @RequestMapping(value = "/{deviceId}", method = RequestMethod.GET)
    public Device queryDeviceByDeviceId(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @PathVariable("deviceId") String deviceId) throws SQLException {
        logger.info("单个设备DeviceController queryDeviceByDeviceId: 用户id：" + checkToken.checkToken(token).getUserId() + "用户姓名：" + checkToken.checkToken(token).getUserName() + ",deviceId:" + deviceId);
        activeUtil.check(request, response);
        Device device = this.deviceService.getDeviceByDeviceId(deviceId, token);
        if (device == null) {
            response.setStatus(415);
        }
        return device;
    }


    /**
     * 分页获取设备信息
     *
     * @param request 请求
     * @param response 返回
     * @param token 用户令牌
     * @param number 页码
     * @param deviceType 设备类型
     * @param deviceFaultCode 设备故障编码
     * @param deviceStatus 设备使用状态
     * @param orgName 设备所属组织
     * @param adminDivisionName 设备所属行政区划
     * @return 某一页十个设备信息
     */
    @ResponseBody
    @RequestMapping(value = "/page/{number}", method = RequestMethod.GET)
    public Map<String, Object> queryDevice(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @PathVariable int number, @RequestParam("deviceType") String deviceType
            , @RequestParam("deviceFaultCode") int deviceFaultCode, @RequestParam("deviceStatus") String deviceStatus
            , @RequestParam("orgName") String orgName, @RequestParam("adminDivisionName") String adminDivisionName) throws SQLException {
        activeUtil.check(request, response);
        if (!authUtil.isAuth(DESC, token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        logger.info("分页获取设备DeviceController queryDevice: 用户id：" + checkToken.checkToken(token).getUserId() + "用户姓名：" + checkToken.checkToken(token).getUserName());
        List<Device> lstDevice =this.deviceService.listDevice(deviceType,deviceFaultCode,(number-1)*10, deviceStatus,orgName,adminDivisionName, token);
        Map<String, Object> map = new HashMap<>(1);
        map.put("deviceList", lstDevice);
        if (lstDevice==null) {
            response.setStatus(415);
        }
        return map;
    }

    /**
     * 获取表的行数
     * @param request 请求
     * @param response 返回
     * @param token 用户令牌
     * @param deviceType 设备类型
     * @param deviceFaultCode 设备故障编码
     * @param deviceStatus 设备使用状态
     * @param orgName 设备所属组织
     * @param adminDivisionName 设备所属行政区划
     * @return 行数
     */
    @ResponseBody
    @RequestMapping(value = "/count",method = RequestMethod.GET)
    public Map<String,Object> pageCount(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestParam("deviceType") String deviceType
            , @RequestParam("deviceFaultCode") int deviceFaultCode, @RequestParam("deviceStatus") String deviceStatus
            , @RequestParam("orgName") String orgName, @RequestParam("adminDivisionName") String adminDivisionName) throws SQLException {
        activeUtil.check(request, response);
        if (!authUtil.isAuth(DESC, token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        Map<String, Object> map = new HashMap<>(2);
        int count = this.deviceService.countPage(deviceType,deviceFaultCode,deviceStatus,orgName,adminDivisionName);
        map.put("count", count);
        return map;
    }
}

