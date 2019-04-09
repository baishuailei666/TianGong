package com.xlauncher.util;

import com.xlauncher.dao.ComponentDao;
import com.xlauncher.entity.Service;
import com.xlauncher.service.ComponentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组件之间同步工具类
 * @author mao ye
 * @since 2018-02-01
 */
@Component
public class ComponentUtil {
    private RestTemplate restTemplate;
    private static Logger logger = Logger.getLogger(ComponentUtil.class);
    @Autowired
    ComponentDao componentDao;

    public ComponentUtil() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        // 设置restTemplate超时时间
        requestFactory.setConnectTimeout(60 * 1000);
        requestFactory.setReadTimeout(60 * 1000);
        restTemplate = new RestTemplate(requestFactory);
    }
    /**
     * 添加服务的同时要将新添加的服务信息发送给其他需要该服务的信息，
     * 并且将已有的且新服务需要的服务信息发送给新服务
     *
     * @param component 组件信息
     */
    public void synchro(com.xlauncher.entity.Component component, List<com.xlauncher.entity.Component> componentList) {
        if (componentList.size() == 0) {
            switch (component.getComponentAbbr()) {
                case "dim": {
                    try {
                        String url = "http://" + component.getComponentIp() + ":" + component.getComponentPort() + "/" + component.getComponentAbbr() + "/service";
                        try {
                            restTemplate.postForEntity(url, component, String.class);
                        } catch (RestClientException e) {
                            logger.error("***dim 转发服务异常：" + e.getCause() + e.getMessage());
                        }
                        logger.info("dim-当前请求url路径：" + url);
                    } catch (RestClientException e) {
                        e.printStackTrace();
                        logger.error("请求url路径不正确：" + e.getMessage());
                    }
                    break;
                }
                default:
                    break;
            }
        }
        if (componentList.size() != 0) {
            for (com.xlauncher.entity.Component singleComponent : componentList)
            {
                switch (singleComponent.getComponentAbbr()) {
                    case "es": {
                        switch (component.getComponentAbbr()) {
                            case "cms": {
                                String url = "http://" + singleComponent.getComponentIp() + ":" + singleComponent.getComponentPort() + "/" + singleComponent.getComponentAbbr() + "/service";
                                try {
                                    restTemplate.postForEntity(url, component, String.class);
                                } catch (RestClientException e) {
                                    logger.warn("***es-cms 转发服务异常：" + e.getCause() + e.getMessage());
                                }
                                logger.info("es-cms当前请求url路径：" + url);
                                break;
                            }
                            default:
                                break;
                        }
                    }
                    break;
                    case "dim": {
                        switch (component.getComponentAbbr()) {
                            case "cms": {
                                String url = "http://" + singleComponent.getComponentIp() + ":" + singleComponent.getComponentPort() + "/" + singleComponent.getComponentAbbr() + "/service";
                                try {
                                    restTemplate.postForEntity(url, component, String.class);
                                } catch (RestClientException e) {
                                    logger.warn("***dim-cms 转发服务异常：" + e.getCause() + e.getMessage());
                                }
                                logger.info("dim-cms当前请求url路径：" + url);
                                break;
                            }
                            case "k8s": {
                                String url = "http://" + singleComponent.getComponentIp() + ":" + singleComponent.getComponentPort() + "/" + singleComponent.getComponentAbbr() + "/service";
                                try {
                                    restTemplate.postForEntity(url, component, String.class);
                                } catch (RestClientException e) {
                                    logger.warn("***dim-k8s 转发服务异常：" + e.getCause() + e.getMessage());
                                }
                                logger.info("dim-k8s当前请求url路径：" + url);
                                break;
                            }
                            case "ics": {
                                String port = component.getComponentPort().substring(6);
                                String url = "http://" + singleComponent.getComponentIp() + ":" + port + "/" + singleComponent.getComponentAbbr() + "/service";
                                try {
                                    restTemplate.postForEntity(url, component, String.class);
                                } catch (RestClientException e) {
                                    logger.warn("***dim-ics 转发服务异常：" + e.getCause() + e.getMessage());
                                }
                                logger.info("dim-ics当前请求url路径：" + url);
                                break;
                            }
                            default:
                                break;
                        }
                    }
                    break;
                    case "ics": {
                        switch (component.getComponentAbbr()) {
                            case "es": {
                                String port = singleComponent.getComponentPort().substring(0,5);
                                String url = "http://" + singleComponent.getComponentIp() + ":" + port + "/service";
                                try {
                                    restTemplate.postForEntity(url, component, String.class);
                                } catch (RestClientException e) {
                                    logger.warn("***ics-es 转发服务异常：" + e.getCause() + e.getMessage());
                                }
                                logger.info("ics-es当前请求url路径：" + url);
                                break;
                            }
                            default:
                                break;
                        }
                    }
                    break;
                    case "k8s": {
                        switch (component.getComponentAbbr()) {
                            case "dim": {
                                String url = "http://" + component.getComponentIp() + ":" + component.getComponentPort() + "/" + component.getComponentAbbr() + "/service";
                                try {
                                    restTemplate.postForEntity(url, component, String.class);
                                    restTemplate.postForEntity(url, singleComponent, String.class);
                                } catch (RestClientException e) {
                                    logger.warn("k8s-dim RestClientException：" + e.getMessage() + e.getCause());
                                }
                                logger.info("k8s-dim当前请求url路径：" + url);
                                break;
                            }
                            default:
                                break;
                        }
                    }
                    break;
                    default:
                        break;
                }
            }
        }
    }


    /**
     * CMS同步es、DIM
     */
    public void synCms() {
        com.xlauncher.entity.Component componentEs = componentDao.getComponentByAbbr("es");
        com.xlauncher.entity.Component componentCms = componentDao.getComponentByAbbr("cms");
        com.xlauncher.entity.Component componentDim = componentDao.getComponentByAbbr("dim");

        try {
            // es服务地址
            String url = "http://" + componentEs.getComponentIp() + ":" + componentEs.getComponentPort() + "/es/service";
            Service serviceEs = new Service();
            serviceEs.setSerName(componentCms.getComponentAbbr());
            serviceEs.setSerIp(componentCms.getComponentIp());
            serviceEs.setSerPort(componentCms.getComponentPort());
            restTemplate.put(url, serviceEs);
            logger.info("[CMS]同步es服务信息, 当前请求url路径：" + url + ", component:" + componentCms);

            // dim服务地址
            String url1 = "http://" + componentDim.getComponentIp() + ":" + componentDim.getComponentPort() + "/dim/service";
            Service serviceCms = new Service();
            serviceCms.setSerName(componentCms.getComponentAbbr());
            serviceCms.setSerIp(componentCms.getComponentIp());
            serviceCms.setSerPort(componentCms.getComponentPort());
            restTemplate.put(url1, serviceCms);
            logger.info("[CMS]同步dim服务信息, 当前请求url路径：" + url1 + ", component:" + componentCms);
        } catch (RestClientException e) {
            logger.error("[CMS]同步更新服务异常：" + e.getMessage() + e.getCause());
        }

    }

    /**
     * DIM同步CMS、RabbitMQ
     */
    public void synDim() {
        com.xlauncher.entity.Component componentDim = componentDao.getComponentByAbbr("dim");
        com.xlauncher.entity.Component componentCms = componentDao.getComponentByAbbr("cms");
        com.xlauncher.entity.Component componentMq = componentDao.getComponentByAbbr("mq");
        try {
            // dim服务地址
            String url = "http://" + componentDim.getComponentIp() + ":" + componentDim.getComponentPort() + "/dim/service";
            Service serviceCms = new Service();
            serviceCms.setSerName(componentCms.getComponentAbbr());
            serviceCms.setSerIp(componentCms.getComponentIp());
            serviceCms.setSerPort(componentCms.getComponentPort());
            restTemplate.put(url, serviceCms);
            logger.info("[DIM]同步CMS服务信息, 当前请求url路径：" + url + ", component:" + componentCms);

            // dim mq服务地址
            String mqUrl = "http://" + componentDim.getComponentIp() + ":" + componentDim.getComponentPort() + "/dim/mqService";
            Map<String, Object> service = new HashMap<>(1);
            service.put("mqIp",componentMq.getComponentIp());
            service.put("mqPort",componentMq.getComponentPort());
            service.put("mqUserName",componentMq.getComponentConfiguration().get("userName"));
            service.put("mqPassword",componentMq.getComponentConfiguration().get("password"));
            service.put("queueImg",componentMq.getComponentConfiguration().get("queue_img"));
            service.put("queueChannel",componentMq.getComponentConfiguration().get("queue_channel"));
            restTemplate.put(mqUrl, service);
            logger.info("[DIM]同步RabbitMQ服务信息, 当前请求url路径：" + mqUrl + ", component:" + componentMq);
        } catch (RestClientException e) {
            logger.error("[DIM]同步更新服务异常：" + e.getMessage() + e.getCause());
        }

    }

    /**
     * ES同步CMS、ics
     */
    public void synEs() {
        com.xlauncher.entity.Component componentEs = componentDao.getComponentByAbbr("es");
        com.xlauncher.entity.Component componentCms = componentDao.getComponentByAbbr("cms");
        com.xlauncher.entity.Component componentIcs = componentDao.getComponentByAbbr("ics");

        try {
            // es服务地址
            String url = "http://" + componentEs.getComponentIp() + ":" + componentEs.getComponentPort() + "/es/service";
            Service serviceCms = new Service();
            serviceCms.setSerName(componentCms.getComponentAbbr());
            serviceCms.setSerIp(componentCms.getComponentIp());
            serviceCms.setSerPort(componentCms.getComponentPort());
            restTemplate.put(url, serviceCms);
            logger.info("[ES]同步CMS服务信息, 当前请求url路径：" + url + ", component:" + componentCms);

            // ics服务地址
            String url1 = "http://" + componentIcs.getComponentIp() + ":" + componentIcs.getComponentPort() + "/ics/service";
            Map<String, Map<String, Object>> serviceMap = new HashMap<>(1);
            Map<String, Object> service = new HashMap<>(1);
            // ES
            service.put("esIp",componentEs.getComponentIp());
            service.put("esPort",componentEs.getComponentPort());
            service.put("esName",componentEs.getComponentAbbr());
            service.put("detectThresh",componentEs.getComponentConfiguration().get("detect_thresh"));
            service.put("classThresh",componentEs.getComponentConfiguration().get("class_thresh"));
            serviceMap.put("es", service);
            restTemplate.put(url1, serviceMap);
            logger.info("[ES]同步ics服务信息, 当前请求url路径：" + url1 + ", component:" + componentIcs);
        } catch (RestClientException e) {
            logger.error("[ES]同步更新服务异常：" + e.getMessage() + e.getCause());
        }

    }

    /**
     * FTP同步ics
     */
    public void synFtp() {
        com.xlauncher.entity.Component componentIcs = componentDao.getComponentByAbbr("ics");
        com.xlauncher.entity.Component componentFtp = componentDao.getComponentByAbbr("ftp");
        try {
            // ics
            String url = "http://" + componentIcs.getComponentIp() + ":" + componentIcs.getComponentPort() + "/ics/service";
            Map<String, Map<String, Object>> serviceMap = new HashMap<>(1);
            Map<String, Object> service = new HashMap<>(1);
            // FTP
            service.put("ftpIp",componentFtp.getComponentIp());
            service.put("ftpPort",componentFtp.getComponentPort());
            service.put("ftpUserName",componentFtp.getComponentConfiguration().get("userName"));
            service.put("ftpPassword",componentFtp.getComponentConfiguration().get("password"));
            service.put("ftpStorePath",componentFtp.getComponentConfiguration().get("store_path"));
            serviceMap.put("ftp", service);
            restTemplate.put(url, serviceMap);
            logger.info("[FTP]同步ics服务信息, 当前请求url路径：" + url + ", component:" + componentIcs);
        } catch (RestClientException e) {
            logger.error("[FTP]同步更新服务异常：" + e.getMessage() + e.getCause());
        }

    }

    /**
     * RabbitMQ同步ics、dim
     */
    public void synMq() {
        com.xlauncher.entity.Component componentIcs = componentDao.getComponentByAbbr("ics");
        com.xlauncher.entity.Component componentDim = componentDao.getComponentByAbbr("dim");
        com.xlauncher.entity.Component componentMq = componentDao.getComponentByAbbr("mq");
        try {
            // ics服务地址
            String url = "http://" + componentIcs.getComponentIp() + ":" + componentIcs.getComponentPort() + "/ics/service";
            Map<String, Map<String, Object>> serviceMap = new HashMap<>(1);
            Map<String, Object> service = new HashMap<>(1);
            // mq
            service.put("mqIp",componentMq.getComponentIp());
            service.put("mqPort",componentMq.getComponentPort());
            service.put("mqUserName",componentMq.getComponentConfiguration().get("userName"));
            service.put("mqPassword",componentMq.getComponentConfiguration().get("password"));
            service.put("queueImg",componentMq.getComponentConfiguration().get("queue_img"));
            service.put("queueChannel",componentMq.getComponentConfiguration().get("queue_channel"));
            serviceMap.put("mq", service);
            restTemplate.put(url, serviceMap);
            logger.info("[RabbitMQ]同步ics服务信息, 当前请求url路径：" + url + ", component:" + componentIcs);

            // dim服务地址
            String url1 = "http://" + componentDim.getComponentIp() + ":" + componentDim.getComponentPort() + "/dim/mqService";
            restTemplate.put(url1, service);
            logger.info("[RabbitMQ]同步dim服务信息, 当前请求url路径：" + url1 + ", component:" + componentDim);
        } catch (RestClientException e) {
            logger.error("[RabbitMQ]同步更新服务异常：" + e.getMessage() + e.getCause());
        }

    }

    /**
     * ICS同步ES、FTP、RabbitMQ
     */
    public void synIcs() {
        com.xlauncher.entity.Component componentIcs = componentDao.getComponentByAbbr("ics");
        com.xlauncher.entity.Component componentFtp = componentDao.getComponentByAbbr("ftp");
        com.xlauncher.entity.Component componentMq = componentDao.getComponentByAbbr("mq");
        com.xlauncher.entity.Component componentEs = componentDao.getComponentByAbbr("es");

        try {
            // ics服务地址
            String url = "http://" + componentIcs.getComponentIp() + ":" + componentIcs.getComponentPort() + "/ics/service";
            Map<String, Map<String, Object>> serviceMap = new HashMap<>(1);
            Map<String, Object> serviceEs = new HashMap<>(1);
            // ES
            serviceEs.put("esIp",componentEs.getComponentIp());
            serviceEs.put("esPort",componentEs.getComponentPort());
            serviceEs.put("esName",componentEs.getComponentAbbr());
            serviceEs.put("detectThresh",componentEs.getComponentConfiguration().get("detect_thresh"));
            serviceEs.put("classThresh",componentEs.getComponentConfiguration().get("class_thresh"));

            Map<String, Object> serviceMq = new HashMap<>(1);
            // MQ
            serviceMq.put("mqIp",componentMq.getComponentIp());
            serviceMq.put("mqPort",componentMq.getComponentPort());
            serviceMq.put("mqUserName",componentMq.getComponentConfiguration().get("userName"));
            serviceMq.put("mqPassword",componentMq.getComponentConfiguration().get("password"));
            serviceMq.put("queueImg",componentMq.getComponentConfiguration().get("queue_img"));
            serviceMq.put("queueChannel",componentMq.getComponentConfiguration().get("queue_channel"));

            Map<String, Object> serviceFtp = new HashMap<>(1);
            // FTP
            serviceFtp.put("ftpIp",componentFtp.getComponentIp());
            serviceFtp.put("ftpPort",componentFtp.getComponentPort());
            serviceFtp.put("ftpUserName",componentFtp.getComponentConfiguration().get("userName"));
            serviceFtp.put("ftpPassword",componentFtp.getComponentConfiguration().get("password"));
            serviceFtp.put("ftpStorePath",componentFtp.getComponentConfiguration().get("store_path"));

            serviceMap.put("es", serviceEs);
            serviceMap.put("mq", serviceMq);
            serviceMap.put("ftp", serviceFtp);
            restTemplate.put(url, serviceMap);
            logger.info("[ICS]同步RabbitMQ、ES、FTP服务信息, 当前请求url路径：" + url + ", component:" + serviceMap);
        } catch (RestClientException e) {
            logger.error("[ICS]同步更新服务异常：" + e.getMessage() + e.getCause());
        }

    }




    /**
     * 更新服务的信息并且同步给其他信息
     *
     * @param component 组件信息
     */
    public void update(com.xlauncher.entity.Component component){
        List<com.xlauncher.entity.Component> list = componentDao.listComponent();
        String port;
        String url;
        for (com.xlauncher.entity.Component singleCom : list) {
            // 给ICS同步ES服务信息和FTP信息和RabbitMQ信息
            if ("ics".equals(singleCom.getComponentAbbr())) {
                logger.info("给ICS同步ES服务信息和FTP信息和RabbitMQ信息, ics:" + singleCom);
                logger.info("[ICS]-当前修改的组件信息component:" + component);
                port = singleCom.getComponentPort();
                url = "http://" + singleCom.getComponentIp() + ":" + port + "/service";
                if ("es".equals(component.getComponentAbbr())) {
                    try {
                        restTemplate.put(url, component);
                        logger.info("给ICS同步ES服务信息,当前请求url路径：" + url + ", component:" + component);
                    } catch (RestClientException e) {
                        logger.error("同步更新服务异常：" + e.getMessage() + e.getCause());
                    }
                }
                if ("ftp".equals(component.getComponentAbbr())) {
                    try {
                        restTemplate.put(url, component);
                        logger.info("给ICS同步ftp服务信息,当前请求url路径：" + url + "component:" + component);
                    } catch (RestClientException e) {
                        logger.error("同步更新服务异常：" + e.getMessage() + e.getCause());
                    }
                }
                if ("mq".equals(component.getComponentAbbr())) {
                    try {
                        restTemplate.put(url, component);
                        logger.info("给ICS同步RabbitMQ服务信息,当前请求url路径：" + url + "component:" + component);
                    } catch (RestClientException e) {
                        logger.error("同步更新服务异常：" + e.getMessage() + e.getCause());
                    }
                }

            }
            // 给DIM同步CMS服务信息和ICS信息
            if ("dim".equals(singleCom.getComponentAbbr()) || "dim".equals(component.getComponentAbbr())) {
                logger.info("给DIM同步CMS服务信息和ICS信息, dim:" + singleCom);
                logger.info("给DIM同步CMS服务信息和ICS信息, 当前修改的组件信息component:" + component);
                if ("cms".equals(component.getComponentAbbr())) {
                    try {
                        url = "http://" + singleCom.getComponentIp() + ":" + singleCom.getComponentPort() + "/service";
                        Service service = new Service();
                        service.setSerId(component.getId());
                        service.setSerIp(component.getComponentIp());
                        service.setSerName(component.getComponentAbbr());
                        service.setSerPort(component.getComponentPort());
                        restTemplate.put(url, service);
                        logger.info("给DIM同步CMS服务信息, 当前请求url路径：" + url + "service:" +service);
                    } catch (RestClientException e) {
                        logger.error("同步更新服务异常：" + e.getMessage() + e.getCause());
                    }
                }
                if ("ics".equals(component.getComponentAbbr())) {
                    try {
                        url = "http://" + singleCom.getComponentIp() + ":" + singleCom.getComponentPort() + "/service";
                        String[] ports = component.getComponentPort().split(",");
                        Service service = new Service();
                        service.setSerId(component.getId());
                        service.setSerIp(component.getComponentIp());
                        service.setSerName(component.getComponentAbbr());
                        service.setSerPort(ports[1]);
                        restTemplate.put(url, service);
                        logger.info("给DIM同步ICS信息, 当前请求url路径：" + url + "service:" +service);
                    } catch (RestClientException e) {
                        logger.error("同步更新服务异常：" + e.getMessage() + e.getCause());
                    }
                }
                if ("mq".equals(component.getComponentAbbr())) {
                    try {
                        url = "http://" + singleCom.getComponentIp() + ":" + singleCom.getComponentPort() + "/mqService";
                        Map<String, Object> map = new HashMap<>(1);
                        map.put("mqIp", component.getComponentIp());
                        map.put("mqPort", component.getComponentPort());
                        map.put("mqUserName", component.getComponentConfiguration().get("userName"));
                        map.put("mqPassword", component.getComponentConfiguration().get("password"));
                        map.put("mqQueue_img", component.getComponentConfiguration().get("queue_img"));
                        map.put("mqQueue_channel", component.getComponentConfiguration().get("queue_channel"));

                        restTemplate.postForObject(url, map, Map.class);
                        logger.info("给DIM同步RabbitMQ信息, 当前请求url路径：" + url + ",map:" + map);
                    } catch (RestClientException e) {
                        logger.error("同步更新服务异常：" + e.getMessage() + e.getCause());
                    }
                }

            }
            // 给ES同步CMS服务信息
            if ("es".equals(singleCom.getComponentAbbr()) || "es".equals(component.getComponentAbbr())) {
                logger.info("给ES同步CMS服务信息, es:" + singleCom);
                logger.info("给ES同步CMS服务信息, 当前修改的组件信息component:" + component);
                if ("cms".equals(component.getComponentAbbr())) {
                    try {
                        url = "http://" + singleCom.getComponentIp() + ":" + singleCom.getComponentPort() + "/service";
                        Service service = new Service();
                        service.setSerId(component.getId());
                        service.setSerIp(component.getComponentIp());
                        service.setSerName(component.getComponentAbbr());
                        service.setSerPort(component.getComponentPort());
                        restTemplate.put(url, service);
                        logger.info("给ES同步CMS服务信息, 当前请求url路径：" + url + ", service:" +service);
                    } catch (RestClientException e) {
                        logger.error("同步更新服务异常：" + e.getMessage() + e.getCause());
                    }
                }
            }
        }
    }


    /**
     * 同步删除服务信息
     *
     * @param id 编号
     */
    public void delete(int id){
        List<com.xlauncher.entity.Component> list = componentDao.listComponent();
        for (com.xlauncher.entity.Component component : list) {
            if (!"cms".equals(component.getComponentAbbr())) {
                try {
                    String url = component.getComponentIp() + ":" + component.getComponentPort() + "/service";
                    restTemplate.delete(url + "?id={id}", id);
                    logger.info("cms同步删除服务信息,当前请求url路径：" + url);
                } catch (RestClientException e) {
                    e.printStackTrace();
                    logger.error("请求url路径不正确：" + e.getMessage());
                }
            }
        }
    }
}
