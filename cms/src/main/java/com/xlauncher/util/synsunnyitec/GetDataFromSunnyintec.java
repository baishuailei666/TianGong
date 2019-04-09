package com.xlauncher.util.synsunnyitec;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 调用第三方接口，同步第三方数据（device、channel、org）
 * @author baisl
 * @Email :baishuailei@xlauncher.io
 * @Date :2018/9/11 0011
 * @Desc :
 **/
@Component
public class GetDataFromSunnyintec {
    private RestTemplate restTemplate;
    private static Logger logger = Logger.getLogger(GetDataFromSunnyintec.class);

    /**
     * 初始化 设置请求连接\超时时间
     */
    public GetDataFromSunnyintec() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        // 设置restTemplate超时时间
        requestFactory.setConnectTimeout(60 * 1000);
        requestFactory.setReadTimeout(60 * 1000);
        restTemplate = new RestTemplate(requestFactory);
    }

    /**
     * 获取正阳设备count数
     * @param ticket
     * @return
     */
    public Map getSunnyintecDeviceCount(String methodName, String ticket) {
        ResponseEntity<Map> mapResponseEntity;
        try {
            String url = "http://api.sunnyintec.com:8082/device/device/" + methodName;
            logger.info("调用第三方设备接口DeviceCount url:" + url);
            mapResponseEntity = restTemplate.exchange(url, HttpMethod.POST, init(ticket), Map.class);

        } catch (RestClientException e) {
            logger.error("getSunnyintecDeviceCount ERROR!" + e.getMessage());
            throw e;
        }
        return mapResponseEntity.getBody();
    }

    /**
     * 分页批量获取正阳设备
     * @param pageNum 当前页
     * @param pageSize 每页数量
     * @param ticket
     * @return
     */
    public Map getSunnyintecDevice(int pageNum, int pageSize, String ticket) {
        ResponseEntity<Map> mapResponseEntity;
        try {
            String url = "http://api.sunnyintec.com:8082/device/device/getdevicelist/" + pageNum + "/" + pageSize;
            logger.info("调用第三方分页批量获取设备接口Device url:" + url);
            mapResponseEntity = restTemplate.exchange(url, HttpMethod.POST, init(ticket), Map.class);

        } catch (RestClientException e) {
            logger.error("getSunnyintecDevice ERROR!" + e.getMessage());
            throw e;
        }
        return mapResponseEntity.getBody();
    }

    /**
     * 获取正阳通道count数
     * @param ticket
     * @return
     */
    public Map getSunnyintecChannelCount(String methodName, String ticket) {
        ResponseEntity<Map> mapResponseEntity;
        try {
            String url = "http://api.sunnyintec.com:8082/device/channel/" + methodName;
            logger.info("调用第三方通道接口ChannelCount url:" + url);
            mapResponseEntity = restTemplate.exchange(url, HttpMethod.POST, init(ticket), Map.class);

        } catch (RestClientException e) {
            logger.error("getSunnyintecChannelCount ERROR!" + e.getMessage());
            throw e;
        }
        return mapResponseEntity.getBody();
    }

    /**
     * 分页批量获取正阳通道
     * @param pageNum 当前页
     * @param pageSize 每页数量
     * @param ticket
     * @return
     */
    public Map getSunnyintecChannel(int pageNum, int pageSize, String ticket) {
        ResponseEntity<Map> mapResponseEntity;
        try {
            String url = "http://api.sunnyintec.com:8082/device/channel/getchannellist/" + pageNum + "/" + pageSize;
            logger.info("调用第三方分页批量获取通道接口Channel url:" + url);
            mapResponseEntity = restTemplate.exchange(url, HttpMethod.POST, init(ticket), Map.class);

        } catch (RestClientException e) {
            logger.error("getSunnyintecChannel ERROR!" + e.getMessage());
            throw e;
        }
        return mapResponseEntity.getBody();
    }

    /**
     * 获取正阳组织
     * @param ticket
     * @return
     */
    public Map getSunnyintecOrg(String ticket) {
        ResponseEntity<List> mapResponseEntity;
        Map map = new HashMap(1);
        try {
            String url = "http://api.sunnyintec.com:8082/sys/org/orgTree";
            logger.info("调用第三方分页批量获取组织接口Org url:" + url);
            mapResponseEntity = restTemplate.exchange(url, HttpMethod.POST, init(ticket), List.class);
            map.put("orgLists",mapResponseEntity.getBody());
        } catch (RestClientException e) {
            logger.error("getSunnyintecOrg ERROR!" + e.getMessage());
            throw e;
        }
        return map;
    }

    /**
     * header设置
     * @param ticket
     * @return
     */
    public HttpEntity<Object> init(String ticket) {
        //header设置
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        headers.add("U3AccessTicket",ticket);
        headers.add("U3domain","prr");
        return new HttpEntity<Object>(headers);
    }
}
