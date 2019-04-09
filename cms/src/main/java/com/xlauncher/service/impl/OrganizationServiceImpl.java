package com.xlauncher.service.impl;

import com.xlauncher.dao.DeviceDao;
import com.xlauncher.dao.OrganizationDao;
import com.xlauncher.dao.UserDao;
import com.xlauncher.entity.Device;
import com.xlauncher.entity.Organization;
import com.xlauncher.entity.User;
import com.xlauncher.service.DeviceService;
import com.xlauncher.service.OrganizationService;
import com.xlauncher.util.CheckToken;
import com.xlauncher.util.Initialise;
import com.xlauncher.util.OperationLogUtil;
import com.xlauncher.util.Recursion;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    Recursion recursion;
    @Autowired
    private OperationLogUtil logUtil;
    @Autowired
    UserDao userDao;
    @Autowired
    DeviceDao deviceDao;
    @Autowired
    DeviceService deviceService;
    @Autowired
    private CheckToken checkToken;
    private static final String MODULE = "组织管理";
    private static final String SYSTEM_MODULE = "网格管理";
    private static final String CATEGORY = "运营面";
    private static Logger logger = Logger.getLogger(OrganizationServiceImpl.class);

    @Override
    public List<Organization> listOrganization(String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询",MODULE,SYSTEM_MODULE,"查询组织信息",CATEGORY);
        return organizationDao.listOrganization();
    }

    @Override
    public int insertOrganization(Organization organization, String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"添加",MODULE,SYSTEM_MODULE,"添加组织信息:" + organization.getName(),CATEGORY);
        if (organization.getOrgId() == null) {
            organization.setOrgId(Initialise.initialise());
        }
        return organizationDao.insertOrganization(organization);
    }

    @Override
    public int updateOrganization(Organization organization, String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"更新",MODULE,SYSTEM_MODULE,"更新组织信息:" + organization.getName(),CATEGORY);
        return organizationDao.updateOrganization(organization);
    }

    @Override
    public int deleteOrganization(String orgId, String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"删除",MODULE,SYSTEM_MODULE,"删除编号为：" + orgId + "的组织信息",CATEGORY);
        List<User> userList = this.userDao.getUserByOrg(orgId);
        // 删除组织下面的用户
        logger.info("删除组织并同时删除组织下面的用户deleteOrganization, userList:" + userList);
        if (userList != null) {
            userList.forEach(user -> {
                this.userDao.deleteUser(user.getUserId());
            });
        }
        List<Device> deviceList = this.deviceDao.getDeviceByOrg(orgId);
        // 删除组织下面的设备及其通道
        logger.info("删除组织并同时删除组织下面的设备及其通道deleteOrganization, deviceList:" + deviceList);
        if (deviceList != null) {
            deviceList.forEach(device -> {
                this.deviceService.deleteDevice(device.getDeviceId(),token);
            });
        }
        recursion.resuisionO(orgId);
        return organizationDao.deleteOrganization(orgId);
    }
}
