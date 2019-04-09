package com.xlauncher.service.impl;

import com.xlauncher.dao.AdminDivisionDao;
import com.xlauncher.dao.ChannelDao;
import com.xlauncher.dao.DeviceDao;
import com.xlauncher.dao.OrganizationDao;
import com.xlauncher.entity.Device;
import com.xlauncher.entity.Division;
import com.xlauncher.entity.Organization;
import com.xlauncher.service.DeviceService;
import com.xlauncher.util.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.*;


/**
 * 真实设备ServiceImpl层
 * @date 2018-05-08
 * @author 白帅雷
 */
@Service
public class DeviceServiceImpl implements DeviceService{
    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private ChannelDao channelDao;
    @Autowired
    private OperationLogUtil logUtil;
    @Autowired
    private DimUtil dimUtil;
    @Autowired
    AdminDivisionDao divisionDao;
    @Autowired
    OrganizationDao organizationDao;
    @Autowired
    AdminDivisionDao adminDivisionDao;
    @Autowired
    private CheckToken checkToken;
    @Autowired
    DivAndOrgUtil divAndOrgUtil;
    private static final String MODULE = "真实设备管理";
    private static final String SYSTEM_MODULE = "设备管理";
    private static final String CATEGORY = "运维面";
    private static Logger logger = Logger.getLogger(DeviceServiceImpl.class);
    /**
     * 用户分页显示设备信息
     *
     * @param deviceType       设备类型
     * @param deviceFaultCode  设备故障编号
     * @param deviceStatus     设备状态
     * @param orgName      设备所属组织
     * @param adminDivisionName 设备所属行政区划
     * @return 满足条件查询的表的行数
     */
    @Override
    public int countPage(String deviceType, int deviceFaultCode, String deviceStatus, String orgName, String adminDivisionName) {
        return this.deviceDao.countPage(deviceType,deviceFaultCode,deviceStatus,orgName,adminDivisionName);
    }

    /**
     * 条件查询设备信息，分页显示
     *
     * @param deviceType       设备类型
     * @param deviceFaultCode  设备故障编号
     * @param number           页码
     * @param deviceStatus 设备状态
     * @param orgName      设备所属组织
     * @param adminDivisionName 设备所属行政区划
     * @param token 用户令牌
     * @return 设备列表
     */
    @Override
    public List<Device> listDevice(String deviceType, int deviceFaultCode, int number, String deviceStatus, String orgName, String adminDivisionName, String token) {
        logger.info("条件查询设备信息，分页显示listDevice");
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询",MODULE,SYSTEM_MODULE,"根据条件组织名称" + orgName + ",行政区划名称" + adminDivisionName + "查询" + (number + 1) + "到" + (number + 11) + "行真实设备信息",CATEGORY);
        switch (deviceStatus) {
            case "0":
                deviceStatus = "停用";
                break;
            case "1":
                deviceStatus = "在用";
                break;
            case "-1":
                deviceStatus = "删除";
                break;
                default:break;
        }
        List<Device> deviceList = this.deviceDao.listDevice(deviceType,deviceFaultCode,number,deviceStatus,orgName,adminDivisionName);
        if (deviceList.size() != 0) {
            for (Device device : deviceList) {
                device.setDeviceCreateTime(device.getDeviceCreateTime().substring(0,19));
                device.setDeviceUpdateTime(device.getDeviceUpdateTime().substring(0,19));
            }
        }
        return deviceList;
    }

