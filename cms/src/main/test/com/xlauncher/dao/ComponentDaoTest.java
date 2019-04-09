package com.xlauncher.dao;

import com.xlauncher.entity.Component;
import com.xlauncher.util.DatetimeUtil;
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
 * ComponentDaoTest
 * @author baishuailei
 * @since 2018-05-10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml","classpath:spring-*.xml"})
@Transactional
public class ComponentDaoTest {
    @Autowired
    ComponentDao componentDao;
    private static Component component;
    @BeforeClass()
    public static void init() {
        component = new Component();
        component.setComponentAbbr("test");
        component.setComponentName("组件添加测试");
        component.setComponentIp("8.11.0.24");
        component.setComponentPort("8087");
        component.setComponentDescription("这是一个组件添加测试，2018-06-12");
        Map<String, Object> map = new HashMap<>(1);
        map.put("time", DatetimeUtil.getDate(System.currentTimeMillis()));
        map.put("data", component.getComponentDescription());
        component.setComponentConfiguration(map);
    }
    @Test
    public void addComponent() throws SQLException {
        System.out.println("addComponent: " + this.componentDao.addComponent(component));
    }

    @Test
    public void deleteComponent() throws SQLException {
        System.out.println("deleteComponent: " + this.componentDao.deleteComponent(1));
    }

    @Test
    public void updateComponent() throws SQLException {
        Component componentUp = new Component();
        componentUp.setId(2);
        componentUp.setComponentIp("8.16.0.41");
        componentUp.setComponentPort("8090");
        Map<String, Object> map = new HashMap<>(1);
        map.put("time", DatetimeUtil.getDate(System.currentTimeMillis()));
        map.put("data", component.getComponentDescription());
        componentUp.setComponentConfiguration(map);
        System.out.println("updateComponent: " + this.componentDao.updateComponent(componentUp));
    }

    @Test
    public void listAllComponentByNum() throws SQLException {
        System.out.println("listAllComponentByNum: " + this.componentDao.listAllComponentByNum(1,"undefined"));
    }

    @Test
    public void countPage() throws SQLException {
        System.out.println("countPage: " + this.componentDao.countPage("undefined"));
    }

    @Test
    public void listComponent() throws SQLException {
        System.out.println("listComponent: " + this.componentDao.listComponent());
    }

    @Test
    public void getComponentById() throws SQLException {
        System.out.println("getComponentById: " + this.componentDao.getComponentById(2));
    }

    @Test
    public void getComponentByAbbr() throws SQLException {
        System.out.println("getComponentByAbbr: " + this.componentDao.getComponentByAbbr("dim"));
    }

}