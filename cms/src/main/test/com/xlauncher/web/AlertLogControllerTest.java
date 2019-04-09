package com.xlauncher.web;

import com.xlauncher.dao.UserDao;
import com.xlauncher.entity.AlertLog;
import com.xlauncher.entity.User;
import com.xlauncher.service.UserService;
import com.xlauncher.util.DatetimeUtil;
import com.xlauncher.util.userlogin.Jwt;
import com.xlauncher.util.userlogin.SessionManageListener;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * AlertLogControllerTest
 * @author baishuailei
 * @since 2018-05-14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:applicationContext.xml","classpath*:spring-mvc.xml"})
@Transactional
public class AlertLogControllerTest {
    @Autowired
    WebApplicationContext context;
    @Autowired
    UserDao userDao;
    @Autowired
    UserService userService;
    private static AlertLog alertLog;
    private static String token = "token is for test";
    private static MockHttpServletRequest request;
    private static MockHttpServletResponse response;
    private User user;
    private MockMvc mockMvc;
    private MockHttpSession session;
    @Before
    public void before() {
        //可以对所有的controller来进行测试
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        request = new MockHttpServletRequest();
        request.setCharacterEncoding("UTF-8");
        response = new MockHttpServletResponse();
        session = new MockHttpSession();
        user = new User();
        user.setUserName("Test Case");
        user.setUserLoginName("AlertLogControllerTest");
        user.setUserPassword("-1ef523c6b645a65441a91fa80df077c2");
        user.setUserPhone("1234567890");
        userDao.insertUser(user);

//
//        Map<String,Object> userLogin = new HashMap<>(1);
//        userLogin.put("userLoginName", user.getUserLoginName());
//        userLogin.put("userPassword", "123456");
        Map<String, Object> responseMap = new HashMap<>(1);
        responseMap.put("userLoginName",user.getUserLoginName());
        String token = Jwt.sign(responseMap, 12 * 60 * 60 * 1000L);

        user.setToken(token);
        System.out.println("user **: " + user);
        userDao.updateUser(user);
//        request.setSession(session);
//        session.setAttribute("userLoginName", userLogin.get("userLoginName"));
//
//        userService.login(userLogin,request,response,responseMap);

    }
    @BeforeClass()
    public static void init() {
        alertLog = new AlertLog();
        alertLog.setAlertPriority("INFO");
        alertLog.setAlertTime(DatetimeUtil.getDate(System.currentTimeMillis()));
        alertLog.setAlertTimeSpan(1500);
        alertLog.setAlertThread("Thread 10");
        alertLog.setAlertLineNum("150");
        alertLog.setAlertFileName("AlertLogControllerTest");
        alertLog.setAlertClassName("AlertLogControllerTest.class");
        alertLog.setAlertMethodName("insertAlertLog");
        alertLog.setAlertMessage("Alert Controller Test");
        alertLog.setAlertType("FileNotFoundException");
    }

    @Test
    public void getAlertLog() throws Exception {
        String responseString = mockMvc.perform(
                get("/alertLog/page/{number}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .param("upStartTime","undefined")
                        .param("lowStartTime","undefined")
                        .param("alertPriority","undefined")
                        .param("alertFileName","undefined"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void pageCount() throws Exception {
        String responseString = mockMvc.perform(
                get("/alertLog/count")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .param("upStartTime","undefined")
                        .param("lowStartTime","undefined")
                        .param("alertPriority","undefined")
                        .param("alertFileName","undefined"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void getFileName() throws Exception {
        String responseString = mockMvc.perform(
                get("/alertLog/alertFileName")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void getAlertLogById() throws Exception {
        String responseString = mockMvc.perform(
                get("/alertLog/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",user.getToken()))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

}