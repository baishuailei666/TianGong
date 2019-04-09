package com.xlauncher.web;


import com.xlauncher.entity.Service;
import com.xlauncher.service.Impl.ServiceProperties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 功能: 服务同步接口控制器
 * 作者：白帅雷
 * 时间：2018-01-15
 */

@Controller
@RequestMapping("/service")
public class ServicePropertiesController {


    /**
     * 不需要注入工具类对象，直接调用静态方法进行获取，而且只加载一次，效率很高
     */
    @Autowired
    ServiceProperties serviceProperties;

    /**
     *  记录器
     */
    private static Logger logger = Logger.getLogger(ServicePropertiesController.class);

    /**
     * 读写service
     * @param service
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public Map<String, Object> setProperties(@RequestBody Service service) {
        logger.info("读写service.properties");
        logger.info(this.serviceProperties.fileName);
        Map<String, Object> map = new HashMap<>(1);
        int retAdd = 0;
        try {
            retAdd = this.serviceProperties.setProperties(service);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Err." + e);
        }
        map.put("status", retAdd);
        return map;
    }

    /**
     * 配置告警事件允许推送告警事件的时间
     * @param eventConfMap
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/eventTime", method = RequestMethod.POST)
    public Map<String, Object> eventConf(@RequestBody Map<String, String> eventConfMap) {
        Map<String, Object> map = new HashMap<>(1);
        int retAdd = 0;
        try {
            retAdd = this.serviceProperties.setEventConf(eventConfMap);
        } catch (Exception e) {
            logger.error("setEventConf is error" + e.getMessage() + e.getCause());
        }
        map.put("status", retAdd);
        return map;
    }
}
