package com.xlauncher.service.impl;

import com.xlauncher.dao.VirtualChannelDao;
import com.xlauncher.dao.VirtualDeviceDao;
import com.xlauncher.entity.VirtualChannel;
import com.xlauncher.service.VirtualChannelService;
import com.xlauncher.util.CheckData;
import com.xlauncher.util.Initialise;
import com.xlauncher.util.OperationLogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * 虚拟通道ServiceImpl层
 * @date 2018-05-09
 * @author 白帅雷
 */
@Service
public class VirtualChannelServiceImpl implements VirtualChannelService {
    @Autowired
    VirtualChannelDao virtualChannelDao;
    @Autowired
    VirtualDeviceDao virtualDeviceDao;
    @Autowired
    private OperationLogUtil logUtil;
    private static final String MODULE = "虚拟设备管理";
    private static final String SYSTEM_MODULE = "设备管理";
    private static final String CATEGORY = "运维面";
    /**
     * 查询某个虚拟设备所拥有的所有通道，分页显示
     *
     * @param virtualChannelSourceId 虚拟设备的资源编号
     * @param number                 页码
     * @return 一个具有相同channelSourceId的通道列表
     */
    @Override
    public List<VirtualChannel> listVirtualChannelBySource(String virtualChannelSourceId, int number) {
        logUtil.opLog("unknown","查询",MODULE,SYSTEM_MODULE,"查询虚拟设备编号为：" + virtualChannelSourceId + "所拥有的所有虚拟通道",CATEGORY);
        List<VirtualChannel> virtualChannelList = this.virtualChannelDao.listVirtualChannelBySource(virtualChannelSourceId,number);
        if (virtualChannelList.size() != 0 ) {
            for (VirtualChannel virtualChannel : virtualChannelList) {
                virtualChannel.setVirtualChannelCreateTime(virtualChannel.getVirtualChannelCreateTime().substring(0,19));
                virtualChannel.setVirtualChannelUpdateTime(virtualChannel.getVirtualChannelUpdateTime().substring(0,19));
            }
        }
        return virtualChannelList;
    }

    /**
     * 添加虚拟通道信息
     *
     * @param virtualChannel 虚拟通道信息
     * @return 数据库操作影响行数
     */
    @Override
    public int insertVirtualChannel(VirtualChannel virtualChannel) {
        int inStatus;
        int len = 32;
        virtualChannel.setVirtualChannelId(Initialise.initialise());
        if (virtualChannel.getVirtualChannelSourceId()==null||virtualChannel.getVirtualChannelNumber()==null||virtualChannel.getVirtualChannelLocation()==null
                ||virtualChannel.getVirtualChannelLongitude()==null||virtualChannel.getVirtualChannelLatitude()==null) {
            return 102;
        }
        if (virtualDeviceDao.getVirtualDeviceByVirtualDeviceId(virtualChannel.getVirtualChannelSourceId())==null){
            return 105;
        }
        if (!CheckData.checkString(virtualChannel.getVirtualChannelId(), len)
                ||!CheckData.checkString(virtualChannel.getVirtualChannelSourceId(), len)){
            return 104;
        }
        logUtil.opLog("unknown","添加",MODULE,SYSTEM_MODULE,"添加虚拟通道编号为：" + virtualChannel.getVirtualChannelId() + "的虚拟通道信息",CATEGORY);
        int virtualChannelCount = this.virtualChannelDao.virtualDeviceChannelCount(virtualChannel.getVirtualChannelSourceId());
        if (virtualChannel.getVirtualChannelNumber() <= virtualChannelCount) {
            int virtualChannelNumber = this.virtualChannelDao.virtualChannelConflict(virtualChannel.getVirtualChannelId(),virtualChannel.getVirtualChannelNumber());
            if (virtualChannelNumber == 0) {
                virtualChannel.setVirtualChannelPodStatus(0);
                inStatus = this.virtualChannelDao.insertVirtualChannel(virtualChannel);
            } else {
                return 106;
            }
        } else {
            return 106;
        }
        return inStatus;
    }

    /**
     * 删除虚拟通道信息
     *
     * @param virtualChannelId 虚拟通道编号
     * @return 数据操作影响行数
     */
    @Override
    public int deleteVirtualChannel(String virtualChannelId) {
        if (virtualChannelId==null){
            return 302;
        }
        if (virtualChannelDao.virtualChannelExistence(virtualChannelId)==0){
            return 303;
        }
        logUtil.opLog("unknown","删除",MODULE,SYSTEM_MODULE,"删除虚拟通道编号为：" + virtualChannelId + "的虚拟通道信息",CATEGORY);
        return this.virtualChannelDao.deleteVirtualChannel(virtualChannelId);
    }

