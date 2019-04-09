package com.xlauncher.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
/**
 * AdminDivisionDaoTest
 * @author baishuailei
 * @since 2018-07-25
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml","classpath:spring-*.xml"})
@Transactional
public class AdminDivisionDaoTest {
    @Autowired
    AdminDivisionDao adminDivisionDao;
    @Test
    public void listDivisionB() throws Exception {
        System.out.println(adminDivisionDao.listDivisionB());
    }

}