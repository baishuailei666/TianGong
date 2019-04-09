package com.xlauncher.web;

import com.xlauncher.dao.UserDao;
import com.xlauncher.entity.Todo;
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

import java.math.BigInteger;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * TodoControllerTest
 * @author baishuailei
 * @since 2018-06-19
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:applicationContext.xml","classpath*:spring-mvc.xml"})
@Transactional
public class TodoControllerTest {
    @Autowired
    WebApplicationContext context;
    @Autowired
    UserDao userDao;
    private static Todo todo;
    private static String token = "token is for test";
    private MockMvc mockMvc;
    @Before
    public void before() {
        //可以对所有的controller来进行测试
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        User user = new User();
        user.setUserName("Test Case");
        user.setUserLoginName("TodoControllerTest");
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
        todo = new Todo();
        todo.setTodoUserId(12);
        todo.setTodoNote("test");
        todo.setTodoTime(DatetimeUtil.getDate(System.currentTimeMillis()));
    }
    @Test
    public void registerList() throws Exception {
        String responseString = mockMvc.perform(
                get("/todo/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .param("page","1"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void registerInfo() throws Exception {
        String responseString = mockMvc.perform(
                get("/todo/info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .param("todoId","1"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void resetCodeList() throws Exception {
        String responseString = mockMvc.perform(
                get("/todo/resetcode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .param("page","1"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void resetCode() throws Exception {
        String responseString = mockMvc.perform(
                put("/todo/resetcode/{todoId}","1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void denyTodo() throws Exception {
        String responseString = mockMvc.perform(
                put("/todo/deny/{todoId}","1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void registerConfirm() throws Exception {
        User user = new User();
        user.setUserId(1);
        user.setAdminDivisionId(BigInteger.valueOf(1));
        user.setUserOrgId("1");
        String responseString = mockMvc.perform(
                put("/todo/confirm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void registerCount() throws Exception {
        String responseString = mockMvc.perform(
                get("/todo/register/count")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .param("page","1"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void resetcodeCount() throws Exception {
        String responseString = mockMvc.perform(
                get("/todo/resetcode/count")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .param("page","1"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

}