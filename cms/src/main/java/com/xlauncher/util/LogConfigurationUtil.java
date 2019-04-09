package com.xlauncher.util;


import com.xlauncher.dao.OperationLogDao;
import com.xlauncher.entity.OperationLog;
import com.xlauncher.entity.RecordLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Stream;

/**
 * 日志配置管理：日志是否记录配置
 * @date 2018-05-21
 * @author 白帅雷
 */
@Component
public class LogConfigurationUtil {

    @Autowired
    private OperationLogDao logDao;
    @Autowired
    private OperationLogUtil logUtil;
    @Autowired
    private CheckToken checkToken;
    private static final String MODULE = "日志配置管理";
    private static final String SYSTEM_MODULE = "日志管理";
    private static final String CATEGORY = "运维面";
    private static Map<String, Object> confMap = new HashMap<>(1);

    /**
     * 根据编号从数据库得到对应的子模块并放入map中
     *
     * @param recordLists 日志配置记录编号列表
     * @param token 用户令牌
     */
    public void configuration(List<String> recordLists, String token){
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"添加",MODULE,SYSTEM_MODULE,"添加日志配置信息" + recordLists,CATEGORY);
        System.out.println("recordLists: " + recordLists);
        confMapDestroyed();
        for (String recordId : recordLists) {
            String recordModule = this.logDao.getRecordModuleById(recordId);
            this.logDao.upRecordStatus(recordId);
            confMap.put(recordModule, true);
        }
        System.out.println("confMap: " + confMap);
    }

    /**
     * 清空confMap中的数据
     */
    private synchronized void confMapDestroyed() {
        Iterator<String> iterator = getConfigurationKeySetIt();
        while (iterator.hasNext()) {
            String module = iterator.next();
            iterator.remove();
            this.logDao.reSetRecordStatus(module);
        }
    }

    /**
     * 迭代器，遍历集合中所有的key
     *
     * @return 迭代器
     */
    private static Iterator<String> getConfigurationKeySetIt() {
        return confMap.keySet().iterator();
    }

    /**
     * 判断是否记录配置
     *
     * @param opModule 操作模块
     * @return 布尔值true说明记录配置，false不记录配置
     */
    synchronized boolean checkIfHasConf(String opModule) {
        Iterator<String> iterator = getConfigurationKeySetIt();
        boolean hasLogin = false;
        while (iterator.hasNext()) {
            String module = iterator.next();
            if (opModule.equals(module)){
                hasLogin = true;
                break;
            }
        }
        return hasLogin;
    }

    /**
     * 给前端提供日志的记录状态
     *
     * @return 封装的日志记录状态
     */
    public List<Object> getRecordStatus() {
        Iterator<String> iterator = getConfigurationKeySetIt();
        List<Object> objectList = new ArrayList<>(1);
        while (iterator.hasNext()) {
            String module = iterator.next();
            List<RecordLog> mapList = this.logDao.getRecordStatus(module);
            for (RecordLog recordLog : mapList) {
                Map<String, Object> map = new HashMap<>(1);
                map.put("recordId", recordLog.getRecordId());
                map.put("recordStatus", recordLog.getRecordStatus());
                objectList.add(map);
            }
        }
        return objectList;
    }

    /**
     * 给前端提供所有日志记录信息
     *
     * @return 封装的日志记录信息
     */
    public Map<String, Object> getRecordModule() {
        Map<String, Object> mapMap = new HashMap<>(1);
        List<Map<String, Object>> list = new ArrayList<>(1);
        Map<String, Object> stringListMap = new HashMap<>(1);
        List<Map<String, Object>> mapOperationList = new ArrayList<>(1);
        List<Map<String, Object>> mapOperatingList = new ArrayList<>(1);

        Map<String, Object> listOperatingMap = new HashMap<>(1);
        List<RecordLog> listEvent = this.logDao.getRecordModuleBySysModule("事件管理");
        listOperatingMap.put("eventM", listEvent);
        listOperatingMap.put("eventName", "事件管理");
        List<RecordLog> listSystem = this.logDao.getRecordModuleBySysModule("系统管理");
        listOperatingMap.put("systemM", listSystem);
        listOperatingMap.put("systemName", "系统管理");
        List<RecordLog> listGrid = this.logDao.getRecordModuleBySysModule("网格管理");
        listOperatingMap.put("gridM", listGrid);
        listOperatingMap.put("gridName", "网格管理");

        Map<String, Object> listOperationMap = new HashMap<>(1);
        List<RecordLog> listRun = this.logDao.getRecordModuleBySysModule("运行监控");
        listOperationMap.put("runM", listRun);
        listOperationMap.put("runName","运行监控");
        List<RecordLog> listDevice = this.logDao.getRecordModuleBySysModule("设备管理");
        listOperationMap.put("deviceM", listDevice);
        listOperationMap.put("deviceName", "设备管理");
        List<RecordLog> listOpera = this.logDao.getRecordModuleBySysModule("组件管理");
        listOperationMap.put("componentM", listOpera);
        listOperationMap.put("componentName", "组件管理");
        List<RecordLog> listLog = this.logDao.getRecordModuleBySysModule("日志管理");
        listOperationMap.put("logM", listLog);
        listOperationMap.put("logName", "日志管理");
        List<RecordLog> listEmail = this.logDao.getRecordModuleBySysModule("邮箱信息");
        listOperationMap.put("emailM", listEmail);
        listOperationMap.put("emailName", "邮箱信息");

        mapOperationList.add(listOperationMap);
        mapOperatingList.add(listOperatingMap);
        stringListMap.put("operatingName", "运营面");
        stringListMap.put("operationName", "运维面");
        stringListMap.put("operating", mapOperatingList);
        stringListMap.put("operation", mapOperationList);
        list.add(stringListMap);
        mapMap.put("data", list);
        return mapMap;
    }

    /**
     * checkModule
     * @return
     */
    public List<Integer> checkModule(){
        return this.logDao.checkModule();
    }
}
