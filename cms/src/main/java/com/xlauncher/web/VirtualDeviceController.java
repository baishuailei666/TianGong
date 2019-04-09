package com.xlauncher.web;

import com.xlauncher.entity.VirtualDevice;
import com.xlauncher.service.VirtualDeviceService;
import org.apache.log4j.Logger;
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
 * 虚拟设备Web层
 * @date 2018-05-09
 * @author 白帅雷
 */
@Controller
@RequestMapping(value = "/virtualDevice")
public class VirtualDeviceController {
    @Autowired
    private VirtualDeviceService virtualDeviceService;
    private static Logger logger = Logger.getLogger(VirtualDeviceController.class);
    /**
     * 添加虚拟设备信息
     * @param virtualDevice 虚拟设备信息
     * @return  添加虚拟设备信息的结果
     */
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Map<String, Object> addVirtualDevice(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestBody VirtualDevice virtualDevice) throws SQLException {
        logger.debug("VirtualDeviceController addVirtualDevice");
        int retAdd = this.virtualDeviceService.insertVirtualDevice(virtualDevice);
        if (retAdd==0) {
            response.setStatus(415);
        }
        String errorMessage = null;
        switch (retAdd){
            case 101:errorMessage="数据重复：推送信息的主键重复导致冲突无法写入数据库。";
                break;
            case 102:errorMessage="数据缺失：推送的信息没有包含必要的信息，有内容缺失。";
                break;
            case 104:errorMessage="数据格式：推送信息的部分字段长度不符合要求。";
                break;
            default:break;
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("status", retAdd);
        map.put("errorMessage",errorMessage);
        return map;
    }

    /**
     * 删除虚拟设备
     * @param virtualDeviceId　虚拟设备编号
     * @return  删除的结果
     */
    @ResponseBody
    @RequestMapping(value = "/{virtualDeviceId}", method = RequestMethod.DELETE)
    public Map<String, Object> deleteVirtualDevice(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @PathVariable("virtualDeviceId") String virtualDeviceId) throws SQLException {
        logger.debug("VirtualDeviceController deleteVirtualDevice");
        int retDel=this.virtualDeviceService.deleteVirtualDevice(virtualDeviceId);
        String errorMessage = null;
        switch (retDel){
            case 300:errorMessage="数据关联：存在其他数据与该数据存在依赖，删除了与其关联的所有数据。";
                break;
            case 303:errorMessage="数据异常：该信息未添加或者已经被删除。";
                break;
            default:break;
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("status", retDel);
        map.put("errorMessage",errorMessage);
        if (retDel==0) {
            response.setStatus(415);
        }
        return map;
    }

    /**
     * 修改虚拟设备
     * @param response 通信的返回状态设置
     * @param virtualDevice 修改的虚拟设备信息
     * @return 数据库的操作情况
     */
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Map<String, Object> updateVirtualDevice(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestBody VirtualDevice virtualDevice) throws SQLException {
        logger.debug("VirtualDeviceController updateVirtualDevice");
        int retUpdStatus = this.virtualDeviceService.updateVirtualDevice(virtualDevice);
        String errorMessage = null;
        Map<String, Object> map = new HashMap<>(1);
        switch (retUpdStatus){
            case 202:errorMessage="数据缺失：无法找到唯一且必要的ID信息定位数据，无法对数据进行更新。";
                break;
            case 203:errorMessage="数据错误：根据编号无法找到对应的信息进行更新。";
                break;
            case 204:errorMessage="数据格式：推送信息的部分字段长度不符合要求。";
                break;
            default:break;
        }
        map.put("status", retUpdStatus);
        map.put("errorMessage",errorMessage);
        if (retUpdStatus == 0) {
            response.setStatus(415);
        }
        return map;
    }
    /**
     * 更新虚拟设备运行状态（dim使用）
     * @param virtualDevice 包含更改虚拟设备状态的设备信息
     * @return 数据库的操作结果
     */
    @ResponseBody
    @RequestMapping(value = "/runTimeStatus", method = RequestMethod.PUT)
    public Map<String, Object> updateRuntimeVirtualDevice(HttpServletResponse response,@RequestBody VirtualDevice virtualDevice) throws SQLException {
        logger.debug("VirtualDeviceController updateRuntimeVirtualDevice");
        int retUpd =this.virtualDeviceService.updateRuntimeVirtualDevice(virtualDevice);
        if (retUpd==0) {
            response.setStatus(415);
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("status", retUpd);
        return map;
    }

    /**
     * 分页获取虚拟设备信息
     *
     * @param number 页码
     * @param token 用户令牌
     * @param virtualDeviceType       虚拟设备类型
     * @param virtualDeviceFaultCode  虚拟设备故障编号
     * @param virtualDeviceStatus 虚拟设备状态
     * @param orgName      虚拟设备所属组织编号
     * @param adminDivisionName 虚拟设备所属行政区划
     * @return 某一页十个设备信息
     */
    @ResponseBody
    @RequestMapping(value = "/page/{number}", method = RequestMethod.GET)
    public Map<String, Object> listVirtualDevice(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @PathVariable int number, @RequestParam("virtualDeviceType") String virtualDeviceType
            , @RequestParam("virtualDeviceFaultCode") int virtualDeviceFaultCode, @RequestParam("virtualDeviceStatus") String virtualDeviceStatus
            , @RequestParam("orgName") String orgName, @RequestParam("adminDivisionName") String adminDivisionName) throws SQLException {
        logger.debug("VirtualDeviceController listVirtualDevice");
        List<VirtualDevice> virtualDeviceList =this.virtualDeviceService.listVirtualDevice(virtualDeviceType,virtualDeviceFaultCode,(number-1)*10, virtualDeviceStatus, orgName, adminDivisionName);
        Map<String, Object> map = new HashMap<>(1);
        map.put("virtualDeviceList", virtualDeviceList);
        if (virtualDeviceList==null) {
            response.setStatus(415);
        }
        return map;
    }

    /**
     * 获取表的行数
     * @param token 用户令牌
     * @param virtualDeviceType       虚拟设备类型
     * @param virtualDeviceFaultCode  虚拟设备故障编号
     * @param virtualDeviceStatus 虚拟设备状态
     * @param orgName      虚拟设备所属组织编号
     * @param adminDivisionName 虚拟设备所属行政区划
     * @return 总数
     */
    @ResponseBody
    @RequestMapping(value = "/count",method = RequestMethod.GET)
    public Map<String,Object> pageCount(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestParam("virtualDeviceType") String virtualDeviceType
            , @RequestParam("virtualDeviceFaultCode") int virtualDeviceFaultCode, @RequestParam("virtualDeviceStatus") String virtualDeviceStatus
            , @RequestParam("orgName") String orgName, @RequestParam("adminDivisionName") String adminDivisionName) throws SQLException {
        logger.debug("VirtualDeviceController pageCount");
        Map<String, Object> map = new HashMap<>(1);
        int count = this.virtualDeviceService.countPage(virtualDeviceType,virtualDeviceFaultCode, virtualDeviceStatus, orgName, adminDivisionName);
        map.put("count", count);
        return map;
    }
}
