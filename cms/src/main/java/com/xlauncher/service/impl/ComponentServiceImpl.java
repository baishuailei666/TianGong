package com.xlauncher.service.impl;

import com.xlauncher.dao.ComponentDao;
import com.xlauncher.entity.Component;
import com.xlauncher.service.ComponentService;
import com.xlauncher.util.CheckToken;
import com.xlauncher.util.ComponentUtil;
import com.xlauncher.util.OperationLogUtil;
import com.xlauncher.util.RestTemplateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组件ServiceImpl层
 * @date 2018-05-10
 * @author 白帅雷
 */
@Service
public class ComponentServiceImpl implements ComponentService {
    @Autowired
    ComponentDao componentDao;
    @Autowired
    private OperationLogUtil logUtil;
    @Autowired
    ComponentUtil componentUtil;
    @Autowired
    RestTemplateUtil restTemplateUtil;
    @Autowired
    private CheckToken checkToken;
    private static final String MODULE = "组件配置管理";
    private static final String SYSTEM_MODULE = "组件管理";
    private static final String CATEGORY = "运维面";
    private Logger logger = Logger.getLogger(ComponentServiceImpl.class);
    /**
     * 添加组件
     *
     * @param component 组件信息
     * @param token 用户令牌
     * @return 数据库操作情况
     */
    @Override
    public int addComponent(Component component, String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"添加",MODULE,SYSTEM_MODULE,"添加组件缩写为：" + component.getComponentAbbr() + "的组件信息",CATEGORY);
        Map<String, Object> map = new HashMap<>(1);
        map.put("data","无");
        switch (component.getComponentAbbr()) {
            case "cms": component.setComponentName("中央管理服务");
                component.setComponentConfiguration(map);
                break;
            case "ics": component.setComponentName("中央处理服务");
                component.setComponentConfiguration(map);
                break;
            case "es": component.setComponentName("告警信息处理服务");
                break;
            case "dim": component.setComponentName("设备管理服务");
                component.setComponentConfiguration(map);
                break;
            case "k8s": component.setComponentName("视频管理服务");
                break;
            case "ftp": component.setComponentName("FTP服务器");
                component.setComponentConfiguration(map);
                break;
            case "mq": component.setComponentName("RabbitMQ服务器");
                component.setComponentConfiguration(map);
                break;
            default:break;
        }
        return this.componentDao.addComponent(component);
    }

    /**
     * 删除组件
     *
     * @param id 组件编号
     * @param token 用户令牌
     * @return 数据库操作情况
     */
    @Override
    public int deleteComponent(int id, String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"删除",MODULE,SYSTEM_MODULE,"删除组件编号为：" + id + "的组件信息",CATEGORY);
        return this.componentDao.deleteComponent(id);
    }

    /**
     * 修改组件
     *
     * @param component 组件信息
     * @param token 用户令牌
     * @return 数据库操作情况
     */
    @Override
    public int updateComponent(Component component, String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"更新",MODULE,SYSTEM_MODULE,"修改组件编号为：" + component.getId() + "的组件信息",CATEGORY);
        if ("es".equals(component.getComponentAbbr())) {
            Map<String, Object> map = new HashMap<>(1);
            map.put("class_thresh", component.getComponentConfiguration().get("class_thresh"));
            map.put("detect_thresh", component.getComponentConfiguration().get("detect_thresh"));
            component.setComponentConfiguration(map);
        }
        int status = this.componentDao.updateComponent(component);
        String abbr = component.getComponentAbbr();
        if (status == 1) {
            logger.info("组件更新成功! status." + status + ",component."+ component);
            switch (abbr){
                case "ics":
                    logger.info("[ICS]同步服务.ics：" + component);
                    this.componentUtil.synIcs();
                    break;
                case "cms":
                    logger.info("[CMS]同步服务.cms：" + component);
                    this.componentUtil.synCms();
                    break;
                case "dim":
                    logger.info("[DIM]同步服务.dim：" + component);
                    this.componentUtil.synDim();
                    break;
                case "es":
                    logger.info("[ES]同步服务.es：" + component);
                    this.componentUtil.synEs();
                    break;
                case "ftp":
                    logger.info("[FTP]同步服务.ftp：" + component);
                    this.componentUtil.synFtp();
                    break;
                case "mq":
                    logger.info("[RabbitMQ]同步服务.mq：" + component);
                    this.componentUtil.synMq();
                    break;
                default:return status;
            }
        } else {
            logger.warn("组件更新失败！：" + component + status);
        }
        return status;
    }

    /**
     * 分页获取所有组件
     *
     * @param number 页码数
     * @param token 用户令牌
     * @param componentName 组件名称
     * @return list列表所有组件
     */
    @Override
    public List<Component> listAllComponentByNum(int number, String componentName, String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询",MODULE,SYSTEM_MODULE,"查询组件名称：" + componentName + (number + 1) + "到" + (number + 11) + "行的组件信息",CATEGORY);
        return this.componentDao.listAllComponentByNum(number, componentName);
    }

    /**
     * 获取行数，用于分页
     *
     * @param componentName 组件名称
     * @return 组件总数
     */
    @Override
    public int countPage(String componentName) {
        return this.componentDao.countPage(componentName);
    }

    /**
     * 获取所有组件用于内部服务同步
     *
     * @return list列表所有组件
     */
    @Override
    public List<Component> listComponent() {
        return this.componentDao.listComponent();
    }

    /**
     * 根据组件编号查询组件
     *
     * @param id 组件编号
     * @param token 用户令牌
     * @return 单个组件
     */
    @Override
    public Component getComponentById(int id, String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询",MODULE,SYSTEM_MODULE,"查询组件编号为：" + id + "的组件信息",CATEGORY);
        return this.componentDao.getComponentById(id);
    }

    /**
     * 根据组件名称模糊查询组件信息
     *
     * @param componentName 组件名称
     * @param token 用户令牌
     * @return 组件信息
     */
    @Override
    public List<Component> getComponentByName(String componentName, String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询",MODULE,SYSTEM_MODULE,"查询组件名称为：" + componentName + "的组件信息",CATEGORY);
        return this.componentDao.getComponentByName(componentName);
    }

    /**
     * 英文缩写获取组件信息
     *
     * @param abbr
     * @return
     */
    @Override
    public Component getComponentByAbbr(String abbr) {
        return this.componentDao.getComponentByAbbr(abbr);
    }

}
