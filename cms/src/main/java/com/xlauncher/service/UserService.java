package com.xlauncher.service;

import com.xlauncher.entity.Question;
import com.xlauncher.entity.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
@Service
public interface UserService {

    /**
     * 用户登录
     * @param userLogin 用户登录信息
     * @param request
     * @param response
     * @param responseMap
     * @return 模糊查询结果
     */
    Map<String,Object> login(Map<String,Object> userLogin, HttpServletRequest request
            , HttpServletResponse response,Map<String,Object> responseMap);

    /**
     * 添加一个新的用户信息(用户注册)
     * @param user 需要添加的用户信息
     * @return 添加操作影响的用户数据库的行数
     */
    int register(User user);

    /**
     * 通过待办表获取用户信息
     * @param todoId 待办编号
     * @param token 令牌
     * @return 用户信息
     */
    User registerInfo(int todoId,String token);

    /**
     * 注册通过
     * @param user 注册的用户信息
     * @param token 令牌
     * @return 更新操作影响的数据库的行数
     */
    int registerConfirm(Map<String,Object> user,String token);

    /**
     * 用户列表页
     * @param userName 用户姓名
     * @param roleName 用户角色
     * @param userCode 用户编号
     * @param number 页码
     * @param token 令牌
     * @return 满足条件的用户列表
     */
    List<User> listUser(String userName,String roleName,String userCode,int number,String token);

    /**
     * 用户记录数
     * @param userName 用户姓名
     * @param roleName 用户角色
     * @param userCode 用户编号
     * @return 满足条件的用户记录数
     */
    int countPage(String userName,String roleName,String userCode,String token);

    /**
     * 个人信息管理页面
     * @return 模糊查询结果
     */
    Map<String,Object> userInfo(String token);

    /**
     * 提供发送邮件的用户信息
     * @param token
     * @param userName
     * @return
     */
    Map<String,Object> mailUser(String token, String userName);

    User getUserById(int userId,String token);

    /**
     * 根据用户名来查询用户
     * @param userLoginName 用户名
     * @return 模糊查询结果
     */
    void getUserByLoginName(String userLoginName);
    /**
     * 用户名查重
     * @param userLoginName 用户登录名
     * @return 已经存在返回1，未存在返回0
     */
    int countUserByLoginName(String userLoginName);

    /**
     * 用户证件信息查重
     * @param userCardId 用户证件信息
     * @return 已经存在返回1，未存在返回0
     */
    int countUserCardId(String userCardId);

    /**
     * 用户编号查重
     * @param userCode 用户编号
     * @param userId
     * @return 用户编号
     */
    int countUserCode(String userCode, String userId);

    /**
     * 添加一个新的用户信息(管理员添加)
     * @param userInfo 需要添加的用户信息
     * @param token 令牌
     * @return 添加操作影响的用户数据库的行数
     */
    int insertUser(Map<String,Object> userInfo,String token);

    /**
     * 更新用户信息
     * @param user 需要更新的用户信息
     * @return 更新操作影响的数据库的行数
     */
    int updatePassword(Map<String,Object> user);

    /**
     * 更新用户信息
     * @param user 需要更新的用户信息
     * @param token 令牌
     * @return 更新操作影响的数据库的行数
     */
    int updateUser(Map<String,Object> user,String token);

    /**
     * 查询用户的角色列表
     * @param userId 用户编号
     * @param token 令牌
     * @return 角色编号列表
     */
    List<Integer> assignList(int userId,String token);

    /**
     * 删除用户
     * @param userId 用户编号
     * @param token 令牌
     * @return 删除操作影响的数据库行数
     */
    int deleteUser(int userId,String token);

    /**
     * 填写用户的密保问题
     * @param question 密保信息
     * @param token 令牌
     * @return 添加密保操作影响的数据库行数
     */
    int insertQuestion(List<Question> question,String token);

    /**
     * 删除用户的密保信息(系统管理员重置密码时需要删除所有该用户的密保信息)
     * @param userId 密保问题对应的编号
     * @param token 令牌
     * @return 删除密保操作影响的数据库行数
     */
    int deleteQuestionByUser(int userId,String token);

    /**
     * 更新用户的密保信息
     * @param question 用户的密保信息
     * @param token 令牌
     * @return 更新密保操作影响的数据库行数
     */
    int updateQuestion(List<Question> question,String token);

    /**
     * 验证用户信息，用于密码重置
     * @param userId 用户信息
     * @return 完全匹配返回1，表示验证成功，反之返回0
     */
    List<Question> listQuestion(int userId);

    int questionCheck(List question);

    int infoCheck(Map<String,Object> userInfo);

    int checkInfo(Map<String,Object> userInfo);

    /**
     * token过期 销毁
     * @param token
     * @return
     * @throws SQLException
     */
    int deleteToken(String token) throws SQLException ;

    /**
     * 根据userLoginName获得token
     * @param userForCheck
     * @param request
     * @param response
     * @return
     * @throws SQLException
     */
    void getToken(Map<String,String> userForCheck, HttpServletRequest request, HttpServletResponse response) throws SQLException;

}