    /**
     * 添加设备信息(需要同步给DIM)
     *
     * @param device 设备信息
     * @param token 用户令牌
     * @return 数据库操作影响行数
     */
    @Override
    public int insertDevice(Device device, String token) {
        if(device.getDeviceId() == null){
            device.setDeviceId(Initialise.initialise());
        }
        logger.info("添加设备信息并同步给DIM" + device);
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"添加",MODULE,SYSTEM_MODULE,"添加编号为：" + device.getDeviceId() + "设备名称：" + device.getDeviceName() + "的真实设备信息",CATEGORY);
        int status = this.deviceDao.insertDevice(device);
        try {
            dimUtil.devicePost(device);
        } catch (SQLException e) {
            status = 11;
            logger.warn("ERROR:DIM服务异常");
            return status;
        }
        return status;
    }

    /**
     * 删除一个设备信息(需要同步给DIM)
     *
     * @param deviceId 设备唯一编号
     * @param token 用户令牌
     * @return 数据操作影响行数
     */
    @Override
    public int deleteDevice(String deviceId, String token) {
        logger.info("删除一个设备信息并同步给DIM" + deviceId);
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"删除",MODULE,SYSTEM_MODULE,"删除编号：" + deviceId + "的真实设备信息",CATEGORY);
        int status = this.deviceDao.deleteDevice(deviceId);
        this.channelDao.deleteChannelByDeviceId(deviceId);
        try {
            dimUtil.deviceDelete(deviceId);
        } catch (Exception e) {
            status = 11;
            logger.warn("ERROR:DIM服务异常");
            return status;
        }
        return status;
    }

    /**
     * 更新一个设备信息(需要同步给DIM)
     *
     * @param device 设备的新信息
     * @param token 用户令牌
     * @return 数据操作影响行数
     */
    @Override
    public int updateDevice(Device device, String token) {
        logger.info("更新一个设备信息并同步给DIM" + device);
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"更新",MODULE,SYSTEM_MODULE,"修改编号为：" + device.getDeviceId() + "设备名称：" + device.getDeviceName() + "的真实设备信息",CATEGORY);
        int status = 0;
        if ("IPcamera".equals(device.getDeviceType())) {
            if (device.getDeviceChannelCount() == 1) {
                logger.info("更新IPcamera设备信息确保通道数只能为 1, DeviceChannelCount:" + device.getDeviceChannelCount());
                status = this.deviceDao.updateDevice(device);
            } else {
                return 10;
            }
        } else {
            logger.warn("更新DVR设备信息device:" + device);
            status = this.deviceDao.updateDevice(device);
        }
        // 同步DIM设备更新信息
        try {
            dimUtil.devicePut(device);
        } catch (Exception e) {
            status = 11;
            logger.warn("ERROR:DIM服务异常status:" + status);
            return status;
        }
        return status;
    }

    /**
     * DIM反馈设备运行状态
     *
     * @param device 设备的运行状态，设备编号
     * @return 数据操作影响行数
     */
    @Override
    public int updateRuntimeDevice(Device device) {
        logger.info("DIM反馈设备运行状态" + device);
        return this.deviceDao.updateRuntimeDevice(device);
    }

    /**
     * 查询设备告警信息并分页展示
     *
     * @param upStartTime    查询条件开始时间
     * @param lowStartTime   查询条件结束时间
     * @param deviceType     设备类型
     * @param deviceName     设备名称
     * @param deviceFault    设备故障信息
     * @param deviceUserName 设备用户名
     * @param deviceStatus   设备状态
     * @param number         页码数
     * @param token 用户令牌
     * @return 数据库信息
     */
    @Override
    public List<Device> getRuntimeDevice(String upStartTime, String lowStartTime, String deviceType, String deviceName, String deviceFault, String deviceUserName, String deviceStatus, int number, String token) {
        logger.info("查询设备告警信息并分页展示" + deviceName);
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询","真实设备告警",SYSTEM_MODULE,"根据条件设备用户名: " + deviceName + "查询" + (number + 1) + "到" + (number + 11) + "行真实设备告警信息",CATEGORY);
        List<Device> deviceList = this.deviceDao.getRuntimeDevice(upStartTime, lowStartTime, deviceType, deviceName, deviceFault, deviceUserName, deviceStatus, number);
        if (deviceList.size() != 0) {
            for(Device device : deviceList) {
                device.setDeviceFaultTime(device.getDeviceFaultTime().substring(0,19));
            }
        }
        return deviceList;
    }

    /**
     * 查询设备告警信息count数
     *
     * @param upStartTime    查询条件开始时间
     * @param lowStartTime   查询条件结束时间
     * @param deviceType     设备类型
     * @param deviceName     设备名称
     * @param deviceFault     设备故障信息
     * @param deviceUserName 设备用户名
     * @param deviceStatus   设备状态
     * @return 数据库信息
     */
    @Override
    public int countRuntimeDevice(String upStartTime, String lowStartTime, String deviceType, String deviceName, String deviceFault, String deviceUserName, String deviceStatus) {
        return this.deviceDao.countRuntimeDevice(upStartTime, lowStartTime, deviceType, deviceName, deviceFault, deviceUserName, deviceStatus);
    }

    /**
     * 显示最新5条的设备告警信息
     *
     * @return List<Device>
     */
    @Override
    public List<Device> getDeviceFault() {
        logger.info("显示最新的5条设备告警信息getDeviceFault");
        return this.deviceDao.getDeviceFault();
    }

    /**
     * 饼状图、获取设备告警类型数量
     *
     * @return List<Device>
     */
    @Override
    public Map<String, Object> getDeviceFaultTypeAndCount() {
        logger.info("饼状图、获取设备告警类型和数量getDeviceFaultTypeAndCount");
        List<String> list = this.deviceDao.getDeviceFaultType();
        List<Object> listCount = new ArrayList<>(1);
        Map<String, Object> map = new HashMap<>(1);
        if (list != null) {
            for (String deviceFault : list) {
                Map<String, Object> dataMap = new HashMap<>(1);
                int count = this.deviceDao.getDeviceFaultCount(deviceFault);
                dataMap.put("value", count);
                dataMap.put("name", deviceFault);
                listCount.add(dataMap);
            }
        }
        map.put("data", listCount);
        return map;
    }


    /**
     * 根据设备编号查询设备
     *
     * @param deviceId 设备编号
     * @param token 用户令牌
     * @return 该编号的完整的设备信息
     */
    @Override
    public Device getDeviceByDeviceId(String deviceId, String token) {
        logger.info("根据设备编号查询设备" + deviceId);
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询",MODULE,SYSTEM_MODULE,"根据设备编号：" + deviceId + "查询真实设备信息",CATEGORY);
        Device device = this.deviceDao.getDeviceByDeviceId(deviceId);
        if (device != null) {
            device.setDeviceCreateTime(device.getDeviceCreateTime().substring(0,19));
            device.setDeviceUpdateTime(device.getDeviceUpdateTime().substring(0,19));
            device.setOrgName(divAndOrgUtil.orgName(device.getDeviceOrgId()));
            device.setDivisionName(divAndOrgUtil.divisionName(device.getDeviceDivisionId()));
        }
        return device;
    }

    /**
     * 激活设备
     *
     * @param deviceId 设备编号
     * @param token 用户令牌
     * @return 数据库的操作情况
     */
    @Override
    public int activeDevice(String deviceId, String token) {
        logger.info("激活设备" + deviceId);
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"激活",MODULE,SYSTEM_MODULE,"激活设备编号为：" + deviceId + "的真实设备信息",CATEGORY);
        int status = this.deviceDao.activeDevice(deviceId);
        channelDao.activeDeviceChannel(deviceId);
        try {
            logger.info("同步激活设备给DIM" + deviceId + ",status:" + status);
            dimUtil.activeDevice(deviceId);
        } catch (Exception e) {
            status = 11;
            logger.warn("ERROR:DIM服务异常");
            return status;
        }
        return status;
    }

    /**
     * 停用设备
     *
     * @param deviceId 设备编号
     * @param token 用户令牌
     * @return 数据库的操作情况
     */
    @Override
    public int disableDevice(String deviceId, String token) {
        logger.info("停用设备" + deviceId);
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"停用",MODULE,SYSTEM_MODULE,"停用设备编号为：" + deviceId + "的真实设备信息",CATEGORY);
        int status = this.deviceDao.disableDevice(deviceId);
        channelDao.disableDeviceChannel(deviceId);
        try {
            logger.info("同步停用设备给DIM" + deviceId + ",status:" + status);
            dimUtil.disableDevice(deviceId);
        } catch (Exception e) {
            status = 11;
            logger.warn("ERROR:DIM服务异常");
            return status;
        }
        return status;
    }

    /**
     * 设备名称查重
     *
     * @param deviceName
     * @param deviceId
     * @return 已经存在返回1，未存在返回0
     */
    @Override
    public int countDeviceName(String deviceName, String deviceId) {
        Device device = this.deviceDao.getDeviceByDeviceId(deviceId);
        if (device != null) {
            logger.info("修改设备-设备名称查重countDeviceName:" + deviceName + ", deviceId:" + deviceId);
            return this.deviceDao.countDeviceName(deviceId,deviceName);
        } else {
            logger.info("添加设备-设备名称查重countDeviceName:" + deviceName + ", deviceId:" + deviceId);
            deviceId = "undefined";
            return this.deviceDao.countDeviceName(deviceId,deviceName);
        }
    }
}
