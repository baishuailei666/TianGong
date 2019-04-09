package com.xlauncher.web;

import com.alibaba.fastjson.JSONObject;
import com.xlauncher.dao.EventAlertDao;
import com.xlauncher.dao.UserDao;
import com.xlauncher.entity.EventAlert;
import com.xlauncher.entity.User;
import com.xlauncher.util.DatetimeUtil;
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
import java.util.Map;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * EventAlertControllerTest
 * @author baishuailei
 * @since 2018-05-30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:applicationContext.xml","classpath*:spring-mvc.xml"})
@Transactional
public class EventAlertControllerTest {
    @Autowired
    WebApplicationContext context;
    @Autowired
    EventAlertDao eventAlertDao;
    @Autowired
    UserDao userDao;
    private static EventAlert eventAlert;
    private static String token = "token is for test";
    private MockMvc mockMvc;
    @Before
    public void before() {
        //可以对所有的controller来进行测试
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        User user = new User();
        user.setUserName("Test Case");
        user.setUserLoginName("EventAlertControllerTest");
        user.setUserPassword("123456");
        user.setUserPhone("1234567890");
        userDao.insertUser(user);
        User userUp = new User();
        userUp.setUserId(user.getUserId());
        userUp.setToken("token is for test");
        userDao.updateUser(userUp);
    }
    @BeforeClass()
    public static void init() {
        eventAlert = new EventAlert();
        eventAlert.setEventId(10001);
        eventAlert.setChannelId("01e2bb474f924b25aa3edc8f76cdac35");
        eventAlert.setEventStartTime(DatetimeUtil.getDate(System.currentTimeMillis()));
        eventAlert.setTypeDescription("非法侵入");
        eventAlert.setEventSource("ftp://8.14.0.108:21/alarm-imgs/2018-08-09-13:45:11.721345-polluter.jpg");

    }

    @Test
    public void listNotCheckAlert() throws Exception {
        String responseString = mockMvc.perform(
                get("/eventAlert/notCheck/page/{number}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .param("upStartTime","undefined")
                        .param("lowStartTime","undefined")
                        .param("typeDescription","undefined")
                        .param("channelLocation","undefined")
                        .param("channelHandler","undefined")
                        .param("channelOrg","undefined"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void pageNotCheckCount() throws Exception {
        String responseString = mockMvc.perform(
                get("/eventAlert/notCheck/count")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .param("upStartTime","undefined")
                        .param("lowStartTime","undefined")
                        .param("typeDescription","undefined")
                        .param("channelLocation","undefined")
                        .param("channelHandler","undefined")
                        .param("channelOrg","undefined"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void listCheckAlert() throws Exception {
        String responseString = mockMvc.perform(
                get("/eventAlert/check/page/{number}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .param("upStartTime","undefined")
                        .param("lowStartTime","undefined")
                        .param("upCheckStartTime","undefined")
                        .param("lowCheckStartTime","undefined")
                        .param("typeDescription","undefined")
                        .param("typeStatus","undefined")
                        .param("channelLocation","undefined")
                        .param("channelHandler","undefined")
                        .param("channelOrg","undefined")
                        .param("eventReviewer","undefined")
                        .param("typeRectify","undefined"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void pageCheckCount() throws Exception {
        String responseString = mockMvc.perform(
                get("/eventAlert/check/count")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .param("upStartTime","undefined")
                        .param("lowStartTime","undefined")
                        .param("upCheckStartTime","undefined")
                        .param("lowCheckStartTime","undefined")
                        .param("typeDescription","undefined")
                        .param("typeStatus","undefined")
                        .param("channelLocation","undefined")
                        .param("channelHandler","undefined")
                        .param("channelOrg","undefined")
                        .param("eventReviewer","undefined")
                        .param("typeRectify","undefined"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }
    @Test
    public void receive() throws Exception {
        String requestInfo = JSONObject.toJSONString(eventAlert);
        String responseString = mockMvc.perform(
                post("/eventAlert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .content(requestInfo))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void getImgByEventId() throws Exception {
        String responseString = mockMvc.perform(
                get("/eventAlert/image/{eventId}",1064)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json 图片大小--------" + responseString.length());
    }

    @Test
    public void getEventByEventId() throws Exception {
        String responseString = mockMvc.perform(
                get("/eventAlert/{eventId}",1001)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void getEventType() throws Exception {
        String responseString = mockMvc.perform(
                get("/eventAlert/eventType")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void addEventType() throws Exception {
        Map<String, Object> map = new HashMap<>(1);
        map.put("typeDescription","检测到河面垃圾");
        map.put("typeStartTime","00:00");
        map.put("typeEndTime","23:59");
        map.put("typePushStatus","是");
        String requestInfo = JSONObject.toJSONString(map);
        String responseString = mockMvc.perform(
                post("/eventAlert/eventType")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .content(requestInfo))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void addEventTime() throws Exception {
        Map<String, Object> map = new HashMap<>(1);
        map.put("eventTime","600000");
        String requestInfo = JSONObject.toJSONString(map);
        String responseString = mockMvc.perform(
                post("/eventAlert/eventTime")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .content(requestInfo))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void putEventType() throws Exception {
        Map<String, Object> map = new HashMap<>(1);
        map.put("typeId",10101);
        map.put("typeDescription","测试修改");
        String requestInfo = JSONObject.toJSONString(map);
        String responseString = mockMvc.perform(
                put("/eventAlert/eventType")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .content(requestInfo))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void deleteEventType() throws Exception {
        String responseString = mockMvc.perform(
                delete("/eventAlert/eventType/{id}",1001)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void updateEventCheck() throws Exception {
        EventAlert eventAlertUp = new EventAlert();
        eventAlertUp.setEventId(1001);
        eventAlertUp.setTypeRectify("错误类型纠正类型");
        eventAlertUp.setEventCheck("已复核");
        eventAlertUp.setEventEndTime(DatetimeUtil.getDate(System.currentTimeMillis()));
        eventAlertUp.setEventReviewer("launcher001");
        String requestInfo = JSONObject.toJSONString(eventAlertUp);
        String responseString = mockMvc.perform(
                put("/eventAlert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .content(requestInfo))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void getCheckTypeStatusCount() throws Exception {
        String responseString = mockMvc.perform(
                get("/eventAlert/checkCount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .param("typeStatus","undefined")
                        .param("time","undefined"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void getTypeDescriptionCount() throws Exception {
        String responseString = mockMvc.perform(
                get("/eventAlert/getTypeCount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .param("typeDescription","undefined"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void getNotCheckCount() throws Exception {
        String responseString = mockMvc.perform(
                get("/eventAlert/notCheckCount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void getNotCheckEvent() throws Exception {
        String responseString = mockMvc.perform(
                get("/eventAlert/notCheckEvent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

}