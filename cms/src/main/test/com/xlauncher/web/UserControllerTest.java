package com.xlauncher.web;

import com.alibaba.fastjson.JSONObject;
import com.xlauncher.dao.UserDao;
import com.xlauncher.entity.Question;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * UserControllerTest
 * @author baishuailei
 * @since 2018-06-19
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:applicationContext.xml","classpath*:spring-mvc.xml"})
@Transactional
public class UserControllerTest {
    @Autowired
    WebApplicationContext context;
    @Autowired
    UserDao userDao;
    private static User userBe;
    private static String token = "token is for test";
    private MockMvc mockMvc;
    @Before
    public void before() {
        //可以对所有的controller来进行测试
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        User user = new User();
        user.setUserName("Test Case");
        user.setUserLoginName("UserControllerTest");
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
        userBe = new User();
        userBe.setUserLoginName("admin0001");
        userBe.setUserPassword("123456");
    }
    @Test
    public void login() throws Exception {
        Map<String,Object> userLogin = new HashMap<>(1);
        userLogin.put("userLoginName","baishuailei");
        userLogin.put("userPassword","111111");
        String requestInfo = JSONObject.toJSONString(userLogin);
        String responseString = mockMvc.perform(
                post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestInfo))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void register() throws Exception {
        User reUser = new User();
        reUser.setUserCode("1001");
        reUser.setUserLoginName("test");
        reUser.setUserPassword("1230123");
        reUser.setUserName("launcher");
        reUser.setUserCardId("413001199307271823");
        reUser.setUserPhone("1478523396");
        reUser.setUserEmail("123456789@qq.com");
        reUser.setAdminDivisionId(BigInteger.valueOf(1));
        reUser.setUserOrgId("1");
        String requestInfo = JSONObject.toJSONString(reUser);
        String responseString = mockMvc.perform(
                post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .content(requestInfo))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void userInfo() throws Exception {
        String responseString = mockMvc.perform(
                get("/user/info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void mailUser() throws Exception {
        String responseString = mockMvc.perform(
                get("/user/mailUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .param("userName","undefined"))
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
//                post("/user/namecheck")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("token",token)
//                        .content(requestInfo))
//                .andDo(print())
//                .andReturn().getResponse().getContentAsString();
//        System.out.println("--------返回的json--------" + responseString);
    }


    @Test
    public void insertUser() throws Exception {
//        Map<String, Object> inUser = new HashMap<>(1);
//        inUser.put("userCode","1001");
//        inUser.put("userLoginName","test");
//        inUser.put("userPassword","123465");
//        inUser.put("userName","launcher");
//        inUser.put("userCardId","413001199307271823");
//        inUser.put("userPhone","14785236900");
//        inUser.put("userEmail","123456789@qq.com");
//        inUser.put("adminDivisionId","1");
//        inUser.put("userOrgId","1");
//        String requestInfo = JSONObject.toJSONString(inUser);
//        String responseString = mockMvc.perform(
//                post("/user")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("token",token)
//                        .content(requestInfo))
//                .andDo(print())
//                .andReturn().getResponse().getContentAsString();
//        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void deleteUser() throws Exception {
        String responseString = mockMvc.perform(
                delete("/user/{userId}","12")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void updateUser() throws Exception {
        User putUser = new User();
        putUser.setUserId(12);
        putUser.setUserCode("1001");
        putUser.setUserLoginName("test");
        putUser.setUserPassword("123456");
        putUser.setUserName("launcher");
        putUser.setUserCardId("413001199307271823");
        putUser.setUserPhone("1478523396");
        putUser.setUserEmail("123456789@qq.com");
        putUser.setAdminDivisionId(BigInteger.valueOf(1));
        putUser.setUserOrgId("1");
        String requestInfo = JSONObject.toJSONString(putUser);
        String responseString = mockMvc.perform(
                put("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .content(requestInfo))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void updatePassword() throws Exception {
//        Map<String, Object> upUser = new HashMap<>(1);
//        upUser.put("userCode","1001");
//        upUser.put("userPassword","1234567890");
//        String requestInfo = JSONObject.toJSONString(upUser);
//        String responseString = mockMvc.perform(
//                put("/user/password")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("token",token)
//                        .content(requestInfo))
//                .andDo(print())
//                .andReturn().getResponse().getContentAsString();
//        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void confirmRegister() throws Exception {
        User putUser = new User();
        putUser.setUserId(12);
        putUser.setUserCode("1001");
        putUser.setAdminDivisionId(BigInteger.valueOf(1));
        putUser.setUserOrgId("1");
        String requestInfo = JSONObject.toJSONString(putUser);
        String responseString = mockMvc.perform(
                put("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .content(requestInfo))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void userCardIdCheck() throws Exception {
        Map<String, String> map = new HashMap<>(1);
        map.put("userCardId","420821199610102368");
        String requestInfo = JSONObject.toJSONString(map);
        String responseString = mockMvc.perform(
                post("/user/userCardId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .content(requestInfo))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void listUser() throws Exception {
        String responseString = mockMvc.perform(
                get("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void countUserCode() throws Exception {
        User userCode = new User();
        userCode.setUserCode("userCode=0&userId=54");
        String requestInfo = JSONObject.toJSONString(userCode);
        String responseString = mockMvc.perform(
                post("/user/userCode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .content(requestInfo))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void countUser() throws Exception {
        String responseString = mockMvc.perform(
                get("/user/count")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void authorizationList() throws Exception {
        String responseString = mockMvc.perform(
                get("/user/assign/{userId}","12")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void getUser() throws Exception {
        String responseString = mockMvc.perform(
                get("/user/{userId}","12")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void checkInfo() throws Exception {
        User checkUser = new User();
        checkUser.setUserLoginName("test");
        checkUser.setUserName("launcher");
        checkUser.setUserCardId("413001199307271823");
        checkUser.setUserPhone("1478523396");
        String requestInfo = JSONObject.toJSONString(checkUser);
        String responseString = mockMvc.perform(
                post("/user/checkinfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .content(requestInfo))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void insertQuestion() throws Exception {
//        List<Question> questionList = new ArrayList<>(1);
//        Question question = new Question();
//        question.setQuestionId(1001);
//        question.setQuestionAnswer("test");
//        question.setQuestionName("test test");
//        question.setQuestionUserId(1);
//
//        Question question1 = new Question();
//        question1.setQuestionId(1002);
//        question1.setQuestionAnswer("test2");
//        question1.setQuestionName("test2 test");
//        question1.setQuestionUserId(2);
//
//        Question question3 = new Question();
//        question3.setQuestionId(1003);
//        question3.setQuestionAnswer("test3");
//        question3.setQuestionName("test3 test");
//        question3.setQuestionUserId(3);
//
//        questionList.add(question);
//        questionList.add(question1);
//        questionList.add(question3);
//        String requestInfo = JSONObject.toJSONString(questionList);
//        String responseString = mockMvc.perform(
//                post("/user/question")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("token",token)
//                        .content(requestInfo))
//                .andDo(print())
//                .andReturn().getResponse().getContentAsString();
//        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void updateQuestion() throws Exception {
        List<Question> questionList = new ArrayList<>(1);
        Question question = new Question();
        question.setQuestionId(1001);
        question.setQuestionAnswer("test");
        question.setQuestionName("2018test");
        question.setQuestionUserId(1);
        questionList.add(question);
        String requestInfo = JSONObject.toJSONString(questionList);
        String responseString = mockMvc.perform(
                put("/user/question")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .content(requestInfo))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void questionCheck() throws Exception {
//        List<Map<String,Object>> questionList = new ArrayList<>(1);
//        Map<String,Object> question = new HashMap<>(1);
//        question.put("questionId","1001");
//        question.put("questionAnswer","test");
//        question.put("questionName","2018test");
//        question.put("questionUserId","1");
//        questionList.add(question);
//        String requestInfo = JSONObject.toJSONString(questionList);
//        String responseString = mockMvc.perform(
//                post("/user/questioncheck")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("token",token)
//                        .content(requestInfo))
//                .andDo(print())
//                .andReturn().getResponse().getContentAsString();
//        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void infoCheck() throws Exception {
        User checkUser = new User();
        checkUser.setUserLoginName("test");
        checkUser.setUserName("launcher");
        checkUser.setUserCardId("413001199307271823");
        checkUser.setUserPhone("1478523396");
        String requestInfo = JSONObject.toJSONString(checkUser);
        String responseString = mockMvc.perform(
                post("/user/infocheck")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .content(requestInfo))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

}