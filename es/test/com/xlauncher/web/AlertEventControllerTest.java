package com.xlauncher.web;

import com.alibaba.fastjson.JSONObject;
import com.xlauncher.entity.AlertEvent;
import com.xlauncher.service.AlertEventService;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"classpath:spring-mybatis.xml", "classpath*:spring-mvc.xml"})
@Transactional
public class AlertEventControllerTest {
    @Autowired
    AlertEventService alertEventService;
    @Autowired
    WebApplicationContext context;
    MockMvc mockMvc;
    private static AlertEvent alertEvent;
    private static int eventId = 1;
    private static String channelId = "";

    @BeforeClass
    public static void initAlertEvent() {
        alertEvent = new AlertEvent();
        alertEvent.setEventStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        channelId = UUID.randomUUID().toString().replace("-", "");
        alertEvent.setChannelId(channelId);
        alertEvent.setTypeId(1);
        alertEvent.setTypeDescription("非法钓鱼");
        alertEvent.setEventSource("http://www.taopic.com/uploads/allimg/140418/235106-14041PRS493.jpg");
        alertEvent.setEventPushStatus("未推送");
        alertEvent.setEventPushSuntecStatus("未推送");
    }

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();  //初始化MockMvc对象
        eventId++;
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void addEvent() throws Exception {
        // test 接口
        String requestInfo = JSONObject.toJSONString(alertEvent);
        String responseString = mockMvc.perform(
                post("/es_alert_event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestInfo))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void updateEventPushStatus() throws Exception {
        // test alertEventService
        AlertEvent alertEvent = new AlertEvent();
        alertEvent.setEventPushStatus("已推送");
        alertEvent.setEventId(2);
        // test 接口
        String requestInfo = JSONObject.toJSONString(alertEvent);
        String responseString = mockMvc.perform(
                put("/es_alert_event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestInfo))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void getAllEvents() throws Exception {
        // test 接口
        String responseString = mockMvc.perform(
                get("/es_alert_event")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void getEventsCount() throws Exception {
        // test 接口
        String responseString = mockMvc.perform(
                get("/es_alert_event/count")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void getEventsCountByChannelId() throws Exception {
        // test 接口
        String responseString = mockMvc.perform(
                get("/es_alert_event/{channelId}/count","dc79f7b5893049e286021f17595bf948")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void getEventBychannelId() throws Exception {
        // test 接口
        String responseString = mockMvc.perform(
                get("/es_alert_event/channelid/{id}", "dc79f7b5893049e286021f17595bf948")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void getAllEventsByPage() throws Exception {
        // test 接口
        String responseString = mockMvc.perform(
                get("/es_alert_event/page/{number}/{count}","1","10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void getEventsByChannelAndPage() throws Exception {
        // test 接口
        String responseString = mockMvc.perform(
                get("/es_alert_event/channelid/{channelId}/page/{number}/{count}","dc79f7b5893049e286021f17595bf948","1","10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void getEventById() throws Exception {
        // test 接口
        String responseString = mockMvc.perform(
                get("/es_alert_event/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

}