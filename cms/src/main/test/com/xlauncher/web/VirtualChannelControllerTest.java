package com.xlauncher.web;

import com.alibaba.fastjson.JSONObject;
import com.xlauncher.dao.VirtualDeviceDao;
import com.xlauncher.entity.VirtualChannel;
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
 * VirtualChannelControllerTest
 * @author baishuailei
 * @since 2018-05-09
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:applicationContext.xml","classpath*:spring-mvc.xml"})
@Transactional
public class VirtualChannelControllerTest {
    @Autowired
    WebApplicationContext context;
    @Autowired
    VirtualDeviceDao virtualDeviceDao;
    private static VirtualChannel virtualChannel;
    private MockMvc mockMvc;
    @Before
    public void before() {
        //可以对所有的controller来进行测试
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        VirtualDevice virtualDevice = new VirtualDevice();
        virtualDevice.setVirtualDeviceId("234f06945c584e6ba400682c94ec1897");
        virtualDevice.setVirtualDeviceName("VirtualChannelServiceTest");
        virtualDevice.setVirtualDeviceIp("8.11.0.13");
        virtualDevice.setVirtualDevicePort("8080");
        virtualDevice.setVirtualDeviceUserName("VirtualDeviceServiceTest");
        virtualDevice.setVirtualDeviceUserPassword("20180509");
        virtualDevice.setVirtualDeviceModel("201805091656");
        virtualDevice.setVirtualDeviceType("dvr");
        virtualDevice.setVirtualDeviceChannelCount(150);
        virtualDevice.setVirtualDeviceOrgId(3);
        virtualDevice.setVirtualDeviceDivisionId(3);
        virtualDevice.setVirtualDeviceCreateTime("2018-05-09 16:50:25");
        virtualDeviceDao.insertVirtualDevice(virtualDevice);
    }
    @BeforeClass()
    public static void init() {
        virtualChannel = new VirtualChannel();
        virtualChannel.setVirtualChannelId(Initialise.initialise());
        virtualChannel.setVirtualChannelName("VirtualChannelControllerTest");
        virtualChannel.setVirtualChannelSourceId("234f06945c584e6ba400682c94ec1897");
        virtualChannel.setVirtualChannelNumber(150);
        virtualChannel.setVirtualChannelGridId("1002");
        virtualChannel.setVirtualChannelLocation("余杭镇联兴街道");
        virtualChannel.setVirtualChannelLongitude("120.19");
        virtualChannel.setVirtualChannelLatitude("30.16");
        virtualChannel.setVirtualChannelHandler("bai");
        virtualChannel.setVirtualChannelHandlerPhone("17826807083");
        virtualChannel.setVirtualChannelCreateTime("2018-05-09 16:50:25");

    }
    @Test
    public void addVirtualChannel() throws Exception {
        String requestInfo = JSONObject.toJSONString(virtualChannel);
        String responseString = mockMvc.perform(
                post("/virtualChannel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",123)
                        .content(requestInfo))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void updateVirtualChannel() throws Exception {
        VirtualChannel virtualChannelUp = new VirtualChannel();
        virtualChannelUp.setVirtualChannelId("e57f7011a73d4c9db76e56a2fe6b8b5d");
        virtualChannelUp.setVirtualChannelSourceId("234f06945c584e6ba400682c94ec1897");
        virtualChannelUp.setVirtualChannelUpdateTime("2018-05-09 14:35:19");
        String requestInfo = JSONObject.toJSONString(virtualChannelUp);
        String responseString = mockMvc.perform(
                put("/virtualChannel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",123)
                        .content(requestInfo))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void updateVirtualChannelStatus() throws Exception {
        VirtualChannel virtualChannelUpStatus = new VirtualChannel();
        virtualChannelUpStatus.setVirtualChannelId("e57f7011a73d4c9db76e56a2fe6b8b5d");
        virtualChannelUpStatus.setVirtualChannelPodStatus(2);
        virtualChannelUpStatus.setVirtualChannelStatus("停用");
        String requestInfo = JSONObject.toJSONString(virtualChannelUpStatus);
        String responseString = mockMvc.perform(
                put("/virtualChannel/virtualChannelStatus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",123)
                        .content(requestInfo))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void deleteVirtualChannel() throws Exception {
        String responseString = mockMvc.perform(
                delete("/virtualChannel/{virtualChannelId}","e57f7011a73d4c9db76e56a2fe6b8b5d")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",123))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void getVirtualChannelByVirtualChannelId() throws Exception {
        String responseString = mockMvc.perform(
                get("/virtualChannel/{virtualChannelId}","e57f7011a73d4c9db76e56a2fe6b8b5d")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",123))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void queryVirtualChannel() throws Exception {
        String responseString = mockMvc.perform(
                get("/virtualChannel/page/{number}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",123))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void queryVirtualChannelBySource() throws Exception {
        String responseString = mockMvc.perform(
                get("/virtualChannel/virtualSourceId/page/{number}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",123)
                        .param("virtualChannelSourceId","fd2731bb68f545a3b03ab945fed9cde7"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void pageCount() throws Exception {
        String responseString = mockMvc.perform(
                get("/virtualChannel/count")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",123)
                        .param("virtualChannelSourceId","fd2731bb68f545a3b03ab945fed9cde7"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

    @Test
    public void overview() throws Exception {
        String responseString = mockMvc.perform(
                get("/virtualChannel/overview")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("token",123))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("--------返回的json--------" + responseString);
    }

}