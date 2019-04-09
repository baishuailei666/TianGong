package com.xlauncher.service;

import com.xlauncher.entity.VirtualDevice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * 虚拟设备Service层
 * @date 2018-05-09
 * @author 白帅雷
 */
@Service
public interface VirtualDeviceService {
    /**
     * 分页显示虚拟设备信息
     *
     * @param virtualDeviceType 虚拟设备类型
     * @param virtualDeviceFaultCode 虚拟设备故障编号
     * @param virtualDeviceStatus 虚拟设备状态
     * @param orgName 虚拟设备所属组织编号
     * @param adminDivisionName 虚拟设备所属行政区划编号
     * @return 满足条件查询的表的行数
     */
    int countPage(@Param("virtualDeviceType") String virtualDeviceType, @Param("virtualDeviceFaultCode") int virtualDeviceFaultCode
            , @Param("virtualDeviceStatus") String virtualDeviceStatus, @Param("orgName") String orgName, @Param("adminDivisionName") String adminDivisionName);
    /**
     * 条件查询虚拟设备信息，分页显示
     *
     * @param number 页码
     * @param virtualDeviceType 虚拟设备类型
     * @param virtualDeviceFaultCode 虚拟设备故障编号
     * @param virtualDeviceStatus 虚拟设备状态
     * @param orgName 虚拟设备所属组织
     * @param adminDivisionName 虚拟设备所属行政区划
     * @return 设备列表
     */
    List<VirtualDevice> listVirtualDevice(@Param("virtualDeviceType") String virtualDeviceType, @Param("virtualDeviceFaultCode") int virtualDeviceFaultCode
            , @Param("number") int number, @Param("virtualDeviceStatus") String virtualDeviceStatus
            , @Param("orgName") String orgName, @Param("adminDivisionName") String adminDivisionName);
    /**
     * 添加虚拟设备信息
     * @param virtualDevice 虚拟设备信息
     * @return 数据库操作影响行数
     */
    int insertVirtualDevice(VirtualDevice virtualDevice);

    /**
     * 删除一个虚拟设备信息
     * @param virtualDeviceId 虚拟设备唯一编号
     * @return 数据操作影响行数
     */
    int deleteVirtualDevice(String virtualDeviceId);

    /**
     * 更新一个虚拟设备信息
     * @param virtualDevice 虚拟设备的新信息
     * @return 数据操作影响行数
     */
    int updateVirtualDevice(VirtualDevice virtualDevice);

    /**
     * DIM反馈虚拟设备运行状态
     * @param virtualDevice 虚拟设备的运行状态，设备编号
     * @return 数据操作影响行数
     */
    int updateRuntimeVirtualDevice(VirtualDevice virtualDevice);
    /**
     * 根据虚拟设备编号查询设备
     * @param virtualDeviceId 虚拟设备编号
     * @return 该编号的完整的设备信息
     */
    VirtualDevice getVirtualDeviceByVirtualDeviceId(String virtualDeviceId);
}
