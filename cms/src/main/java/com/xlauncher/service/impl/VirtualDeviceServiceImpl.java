package com.xlauncher.service.impl;

import com.xlauncher.dao.VirtualChannelDao;
import com.xlauncher.dao.VirtualDeviceDao;
import com.xlauncher.entity.VirtualChannel;
import com.xlauncher.entity.VirtualDevice;
import com.xlauncher.service.VirtualDeviceService;
import com.xlauncher.util.CheckData;
import com.xlauncher.util.Initialise;
import com.xlauncher.util.OperationLogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * 虚拟设备ServiceImpl层
 * @date 2018-05-09
 * @author 白帅雷
 */
@Service
public class VirtualDeviceServiceImpl implements VirtualDeviceService {
    @Autowired
    VirtualDeviceDao virtualDeviceDao;
    @Autowired
    VirtualChannelDao virtualChannelDao;
    @Autowired
    private OperationLogUtil logUtil;
    private static final String MODULE = "虚拟设备管理";
    private static final String SYSTEM_MODULE = "设备管理";
    private static final String CATEGORY = "运维面";
    /**
     * 分页显示虚拟设备信息
     *
     * @param virtualDeviceType       虚拟设备类型
     * @param virtualDeviceFaultCode  虚拟设备故障编号
     * @param virtualDeviceStatus 虚拟设备状态
     * @param orgName      虚拟设备所属组织编号
     * @param adminDivisionName 虚拟设备所属行政区划
     * @return 满足条件查询的表的行数
     */
    @Override
    public int countPage(String virtualDeviceType, int virtualDeviceFaultCode, String virtualDeviceStatus, String orgName, String adminDivisionName) {
        return this.virtualDeviceDao.countPage(virtualDeviceType,virtualDeviceFaultCode,virtualDeviceStatus,orgName,adminDivisionName);
    }

    /**
     * 条件查询虚拟设备信息，分页显示
     *
     * @param virtualDeviceType       虚拟设备类型
     * @param virtualDeviceFaultCode  虚拟设备故障编号
     * @param number                  页码
     * @param virtualDeviceStatus 虚拟设备状态
     * @param orgName      虚拟设备所属组织
     * @param adminDivisionName 虚拟设备所属行政区划
     * @return 设备列表
     */
    @Override
    public List<VirtualDevice> listVirtualDevice(String virtualDeviceType, int virtualDeviceFaultCode, int number, String virtualDeviceStatus, String orgName, String adminDivisionName) {
        logUtil.opLog("unknown","查询",MODULE,SYSTEM_MODULE,"根据条件虚拟设备类型：" + virtualDeviceType + ",虚拟设备故障编码：" + virtualDeviceFaultCode + ",虚拟设备使用状态：" + virtualDeviceStatus + ",虚拟设备组织名称：" + orgName + ",虚拟设备行政区划名称：" + adminDivisionName + "查询" + (number + 1) + "到" + (number + 11) + "行虚拟设备信息",CATEGORY);
        List<VirtualDevice> virtualDeviceList = this.virtualDeviceDao.listVirtualDevice(virtualDeviceType,virtualDeviceFaultCode,number,virtualDeviceStatus,orgName,adminDivisionName);
        if (virtualDeviceList.size() != 0) {
            for (VirtualDevice virtualDevice : virtualDeviceList) {
                virtualDevice.setVirtualDeviceCreateTime(virtualDevice.getVirtualDeviceCreateTime().substring(0,19));
                virtualDevice.setVirtualDeviceUpdateTime(virtualDevice.getVirtualDeviceUpdateTime().substring(0,19));
            }
        }
        return virtualDeviceList;
    }

