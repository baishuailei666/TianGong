package com.xlauncher.web;

import com.alibaba.fastjson.JSONObject;
import com.xlauncher.entity.VirtualDevice;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * VirtualDeviceControllerTest
 * @author baishuailei
 * @since 2018-05-09
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:applicationContext.xml","classpath*:spring-mvc.xml"})
@Transactional
public class VirtualDeviceControllerTest {
    @Autowired
    WebApplicationContext context;
    private static VirtualDevice virtualDevice;
    private MockMvc mockMvc;
    @Before
    public void before() {
        //可以对所有的controller来进行测试
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    @BeforeClass()
    public static void init() {
        virtualDevice = new VirtualDevice();
        virtualDevice.setVirtualDeviceId(Initialise.initialise());
        virtualDevice.setVirtualDeviceName("VirtualDeviceControllerTest");
        virtualDevice.setVirtualDeviceIp("8.11.0.13");
        virtualDevice.setVirtualDevicePort("8080");
        virtualDevice.setVirtualDeviceStatus("在用");
        virtualDevice.setVirtualDeviceUserName("VirtualDeviceControllerTest");
        virtualDevice.setVirtualDeviceUserPassword("20180509");
        virtualDevice.setVirtualDeviceModel("201805091656");
        virtualDevice.setVirtualDeviceType("dvr");
        virtualDevice.setVirtualDeviceChannelCount(150);
        virtualDevice.setVirtualDeviceOrgId(3);
        virtualDevice.setVirtualDeviceDivisionId(3);
        virtualDevice.setVirtualDeviceCreateTime("2018-05-09 17:50:25");
    }
    @Test
    public void addVirtualDevice() throws Exception {
        String requestInfo = JSONObject.toJSONString(virtualDevice);
        String responseString = mockMvc.perform(
                post("/virtualDevice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",123)
                        .content(requestInfo))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void deleteVirtualDevice() throws Exception {
        String responseString = mockMvc.perform(
                delete("/virtualDevice/{virtualDeviceId}","230f06945c584e6ba400682c94ec1892")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",123))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void updateVirtualDevice() throws Exception {
        VirtualDevice virtualDeviceUp = new VirtualDevice();
        virtualDeviceUp.setVirtualDeviceId("8d5265e1d9b74e548d58eff619a3c6d6");
        virtualDeviceUp.setVirtualDeviceUpdateTime("2018-05-09 16:49:50");
        String requestInfo = JSONObject.toJSONString(virtualDeviceUp);
        String responseString = mockMvc.perform(
                put("/virtualDevice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",123)
                        .content(requestInfo))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void updateRuntimeVirtualDevice() throws Exception {
        VirtualDevice virtualDeviceUpRuntime = new VirtualDevice();
        virtualDeviceUpRuntime.setVirtualDeviceId("8d5265e1d9b74e548d58eff619a3c6d6");
        virtualDeviceUpRuntime.setVirtualDeviceFaultCode(2);
        String requestInfo = JSONObject.toJSONString(virtualDeviceUpRuntime);
        String responseString = mockMvc.perform(
                put("/virtualDevice/runTimeStatus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",123)
                        .content(requestInfo))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void listVirtualDevice() throws Exception {
        String responseString = mockMvc.perform(
                get("/virtualDevice/page/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",123)
                        .param("virtualDeviceType","undefined")
                        .param("virtualDeviceFaultCode","0")
                        .param("virtualDeviceStatus","undefined")
                        .param("orgName","undefined")
                        .param("adminDivisionName","undefined"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void pageCount() throws Exception {
        String responseString = mockMvc.perform(
                get("/virtualDevice/count")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",123)
                        .param("virtualDeviceType","undefined")
                        .param("virtualDeviceFaultCode","0")
                        .param("virtualDeviceStatus","undefined")
                        .param("orgName","undefined")
                        .param("adminDivisionName","undefined"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

}