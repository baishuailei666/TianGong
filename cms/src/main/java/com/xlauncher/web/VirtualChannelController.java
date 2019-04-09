package com.xlauncher.web;

import com.xlauncher.entity.VirtualChannel;
import com.xlauncher.service.VirtualChannelService;
import org.apache.ibatis.annotations.Param;
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
 * 虚拟通道Web层
 * @date 2018-05-09
 * @author 白帅雷
 */
@Controller
@RequestMapping(value = "/virtualChannel")
public class VirtualChannelController {
    @Autowired
    private VirtualChannelService virtualChannelService;
    private static Logger logger = Logger.getLogger(VirtualChannelController.class);
    /**
     * 添加虚拟通道信息
     * @param virtualChannel 虚拟通道信息
     * @return 添加虚拟通道的结果，1为成功添加，0添加失败
     */
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Map<String, Object> addVirtualChannel(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestBody @Param("virtualChannel") VirtualChannel virtualChannel) throws SQLException {
        logger.debug("VirtualChannelController addVirtualChannel");
        String errorMessage = null;
        int retAdd =this.virtualChannelService.insertVirtualChannel(virtualChannel);
        if (retAdd == 0) {
            response.setStatus(415);
        }
        switch (retAdd) {
            case 106:errorMessage="数据重复：推送信息的主键重复导致冲突无法写入数据库，通道序号必须小于设备的通道数且不能重复。";
                break;
            case 105:errorMessage="数据缺失：推送的信息没有包含必要的信息，有内容缺失。";
                break;
            case 104:errorMessage="数据格式：推送信息的部分字段长度不符合要求。";
                break;
            case 102:errorMessage="数据依赖：数据所需要的依赖信息不存在，无法添加。";
                break;
            default:break;
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("status", retAdd);
        map.put("errorMessage",errorMessage);
        return map;
    }

    /**
     * 更新虚拟通道接口
     * @param response HTTP响应
     * @param virtualChannel 虚拟通道信息
     * @return 数据库操作影响行数
     */
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Map<String, Object> updateVirtualChannel(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestBody VirtualChannel virtualChannel) throws SQLException {
        logger.debug("VirtualChannelController updateVirtualChannel");
        int retUpd=this.virtualChannelService.updateVirtualChannel(virtualChannel);
        Map<String, Object> map = new HashMap<>(1);
        if (retUpd == 0) {
            response.setStatus(415);
        }
        String errorMessage = null;
        switch (retUpd){
            case 202:errorMessage="数据缺失：无法找到唯一且必要ID定位数据，无法对数据进行更新。";
                break;
            case 203:errorMessage="数据错误：根据编号无法找到对应的信息进行更新。";
                break;
            case 204:errorMessage="数据格式：推送信息的部分字段长度不符合要求。";
                break;
            case 205:errorMessage="数据依赖：数据所需要的依赖信息不存在，无法修改。";
                break;
            case 206:errorMessage="数据异常：通道序号必须小于设备的通道数且不能重复。";
                break;
            default:break;
        }
        map.put("status", retUpd);
        map.put("errorMessage",errorMessage);
        return map;
    }

    /**
     * 更新虚拟通道状态（dim模块使用）
     * @param virtualChannel 新的虚拟通道状态信息
     * @return 更新的结果，数据库操作影响行数
     */
    @ResponseBody
    @RequestMapping(value = "/virtualChannelStatus", method = RequestMethod.PUT)
    public Map<String, Object> updateVirtualChannelStatus(HttpServletResponse response,@RequestBody VirtualChannel virtualChannel) throws SQLException {
        logger.debug("VirtualChannelController updateChannelStatus");
        int retUpdStatus =this.virtualChannelService.updateVirtualChannelStatus(virtualChannel);
        if (retUpdStatus==0) {
            response.setStatus(415);
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("status", retUpdStatus);
        return map;
    }

    /**
     * 删除虚拟通道
     * @param virtualChannelId 虚拟通道的唯一id
     * @return 删除通道的结果，非零为成功删除，0为删除失败
     */
    @ResponseBody
    @RequestMapping(value = "/{virtualChannelId}", method = RequestMethod.DELETE)
    public Map<String, Object> deleteVirtualChannel(HttpServletRequest request,HttpServletResponse response
            ,@RequestHeader("token") String token,@PathVariable("virtualChannelId") String virtualChannelId) throws SQLException {
        logger.debug("VirtualChannelController deleteChannel");
        int retDel = this.virtualChannelService.deleteVirtualChannel(virtualChannelId);
        Map<String, Object> map = new HashMap<>(1);
        String errorMessage = null;
        if (retDel == 0) {
            response.setStatus(415);
        }
        switch (retDel){
            case 302:errorMessage="数据缺失：无法找到唯一且必要ID定位数据，无法对数据进行删除。";
                break;
            case 303:errorMessage="数据异常：该数据不存在或者已经被删除。";
                break;
            default: break;
        }
        map.put("status", retDel);
        map.put("errorMessage",errorMessage);
        return map;
    }

    /**
     * 根据虚拟通道编号查询通道信息
     *
     * @param virtualChannelId 虚拟通道编号
     * @return 虚拟通道信息
     */
    @ResponseBody
    @RequestMapping(value = "/{virtualChannelId}", method = RequestMethod.GET)
    public Map<String, Object> getVirtualChannelByVirtualChannelId(HttpServletRequest request,HttpServletResponse response
            ,@RequestHeader("token") String token,@PathVariable("virtualChannelId") String virtualChannelId) throws SQLException {
        logger.debug("ChannelController getChannelByChannelId");
        VirtualChannel virtualChannel = this.virtualChannelService.getVirtualChannelByVirtualChannelId(virtualChannelId);
        Map<String, Object> map = new HashMap<>(1);
        if (virtualChannel == null) {
            response.setStatus(415);
        }
        map.put("virtualChannel", virtualChannel);
        return map;
    }

    /**
     * 分页获取虚拟通道信息
     *
     * @param number 页码
     * @return 一页十个虚拟通道数据
     */
    @ResponseBody
    @RequestMapping(value = "/page/{number}", method = RequestMethod.GET)
    public Map<String, Object> queryVirtualChannel(@PathVariable("number") int number) throws SQLException {
        logger.debug("VirtualChannelController queryVirtualChannel");
        List<VirtualChannel> virtualChannelList =this.virtualChannelService.listVirtualChannel((number-1)*10);
        Map<String, Object> map = new HashMap<>(1);
        map.put("virtualChannelList", virtualChannelList);
        return map;
    }

    /**
     * 通过虚拟设备id来获取该虚拟设备所有的通道信息
     * @param virtualChannelSourceId 获取的虚拟设备id
     * @return 虚拟通道信息列表
     */
    @ResponseBody
    @RequestMapping(value = "/virtualSourceId/page/{number}", method = RequestMethod.GET)
    public Map<String, Object> queryVirtualChannelBySource(HttpServletRequest request,HttpServletResponse response
            , @RequestHeader("token") String token, @RequestParam("virtualChannelSourceId") String virtualChannelSourceId, @PathVariable("number")int number) throws SQLException {
        logger.debug("VirtualChannelController queryVirtualChannelBySource");
        List<VirtualChannel> virtualChannelListBySource = this.virtualChannelService.listVirtualChannelBySource(virtualChannelSourceId,(number-1)*3);
        Map<String, Object> map = new HashMap<>(1);
        map.put("virtualChannelListBySource", virtualChannelListBySource);
        if (virtualChannelListBySource == null) {
            response.setStatus(415);
        }
        return map;
    }

    /**
     * 获取表的行数
     *
     * @return 满足条件的虚拟通道数
     */
    @ResponseBody
    @RequestMapping(value = "/count",method = RequestMethod.GET)
    public Map<String,Object> pageCount(HttpServletRequest request,HttpServletResponse response
            , @RequestHeader("token") String token, @RequestParam("virtualChannelSourceId") String virtualChannelSourceId) throws SQLException {
        logger.debug("VirtualChannelController pageCount");
        Map<String, Object> map = new HashMap<>(1);
        int count= this.virtualChannelService.countPage(virtualChannelSourceId);
        map.put("count", count);
        return map;
    }

    /**
     * 获取所有虚拟摄像头及其地理信息以及是否有告警事件
     * @return 概览页所需数据
     */
    @ResponseBody
    @RequestMapping(value = "/overview",method = RequestMethod.GET)
    public List overview() throws SQLException {
        logger.debug("VirtualChannelController overview");
        return this.virtualChannelService.overview();
    }
}
