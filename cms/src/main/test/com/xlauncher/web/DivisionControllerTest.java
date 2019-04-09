package com.xlauncher.web;

import com.xlauncher.dao.UserDao;
import com.xlauncher.entity.Device;
import com.xlauncher.entity.User;
import org.junit.Before;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * DivisionControllerTest
 * @author baishuailei
 * @since 2018-06-19
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:applicationContext.xml","classpath:spring-mvc.xml"})
@Transactional
public class DivisionControllerTest {
    @Autowired
    WebApplicationContext context;
    @Autowired
    UserDao userDao;
    private static String token = "token is for test";
    private MockMvc mockMvc;
    @Before
    public void before() {
        //可以对所有的controller来进行测试
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        User user = new User();
        user.setUserName("Test Case");
        user.setUserLoginName("DivisionControllerTest");
        user.setUserPassword("123456");
        user.setUserPhone("1234567890");
        userDao.insertUser(user);
        User userUp = new User();
        userUp.setUserId(user.getUserId());
        userUp.setToken("token is for test");
        userDao.updateUser(userUp);
    }
    @Test
    public void listDivisionP() throws Exception {
//        String responseString = mockMvc.perform(
//                get("/division/province")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("token",token))
//                .andDo(print())
//                .andReturn().getResponse().getContentAsString();
//        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void listDivisionCi() throws Exception {
//        String responseString = mockMvc.perform(
//                get("/division/city")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("token",token))
//                .andDo(print())
//                .andReturn().getResponse().getContentAsString();
//        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void listDivisionC() throws Exception {
//        String responseString = mockMvc.perform(
//                get("/division/county")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("token",token))
//                .andDo(print())
//                .andReturn().getResponse().getContentAsString();
//        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void listDivisionT() throws Exception {
//        String responseString = mockMvc.perform(
//                get("/division/town")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("token",token))
//                .andDo(print())
//                .andReturn().getResponse().getContentAsString();
//        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void listDivisionV() throws Exception {
//        String responseString = mockMvc.perform(
//                get("/division/village")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("token",token))
//                .andDo(print())
//                .andReturn().getResponse().getContentAsString();
//        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void listDivision() throws Exception {
//        String responseString = mockMvc.perform(
//                get("/division/list")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("token",token))
//                .andDo(print())
//                .andReturn().getResponse().getContentAsString();
//        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void insertDivision() throws Exception {

    }

    @Test
    public void updateDivision() throws Exception {

    }

    @Test
    public void deleteDivision() throws Exception {
        String responseString = mockMvc.perform(
                delete("/division/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

}