    /**
     * 删除某个虚拟设备下所有的通道信息
     *
     * @param virtualDeviceId 虚拟设备编号，即虚拟通道中的virtualChannelSourceId
     * @return 数据库操作影响的行数
     */
    @Override
    public int deleteVirtualChannelByVirtualDeviceId(String virtualDeviceId) {
        logUtil.opLog("unknown","删除",MODULE,SYSTEM_MODULE,"删除虚拟设备编号为" + virtualDeviceId + "的所有虚拟通道信息",CATEGORY);
        return this.virtualChannelDao.deleteVirtualChannelByVirtualDeviceId(virtualDeviceId);
    }

    /**
     * 更新虚拟通道信息
     *
     * @param virtualChannel 虚拟通道的更新信息
     * @return 数据操作影响行数
     */
    @Override
    public int updateVirtualChannel(VirtualChannel virtualChannel) {
        int len = 32;
        if (virtualChannel.getVirtualChannelId()==null){
            return 202;
        } else if (!CheckData.checkString(virtualChannel.getVirtualChannelId(),len)
                ||!CheckData.checkString(virtualChannel.getVirtualChannelSourceId(),len)){
            return 204;
        }
        if (virtualChannelDao.virtualChannelExistence(virtualChannel.getVirtualChannelId())==0){
            return 203;
        }
        if (virtualChannel.getVirtualChannelSourceId()!=null) {
            if (virtualDeviceDao.getVirtualDeviceByVirtualDeviceId(virtualChannel.getVirtualChannelSourceId()) == null) {
                return 205;
            }
        }
        if (virtualChannel.getVirtualChannelNumber()!=null) {
            if (virtualChannel.getVirtualChannelNumber() > virtualDeviceDao.getVirtualDeviceByVirtualDeviceId(virtualChannel.getVirtualChannelSourceId()).getVirtualDeviceChannelCount()
                    || virtualChannelDao.virtualChannelConflict(virtualChannel.getVirtualChannelId(), virtualChannel.getVirtualChannelNumber()) != 0) {
                return 206;
            }
        } else {
            return 206;
        }
        logUtil.opLog("unknown","更新",MODULE,SYSTEM_MODULE,"修改虚拟通道编号为：" + virtualChannel.getVirtualChannelId() + "的虚拟通道信息",CATEGORY);
        return this.virtualChannelDao.updateVirtualChannel(virtualChannel);
    }

    /**
     * 根据虚拟通道编号查询虚拟通道信息
     *
     * @param virtualChannelId 虚拟通道编号
     * @return 虚拟通道信息
     */
    @Override
    public VirtualChannel getVirtualChannelByVirtualChannelId(String virtualChannelId) {
        logUtil.opLog("unknown","查询",MODULE,SYSTEM_MODULE,"查询虚拟通道编号为" + virtualChannelId + "的虚拟通道信息",CATEGORY);
        VirtualChannel virtualChannel = this.virtualChannelDao.getVirtualChannelByVirtualChannelId(virtualChannelId);
        if (virtualChannel != null) {
            virtualChannel.setVirtualChannelCreateTime(virtualChannel.getVirtualChannelCreateTime().substring(0,19));
            virtualChannel.setVirtualChannelUpdateTime(virtualChannel.getVirtualChannelUpdateTime().substring(0,19));
        }
        return virtualChannel;
    }

    /**
     * 查询虚拟通道信息
     *
     * @param number 页码
     * @return 一个包含十个通道的列表
     */
    @Override
    public List<VirtualChannel> listVirtualChannel(int number) {
        logUtil.opLog("unknown","查询",MODULE,SYSTEM_MODULE,"查询" + (number + 1) + "到" + (number + 11) + "行的虚拟通道信息",CATEGORY);
        List<VirtualChannel> virtualChannelList = this.virtualChannelDao.listVirtualChannel(number);
        if (virtualChannelList.size() != 0) {
            for (VirtualChannel virtualChannel : virtualChannelList) {
                virtualChannel.setVirtualChannelCreateTime(virtualChannel.getVirtualChannelCreateTime().substring(0,19));
                virtualChannel.setVirtualChannelUpdateTime(virtualChannel.getVirtualChannelUpdateTime().substring(0,19));
            }
        }
        return virtualChannelList;
    }

    /**
     * 获取某虚拟设备下所有的虚拟通道数量，用于分页
     *
     * @param virtualChannelSourceId 虚拟设备的编号
     * @return 该虚拟设备所拥有的通道数
     */
    @Override
    public int countPage(String virtualChannelSourceId) {
        return this.virtualChannelDao.countPage(virtualChannelSourceId);
    }

    /**
     * 将所有的虚拟通道的地理位置取出，供地图打点使用
     *
     * @return 虚拟通道地理位置信息列表
     */
    @Override
    public List<VirtualChannel> overview() {
        return this.virtualChannelDao.overview();
    }

    /**
     * DIM反馈虚拟通道运行状态
     *
     * @param virtualChannel 虚拟通道的状态，通道编号
     * @return 数据操作影响行数
     */
    @Override
    public int updateVirtualChannelStatus(VirtualChannel virtualChannel) {
        return this.virtualChannelDao.updateVirtualChannelStatus(virtualChannel);
    }
}
