package com.xlauncher.web;

import com.alibaba.fastjson.JSONObject;
import com.xlauncher.dao.DeviceDao;
import com.xlauncher.dao.UserDao;
import com.xlauncher.entity.Channel;
import com.xlauncher.entity.Device;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * ChannelControllerTest
 * @author baishuailei
 * @since 2018-05-09
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:applicationContext.xml","classpath*:spring-mvc.xml"})
@Transactional
public class ChannelControllerTest {
    @Autowired
    WebApplicationContext context;
    @Autowired
    DeviceDao deviceDao;
    @Autowired
    UserDao userDao;
    private static Channel channel;
    private static String token = "token is for test";
    private MockMvc mockMvc;
    @Before
    public void before() {
        //可以对所有的controller来进行测试
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        Device device = new Device();
        device.setDeviceId("9a78ac2ddfca45f39f2ed056a9f6ac24");
        device.setDeviceName("test case service");
        device.setDeviceIp("8.11.0.1");
        device.setDevicePort("80803");
        device.setDeviceUserName("test service");
        device.setDeviceUserPassword("125080");
        device.setDeviceModel("201850");
        device.setDeviceType("dvr");
        device.setDeviceChannelCount(150);
        device.setDeviceOrgId("3");
        device.setDeviceDivisionId(BigInteger.valueOf(3));
        device.setDeviceCreateTime("2018-05-08 17:30:25");
        deviceDao.insertDevice(device);

        User user = new User();
        user.setUserName("Test Case");
        user.setUserLoginName("ChannelControllerTest");
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
        channel = new Channel();
        channel.setChannelId("65f9ba40acc7468fab653f9ddade43b0");
        channel.setChannelName("ChannelControllerTest 01");
        channel.setChannelSourceId("9a78ac2ddfca45f39f2ed056a9f6ac24");
        channel.setChannelNumber(1);
        channel.setChannelGridId("1002");
        channel.setChannelLocation("杭州市西湖区西湖街道");
        channel.setChannelLongitude("120.60");
        channel.setChannelLatitude("30.77");
        channel.setChannelHandler("bai");
        channel.setChannelHandlerPhone("17826807083");
        channel.setChannelCreateTime("2018-05-09 14:50:25");

    }
    @Test
    public void addChannel() throws Exception {
        String requestInfo = JSONObject.toJSONString(channel);
        String responseString = mockMvc.perform(
                post("/cms/channel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .content(requestInfo))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void updateChannel() throws Exception {
        Channel channelUp = new Channel();
        channelUp.setChannelId("bc17d8710c304d29aeb5066a294b31bd");
        channelUp.setChannelUpdateTime("2018-05-09 14:35:19");
        String requestInfo = JSONObject.toJSONString(channelUp);
        String responseString = mockMvc.perform(
                put("/channel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .content(requestInfo))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void updateChannelStatus() throws Exception {
        Channel channelUpStatus = new Channel();
        channelUpStatus.setChannelId("bc17d8710c304d29aeb5066a294b31bd");
        channelUpStatus.setChannelFaultCode(1);
        channelUpStatus.setChannelFault("信号丢失");
        channelUpStatus.setChannelFaultTime(DatetimeUtil.getDate(System.currentTimeMillis()));
        channelUpStatus.setChannelPodStatus(1);
        String requestInfo = JSONObject.toJSONString(channelUpStatus);
        String responseString = mockMvc.perform(
                put("/channel/channelStatus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .content(requestInfo))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void deleteChannel() throws Exception {
        String responseString = mockMvc.perform(
                delete("/channel/{channelId}","60f9ba40acc7468fab653f9ddade43b3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void getChannelByChannelId() throws Exception {
        String responseString = mockMvc.perform(
                get("/channel/{channelId}","60f9ba40acc7468fab653f9ddade43b3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void queryChannel() throws Exception {
        // test 接口
        String responseString = mockMvc.perform(
                get("/channel/page/{number}/{channelSourceId}",1,"undefined")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .param("channelName","undefined")
                        .param("channelLocation","undefined")
                        .param("channelStatus","undefined"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void pageCountChannel() throws Exception {
        String responseString = mockMvc.perform(
                get("/channel/countChannel/{channelSourceId}","9a78ac2ddfca45f39f2ed056a9f6ac24")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .param("channelName","undefined")
                        .param("channelLocation","undefined")
                        .param("channelStatus","undefined"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void overview() throws Exception {
        String responseString = mockMvc.perform(
                get("/channel/overview")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }
    @Test
    public void activeChannel() throws Exception {
        String responseString = mockMvc.perform(
                put("/channel/activeChannel/{channelId}","4978523fe0fd41b79a7acede12dd13e9")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void disableChannel() throws Exception {
        String responseString = mockMvc.perform(
                put("/channel/disableChannel/{channelId}","9287b49ed3da4004a19488c30a1aa9ee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void getChannelStatus() throws Exception {
        String responseString = mockMvc.perform(
                get("/channel/page/getStatus/{number}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .param("upStartTime","undefined")
                        .param("lowStartTime","undefined")
                        .param("channelHandler ","undefined")
                        .param("channelLocation ","undefined")
                        .param("channelName ","undefined"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void countChannelStatus() throws Exception {
        String responseString = mockMvc.perform(
                get("/channel/countStatus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .param("upStartTime","undefined")
                        .param("lowStartTime","undefined")
                        .param("channelHandler ","undefined")
                        .param("channelLocation ","undefined")
                        .param("channelName ","undefined"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void getChannelFault() throws Exception {
        String responseString = mockMvc.perform(
                get("/channel/fault")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void getChannelFaultTypeAndCount() throws Exception {
        String responseString = mockMvc.perform(
                get("/channel/faultCount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }
}