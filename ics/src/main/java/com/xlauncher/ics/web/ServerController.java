package com.xlauncher.ics.web;

import com.xlauncher.ics.service.ServiceInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author :baisl
 * @Email :baishuailei@xlauncher.io
 * @Date :2018/12/19 0019
 * @Desc :
 **/
@RestController
public class ServerController {

    private final ServiceInfoService serviceInfoService;

    private static Logger logger = Logger.getLogger(ServerController.class);

    @Autowired
    public ServerController(ServiceInfoService serviceInfoService) {
        this.serviceInfoService = serviceInfoService;
    }

    /**
     * 提供心跳监测接口
     */
    @GetMapping(value = "/heartbeat")
    public void getHeartBeat() {

    }

    /**
     * 写入ES、FTP、RabbitMQ服务配置信息
     *
     * @param serviceMap serviceMap
     */
    @ResponseBody
    @PutMapping(value = "/service")
    public void writeService(@RequestBody Map<String, Map<String, Object>> serviceMap) {
        logger.info("[ics]用于同步 ES、FTP、RabbitMQ服务" + serviceMap);
        System.out.println(serviceMap);
        serviceInfoService.writeService(serviceMap);
    }
}
