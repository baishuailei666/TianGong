package com.xlauncher.web;

import com.alibaba.fastjson.JSONObject;
import com.xlauncher.entity.Service;
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
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"classpath:spring-mybatis.xml", "classpath*:spring-mvc.xml"})
public class ServicePropertiesControllerTest {
    @Autowired
    WebApplicationContext context;
    MockMvc mockMvc;
    @Before
    public void before() {
        //可以对所有的controller来进行测试
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        //仅仅对单个Controller来进行测试
//        mockMvc = MockMvcBuilders.standaloneSetup(new ServicePropertiesController()).build();
    }
    @Test
    public void setProperties() throws Exception {
        Service serviceInfo = new Service();
        serviceInfo.setSerId(101);
        serviceInfo.setSerIp("8.11.0.13");
        serviceInfo.setSerName("es");
        serviceInfo.setSerPort("8080");
        String requestInfo = JSONObject.toJSONString(serviceInfo);
        String responseS = mockMvc.perform(put("/service")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestInfo))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("-------- 返回的json --------" + responseS);

    }

}