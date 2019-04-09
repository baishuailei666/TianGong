package com.xlauncher.util;

import com.xlauncher.dao.UserDao;
import com.xlauncher.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuestionStatus {

    @Autowired
    UserDao userDao;

    public void setStatusNegative(int userId){
        User user = userDao.getToDoUserById(userId);
        user.setUserQuestion(-2);
        userDao.updateUser(user);
    }

    public void setStatusPositive(int userId){
        User user = userDao.getToDoUserById(userId);
        user.setUserQuestion(1);
        userDao.updateUser(user);
    }

}
