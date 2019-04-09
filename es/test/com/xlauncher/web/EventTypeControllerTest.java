package com.xlauncher.web;

import com.alibaba.fastjson.JSONObject;
import com.xlauncher.entity.EventType;
import com.xlauncher.service.EventTypeService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"classpath:spring-mybatis.xml", "classpath*:spring-mvc.xml"})
@Transactional
public class EventTypeControllerTest {
    @Autowired
    WebApplicationContext context;
    MockMvc mockMvc;
    @Autowired
    EventTypeService eventTypeService;
    private static EventType eventType;
    @BeforeClass
    public static void initAlertEvent() {
        eventType = new EventType();
        eventType.setTypeId((int) Math.random());
        eventType.setTypeDescription("检测到河面垃圾");
        eventType.setTypePushStatus("是");
        eventType.setTypeStartTime("00:00");
        eventType.setTypeEndTime("23:59");
    }
    @Before
    public void before() {
        //可以对所有的controller来进行测试
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        //仅仅对单个Controller来进行测试
//        mockMvc = MockMvcBuilders.standaloneSetup(new EventTypeController()).build();
    }
    @Test
    public void addEventType() throws Exception {
        String requestInfo = JSONObject.toJSONString(eventType);
        // test 接口
        String responseString = mockMvc.perform(
                post("/es_event_type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestInfo))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    /**
     * 异常测试:TypeDescription is null
     * @throws Exception
     */
    @Test
    public void addEventTypeException() throws Exception {
        EventType eventTypeException = new EventType();
        eventTypeException.setTypeId(2018);
        String requestInfo = JSONObject.toJSONString(eventTypeException);
        // test 接口
        String responseString = mockMvc.perform(
                post("/es_event_type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestInfo))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void deleteEventContentById() throws Exception {
        // test 接口
        String responseString = mockMvc.perform(
                delete("/es_event_type/{id}",100)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    /**
     * 测试异常：typeId：不符合要求
     * @throws Exception
     */
    @Test
    public void deleteEventContentByIdException() throws Exception {
        // test 接口
        String responseString = mockMvc.perform(
                delete("/es_event_type/{id}","delete2018")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void updateEventContent() throws Exception {
        String requestInfo = JSONObject.toJSONString(eventType);
        // test 接口
        String responseString = mockMvc.perform(
                put("/es_event_type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestInfo))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    /**
     * 测试异常：
     * @throws Exception
     */
    @Test
    public void updateEventContentException() throws Exception {
        EventType eventTypeException = new EventType();
        eventTypeException.setTypeId(2018);
        eventTypeException.setTypeDescription("");
        String requestInfo = JSONObject.toJSONString(eventTypeException);
        // test 接口
        String responseString = mockMvc.perform(
                put("/es_event_type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestInfo))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void getEventContentById() throws Exception {
        // test 接口
        String responseString = mockMvc.perform(
                get("/es_event_type/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json---0-----" + responseString);
    }

    @Test
    public void getAllEventsType() throws Exception {
        // test 接口
        String responseString = mockMvc.perform(
                get("/es_event_type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("typeDescription","undefined"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);

    }

    @Test
    public void getTypes() throws Exception {
        // test 接口
        String responseString = mockMvc.perform(
                get("/es_event_type/getTypes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("typeDescription","undefined"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);

    }

}