package com.xlauncher.web;

import com.alibaba.fastjson.JSONObject;
import com.xlauncher.dao.UserDao;
import com.xlauncher.entity.Organization;
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
 * OrganizationControllerTest
 * @author baishuailei
 * @since 2018-06-19
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:applicationContext.xml","classpath*:spring-mvc.xml"})
@Transactional
public class OrganizationControllerTest {
    @Autowired
    WebApplicationContext context;
    @Autowired
    UserDao userDao;
    private static Organization organization;
    private static String token = "token is for test";
    private MockMvc mockMvc;
    @Before
    public void before() {
        //可以对所有的controller来进行测试
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        User user = new User();
        user.setUserName("Test Case");
        user.setUserLoginName("OrganizationControllerTest");
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
        organization = new Organization();
        organization.setName("OrganizationControllerTest");
        organization.setOrgLeader("launcher");
        organization.setOrgLocation("hangzhou");
        organization.setOrgDuty("test");
        organization.setOrgSuperiorId("1");
        organization.setOrgEmail("123456789@123.com");
        organization.setOrgPhone("123789654");
    }

    @Test
    public void listOrganization() throws Exception {
        String responseString = mockMvc.perform(
                get("/organization/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void insertOrganization() throws Exception {
        String requestInfo = JSONObject.toJSONString(organization);
        String responseString = mockMvc.perform(
                post("/organization")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .content(requestInfo))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void updateOrganization() throws Exception {
        Organization organizationUp = new Organization();
        organizationUp.setOrgId("1");
        organizationUp.setOrgPhone("147852369");
        String requestInfo = JSONObject.toJSONString(organizationUp);
        String responseString = mockMvc.perform(
                put("/organization")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .content(requestInfo))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void deleteOrganization() throws Exception {
//        String responseString = mockMvc.perform(
//                delete("/organization/{orgId}",1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("token",token))
//                .andDo(print())
//                .andReturn().getResponse().getContentAsString();
//        System.out.println("--------返回的json--------" + responseString);
    }

}