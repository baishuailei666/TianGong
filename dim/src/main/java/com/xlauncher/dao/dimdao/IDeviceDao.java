package com.xlauncher.dao.dimdao;

import com.xlauncher.entity.Device;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备Dao层
 * @author YangDengcheng
 * @date 2018/2/27 14:37
 */
@Service("iDeviceDao")
public interface IDeviceDao {
    /**
     * 操作DIM数据库（增加一台设备）
     * @param device
     * @return int success：1 failed：0
     */
    int insertDevice(Device device);

    /**
     * 操作DIM数据库（查询所有设备信息）
     * @return List<Device>
     */
    List<Device> queryAllDevice();

    /**
     * 操作数据库（更新一台设备信息）
     * @param device
     * @return int success：1 failed：0
     */
    int updateDeviceMsg(Device device);
    /**
     * 反馈设备故障状态信息
     * @param device
     * @return int success：1 failed：0
     */
    int updateRuntimeDevice(Device device);
    /**
     * 操作数据库（删除一台设备）
     * @param id
     * @return int success：1 failed：0
     */
    int deleteDevice(String id);

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
     * 数据库操作（查看具体某台设备的详细信息）
     * @param id 设备ID
     * @return Device对象
     */
    Device queryDeviceMsg(String id);

    /**
     * 数据库操作（查看某台设备的所有相关通道编号）
     * @param id 设备ID
     * @return Device对象
     */
    List<String> queryAllInSameDevice(String id);
}
