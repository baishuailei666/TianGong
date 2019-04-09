package com.xlauncher.service.impl;

import com.xlauncher.dao.ChannelDao;
import com.xlauncher.dao.DeviceDao;
import com.xlauncher.dao.OrganizationDao;
import com.xlauncher.entity.*;
import com.xlauncher.util.*;
import com.xlauncher.util.synsunnyitec.GetDataFromSunnyintec;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.SQLException;


/**
 * 第三方同步数据（device、channel、org）
 * @author 白帅雷
 * @date 2018-09-10
 */
@Service
public class SynSunnyintec {

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private ChannelDao channelDao;

    @Autowired
    private OrganizationDao orgDao;
    @Autowired
    private DimUtil dimUtil;
    @Autowired
    private GetDataFromSunnyintec getDataFromSunnyintec;
    private static Logger logger = Logger.getLogger(SynSunnyintec.class);

    /**
     * 从正阳科技同步设备信息
     */
    public void synDevice(String ticket) {

        logger.info(" 0._____synDevice ticket:" + ticket);
        int total = getCount("getdevicecount",ticket);
        logger.info(" 1._____synDevice total:" + total);
        if (total == 0) {
            return;
        }
        int count = 10;
        int totalPage = (total / count) + 1;
        for (int page = 1; page <= totalPage; page++) {
            // 导入net.sf.json类，使用JSONObject中的方法，先将数据转成json字符串，再转成对应实体对象
            JSONArray lists = JSONArray.fromObject(getDataFromSunnyintec.getSunnyintecDevice(page,count,ticket).get("pageList"));
            logger.info(" 2._____synDevice lists:" + lists);
            for (Object list : lists) {
                JSONObject object = (JSONObject) list;
                Device device = (Device) JSONObject.toBean(object, Device.class);
                //判空
                if (device == null) {
                    continue;
                }
                //判定设备编号为32位UUID
                if (device.getDeviceId().length() != 32) {
                    continue;
                }
                //判断设备核心字段是否为空
                if (device.getDeviceId() == null || device.getDeviceIp() == null
                        || device.getDevicePort() == null || device.getDeviceUserName() == null
                        || device.getDeviceUserPassword() == null || device.getDeviceDivisionId() == null
                        || device.getDeviceStatus() == null || device.getDeviceOrgId() == null
                        || device.getDeviceChannelCount() == null) {
                    continue;
                }
                /**
                 * 全量同步，不存在则插入，存在则更新
                 * 1.判断是否存在；
                 * 2.存在，在用则更新；存在停用则删除；不存在直接插入。
                 */
                try {
                    if (device.getDeviceUpdateTime() == null) {
                        device.setDeviceUpdateTime(DatetimeUtil.stampToDate(device.getDeviceUpdateTime()));
                    } else {
                        device.setDeviceUpdateTime(DatetimeUtil.stampToDate(device.getDeviceUpdateTime()));
                    }
                    device.setDeviceCreateTime(DatetimeUtil.stampToDate(device.getDeviceCreateTime()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (checkDeviceIfExist(device)) {
                    try {
                        if ("在用".equals(device.getDeviceStatus())) {
                            int status = deviceDao.updateDevice(device);
                            logger.info(" 4._____synDevice updateDevice status:" + status);
                            this.dimUtil.devicePut(device);
                            logger.info(" 5._____synDevice dim devicePut");
                        } else {
                            int status = deviceDao.deleteDevice(device.getDeviceId());
                            int status1 = channelDao.deleteChannelByDeviceId(device.getDeviceId());
                            logger.info(" 6._____synDevice deleteDevice status:" + status + " ,deleteChannelByDeviceId status:" + status1);
                            this.dimUtil.deviceDelete(device.getDeviceId());
                            logger.info(" 7._____synDevice dim deviceDelete");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        int status = deviceDao.insertDevice(device);
                        logger.info(" 8._____synDevice insertDevice status:" + status);
                        this.dimUtil.devicePost(device);
                        logger.info(" 9._____synDevice dim devicePost");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    /**
     * 从正阳科技同步通道信息
     */
    public void synChannel(String ticket) {

        logger.info(" 0._____synChannel ticket:" + ticket);
        int total = this.getCount("getchannelcount",ticket);
        logger.info(" 1._____synChannel total:" + total);
        if (total == 0) {
            return;
        }
        int count = 10;
        int totalPage = (total / count) + 1;
        for (int page = 1; page <= totalPage; page++) {
            try {
                // 导入net.sf.json类，使用JSONObject中的方法，先将数据转成json字符串，再转成对应实体对象
                JSONArray lists = JSONArray.fromObject(getDataFromSunnyintec.getSunnyintecChannel(page,count,ticket).get("pageList"));
                logger.info(" 2._____synChannel lists:" + lists);
                for (Object list : lists) {
                    JSONObject object = (JSONObject) list;
                    object.remove("channelOrgId");
                    object.remove("channelSourceName");
                    object.remove("channelGridName");
                    Channel channel = (Channel) JSONObject.toBean(object, Channel.class);
                    //判空
                    if (channel == null) {
                        continue;
                    }
                    //确定编号字段都为32位
                    if (channel.getChannelSourceId().length() != 32
                            || channel.getChannelId().length() != 32) {
                        continue;
                    }
                    //判断通道依赖的设备存在
                    if (deviceDao.getDeviceByDeviceId(channel.getChannelSourceId()) == null) {
                        continue;
                    }
                    //判断通道号是否已被占用以及是否合法
                    if (channel.getChannelNumber() < 0
                            || channel.getChannelNumber() > deviceDao.getDeviceByDeviceId(channel.getChannelSourceId()).getDeviceChannelCount()
                            || channelDao.channelConflict(channel.getChannelId(), channel.getChannelNumber()) != 0) {
                        continue;
                    }
                    //关键字段判空
                    if (channel.getChannelSourceId() == null
                            || channel.getChannelNumber() == null
                            || channel.getChannelLocation() == null
                            || channel.getChannelLongitude() == null
                            || channel.getChannelLatitude() == null
                            || channel.getChannelCreateTime() == null
                            ) {
                        continue;
                    }
                    /**
                     * 全量同步，不存在则插入，存在则更新
                     * 1.判断是否存在；
                     * 2.存在，在用则更新；存在停用则删除；不存在直接插入。
                     */
                    try {
                        if (channel.getChannelUpdateTime() == null) {
                            channel.setChannelUpdateTime(DatetimeUtil.stampToDate(channel.getChannelCreateTime()));
                        } else {
                            channel.setChannelUpdateTime(DatetimeUtil.stampToDate(channel.getChannelUpdateTime()));
                        }
                        channel.setChannelCreateTime(DatetimeUtil.stampToDate(channel.getChannelCreateTime()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (checkChannelIfExist(channel)) {

                        if ("在用".equals(channel.getChannelStatus())) {
                            int status = channelDao.updateChannel(channel);
                            logger.info(" 4._____synChannel updateChannel status:" + status);
                            this.dimUtil.channelPut(channel);
                            logger.info(" 5._____synChannel dim channelPut :");
                        } else {
                            int status = channelDao.deleteChannel(channel.getChannelId());
                            logger.info(" 6._____synChannel deleteChannel status:" + status);
                            this.dimUtil.channelDelete(channel.getChannelId());
                            logger.info(" 7._____synChannel dim channelDelete :");
                        }
                    } else {
                        int status = channelDao.insertChannel(channel);
                        logger.info(" 8._____synChannel insertChannel status:" + status);
                        this.dimUtil.channelPost(channel);
                        logger.info(" 7._____synChannel dim channelPost :");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从正阳科技同步组织信息
     */
    public void synOrg(String ticket) {

        logger.info(" 0._____synOrg ticket:" + ticket);
        try {
            // 导入net.sf.json类，使用JSONObject中的方法，先将数据转成json字符串，再转成对应实体对象
            JSONArray lists = JSONArray.fromObject(getDataFromSunnyintec.getSunnyintecOrg(ticket).get("orgLists"));
            logger.info(" 2._____synOrg orgLists:" + lists);
            for (Object list : lists) {
                JSONObject object = (JSONObject) list;
                Organization organization = new Organization();
                organization.setOrgId((String) object.get("orgid"));
                organization.setName((String) object.get("orgname"));
                String sid = String.valueOf(object.get("parid"));
                if (sid != null) {
                    organization.setOrgSuperiorId(sid);
                } else {
                    organization.setOrgSuperiorId("0");
                }
                organization.setOrgDuty((String) object.get("orgtype"));
                organization.setOrgLocation((String) object.get("orgmarks"));
                if (organization.getOrgId() == null
                        || organization.getOrgSuperiorId() == null
                        || organization.getName() == null) {
                    continue;
                }
                /**
                 * 全量同步，不存在则插入，存在则更新
                 * 1.判断是否存在；
                 * 2.存在，在用则更新；存在停用则删除；不存在直接插入。
                 */
                if (checkOrgIfExist(organization)) {
                    int status = orgDao.updateOrganization(organization);
                    logger.info(" 4._____synOrg updateOrganization status:" + status);
                } else {
                    int status = this.orgDao.insertOrganization(organization);
                    logger.info(" 5._____synOrg insertOrganization status:" + status);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 根据指定的方法名称获取设备和通道的总数
     * @param methodName 获取设备或通道总数的接口方法名称getDviceCount  getChannelCount
     * @return 返回设备或者通道的总数
     */
    public int getCount(String methodName, String ticket) {
        logger.info(" 1.getCount methodName:" + methodName);
        if ("getdevicecount".equals(methodName)) {
            return (int) getDataFromSunnyintec.getSunnyintecDeviceCount(methodName,ticket).get("count");
        } else if ("getchannelcount".equals(methodName)) {
            return (int) getDataFromSunnyintec.getSunnyintecChannelCount(methodName,ticket).get("count");
        } else {
            return 0;
        }
    }

    /**
     * 检测设备在CMS数据库中是否存在
     * @param device 设备信息
     * @return 存在返回true;不存在返回false。
     */
    public boolean checkDeviceIfExist(Device device) {
        logger.info(" 3._____check Device If Exist device:" + device);
        return deviceDao.deviceExistence(device.getDeviceId()) == 1;
    }

    /**
     * 检测通道在通道列表中是否存在
     * @param channel 通道信息
     * @return 存在返回true;不存在返回false。
     */
    public boolean checkChannelIfExist(Channel channel) {
        logger.info(" 3._____check Channel If Exist channel:" + channel);
        return channelDao.channelExistence(channel.getChannelId()) == 1;
    }

    /**
     * 检测组织在组织列表中是否存在
     * @param org 组织信息
     * @return 存在返回true;不存在返回false。
     */
    public boolean checkOrgIfExist(Organization org) {
        logger.info(" 3._____check Org If Exist org:" + org);
        return orgDao.orgExistence(org.getOrgId()) == 1;
    }

}
