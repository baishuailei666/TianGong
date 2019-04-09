package com.xlauncher.util;

import com.xlauncher.dao.TodoDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TodoUtil {

    @Autowired
    TodoDao todoDao;
    private Logger logger = Logger.getLogger(TodoUtil.class);

    public void registerTodo(int userId){
        logger.info("TodoUtil-注册确认" + DatetimeUtil.getDate(System.currentTimeMillis()));
        todoDao.insertTodo(userId,"注册确认",DatetimeUtil.getDate(System.currentTimeMillis()));
    }

    public void resetCodeTodo(int userId){
        logger.info("TodoUtil-密码重置, userId：" + userId);
        System.out.println(todoDao.checkRepeat(userId,"密码重置"));
        if (todoDao.checkRepeat(userId,"密码重置")==0) {
            todoDao.insertTodo(userId, "密码重置", DatetimeUtil.getDate(System.currentTimeMillis()));
        }
    }

}
