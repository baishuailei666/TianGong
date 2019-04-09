package com.xlauncher.service.impl;

import com.xlauncher.dao.QuestionDao;
import com.xlauncher.dao.TodoDao;
import com.xlauncher.dao.UserDao;
import com.xlauncher.entity.Todo;
import com.xlauncher.entity.User;
import com.xlauncher.service.TodoService;
import com.xlauncher.util.CheckToken;
import com.xlauncher.util.OperationLogUtil;
import com.xlauncher.util.PassReset;
import com.xlauncher.util.QuestionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoServiceImp implements TodoService{

    @Autowired
    private TodoDao todoDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PassReset passReset;

    @Autowired
    QuestionStatus questionStatus;

    @Autowired
    QuestionDao questionDao;
    @Autowired
    private OperationLogUtil logUtil;
    @Autowired
    private CheckToken checkToken;
    private static final String MODULE = "我的待办";
    private static final String SYSTEM_MODULE = "系统管理";
    private static final String CATEGORY = "运营面";

    /**
     * 获取注册待办列表
     * @param number 页码
     * @param token  令牌
     * @return
     */
    @Override
    public List<Todo> listRegisterTodo(int number,String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询",MODULE,SYSTEM_MODULE,"查询注册的待办信息",CATEGORY);
        List<Todo> todos = todoDao.listRegisterTodo((number-1)*10);
        if (todos.size()!=0){
            for (Todo todo :
                    todos) {
                todo.setUser(userDao.getToDoUserById(todo.getTodoUserId()));
            }
        }
        return todos;
    }

    /**
     * 获取注册待办count数
     * @param token 令牌
     * @return
     */
    @Override
    public int countRegisterTodo(String token) {
        return todoDao.countRegisterTodo();
    }

    /**
     * 获取密码重置列表
     * @param number 页码
     * @param token  令牌
     * @return
     */
    @Override
    public List<Todo> listResetCodeTode(int number, String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"查询",MODULE,SYSTEM_MODULE,"查询密码重置的待办信息",CATEGORY);
        List<Todo> todos = todoDao.listResetCodeTode((number-1)*10);
        if (todos.size()!=0){
            for (Todo todo :
                    todos) {
                todo.setUser(userDao.getToDoUserById(todo.getTodoUserId()));
            }
        }
        return todos;
    }

    /**
     * 获取密码重置count数
     * @param token 令牌
     * @return
     */
    @Override
    public int countResetCodetode(String token) {
        return todoDao.countResetCodetode();
    }

    /**
     * 重置密码
     * @param todoId
     * @param token  令牌
     * @return
     */
    @Override
    public int resetCode(int todoId,String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"维护",MODULE,SYSTEM_MODULE,"用户重置密码确认",CATEGORY);
        todoDao.agreeTodo(todoId, checkToken.checkToken(token).getUserName());
        questionStatus.setStatusNegative(todoDao.getUserById(todoId));
        questionDao.deleteQuestionByUser(todoDao.getUserById(todoId));
        return passReset.passwordReset(todoDao.getUserById(todoId));
    }

    /**
     * 拒绝用户注册
     * @param todoId 待办事件编号
     * @param token  令牌
     * @return
     */
    @Override
    public int denyTodo(int todoId, String token) {
        logUtil.opLog(checkToken.checkToken(token).getUserName(),"维护",MODULE,SYSTEM_MODULE,"用户注册确认",CATEGORY);
        User user = userDao.getToDoUserById(todoDao.getUserById(todoId));
        if (user!=null) {
            userDao.deleteUser(user.getUserId());
        }
        return todoDao.denyTodo(todoId, checkToken.checkToken(token).getUserName());
    }
}
