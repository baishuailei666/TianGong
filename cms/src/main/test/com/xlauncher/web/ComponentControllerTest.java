package com.xlauncher.web;

import com.alibaba.fastjson.JSONObject;
import com.xlauncher.dao.UserDao;
import com.xlauncher.entity.Component;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * ComponentControllerTest
 * @author baishuailei
 * @since 2018-05-10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:applicationContext.xml","classpath*:spring-mvc.xml"})
@Transactional
public class ComponentControllerTest {
    @Autowired
    WebApplicationContext context;
    @Autowired
    UserDao userDao;
    private static Component component;
    private static String token = "token is for test";
    private MockMvc mockMvc;
    @Before
    public void before() {
        //可以对所有的controller来进行测试
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        User user = new User();
        user.setUserName("Test Case");
        user.setUserLoginName("ComponentControllerTest");
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
        component = new Component();
        component.setComponentAbbr("ComponentControllerTest");
        component.setComponentName("控制层测试组件");
        component.setComponentIp("8.11.0.13");
        component.setComponentPort("8080");
        component.setComponentDescription("这是一个控制层测试组件的组件，2018-05-10");
        Map<String, Object> map = new HashMap<>(1);
        map.put("time", DatetimeUtil.getDate(System.currentTimeMillis()));
        map.put("data", component.getComponentDescription());
        component.setComponentConfiguration(map);
    }
    @Test
    public void addComponent() throws Exception {
        String requestInfo = JSONObject.toJSONString(component);
        String responseString = mockMvc.perform(
                post("/component")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .content(requestInfo))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void deleteComponent() throws Exception {
        String requestInfo = JSONObject.toJSONString(component);
        String responseString = mockMvc.perform(
                delete("/component/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .content(requestInfo))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void updateComponent() throws Exception {
        Component componentUp = new Component();
        componentUp.setId(3);
        componentUp.setComponentAbbr("cms");
        componentUp.setComponentIp("8.16.0.46");
        componentUp.setComponentPort("8080");
        Map<String, Object> map = new HashMap<>(1);
        map.put("time", DatetimeUtil.getDate(System.currentTimeMillis()));
        map.put("data", component.getComponentDescription());
        component.setComponentConfiguration(map);
        String requestInfo = JSONObject.toJSONString(componentUp);
        String responseString = mockMvc.perform(
                put("/component")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .content(requestInfo))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void listAllComponentByNum() throws Exception {
        String responseString = mockMvc.perform(
                get("/component/page/{number}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .param("componentName","undefined"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void countPage() throws Exception {
        String responseString = mockMvc.perform(
                get("/component/count")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .param("componentName","undefined"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void listComponent() throws Exception {
        String responseString = mockMvc.perform(
                get("/component")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void getComponentById() throws Exception {
        String responseString = mockMvc.perform(
                get("/component/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void getComponentStatus() throws Exception {
        String responseString = mockMvc.perform(
                get("/component/heartbeat",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }
}