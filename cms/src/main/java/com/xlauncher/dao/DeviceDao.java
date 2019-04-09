package com.xlauncher.dao;
import com.xlauncher.entity.Device;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 真实设备Dao层
 * @date 2018-05-08
 * @author 白帅雷
 */
@Service
public interface DeviceDao {
    /**
     * 分页显示设备信息
     * @param deviceType 设备类型
     * @param deviceFaultCode 设备故障编号
     * @param deviceStatus 设备状态
     * @param orgName 设备所属组织
     * @param adminDivisionName 设备所属行政区划
     * @return 满足条件查询的表的行数
     */
    int countPage(@Param("deviceType") String deviceType, @Param("deviceFaultCode") int deviceFaultCode
            , @Param("deviceStatus") String deviceStatus, @Param("orgName")  String orgName, @Param("adminDivisionName") String adminDivisionName);
    /**
     * 条件查询设备信息，分页显示
     *
     * @param number 页码
     * @param deviceType 设备类型
     * @param deviceFaultCode 设备故障编号
     * @param deviceStatus 设备状态
     * @param orgName 设备所属组织
     * @param adminDivisionName 设备所属行政区划
     * @return 设备列表
     */
    List<Device> listDevice(@Param("deviceType") String deviceType, @Param("deviceFaultCode") int deviceFaultCode
            , @Param("number") int number, @Param("deviceStatus") String deviceStatus
            , @Param("orgName") String orgName, @Param("adminDivisionName") String adminDivisionName);
    /**
     * 添加设备信息
     * @param device 设备信息
     * @return 数据库操作影响行数
     */
    int insertDevice(Device device);

    /**
     * 删除一个设备信息
     * @param deviceId 设备唯一编号
     * @return 数据操作影响行数
     */
    int deleteDevice(String deviceId);

    /**
     * 更新一个设备信息
     * @param device 设备的新信息
     * @return 数据操作影响行数
     */
    int updateDevice(Device device);

    /**
     * DIM反馈设备运行状态
     * @param device 设备的运行状态，设备编号
     * @return 数据操作影响行数
     */
    int updateRuntimeDevice(Device device);

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
    List<Device> getRuntimeDevice(@Param("upStartTime") String upStartTime, @Param("lowStartTime") String lowStartTime
            , @Param("deviceType") String deviceType, @Param("deviceName") String deviceName
            , @Param("deviceFault") String deviceFault, @Param("deviceUserName") String deviceUserName, @Param("deviceStatus") String deviceStatus, @Param("number") int number);

    /**
     * 查询设备告警信息count数
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
    int countRuntimeDevice(@Param("upStartTime") String upStartTime, @Param("lowStartTime") String lowStartTime
            , @Param("deviceType") String deviceType, @Param("deviceName") String deviceName
            , @Param("deviceFault") String deviceFault, @Param("deviceUserName") String deviceUserName, @Param("deviceStatus") String deviceStatus);

    /**
     * 根据设备编号查询设备
     * @param deviceId 设备编号
     * @return 该编号的完整的设备信息
     */
    Device getDeviceByDeviceId(String deviceId);

    /**
     * 激活设备
     * @param deviceId 设备编号
     * @return 数据库的操作情况
     */
    int activeDevice(String deviceId);

    /**
     * 停用设备
     * @param deviceId 设备编号
     * @return 数据库的操作情况
     */
    int disableDevice(String deviceId);

    /**
     * 查询某一个设备下支持的通道总数
     * @param channelSourceId 通道资源编号
     * @return 数据操作影响行数
     */
    Device deviceChannelCount(@Param("channelSourceId") String channelSourceId);

    /**
     * 设备名称查重
     * @param deviceName
     * @param deviceId 添加设备，对所有名称校验；修改设备，除当前设备外校验
     * @return 已经存在返回1，未存在返回0
     */
    int countDeviceName(@Param("deviceId") String deviceId, @Param("deviceName") String deviceName);
    /**
     * 显示最新5条的设备告警信息
     *
     * @return List<Device>
     */
    List<Device> getDeviceFault();

    /**
     * 饼状图、获取设备告警类型数量
     *
     * @return List<Device>
     */
    List<String> getDeviceFaultType();

    /**
     * 饼状图、获取设备告警数量
     *
     * @param deviceFault 故障信息
     * @return int
     */
    int getDeviceFaultCount(@Param("deviceFault") String deviceFault);

    /**
     * 根据组织获取设备编号
     * @param orgId
     * @return
     */
    List<Device> getDeviceByOrg(String orgId);

    /**
     * 设备是非存在的验证，供正阳对接使用
     * @param deviceId 设备编号
     * @return 1为已存在，0为不存在
     */
    int deviceExistence(String deviceId);
}
