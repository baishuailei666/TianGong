package com.xlauncher.web;

import com.xlauncher.dao.UserDao;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * ExportExcelControllerTest
 * @author baishuailei
 * @since 2018-06-04
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:applicationContext.xml","classpath*:spring-mvc.xml"})
@Transactional
public class ExportExcelControllerTest {
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
        user.setUserLoginName("ExportExcelControllerTest");
        user.setUserPassword("123456");
        user.setUserPhone("1234567890");
        userDao.insertUser(user);
        User userUp = new User();
        userUp.setUserId(user.getUserId());
        userUp.setToken("token is for test");
        userDao.updateUser(userUp);
    }
    @Test
    public void alertLogExportExcel() throws Exception {
        String responseString = mockMvc.perform(
                get("/excel/alertLog")
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
    public void notCheckEventAlertExportExcel() throws Exception {
        String responseString = mockMvc.perform(
                get("/excel/notCheck")
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
    public void checkEventAlertExportExcel() throws Exception {
        String responseString = mockMvc.perform(
                get("/excel/check")
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
    public void operationExportExcel() throws Exception {
        String responseString = mockMvc.perform(
                get("/excel/operation")
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
    public void operatingExportExcel() throws Exception {
        String responseString = mockMvc.perform(
                get("/excel/operating")
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
    public void eventExportExcel() throws Exception {
        String responseString = mockMvc.perform(
                get("/excel/event")
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

}