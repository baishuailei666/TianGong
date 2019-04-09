package com.xlauncher.service;

import com.xlauncher.dao.UserDao;
import com.xlauncher.entity.Component;
import com.xlauncher.entity.User;
import com.xlauncher.util.DatetimeUtil;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * ComponentServiceTest
 * @author baishuailei
 * @since 2018-05-10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml","classpath:spring-*.xml"})
@Transactional
public class ComponentServiceTest {
    @Autowired
    ComponentService componentService;
    @Autowired
    UserDao userDao;
    private static Component component;
    private static String token = "token is for test";
    @BeforeClass()
    public static void init() {
        component = new Component();
        component.setComponentAbbr("ComponentServiceTest");
        component.setComponentName("服务层测试组件");
        component.setComponentIp("8.11.0.13");
        component.setComponentPort("8080");
        component.setComponentDescription("这是一个服务层测试组件的组件，2018-05-10");
        Map<String, Object> map = new HashMap<>(1);
        map.put("time", DatetimeUtil.getDate(System.currentTimeMillis()));
        map.put("data", component.getComponentDescription());
        component.setComponentConfiguration(map);
    }
    @Before
    public void beTest() {
        User user = new User();
        user.setUserName("Test Case");
        user.setUserLoginName("ComponentServiceTest");
        user.setUserPassword("123456");
        user.setUserPhone("1234567890");
        userDao.insertUser(user);
        User userUp = new User();
        userUp.setUserId(user.getUserId());
        userUp.setToken("token is for test");
        userDao.updateUser(userUp);
    }
    @Test
    public void addComponent() throws SQLException {
        System.out.println("addComponent: " + this.componentService.addComponent(component, token));
    }

    @Test
    public void deleteComponent() throws SQLException {
        System.out.println("deleteComponent: " + this.componentService.deleteComponent(1, token));
    }

    @Test
    public void updateComponent() throws SQLException {
        Component componentUp = new Component();
        componentUp.setId(4);
        componentUp.setComponentAbbr("es");
        componentUp.setComponentIp("8.16.0.41");
        componentUp.setComponentPort("8090");
        Map<String, Object> map = new HashMap<>(1);
        map.put("time", DatetimeUtil.getDate(System.currentTimeMillis()));
        map.put("data", component.getComponentDescription());
        component.setComponentConfiguration(map);
        System.out.println("updateComponent: " + this.componentService.updateComponent(componentUp, token));
    }

    @Test
    public void listAllComponentByNum() throws SQLException {
        System.out.println("listAllComponentByNum: " + this.componentService.listAllComponentByNum(1,"undefined", token));
    }

    @Test
    public void countPage() throws SQLException {
        System.out.println("countPage: " + this.componentService.countPage("undefined"));
    }

    @Test
    public void listComponent() throws SQLException {
        System.out.println("listComponent: " + this.componentService.listComponent());
    }

    @Test
    public void getComponentById() throws SQLException {
        System.out.println("getComponentById: " + this.componentService.getComponentById(1, token));
    }

}