    /**
     * 添加虚拟设备信息
     *
     * @param virtualDevice 虚拟设备信息
     * @return 数据库操作影响行数
     */
    @Override
    public int insertVirtualDevice(VirtualDevice virtualDevice) {
        int len = 32;
        if (virtualDeviceDao.virtualDeviceExistence(virtualDevice.getVirtualDeviceId()) == 1){
            return 101;
        }
        if (virtualDevice.getVirtualDeviceIp()==null||virtualDevice.getVirtualDevicePort()==null||virtualDevice.getVirtualDeviceUserName()==null||virtualDevice.getVirtualDeviceStatus()==null
                ||virtualDevice.getVirtualDeviceUserPassword()==null||virtualDevice.getVirtualDeviceType()==null||virtualDevice.getVirtualDeviceChannelCount()==null) {
            return 102;
        }
        logUtil.opLog("unknown","添加",MODULE,SYSTEM_MODULE,"添加编号为：" + virtualDevice.getVirtualDeviceId() + "的虚拟设备信息",CATEGORY);
        if (virtualDevice.getVirtualDeviceId() == null) {
            virtualDevice.setVirtualDeviceId(Initialise.initialise());
        } else if (!CheckData.checkString(virtualDevice.getVirtualDeviceId(), len)){
            return 104;
        }
        return this.virtualDeviceDao.insertVirtualDevice(virtualDevice);
    }

    /**
     * 删除一个虚拟设备信息
     *
     * @param virtualDeviceId 虚拟设备唯一编号
     * @return 数据操作影响行数
     */
    @Override
    public int deleteVirtualDevice(String virtualDeviceId) {
        if (virtualDeviceDao.virtualDeviceExistence(virtualDeviceId)==0){
            return 303;
        }
        int virtualChannelStatus = virtualChannelDao.deleteVirtualChannelByVirtualDeviceId(virtualDeviceId);
        if (virtualChannelStatus != 0){
            return 300;
        }
        logUtil.opLog("unknown","删除",MODULE,SYSTEM_MODULE,"删除编号为：" + virtualDeviceId + "的虚拟设备信息",CATEGORY);
        return this.virtualDeviceDao.deleteVirtualDevice(virtualDeviceId);
    }

    /**
     * 更新一个虚拟设备信息
     *
     * @param virtualDevice 虚拟设备的新信息
     * @return 数据操作影响行数
     */
    @Override
    public int updateVirtualDevice(VirtualDevice virtualDevice) {
        int len = 32;
        if (virtualDevice.getVirtualDeviceId() == null) {
            return 202;
        } else if (!CheckData.checkString(virtualDevice.getVirtualDeviceId(),len)){
            return 204;
        }
        if (virtualDeviceDao.virtualDeviceExistence(virtualDevice.getVirtualDeviceId())==0){
            return 203;
        }
        logUtil.opLog("unknown","更新",MODULE,SYSTEM_MODULE,"修改编号为：" + virtualDevice.getVirtualDeviceId() + "的虚拟设备信息",CATEGORY);
        return this.virtualDeviceDao.updateVirtualDevice(virtualDevice);
    }

    /**
     * DIM反馈虚拟设备运行状态
     *
     * @param virtualDevice 虚拟设备的运行状态，设备编号
     * @return 数据操作影响行数
     */
    @Override
    public int updateRuntimeVirtualDevice(VirtualDevice virtualDevice) {
        return this.virtualDeviceDao.updateRuntimeVirtualDevice(virtualDevice);
    }

    /**
     * 根据虚拟设备编号查询设备
     *
     * @param virtualDeviceId 虚拟设备编号
     * @return 该编号的完整的设备信息
     */
    @Override
    public VirtualDevice getVirtualDeviceByVirtualDeviceId(String virtualDeviceId) {
        logUtil.opLog("unknown","查询",MODULE,SYSTEM_MODULE,"查询编号为：" + virtualDeviceId + "的虚拟设备信息",CATEGORY);
        VirtualDevice virtualDevice = this.virtualDeviceDao.getVirtualDeviceByVirtualDeviceId(virtualDeviceId);
        if (virtualDevice != null) {
            virtualDevice.setVirtualDeviceCreateTime(virtualDevice.getVirtualDeviceCreateTime().substring(0,19));
            virtualDevice.setVirtualDeviceUpdateTime(virtualDevice.getVirtualDeviceUpdateTime().substring(0,19));

        }
        return virtualDevice;
    }
}
