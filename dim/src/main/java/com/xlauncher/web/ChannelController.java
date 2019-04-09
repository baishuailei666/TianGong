package com.xlauncher.web;

import com.xlauncher.entity.Channel;
import com.xlauncher.service.ChannelService;
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
 * 通道web层
 * @author YangDengcheng
 * @date 2018/2/27 16:42
 */
@Controller
@RequestMapping(value = "")
public class ChannelController {
    private static final Logger LOGGER = Logger.getLogger(ChannelController.class);

    @Autowired
    ChannelService channelService;

    /**
     * 查看所有通道信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/channel",method = RequestMethod.GET)
    public List<Channel> queryAllChannel(){
        LOGGER.info("[queryAllChannel] 查看所有通道信息");
        return channelService.queryAllChannel();
    }

    /**
     * 查看某个通道具体信息
     *
     * @param channelId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/channel/{channelId}",method = RequestMethod.GET)
    public Channel queryChannel(@PathVariable String channelId){
        LOGGER.info("[queryChannel] 查看某个通道具体信息");
        return channelService.queryChannelMsg(channelId);
    }

    /**
     * 新增一个通道
     *
     * @param channel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/channel",method = RequestMethod.POST)
    public Map<String,Object> insertDevice(@RequestBody Channel channel){
        Map<String, Object> resultMap = new HashMap<>(1);
        try {
            LOGGER.info("[URL:/dim/channel,method:POST] Invoke DIM's service interface successfully!");
            channelService.insertChannelWithDIM(channel);
            resultMap.put("status",1);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("[URL:/dim/channel,method:POST] DIM exexcutes sql and k8s operation unsuccessfully!" + e);
            resultMap.put("status",0);
            resultMap.put("message", e.getMessage() + e.getCause());
            return resultMap;
        }
    }

    /**
     * 修改一个通道信息
     * @param channel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/channel",method = RequestMethod.PUT)
    public Map<String,Integer> updateDevice(@RequestBody Channel channel){
        Map<String,Integer> resultMap = new HashMap<String, Integer>();
        try {
            LOGGER.info("[URL:/dim/channel,method:PUT] Invoke DIM's service interface successfully");
            channelService.updateChannelWithDIM(channel);
            LOGGER.info("DIM exexcutes sql,update tb_channel in database successfully");
            resultMap.put("status",1);
            return resultMap;
        }catch (Exception e){
            LOGGER.error("[URL:/dim/channel,method:PUT] DIM exexcutes sql unsuccessfully" + e);
            e.printStackTrace();
            resultMap.put("status",0);
            return resultMap;
        }
    }

    /**
     * 删除通道
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/channel",method = RequestMethod.DELETE)
    public Map<String,Integer> deleteCamera(@RequestParam String id)  {
        Map<String,Integer> resultMap = new HashMap<String, Integer>();
        try {
            LOGGER.info("[URL:/dim/channel,method:DELETE] Invoke DIM's service interface successfully");
            channelService.deleteChannelWithDIM(id);
            LOGGER.info("DIM exexcutes sql,delete tb_channel in database successfully");
            resultMap.put("status",1);
            return resultMap;
        }catch (Exception e){
            LOGGER.error("[URL:/dim/channel,method:DELETE] DIM exexcutes sql and k8s operation unsuccessfully" + e);
            e.printStackTrace();
            resultMap.put("status",0);
            return resultMap;
        }
    }

    /**
     * 激活通道
     *
     * @param response 通信的返回状态设置
     * @param channelId 通道编号
     * @return 数据库操作情况激活成功或失败
     */
    @ResponseBody
    @RequestMapping(value = "/activeChannel/{channelId}", method = RequestMethod.PUT)
    public Map<String, Object> activeChannel(HttpServletResponse response, @PathVariable("channelId") String channelId) throws SQLException {
        int retActiveStatus = this.channelService.activeChannel(channelId);
        Map<String, Object> map = new HashMap<>(1);
        map.put("status", retActiveStatus);
        LOGGER.info("激活通道：" + channelId + retActiveStatus);
        if (retActiveStatus == 0) {
            response.setStatus(415);
        }
        return map;
    }

    /**
     * 停用通道
     *
     * @param response 通信的返回状态设置
     * @param channelId 通道编号
     * @return 数据库操作情况停用成功或失败
     */
    @ResponseBody
    @RequestMapping(value = "/disableChannel/{channelId}", method = RequestMethod.PUT)
    public Map<String, Object> disableChannel(HttpServletResponse response, @PathVariable("channelId") String channelId) throws SQLException {
        int retDisableStatus = this.channelService.disableChannel(channelId);
        Map<String, Object> map = new HashMap<>(1);
        map.put("status", retDisableStatus);
        LOGGER.info("停用通道：" + channelId + retDisableStatus);
        if (retDisableStatus == 0) {
            response.setStatus(415);
        }
        return map;
    }

    /**
     * 接受设备传来的状态信息
     *
     * @param channel
     */
    @ResponseBody
    @RequestMapping(value = "/status",method = RequestMethod.POST)
    public void receiveStatus(@RequestBody Channel channel){
        try {
            LOGGER.info("[URL:/dim/status,method:POST] Invoke DIM's service interface successfully");
            channelService.updateChannelMsg(channel);
            LOGGER.info("DIM exexcutes sql,update tb_channel's status in database successfully");
        }catch (Exception e){
            LOGGER.error("[URL:/dim/status,method:POST] DIM exexcutes sql unsuccessfully"  + e);
            e.printStackTrace();
        }
    }
}
