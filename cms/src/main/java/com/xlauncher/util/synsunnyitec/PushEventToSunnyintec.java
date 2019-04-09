package com.xlauncher.util.synsunnyitec;

import com.xlauncher.entity.EventAlert;
import com.xlauncher.service.EventAlertService;
import com.xlauncher.util.HttpMessageConverter;
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

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 调用第三方接口：用户接口获取ticket，上报告警接口
 * @author 白帅雷
 * @date 2018-09-08
 */
@Component
public class PushEventToSunnyintec {
    @Autowired
    private SynSunnyintecProperties synSunnyintecProperties;
    @Autowired
    private EventAlertService eventAlertService;
    private RestTemplate restTemplate;
    private static Logger logger = Logger.getLogger(PushEventToSunnyintec.class);

    /**
     * 初始化
     */
    public PushEventToSunnyintec() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        // 设置restTemplate超时时间
        requestFactory.setConnectTimeout(60 * 1000 * 2);
        requestFactory.setReadTimeout(60 * 1000 * 2);
        restTemplate = new RestTemplate(requestFactory);
    }

    /**
     * 调用第三方用户接口
     * @return mapResponseEntity
     */
    public Map sunnyintecLogin() {
        Map<String, Object> map = new HashMap(1);
        ResponseEntity<Map> mapResponseEntity;
        //header设置
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        headers.add("U3domain","prr");
        Map<String, Object> postMap = new HashMap<>(1);
        postMap.put("account", synSunnyintecProperties.synAccount());
        postMap.put("password", synSunnyintecProperties.synPassword());
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(postMap, headers);
        String url = "http://api.sunnyintec.com:8082/sys/login/login";
        logger.info("调用第三方用户接口sunnyintecLogin url:" + url + " , postMap" + postMap);
        try {
            mapResponseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Map.class);
            if (!mapResponseEntity.getBody().get("code").equals(500)) {
                logger.info("成功! 返回的结果:" + mapResponseEntity.getBody());
                Map list = (Map) mapResponseEntity.getBody().get("loginDto");
                map.put("code", 200);
                map.put("ticket", list.get("ticket"));
                return map;
            } else {
                map.put("code", mapResponseEntity.getBody().get("code"));
                map.put("msg", "失败!" + mapResponseEntity.getBody());
                return map;
            }
        } catch (RestClientException e) {
            logger.error("sunnyintecLogin ERROR!" + e.getMessage());
        }
        map.put("code", 400);
        map.put("msg", "用户接口未知错误!");
        return map;
    }

    /**
     * 给第三方上报告警事件
     * // 非必填字段
     * postMap.put("receiveOrg", "1008011");
     * postMap.put("receiveOrgName", "杨庄子村");
     * postMap.put("suguestDate", "");
     * postMap.put("receiveEmployeeName", "");
     * @param u3AccessTicket 需要调用用户接口获取U3AccessTicket
     * @param eventAlert 报警事件
     * @return responseEntity
     */
    public Map sunnyintecSubmitEvent(EventAlert eventAlert, String u3AccessTicket) {
        Map<String, Object> resultMap = new HashMap(1);
        ResponseEntity<Map> responseEntity;
        //header设置
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        // 需要调用用户接口获取U3AccessTicket
        headers.add("U3AccessTicket",u3AccessTicket);
        headers.add("U3domain","prr");
        Map<String, Object> postMap = new HashMap<>(1);
        // 创建事件为固定值
        postMap.put("workflowArgs", "WF_BUTTON_SAVE,WF_BUTTON_REPORT");
        // 创建事件为固定值
        postMap.put("workflowId", "20");
        List<Map<String, Object>> filesList = new ArrayList<>(1);
        Map<String, Object> objectMap = new HashMap<>(1);
        // 数组类型，name属性 存放附件名称 例如：图片名称2018-08-14-09:13:21.169843-boat.jpg
        objectMap.put("name", name(eventAlert.getEventSource()));
        // content属性 存放附件的base64转码的字符串
        objectMap.put("content", eventAlertService.getImgData(eventAlert.getEventId()));
        filesList.add(objectMap);
        // 数组类型 接收附件
        postMap.put("files", filesList);
        // 事件来源
        postMap.put("type", "人工智能");
        // 事件分类
        postMap.put("typeDtl", eventAlert.getTypeDescription() + "_人工智能");
        // 所属网格code
        postMap.put("gridCode", eventAlert.getChannelGridId());
        // 所属网格name
        postMap.put("gridName", eventAlert.getChannelOrg());
        // 主题
        postMap.put("subject", eventAlert.getTypeDescription());
        // 描述
        postMap.put("description", eventAlert.getTypeDescription());
        // 发生时间
        postMap.put("occurDate", eventAlert.getEventStartTime().substring(0,19));
        // 发生地点
        postMap.put("occurPlace", eventAlert.getChannelLocation());
        // 上报人
        postMap.put("reporter", eventAlert.getChannelHandler());

        HttpEntity<Object> httpEntity = new HttpEntity<Object>(postMap, headers);
        restTemplate.getMessageConverters().add(new HttpMessageConverter());
        String url = "http://api.sunnyintec.com:8082/event/event/submiteventinterface";
        logger.info("上报告警事件sunnyintecSubmitEvent url:" + url + " , postMap" + postMap);

        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Map.class);
            if (responseEntity.getBody().get("code").equals(0)) {
                logger.info("成功! 返回的结果:" + responseEntity.getBody());
                return responseEntity.getBody();
            } else {
                logger.info("失败! 返回的结果:" + responseEntity.getBody());
                resultMap.put("msg", "失败!" + responseEntity.getBody());
                resultMap.put("code", responseEntity.getBody().get("code"));
                return resultMap;
            }
        } catch (RestClientException e) {
            logger.error("sunnyintecSubmitEvent ERROR!" + e.getMessage());
        }
        resultMap.put("msg", "上报告警未知错误!");
        return resultMap;
    }

    /**
     * 正则匹配文件名
     * @param source
     * @return
     */
    private String name(String source) {
        String patternStart = "/";
        Pattern pattern = Pattern.compile(patternStart);
        Matcher matcher = pattern.matcher(source);
        List<Integer> indexList = new ArrayList<>();
        while (matcher.find()) {
            indexList.add(matcher.start());
        }
        // 文件路径
        return source.substring(indexList.get(indexList.size() - 1) + 1);
    }
}
