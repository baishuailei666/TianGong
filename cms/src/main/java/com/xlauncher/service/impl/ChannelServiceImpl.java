package com.xlauncher.service.impl;

import com.xlauncher.dao.ChannelDao;
import com.xlauncher.dao.DeviceDao;
import com.xlauncher.dao.EventAlertDao;
import com.xlauncher.entity.Channel;
import com.xlauncher.entity.Device;
import com.xlauncher.service.ChannelService;
import com.xlauncher.util.CheckToken;
import com.xlauncher.util.DimUtil;
import com.xlauncher.util.Initialise;
import com.xlauncher.util.OperationLogUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * 真实通道ServiceImpl层
 * @date 2018-05-08
 * @author 白帅雷
 */
@Service
public class ChannelServiceImpl implements ChannelService {
    @Autowired
    ChannelDao channelDao;
    @Autowired
    DeviceDao deviceDao;
    @Autowired
    private OperationLogUtil logUtil;
    @Autowired
    EventAlertDao eventAlertDao;
    @Autowired
    private DimUtil dimUtil;
    @Autowired
    private CheckToken checkToken;
    private static final String MODULE = "真实设备管理";
    private static final String SYSTEM_MODULE = "设备管理";
    private static final String CATEGORY = "运维面";
    private static Logger logger = Logger.getLogger(ChannelServiceImpl.class);

