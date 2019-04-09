package com.xlauncher.dao;

import com.xlauncher.entity.Question;
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
public class QuestionDaoTest {
    @Autowired
    QuestionDao questionDao;

    @Test
    public void insertQuestion() throws Exception {
        questionDao.insertQuestion(new Question());
    }

    @Test
    public void deleteQuestionByUser() throws Exception {
        questionDao.deleteQuestionByUser(1);
    }

    @Test
    public void deleteQuestion() throws Exception {
        questionDao.deleteQuestion(1);
    }

    @Test
    public void listQuestion() throws Exception {
        questionDao.listQuestion(1);
    }

    @Test
    public void getQuestionById() throws Exception {
        questionDao.getQuestionById(1);
    }

}