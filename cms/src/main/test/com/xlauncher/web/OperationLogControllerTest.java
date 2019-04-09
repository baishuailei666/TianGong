package com.xlauncher.web;

import com.xlauncher.dao.UserDao;
import com.xlauncher.entity.OperationLog;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * OperationLogControllerTest
 * @author baishuailei
 * @since 2018-05-11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:applicationContext.xml","classpath*:spring-mvc.xml"})
@Transactional
public class OperationLogControllerTest {
    @Autowired
    WebApplicationContext context;
    @Autowired
    UserDao userDao;
    private static OperationLog log;
    private static String token = "token is for test";
    private MockMvc mockMvc;
    @Before
    public void before() {
        //可以对所有的controller来进行测试
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        User user = new User();
        user.setUserName("Test Case");
        user.setUserLoginName("OperationLogControllerTest");
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
        log = new OperationLog();
        log.setOperationPerson("OperationLogControllerTest");
        log.setOperationTime(DatetimeUtil.getDate(System.currentTimeMillis()));
        log.setOperationType("Operation Log Controller Test");
        log.setOperationModule("OperationLog");
        log.setOperationDescription("2018-05-11 OperationLogControllerTest");
        log.setOperationCategory("事件");
    }
    @Test
    public void deleteLog() throws Exception {
        String responseString = mockMvc.perform(
                delete("/opLog/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void queryAllOperationLog() throws Exception {
        String responseString = mockMvc.perform(
                get("/opLog/operation/page/{number}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .param("upStartTime","undefined")
                        .param("lowStartTime","undefined")
                        .param("opPerson","undefined")
                        .param("opType","undefined")
                        .param("opModule","undefined")
                        .param("opCategory","运维")
                        .param("opSystemModule","undefined"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void queryAllOperatingLog() throws Exception {
        String responseString = mockMvc.perform(
                get("/opLog/operating/page/{number}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .param("upStartTime","undefined")
                        .param("lowStartTime","undefined")
                        .param("opPerson","undefined")
                        .param("opType","undefined")
                        .param("opModule","undefined")
                        .param("opCategory","运营")
                        .param("opSystemModule","undefined"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void pageCount() throws Exception {
        String responseString = mockMvc.perform(
                get("/opLog/count")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .param("upStartTime","undefined")
                        .param("lowStartTime","undefined")
                        .param("opPerson","undefined")
                        .param("opType","undefined")
                        .param("opModule","undefined")
                        .param("opCategory","undefined")
                        .param("opSystemModule","undefined"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void getOperationLogById() throws Exception {
        String responseString = mockMvc.perform(
                get("/opLog/operation/{id}",10)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void getOperatingLogById() throws Exception {
        String responseString = mockMvc.perform(
                get("/opLog/operating/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void getEventLogById() throws Exception {
        String responseString = mockMvc.perform(
                get("/opLog/event/{id}",5)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }
    @Test
    public void recordModule() throws Exception {
        String responseString = mockMvc.perform(
                get("/opLog/recordModule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .param("recordCategory","undefined")
                        .param("recordSystemModule","undefined"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }
    @Test
    public void recordSystemModule() throws Exception {
        String responseString = mockMvc.perform(
                get("/opLog/recordSystemModule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .param("recordCategory","undefined"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }
}