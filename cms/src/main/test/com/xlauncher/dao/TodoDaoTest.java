package com.xlauncher.dao;

import com.xlauncher.util.DatetimeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml","classpath:spring-*.xml"})
@Transactional
public class TodoDaoTest {
    @Autowired
    TodoDao todoDao;

    @Test
    public void insertTodo() throws Exception {
        todoDao.insertTodo(1,"测试", DatetimeUtil.getDate(System.currentTimeMillis()));
    }

    @Test
    public void listRegisterTodo() throws Exception {
        todoDao.listRegisterTodo(1);
    }

    @Test
    public void countRegisterTodo() throws Exception {
        todoDao.countRegisterTodo();
    }

    @Test
    public void listResetCodeTode() throws Exception {
        todoDao.listResetCodeTode(1);
    }

    @Test
    public void countResetCodetode() throws Exception {
        todoDao.countResetCodetode();
    }

    @Test
    public void agreeTodo() throws Exception {
        todoDao.agreeTodo(1,"测试人员");
    }

    @Test
    public void denyTodo() throws Exception {
        todoDao.denyTodo(1,"测试人员");
    }

    @Test
    public void getUserById() throws Exception {
        todoDao.getUserById(1);
    }

    @Test
    public void checkRepeat() throws Exception {
        todoDao.checkRepeat(1,"测试");
    }

}