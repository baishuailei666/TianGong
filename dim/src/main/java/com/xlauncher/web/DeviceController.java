package com.xlauncher.web;

import com.xlauncher.entity.Device;
import com.xlauncher.service.ChannelService;
import com.xlauncher.service.DeviceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备管理web层
 * @author YangDengcheng
 * @date 2018/2/27 15:48
 */
@Controller
@RequestMapping(value = "")
public class DeviceController {

    private static final Logger LOGGER = Logger.getLogger(DeviceController.class);

    @Autowired
    DeviceService deviceService;
    @Autowired
    ChannelService channelService;

    /**
     * 请求所有设备的列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/device",method = RequestMethod.GET)
    public List<Device> queryAllDevice(){
        LOGGER.info("[queryAllDevice] 请求所有设备的列表");
        return deviceService.queryAllDevice();
    }

    /**
     * 查看具体某个设备的详细信息
     *
     * @param deviceId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/device/{deviceId}",method = RequestMethod.GET)
    public Device queryDeviceMsg(@PathVariable String deviceId){
        LOGGER.info("[queryDeviceMsg] 查看具体某个设备的详细信息");
        return deviceService.queryDeviceMsg(deviceId);
    }

    /**
     * 新增一台设备
     *
     * @param device
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/device",method = RequestMethod.POST)
    public Map<String,Integer> insertDevice(@RequestBody Device device){
        Map<String,Integer> resultMap = new HashMap<String, Integer>();
        try {
            LOGGER.info("[URL:/dim/device,method:POST] Invoke DIM's service interface successfully");
            deviceService.insertDevice(device);
            LOGGER.info("DIM exexcutes insert sql and k8s operation successfully");
            resultMap.put("status",1);
            return resultMap;
        }catch (Exception e){
            LOGGER.error("[URL:/dim/device,method:POST] DIM exexcutes sql and k8s operation unsuccessfully" + e);
            resultMap.put("status",0);
            return resultMap;
        }
    }

    /**
     * 修改一台设备信息
     *
     * @param device
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/device",method = RequestMethod.PUT)
    public Map<String,Integer> updateDevice(@RequestBody Device device){
        Map<String,Integer> resultMap = new HashMap<String, Integer>();
        try {
            LOGGER.info("[URL:/dim/device,method:PUT] Invoke DIM's service interface successfully");
            deviceService.updateDeviceMsg(device);
            LOGGER.info("DIM exexcutes sql,update tb_device in database successfully");
            resultMap.put("status",1);
            return resultMap;
        }catch (Exception e){
            LOGGER.error("[URL:/dim/device,method:PUT] DIM exexcutes sql unsuccessfully" + e);
            e.printStackTrace();
            resultMap.put("status",0);
            return resultMap;
        }
    }

    /**
     * 删除一台设备
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/device",method = RequestMethod.DELETE)
    public Map<String,Integer> deleteCamera(@RequestParam String id)  {
        //查询所有在该Device下的Channel
        List channelIdList = deviceService.queryAllInSameDevice(id);

        //删除所有在该Device下的Channel
        for(int i=0;i<channelIdList.size();i++){
            channelService.deleteChannelWithDIM(channelIdList.get(i).toString());
        }

        Map<String,Integer> resultMap = new HashMap<String, Integer>();
        try {
            LOGGER.info("[URL:/dim/device,method:DELETE] Invoke DIM's service interface successfully");
            deviceService.deleteDevice(id);
            LOGGER.info("DIM exexcutes sql,delete tb_device in database successfully");
            resultMap.put("status",1);
            return resultMap;
        }catch (Exception e){
            LOGGER.error("Invoke DIM's service interface unsuccessfully");
            LOGGER.error("[URL:/dim/device,method:DELETE] DIM exexcutes sql and k8s operation unsuccessfully"  + e);
            e.printStackTrace();
            resultMap.put("status",0);
            return resultMap;
        }
    }

    /**
     * 激活设备
     *
     * @param response 通信的返回状态设置
     * @param deviceId 设备编号
     * @return 数据库操作情况激活成功或失败
     */
    @ResponseBody
    @RequestMapping(value = "/activeDevice/{deviceId}", method = RequestMethod.PUT)
    public Map<String, Object> activeDevice(HttpServletResponse response, @PathVariable("deviceId") String deviceId) throws SQLException {
        int retActiveStatus = this.deviceService.activeDevice(deviceId);
        this.channelService.activeDeviceChannel(deviceId);
        Map<String, Object> map = new HashMap<>(1);
        map.put("status", retActiveStatus);
        if (retActiveStatus == 0) {
            response.setStatus(415);
        }
        return map;
    }

    /**
     * 停用设备
     *
     * @param response 通信的返回状态设置
     * @param deviceId 设备编号
     * @return 数据库操作情况停用成功或失败
     */
    @ResponseBody
    @RequestMapping(value = "/disableDevice/{deviceId}", method = RequestMethod.PUT)
    public Map<String, Object> disableDevice(HttpServletResponse response, @PathVariable("deviceId") String deviceId) throws SQLException {
        int retDisableStatus = this.deviceService.disableDevice(deviceId);
        this.channelService.activeDeviceChannel(deviceId);
        Map<String, Object> map = new HashMap<>(1);
        map.put("status", retDisableStatus);
        if (retDisableStatus == 0) {
            response.setStatus(415);
        }
        return map;
    }
}
