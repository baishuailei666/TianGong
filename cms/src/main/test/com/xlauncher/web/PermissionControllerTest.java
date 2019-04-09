package com.xlauncher.web;

import com.alibaba.fastjson.JSONObject;
import com.xlauncher.dao.UserDao;
import com.xlauncher.entity.Organization;
import com.xlauncher.entity.Permission;
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

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * PermissionControllerTest
 * @author baishuailei
 * @since 2018-06-19
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:applicationContext.xml","classpath*:spring-mvc.xml"})
@Transactional
public class PermissionControllerTest {
    @Autowired
    WebApplicationContext context;
    @Autowired
    UserDao userDao;
    private static Permission permission;
    private static String token = "token is for test";
    private MockMvc mockMvc;
    @Before
    public void before() {
        //可以对所有的controller来进行测试
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        User user = new User();
        user.setUserName("Test Case");
        user.setUserLoginName("PermissionControllerTest");
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
        permission = new Permission();
        permission.setPermissionName("PermissionControllerTest");
        permission.setPermissionNote("2018-06-19 PermissionControllerTest");
        permission.setPermissionSuperiorName("launcher");
    }
    @Test
    public void insertPermission() throws Exception {
//        String requestInfo = JSONObject.toJSONString(permission);
//        String responseString = mockMvc.perform(
//                post("/permission")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("token",token)
//                        .content(requestInfo))
//                .andDo(print())
//                .andReturn().getResponse().getContentAsString();
//        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void deletePermission() throws Exception {
        String responseString = mockMvc.perform(
                delete("/permission/{permissionId}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void updatePermission() throws Exception {
//        Permission permissionUp = new Permission();
//        permissionUp.setPermissionId(1);
//        permissionUp.setPermissionNote("2018-06-19 PermissionControllerTest today");
//        String requestInfo = JSONObject.toJSONString(permissionUp);
//        String responseString = mockMvc.perform(
//                put("/permission")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("token",token)
//                        .content(requestInfo))
//                .andDo(print())
//                .andReturn().getResponse().getContentAsString();
//        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void listPermission() throws Exception {
        String responseString = mockMvc.perform(
                get("/permission")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

}