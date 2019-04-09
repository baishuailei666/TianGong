package com.xlauncher.dao;

import com.xlauncher.entity.Todo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TodoDao {

    /**
     * 添加待办事件
     * @param userId 提交待办事件的用户编号
     * @param todoNote 待办事件的类型
     * @param todoTime 待办事件提交的事件
     * @return 添加操作影响的数据库行数
     */
    int insertTodo(@Param("userId") int userId, @Param("todoNote") String todoNote,@Param("todoTime") String todoTime);


    /**
     * 管理员查看所有注册确认请求(需要注意的是查询的时候要查询出用户的具体信息）
     * @param number 页码
     * @return 默认为未处理的待办事件列表
     */
    List<Todo> listRegisterTodo(@Param("number") int number);

    /**
     * 所有注册确认请求的数量，用于分页
     * @return 注册请求的数据库行数
     */
    int countRegisterTodo();

    /**
     * 管理员分页查询所有密码重置请求
     * @param number 页码
     * @return  一页十个按时间排序的密码重置请求详细信息
     */
    List<Todo> listResetCodeTode(@Param("number") int number);

    /**
     * 所有密码重置请求的数量，用于分页
     * @return 密码重置请求的数据库行数
     */
    int countResetCodetode();

    /**
     * 用户被删除待办信息同时删除
     * @param userId
     * @return
     */
    int deleteUserTodo(int userId);

    /**
     * 管理员同意处理该待办事件
     * @param todoId 待办事件编号
     * @param todoHandler 处理人
     * @return 用户所有角色的列表
     */
    int agreeTodo(@Param("todoId") int todoId,@Param("todoHandler") String todoHandler);

    /**
     * 管理员拒绝处理该待办事件
     * @param todoId 待办事件编号
     * @param todoHandler 处理人
     * @return 用户所有角色的列表
     */
    int denyTodo(@Param("todoId") int todoId,@Param("todoHandler") String todoHandler);

    /**
     * 用编号获取用户编号
     * @param todoId 待办编号
     * @return 用户编号
     */
    int getUserById(int todoId);

    /**
     * 提供接口显示用户当前注册确认状态
     * @param userId
     * @return
     */
    Todo getStatusByUserId(int userId);
    /**
     * 检查重复的待办请求
     * @param userId 用户编号
     * @param todoNote 请求类型
     * @return 1为重复，0为未重复
     */
    int checkRepeat(@Param("userId") int userId, @Param("todoNote") String todoNote);
}
