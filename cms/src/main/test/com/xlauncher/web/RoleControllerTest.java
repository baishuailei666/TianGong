package com.xlauncher.web;

import com.alibaba.fastjson.JSONObject;
import com.xlauncher.dao.UserDao;
import com.xlauncher.entity.Role;
import com.xlauncher.entity.User;
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
 * RoleControllerTest
 * @author baishuailei
 * @since 2018-06-19
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:applicationContext.xml","classpath*:spring-mvc.xml"})
@Transactional
public class RoleControllerTest {
    @Autowired
    WebApplicationContext context;
    @Autowired
    UserDao userDao;
    private static Role role;
    private static String token = "token is for test";
    private MockMvc mockMvc;
    @Before
    public void before() {
        //可以对所有的controller来进行测试
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        User user = new User();
        user.setUserName("Test Case");
        user.setUserLoginName("RoleControllerTest");
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
        role = new Role();
        role.setRoleName("RoleControllerTest");
        role.setRoleDescription("test");
        role.setRoleStatus("1");
    }
    @Test
    public void insertRole() throws Exception {
        String requestInfo = JSONObject.toJSONString(role);
        String responseString = mockMvc.perform(
                post("/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .content(requestInfo))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void roleNameCheck() throws Exception {
//        Map<String, String> map = new HashMap<>(1);
//        map.put("roleName","test");
//        String requestInfo = JSONObject.toJSONString(map);
//        String responseString = mockMvc.perform(
//                post("/role/namecheck")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("token",token)
//                        .content(requestInfo))
//                .andDo(print())
//                .andReturn().getResponse().getContentAsString();
//        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void deleteRole() throws Exception {
        String responseString = mockMvc.perform(
                delete("/role/{roleId}","1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void updateRole() throws Exception {
//        Map<String, Object> role = new HashMap<>(1);
//        role.put("roleId","1001");
//        role.put("roleDescription","update role");
//        String requestInfo = JSONObject.toJSONString(role);
//        String responseString = mockMvc.perform(
//                put("/role")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("token",token)
//                        .content(requestInfo))
//                .andDo(print())
//                .andReturn().getResponse().getContentAsString();
//        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void listRole() throws Exception {
        String responseString = mockMvc.perform(
                get("/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void authorizationList() throws Exception {
        String responseString = mockMvc.perform(
                get("/role/authorization/{roleId}","1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void getROle() throws Exception {
        String responseString = mockMvc.perform(
                get("/role/{roleId}","1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

}