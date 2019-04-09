package com.xlauncher.service;

import com.xlauncher.entity.Todo;

import java.util.List;

public interface TodoService {

    /**
     * 管理员查看所有注册确认请求
     * @param number 页码
     * @param token 令牌
     * @return 默认为未处理的待办事件列表
     */
    List<Todo> listRegisterTodo(int number,String token);

    /**
     * 所有注册确认请求的数量，用于分页
     * @param token 令牌
     * @return 注册请求的数据库行数
     */
    int countRegisterTodo(String token);

    /**
     * 管理员分页查询所有密码重置请求
     * @param number 页码
     * @param token 令牌
     * @return  一页十个按时间排序的密码重置请求详细信息
     */
    List<Todo> listResetCodeTode(int number,String token);

    /**
     * 所有密码重置请求的数量，用于分页
     * @param token 令牌
     * @return 密码重置请求的数据库行数
     */
    int countResetCodetode(String token);

    /**
     * 重置用户密码
     * @param todoId 编号
     * @param token 令牌
     * @return 重置成功返回1，失败返回0
     */
    int resetCode(int todoId,String token);

    /**
     * 管理员拒绝处理该待办事件
     * @param todoId 待办事件编号
     * @param token 令牌
     * @return 用户所有角色的列表
     */
    int denyTodo(int todoId,String token);

}
