package com.xlauncher.web;

import com.alibaba.fastjson.JSONObject;
import com.xlauncher.dao.UserDao;
import com.xlauncher.entity.Device;
import com.xlauncher.entity.User;
import com.xlauncher.util.DatetimeUtil;
import com.xlauncher.util.Initialise;
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
 * DeviceControllerTest
 * @author baishuailei
 * @since 2018-05-8
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:applicationContext.xml","classpath:spring-mvc.xml"})
@Transactional
public class DeviceControllerTest {
    @Autowired
    WebApplicationContext context;
    @Autowired
    UserDao userDao;
    private static Device device;
    private static String token = "token is for test";
    private MockMvc mockMvc;
    @Before
    public void before() {
        //可以对所有的controller来进行测试
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
//        mockMvc = MockMvcBuilders.standaloneSetup(new DeviceController()).build();
        User user = new User();
        user.setUserName("Test Case");
        user.setUserLoginName("DeviceControllerTest");
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
        device = new Device();
        device.setDeviceId(Initialise.initialise());
        device.setDeviceName("DeviceControllerTest");
        device.setDeviceIp("8.11.0.1");
        device.setDevicePort("80803");
        device.setDeviceUserName("test web");
        device.setDeviceUserPassword("125080");
        device.setDeviceModel("201850");
        device.setDeviceType("dvr");
        device.setDeviceChannelCount(200);
        device.setDeviceOrgId("2");
        device.setDeviceDivisionId(BigInteger.valueOf(1));
        device.setDeviceCreateTime(DatetimeUtil.getDate(System.currentTimeMillis()));
    }
    @Test
    public void addDevice() throws Exception {
        String requestInfo = JSONObject.toJSONString(device);
        String responseString = mockMvc.perform(
                post("/device")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .content(requestInfo))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void deleteDevice() throws Exception {
        String responseString = mockMvc.perform(
                delete("/device/230f06945c584e6ba400682c94ec1892")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void updateDevice() throws Exception {
        Device deviceUp = new Device();
        deviceUp.setDeviceId("ec19f012ce8044a0ad3a0fb2b1cf90b1");
        deviceUp.setDeviceUpdateTime("2018-05-08 17:32:25");
        String requestInfo = JSONObject.toJSONString(deviceUp);
        String responseString = mockMvc.perform(
                put("/device")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .content(requestInfo))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void updateDeviceStatus() throws Exception {
        Device deviceUpRuntime = new Device();
        deviceUpRuntime.setDeviceId("ec19f012ce8044a0ad3a0fb2b1cf90b1");
        deviceUpRuntime.setDeviceFaultCode(1);
        deviceUpRuntime.setDeviceFaultTime("2018-05-30 17:32:25");
        String requestInfo = JSONObject.toJSONString(deviceUpRuntime);
        String responseString = mockMvc.perform(
                put("/device/runTimeStatus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .content(requestInfo))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void getDeviceStatus() throws Exception {
        String responseString = mockMvc.perform(
                get("/device/page/getStatus/{number}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .param("upStartTime","undefined")
                        .param("lowStartTime","undefined")
                        .param("deviceType","undefined")
                        .param("deviceName","undefined")
                        .param("deviceFault","undefined")
                        .param("deviceUserName","undefined")
                        .param("deviceStatus","undefined"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void getCountDeviceStatus() throws Exception {
        String responseString = mockMvc.perform(
                get("/device/countStatus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .param("upStartTime","undefined")
                        .param("lowStartTime","undefined")
                        .param("deviceType","undefined")
                        .param("deviceName","undefined")
                        .param("deviceFault","undefined")
                        .param("deviceUserName","undefined")
                        .param("deviceStatus","undefined"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }
    @Test
    public void getDeviceFault() throws Exception {
        String responseString = mockMvc.perform(
                get("/device/fault")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void getDeviceFaultTypeAndCount() throws Exception {
        String responseString = mockMvc.perform(
                get("/device/faultCount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void activeDevice() throws Exception {
        String responseString = mockMvc.perform(
                put("/device/activeDevice/{deviceId}","9287b49ed3da4004a19488c30a1aa9ee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void disableDevice() throws Exception {
        String responseString = mockMvc.perform(
                put("/device/disableDevice/{deviceId}","9287b49ed3da4004a19488c30a1aa9ee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }
    @Test
    public void queryDevice() throws Exception {
        // test 接口
        String responseString = mockMvc.perform(
                get("/device/page/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .param("deviceType","undefined")
                        .param("deviceFaultCode","0")
                        .param("deviceStatus","undefined")
                        .param("orgName","undefined")
                        .param("adminDivisionName","undefined"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void pageCount() throws Exception {
        // test 接口
        String responseString = mockMvc.perform(
                get("/device/count")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token)
                        .param("deviceType","undefined")
                        .param("deviceFaultCode","0")
                        .param("deviceStatus","undefined")
                        .param("orgName","undefined")
                        .param("adminDivisionName","undefined"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }
    @Test
    public void queryDeviceByDeviceId() throws Exception {
        // test 接口
        String responseString = mockMvc.perform(
                get("/device/ec19f012ce8044a0ad3a0fb2b1cf90b1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",token))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }
}