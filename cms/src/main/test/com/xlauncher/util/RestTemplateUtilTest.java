package com.xlauncher.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml","classpath:spring-*.xml"})
@Transactional
public class RestTemplateUtilTest {
    @Autowired
    private RestTemplateUtil rt;
    @Test
    public void addEventTypeToES() throws Exception {
        Map<String, Object> map = new HashMap<>(1);
        map.put("typeDescription","检测到河面垃圾");
        map.put("typeStartTime","00:00");
        map.put("typeEndTime","23:59");
        map.put("typePushStatus","是");
        System.out.println("addEventTypeToES: " + rt.addEventTypeToES(map));
    }

    @Test
    public void addEventTimeToES() throws Exception {
        Map<String, Object> map = new HashMap<>(1);
        map.put("eventTime","600000");
        System.out.println("addEventTimeToES: " + rt.addEventTimeToES(map));
    }

    @Test
    public void putEventTypeToES() throws Exception {
        Map<String, Object> map = new HashMap<>(1);
        map.put("typeId",1001);
        map.put("typeDescription","测试修改");
        System.out.println("putEventTypeToES: " + rt.putEventTypeToES(map));
    }
    @Test
    public void deleteEventTypeToES() throws Exception {
        Map<String, Object> map = new HashMap<>(1);
        map.put("id",1001);
        System.out.println("deleteEventTypeToES: " + rt.deleteEventTypeToES(map));
    }
    @Test
    public void getEventTypeToES() throws Exception {
        System.out.println("getEventTypeToES: " + rt.getEventTypeToES("undefined"));
    }

    @Test
    public void login() throws Exception {
        ResponseEntity<Map> mapResponseEntity;
        RestTemplate restTemplate = new RestTemplate();
        try {
            Map<String, Object> addMap = new HashMap<>(1);
            addMap.put("userLoginName","admin");
            addMap.put("userPassword","111111");
            //header设置
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
            headers.add("Content-Type", "application/json;charset=UTF-8");
            HttpEntity<Object> httpEntity = new HttpEntity<Object>(addMap, headers);
            String postUrl = "http://8.11.0.11:8080/user/login";
            mapResponseEntity = restTemplate.exchange(postUrl, HttpMethod.POST, httpEntity, Map.class);
            System.out.println(mapResponseEntity.toString());
        } catch (RestClientException e) {
            throw e;
        }
    }
}