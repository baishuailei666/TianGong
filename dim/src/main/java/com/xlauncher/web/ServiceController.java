package com.xlauncher.web;

import com.rabbitmq.client.*;
import com.xlauncher.entity.Service;
import com.xlauncher.service.ConfigService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 服务配置控制器
 * @author YangDengcheng
 * @date 2018/1/22 10:05
 */
@Controller

public class ServiceController {

    private static final Logger LOGGER = Logger.getLogger(ServiceController.class);

    @Autowired
    ConfigService configService;

    /**
     * 获取服务的信息
     *
     * @param service
     */
    @RequestMapping(value = "/service" , method = RequestMethod.POST)
    @ResponseBody
    public void getService(@RequestBody Service service){
        try {
            LOGGER.info("[URL:/service,method:POST] Invoke DIM's addConfig interface,execute to add configuration information to the configuration file successfully");
            configService.addConfigService(service);
        }catch (Exception e){
            LOGGER.error("[URL:/service,method:POST] Invoke DIM's addConfig interface,execute to add configuration information to the configuration file unsuccessfully");
            e.printStackTrace();
        }

    }


    /**
     * 获取RabbitMQ服务的信息
     *
     * @param service
     */
    @ResponseBody
    @RequestMapping(value = "/mqService" , method = RequestMethod.PUT)
    public void getMqService(@RequestBody Map<String, Object> service){
        try {
            LOGGER.info("[URL:/mqService,method:PUT] Invoke DIM's addConfig interface,execute to add configuration information to the configuration file successfully");
            configService.addRabbitMqService(service);
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("[URL:/mqService,method:PUT] Invoke DIM's addConfig interface,execute to add configuration information to the configuration file unsuccessfully");
        }

    }

    /**
     * 修改服务的信息
     *
     * @param service
     */
    @RequestMapping(value = "/service" , method = RequestMethod.PUT)
    @ResponseBody
    public void updateService(@RequestBody Service service){
        try {
            LOGGER.info("[URL:/service,method:PUT] Invoke DIM's updateConfig interface,execute to update configuration information to the configuration file successfully");
            LOGGER.info("修改服务的信息serviceConfig:" + service);
            configService.updateConfigService(service);
        }catch (Exception e){
            LOGGER.error("[URL:/service,method:PUT] Invoke DIM's updateConfig interface,execute to update configuration information to the configuration file unsuccessfully");
            e.printStackTrace();
        }

    }


    /**
     * 删除服务配置
     *
     * @param service
     */
    @RequestMapping(value = "/service" , method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteService(@RequestBody Service service){
        try {
            LOGGER.info("[URL:/service,method:DELETE] Invoke DIM's deleteConfig interface,execute to delete configuration information to the configuration file successfully");
            configService.updateConfigService(service);
        }catch (Exception e){
            LOGGER.error("[URL:/service,method:DELETE] Invoke DIM's deleteConfig interface,execute to delete configuration information to the configuration file unsuccessfully");
            e.printStackTrace();
        }

    }

}
