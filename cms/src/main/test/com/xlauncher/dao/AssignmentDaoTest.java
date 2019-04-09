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
public class AssignmentDaoTest {
    @Autowired
    AssignmentDao assignmentDao;

    @Test
    public void insertAssign() throws Exception {
        assignmentDao.insertAssign(1,1);
    }

    @Test
    public void deleteAssign() throws Exception {
        assignmentDao.deleteAssign(1);
    }

    @Test
    public void listAssign() throws Exception {
        assignmentDao.listAssign(1);
    }

    @Test
    public void deleteAssignByUser() throws Exception {
        assignmentDao.deleteAssignByUser(1);
    }

    @Test
    public void deleteAssignByRole() throws Exception {
        assignmentDao.deleteAssignByRole(1);
    }

}