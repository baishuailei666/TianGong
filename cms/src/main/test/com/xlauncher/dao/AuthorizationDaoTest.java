package com.xlauncher.dao;

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
public class AuthorizationDaoTest {
    @Autowired
    AuthorizationDao authorizationDao;

    @Test
    public void insertAuthorization() throws Exception {
        authorizationDao.insertAuthorization(1,1);
    }

    @Test
    public void deleteAuthorization() throws Exception {
        authorizationDao.deleteAuthorization(1);
    }

    @Test
    public void deleteAuthorizationByRole() throws Exception {
        authorizationDao.deleteAuthorizationByRole(1);
    }

    @Test
    public void listAuthorization() throws Exception {
        authorizationDao.listAuthorization(1);
    }

}