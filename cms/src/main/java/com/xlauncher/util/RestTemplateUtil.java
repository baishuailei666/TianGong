package com.xlauncher.util;

import com.xlauncher.dao.ComponentDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * 转发工具类
 * @date 2018-06-06
 * @author baishuailei
 */
@Component
public class RestTemplateUtil {
    @Autowired
    ComponentDao componentDao;
    private int pushStatus = 0;
    private RestTemplate restTemplate;
    private static Logger logger = Logger.getLogger(RestTemplateUtil.class);

    public RestTemplateUtil() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        // 设置restTemplate超时时间
        requestFactory.setConnectTimeout(60 * 1000);
        requestFactory.setReadTimeout(60 * 1000);
        restTemplate = new RestTemplate(requestFactory);
    }
    /**
     * 得到ES组件的信息
     *
     * @return 组件信息
     */
    private com.xlauncher.entity.Component getESComponent() {
        return this.componentDao.getComponentByAbbr("es");
    }

    /**
     * 转发修改事件类型给ES
     *
     * @param putMap 修改的内容
     * @return ResponseEntity<Map>
     */
    public int putEventTypeToES(Map<String, Object> putMap) {
        ResponseEntity<Map> mapResponseEntity;
        try {
            //header设置
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
            headers.add("Content-Type", "application/json;charset=UTF-8");
            HttpEntity<Object> httpEntity = new HttpEntity<Object>(putMap, headers);
            String postUrl = "http://" + getESComponent().getComponentIp() + ":" + getESComponent().getComponentPort() + "/es/es_event_type";
            mapResponseEntity = restTemplate.exchange(postUrl, HttpMethod.PUT, httpEntity, Map.class);
            if (mapResponseEntity != null) {
                int value = 200;
                if (mapResponseEntity.getStatusCodeValue() == value) {
                    pushStatus = 200;
                    logger.info("成功!ES成功返回的结果:" + mapResponseEntity.getBody());
                } else {
                    pushStatus = 400;
                    logger.error("推送失败: 因为ES原因(" + mapResponseEntity.getBody() +")导致, putMap:" + putMap);
                }
            }
        } catch (RestClientException e) {
            logger.warn("*****ES服务器异常*****putEventTypeToES: " + e.getMessage());
            throw e;
        }
        logger.info("提示-修改告警事件参数配置：" + putMap);
        return pushStatus;
    }

    /**
     * 转发添加事件类型给ES
     *
     * @param addMap 转发的内容
     * @return ResponseEntity<Map>
     */
    public int addEventTypeToES(Map<String, Object> addMap) {
        ResponseEntity<Map> mapResponseEntity;
        try {
            //header设置
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
            headers.add("Content-Type", "application/json;charset=UTF-8");
            HttpEntity<Object> httpEntity = new HttpEntity<Object>(addMap, headers);
            String postUrl = "http://" + getESComponent().getComponentIp() + ":" + getESComponent().getComponentPort() + "/es/es_event_type";
            mapResponseEntity = restTemplate.exchange(postUrl, HttpMethod.POST, httpEntity, Map.class);
            if (mapResponseEntity != null) {
                int value = 1;
                int status = (int) mapResponseEntity.getBody().get("status");
                if (status == value) {
                    pushStatus = status;
                    logger.info("添加成功!ES成功返回的结果:" + mapResponseEntity.getBody());
                } else {
                    pushStatus = status;
                    logger.warn("推送失败: 因为ES原因(" + mapResponseEntity.getBody() +")导致, addMap:" +addMap);
                }
            }
        } catch (RestClientException e) {
            logger.warn("*****ES服务器异常*****addEventTypeToES: " + e.getMessage());
            throw e;
        }
        logger.info("提示-添加告警事件参数配置：" + addMap);
        return pushStatus;
    }

    /**
     * 转发删除事件类型给ES
     *
     * @param deleteMap 转发的内容
     * @return ResponseEntity<Map>
     */
    public int deleteEventTypeToES(Map<String, Object> deleteMap) {
        ResponseEntity<Map> mapResponseEntity;
        try {
            //header设置
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
            headers.add("Content-Type", "application/json;charset=UTF-8");
            HttpEntity<Object> httpEntity = new HttpEntity<Object>(deleteMap, headers);
            String postUrl = "http://" + getESComponent().getComponentIp() + ":" + getESComponent().getComponentPort() + "/es/es_event_type/" + deleteMap.get("id");
            mapResponseEntity = restTemplate.exchange(postUrl, HttpMethod.DELETE, httpEntity, Map.class);
            if (mapResponseEntity != null) {
                int value = 200;
                if (mapResponseEntity.getStatusCodeValue() == value) {
                    pushStatus = 200;
                    logger.info("成功!ES成功返回的结果:" + mapResponseEntity.getBody());
                } else {
                    pushStatus = 400;
                    logger.warn("推送失败: 因为ES原因(" + mapResponseEntity.getBody() +")导致, deleteMap:" +deleteMap);
                }
            }
        } catch (RestClientException e) {
            logger.warn("*****ES服务器异常*****deleteEventTypeToES: " + e.getMessage());
            throw e;
        }
        logger.info("提示-删除告警事件参数配置：" + deleteMap);
        return pushStatus;
    }

    /**
     * 转发查询事件类型给ES
     *
     * @param typeDescription 告警事件类型
     * @return ResponseEntity<Map>
     */
    public Map getEventTypeToES(String typeDescription) {
        ResponseEntity<Map> mapResponseEntity;
        try {
            //header设置
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
            headers.add("Content-Type", "application/json;charset=UTF-8");
            HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
            String getUrl = "http://" + getESComponent().getComponentIp() + ":" + getESComponent().getComponentPort() + "/es/es_event_type?typeDescription=" + typeDescription;
            mapResponseEntity = restTemplate.exchange(getUrl, HttpMethod.GET, httpEntity, Map.class);
        } catch (RestClientException e) {
            logger.warn("*****ES服务器异常***** getEventTypeToES: " + e.getMessage());
            throw e;
        }
        return mapResponseEntity.getBody();
    }

    /**
     * 转发查询事件类型给ES用于CMS事件监控中心饼状图分析展示
     *
     * @param typeDescription 告警事件类型
     * @return ResponseEntity<Map>
     */
    public List<Map<String, String>> getESTypes(String typeDescription) {
        ResponseEntity<List> mapResponseEntity;
        try {
            String getUrl = "http://" + getESComponent().getComponentIp() + ":" + getESComponent().getComponentPort() + "/es/es_event_type/getTypes?typeDescription=" + typeDescription;
            mapResponseEntity = restTemplate.getForEntity(getUrl, List.class);
        } catch (RestClientException e) {
            logger.warn("*****ES服务器异常*****getESTypes: " + e.getMessage());
            throw e;
        }
        return mapResponseEntity.getBody();
    }

    /**
     * 转发查询事件类型给ES
     *
     * @return ResponseEntity<Map>
     */
    public Map getEventTypeByIdToES(String id) {
        ResponseEntity<Map> mapResponseEntity;
        try {
            //header设置
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
            headers.add("Content-Type", "application/json;charset=UTF-8");
            HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
            String getUrl = "http://" + getESComponent().getComponentIp() + ":" + getESComponent().getComponentPort() + "/es/es_event_type/" + id;
            mapResponseEntity = restTemplate.exchange(getUrl, HttpMethod.GET, httpEntity, Map.class);
        } catch (RestClientException e) {
            logger.warn("*****ES服务器异常*****getEventTypeByIdToES: " + e.getMessage());
            throw e;
        }
        return mapResponseEntity.getBody();
    }

    /**
     * 转发添加事件配置的时间给ES
     *
     * @param eventTime 转发的内容
     * @return ResponseEntity<Map>
     */
    public int addEventTimeToES(Map<String, Object> eventTime) {
        ResponseEntity<Map> mapResponseEntity;
        try {
            //header设置
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
            headers.add("Content-Type", "application/json;charset=UTF-8");
            HttpEntity<Object> httpEntity = new HttpEntity<Object>(eventTime, headers);
            String postUrl = "http://" + getESComponent().getComponentIp() + ":" + getESComponent().getComponentPort() + "/es/service/eventTime";
            mapResponseEntity = restTemplate.exchange(postUrl, HttpMethod.POST, httpEntity, Map.class);
            if (mapResponseEntity != null) {
                int value = 200;
                if (mapResponseEntity.getStatusCodeValue() == value) {
                    pushStatus = 200;
                } else {
                    logger.info("推送失败: 因为ES未知原因导致, addEventTimeToES：" + eventTime);
                }
            }
        } catch (RestClientException e) {
            logger.warn("*****ES服务器异常*****addEventTimeToES: " + e.getMessage());
            throw e;
        }
        logger.info("提示-添加告警事件时间参数配置：" + eventTime);
        return pushStatus;
    }

    /**
     * 给ICS发送服务信息
     * @param component
     */
    public void restICS(com.xlauncher.entity.Component component){
        try {
            logger.info("给ICS发送服务信息-component:" + component);
            com.xlauncher.entity.Component componentIcs = this.componentDao.getComponentByAbbr("ics");
            String port = componentIcs.getComponentPort().substring(0,5);
            String url = "http://" + componentIcs.getComponentIp() + ":" + port + "/service";
            restTemplate.postForEntity(url, component, String.class);
            logger.info("给ICS发送服务信息,当前请求url路径：" + url);
        } catch (RestClientException e) {
            e.printStackTrace();
            logger.error("请求url路径不正确：" + e.getMessage());
        }
    }
}