    /**
     * 添加通道信息,确保添加的通道序号小于设备支持的通道总数且不重复
     *
     * @param channel 通道信息
     * @param token 用户令牌
     * @return 数据库操作影响行数,如果通道序号大于设备支持的通道总数返回-1；通道序号重复返回-2；
     */
    @Override
    public int insertChannel(Channel channel, String token) {
        int inStatus;
        if (channel.getChannelId() == null) {
            channel.setChannelId(Initialise.initialise());
        }
        logger.info("添加通道信息,确保添加的通道序号小于设备支持的通道总数且不重复" + channel);
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"添加",MODULE,SYSTEM_MODULE,"添加通道编号为：" + channel.getChannelId() + "的真实通道信息",CATEGORY);
        Device channelCount = this.deviceDao.deviceChannelCount(channel.getChannelSourceId());
        if (channelCount != null) {
            if (Objects.equals(channelCount.getDeviceType(), "IPcamera")) {
                Channel channelChannel = this.channelDao.getChannelBySourceId(channel.getChannelSourceId());
                if (channelChannel != null) {
                    return 107;
                }
            }
            if (channel.getChannelNumber() <= channelCount.getDeviceChannelCount()) {
                int channelNumber = this.channelDao.channelConflict(channel.getChannelId(), channel.getChannelNumber());
                if (channelNumber == 0) {
                    String chanelLongitude;
                    try {
                        chanelLongitude = channelDao.getLongitudeByLatitude(channel.getChannelId(),channel.getChannelLatitude());
                    } catch (Exception e) {
                       return 105;
                    }
                    if (chanelLongitude == null || !chanelLongitude.equals(channel.getChannelLongitude())) {
                        channel.setChannelPodStatus(0);
                        inStatus = this.channelDao.insertChannel(channel);
                        try {
                            dimUtil.channelPost(channel);
                        } catch (Exception e) {
                            inStatus = 11;
                            logger.error("ERROR:DIM服务异常" + e.getMessage() + ",inStatus:" + inStatus);
                            return inStatus;
                        }
                    } else {
                        logger.warn("通道的经纬度不能重复！");
                        inStatus = 105;
                    }
                } else {
                    logger.warn("通道序号已经存在且不能重复！" + channel.getChannelNumber());
                    inStatus = 106;
                }
            } else {
                logger.warn("通道序号大于要求的通道数！" + channel.getChannelNumber());
                inStatus = 106;
            }
        } else {
            logger.warn("无效的通道资源编号：" + channel);
            return 106;
        }
        return inStatus;
    }

    /**
     * 删除通道信息
     *
     * @param channelId 通道编号
     * @param token 用户令牌
     * @return 数据操作影响行数
     */
    @Override
    public int deleteChannel(String channelId, String token) {
        logger.info("删除通道信息：" + channelId);
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"删除",MODULE,SYSTEM_MODULE,"删除通道编号为：" + channelId + "的真实通道信息",CATEGORY);
        int status = channelDao.deleteChannel(channelId);
        try {
            logger.info("删除通道信息同步给DIM：" + channelId + ",status:" + status);
            dimUtil.channelDelete(channelId);
        } catch (Exception e) {
            status = 11;
            logger.error("ERROR:DIM服务异常" + ",inStatus:" + status);
            return status;
        }
        return status;
    }

    /**
     * 修改通道信息
     *
     * @param channel 通道的更新信息
     * @param token 用户令牌
     * @return 数据操作影响行数
     */
    @Override
    public int updateChannel(Channel channel, String token) {
        int status;
        logger.info("修改通道信息：" + channel);
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"更新",MODULE,SYSTEM_MODULE,"修改通道编号为：" + channel.getChannelId() + "的真实通道信息",CATEGORY);
        String chanelLongitude = channelDao.getLongitudeByLatitude(channel.getChannelId(),channel.getChannelLatitude());
        if (chanelLongitude==null || !chanelLongitude.equals(channel.getChannelLongitude())) {
            status=channelDao.updateChannel(channel);
            try {
                logger.info("修改通道信息同步给DIM：" + channel + ",status:" + status);
                dimUtil.channelPut(channel);
            } catch (Exception e) {
                logger.error("ERROR:DIM服务异常" + ",inStatus:" + status);
                return 11;
            }
        } else {
            logger.warn("通道的经纬度不能重复！" + chanelLongitude);
            return 10;
        }
        return status;
    }

    /**
     * 根据通道编号查询通道信息
     *
     * @param channelId 通道编号
     * @param token 用户令牌
     * @return 通道信息
     */
    @Override
    public Channel getChannelByChannelId(String channelId, String token) {
        logger.info("根据通道编号查询通道信息：" + channelId);
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询",MODULE,SYSTEM_MODULE,"查询通道编号为：" + channelId + "的真实通道信息",CATEGORY);
        Channel channel = this.channelDao.getChannelByChannelId(channelId);
        if (channel != null) {
            channel.setChannelCreateTime(channel.getChannelCreateTime().substring(0,19));
            channel.setChannelUpdateTime(channel.getChannelUpdateTime().substring(0,19));
        }
        return channel;
    }

    /**
     * 查询通道信息
     *
     * @param channelName     通道名称
     * @param channelLocation 通道位置
     * @param channelStatus   通道状态
     * @param number          页码
     * @param token 用户令牌
     * @param channelSourceId 通道资源编号
     * @return 一个包含十个通道的列表
     */
    @Override
    public List<Channel> listChannel(String channelName, String channelLocation, String channelStatus, int number, String channelSourceId, String token) {
        logger.info("查询通道信息listChannel：" + channelName);
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询",MODULE,SYSTEM_MODULE,"根据条件通道名称:" + channelName + ",通道位置：" + channelLocation + "查询" + (number + 1) + "到" + (number + 11) + "行的真实通道信息",CATEGORY);
        List<Channel> channelList = this.channelDao.listChannel(channelName,channelLocation,channelStatus,number,channelSourceId);
        if (channelList.size() != 0) {
            for (Channel channel : channelList) {
                channel.setChannelCreateTime(channel.getChannelCreateTime().substring(0,19));
                channel.setChannelUpdateTime(channel.getChannelUpdateTime().substring(0,19));
            }
        }
        return channelList;
    }

    /**
     * 获取所有的通道数量，用于分页
     *
     * @param channelName     通道名称
     * @param channelLocation 通道位置
     * @param channelStatus   通道状态
     * @param channelSourceId 通道资源编号
     * @return 该设备所拥有的通道数
     */
    @Override
    public int countPageChannel(String channelName, String channelLocation, String channelStatus, String channelSourceId) {
        return this.channelDao.countPageChannel(channelName,channelLocation,channelStatus,channelSourceId);
    }

    /**
     * 将所有的通道的地理位置取出，供地图打点使用
     *
     * @return 通道地理位置信息列表
     */
    @Override
    public List<Channel> overview() {
        List overviewList = new ArrayList(1);
        List<Channel> channels = channelDao.overview();
        List<String> eventChannel = eventAlertDao.overview();
        Map<String, Object> locationMap = new HashMap<>(1);
        List lstValue = new ArrayList(10);
        for(int i = 0;i<channels.size();i++) {
            List lstLocation = new ArrayList(10);
            lstLocation.add(Float.parseFloat(channels.get(i).getChannelLongitude()));
            lstLocation.add(Float.parseFloat(channels.get(i).getChannelLatitude()));
            locationMap.put(channels.get(i).getChannelId(), lstLocation);
            Map<String, Object> valueMap = new HashMap<>(1);
            valueMap.put("name", channels.get(i).getChannelId());
            int count = 0;
            int value = 0;
            for (int j = 0;j<eventChannel.size();j++){
                if (channels.get(i).getChannelId().equals(eventChannel.get(j))){
                    value = 1;
                    count = eventAlertDao.countByChannel(eventChannel.get(j));
                }
            }
            valueMap.put("count",count);
            valueMap.put("value",value);
            valueMap.put("id", channels.get(i).getChannelName());
            lstValue.add(valueMap);
        }
        overviewList.add(locationMap);
        overviewList.add(lstValue);
        return overviewList;
    }


    /**
     * 将所有的通道的地理位置取出，供地图打点使用
     * @return 通道地理位置信息列表
     */
    @Override
    public List overviewStatus() {
        List overviewList = new ArrayList(1);
        List<Channel> channels = channelDao.overviewStatus();
        List<String> errorChannel = channelDao.overviewError();
        Map<String,Object> locationMap = new HashMap<>();
        List lstValue = new ArrayList();
        for (int i = 0;i<channels.size();i++){
            List lstLocation = new ArrayList();
            lstLocation.add(Float.parseFloat(channels.get(i).getChannelLongitude()));
            lstLocation.add(Float.parseFloat(channels.get(i).getChannelLatitude()));
            locationMap.put(channels.get(i).getChannelId(),lstLocation);

            Map<String, Object> valueMap = new HashMap<>(1);
            valueMap.put("name", channels.get(i).getChannelId());
            String fault = null;
            int value = 0;
            for (int j = 0;j<errorChannel.size();j++){
                if (channels.get(i).getChannelId().equals(errorChannel.get(j))){
                    value = 1;
                    fault = channels.get(i).getChannelFault();
                }
            }
            valueMap.put("fault",fault);
            valueMap.put("value",value);
            valueMap.put("id", channels.get(i).getChannelName());
            lstValue.add(valueMap);
        }
        overviewList.add(locationMap);
        overviewList.add(lstValue);
        return overviewList;
    }

    /**
     * 显示最新5条的设备告警信息
     *
     * @return List<Channel>
     */
    @Override
    public List<Channel> getChannelFault() {
        logger.info("显示最新的5条设备告警信息getChannelFault");
        return this.channelDao.getChannelFault();
    }

    /**
     * 饼状图、获取设备告警类型和数量
     *
     * @return List<Channel>
     */
    @Override
    public Map<String, Object> getChannelFaultTypeAndCount() {
        logger.info("饼状图、获取设备告警类型和数量getChannelFaultTypeAndCount");
        List<String> list = this.channelDao.getChannelFaultType();
        List<Object> listCount = new ArrayList<>(1);
        Map<String, Object> map = new HashMap<>(1);
        if (list != null) {
            for (String channelFault : list) {
                Map<String, Object> dataMap = new HashMap<>(1);
                int count = this.channelDao.getChannelFaultCount(channelFault);
                dataMap.put("value", count);
                dataMap.put("name", channelFault);
                listCount.add(dataMap);
            }
        }
        map.put("data", listCount);
        return map;
    }

    /**
     * 通道名称查重
     *
     * @param channelName
     * @param id 添加通道时发送deviceId，修改通道时发送channelId
     * @return 已经存在返回1，未存在返回0
     */
    @Override
    public int countChannelName(String channelName, String id) {
        logger.info("通道名称查重countChannelName:" + channelName);
        Channel channel = this.channelDao.getChannelByChannelId(id);
        if (channel != null) {
            logger.info("修改通道-通道名称查重 - 正确! channelName:" + channelName + ", " + id);
            List<String> channelIds = this.channelDao.listChannelId(channel.getChannelSourceId());
            final int[] status = {0};
            channelIds.remove(id);
            channelIds.forEach(channelId->{
                status[0] = this.channelDao.countChannelName(channelId,channelName);
            });
            logger.info("status[0]:" + status[0]);
            return status[0];
        } else {
            logger.info("添加通道-通道名称查重 - 正确! channelName:" + channelName + ", " + id);
            logger.info("这里的ID是deviceId：" + id);
            List<String> channelIds = this.channelDao.listChannelId(id);
            logger.info("不同设备通道名称可以相同-该deviceId下面的channelIds" + channelIds);
            int status = 0;
            for (int i=0; i<channelIds.size(); i++) {
                status = this.channelDao.countChannelName(channelIds.get(i),channelName);
                if (status == 1) {
                    logger.info("此设备存在该通道名称：" + channelName + "返回status：" + status);
                    return status;
                }
            }
            return status;
        }
    }

    /**
     * 通道序号查重
     *
     * @param channelNumber
     * @param id 添加通道时发送deviceId，修改通道时发送channelId
     * @return 已经存在返回1，未存在返回0
     */
    @Override
    public int countChannelNumber(int channelNumber, String id) {
        logger.info("通道序号查重countChannelNumber, " + id + ", "+ channelNumber);
        Channel channel = this.channelDao.getChannelByChannelId(id);
        if (channel != null) {
            String deviceId = channel.getChannelSourceId();
            int channelCount = this.deviceDao.deviceChannelCount(deviceId).getDeviceChannelCount();
            if (channelNumber <= channelCount) {
                logger.info("修改通道-通道序号查重 - 正确! channelNumber不大于channelCount" + channelNumber + ", " + channelCount);
                List<String> channelIds = this.channelDao.listChannelId(deviceId);
                final int[] status = {0};
                channelIds.remove(id);
                channelIds.forEach(channelId->{
                    status[0] = this.channelDao.countChannelNumber(channelId,channelNumber);
                });
                logger.info("status[0]:" + status[0]);
                return status[0];
            } else {
                logger.info("修改通道-通道序号查重 - 错误! channelNumber大于channelCount" + channelNumber + ", " + channelCount);
                return 1;
            }
        } else {
            int channelCount = this.deviceDao.deviceChannelCount(id).getDeviceChannelCount();
            if (channelNumber <= channelCount) {
                logger.info("添加通道-通道序号查重 - 正确! channelNumber不大于channelCount. " + channelNumber + ", " + channelCount);
                logger.info("这里的ID是deviceId：" + id);
                List<String> channelIds = this.channelDao.listChannelId(id);
                logger.info("不同设备通道序号可以相同-该deviceId下面的channelIds:" + channelIds);
                int status = 0;
                for (int i=0; i<channelIds.size(); i++) {
                    status = this.channelDao.countChannelNumber(channelIds.get(i),channelNumber);
                    if (status == 1) {
                        logger.info("此设备存在该通道序号：" + channelNumber + "返回status：" + status);
                        return status;
                    }
                }
                return status;
            } else {
                logger.info("添加通道-通道序号查重 - 错误! channelNumber大于channelCount" + channelNumber + ", " + channelCount);
                return 1;
            }
        }
    }

    /**
     * DIM反馈通道状态
     *
     * @param channel 通道的状态，通道编号
     * @return 数据操作影响行数
     */
    @Override
    public int updateChannelStatus(Channel channel) {
        logger.info("DIM反馈通道状态updateChannelStatus：" + channel);
        return this.channelDao.updateChannelStatus(channel);
    }

    /**
     * 激活通道
     *
     * @param channelId 通道编号
     * @param token     用户令牌
     * @return 数据库的操作情况
     */
    @Override
    public int activeChannel(String channelId, String token) {
        logger.info("激活通道：" + channelId);
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"激活",MODULE,SYSTEM_MODULE,"激活通道编号为：" + channelId + "的真实通道信息",CATEGORY);
        int status = this.channelDao.activeChannel(channelId);
        try {
            logger.info("激活通道同步给DIM：" + channelId + ",status:" + status);
            dimUtil.activeChannel(channelId);
        } catch (Exception e) {
            status=11;
            logger.warn("ERROR:DIM服务异常");
            return status;
        }
        return status;
    }

    /**
     * 停用通道
     *
     * @param channelId 通道编号
     * @param token     用户令牌
     * @return 数据库的操作情况
     */
    @Override
    public int disableChannel(String channelId, String token) {
        logger.info("停用通道：" + channelId);
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"停用",MODULE,SYSTEM_MODULE,"停用通道编号为：" + channelId + "的真实通道信息",CATEGORY);
        int status = this.channelDao.disableChannel(channelId);
        try {
            logger.info("停用通道同步给DIM：" + channelId + ",status:" + status);
            dimUtil.disableChannel(channelId);
        } catch (Exception e) {
            status=11;
            logger.warn("ERROR:DIM服务异常");
            return status;
        }
        return status;
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
     * @param token 用户令牌
     * @return 数据库信息
     */
    @Override
    public List<Channel> getRuntimeChannel(String upStartTime, String lowStartTime, String channelHandler, String channelLocation, String channelName, int number, String token) {
        logger.info("查询通道告警信息并分页展示：" + channelName);
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询","真实设备告警",SYSTEM_MODULE,"根据条件通道负责人:" + channelHandler + ",通道名称：" + channelName + "查询" + (number + 1) + "到" + (number + 11) + "行的真实通道告警信息",CATEGORY);
        List<Channel> channelList = this.channelDao.getRuntimeChannel(upStartTime, lowStartTime, channelHandler, channelLocation, channelName, number);
        if (channelList.size() != 0) {
            for(Channel channel : channelList) {
                channel.setChannelFaultTime(channel.getChannelFaultTime().substring(0,19));
            }
        }
        return channelList;
    }

    /**
     * 查询通道告警信息count数
     *
     * @param upStartTime     查询条件开始时间
     * @param lowStartTime    查询条件结束时间
     * @param channelHandler 通道资负责人
     * @param channelLocation 通道位置
     * @param channelName     通道名称
     * @return 数据库信息
     */
    @Override
    public int countRuntimeChannel(String upStartTime, String lowStartTime, String channelHandler, String channelLocation, String channelName) {
        return this.channelDao.countRuntimeChannel(upStartTime, lowStartTime, channelHandler, channelLocation, channelName);
    }
}
