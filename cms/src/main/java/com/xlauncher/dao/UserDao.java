package com.xlauncher.dao;

import com.xlauncher.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public interface UserDao {

    /**
     * 添加一个新的用户信息(注册或者管理员添加)
     * @param user 需要添加的用户信息
     * @return 添加操作影响的用户数据库的行数
     */
    int insertUser(User user);
    /**
     * 初始化一个超级管理员（服务启动时加载）
     * @param user 需要添加的用户信息
     * @return 添加操作影响的用户数据库的行数
     */
    int rootUser(User user);
    /**
     * 初始化用户 - 判断数据库是否被初始化
     * @return 添加操作影响的用户数据库的行数
     */
    List<User> rootCheck();
    /**
     * 根据id查询用户信息
     * @param userId 用户ID
     * @return 用户信息
     */
    User getUserById(int userId);

    /**
     * 根据id查询待办用户信息
     * @param userId 用户ID
     * @return 用户信息
     */
    User getToDoUserById(int userId);

    /**
     * 根据用户名来查询用户
     * @param userLoginName 用户名
     * @return 模糊查询结果
     */
    User getUserByLoginName(String userLoginName);

    /**
     * 用户名查重
     * @param userLoginName 用户名
     * @return 模糊查询结果
     */
    int countUserByLoginName(String userLoginName);
    /**
     * 用户证件信息查重
     * @param userCardId 用户证件信息
     * @return 已经存在返回1，未存在返回0
     */
    int countUserCardId(String userCardId);

    /**
     * 获取用户编号用于前端校验
     * @param userCode 用户编号
     * @param userId
     * @return 用户编号
     */
    int countUserCode(@Param("userCode") String userCode, @Param("userId") String userId);

    /**
     * 验证用户信息，用于密码重置
     * @param userLoginName 用户登录名
     * @param userName 用户姓名
     * @param userCardId 用户电话
     * @param userPhone 用户身份证
     * @return 完全匹配返回1，表示验证成功，反之返回0
     */
    User userCheck(@Param("userLoginName") String userLoginName,@Param("userName") String userName,@Param("userCardId") String userCardId,@Param("userPhone") String userPhone);

    /**
     * 用户列表页
     * @param userName 用户姓名
     * @param userCode 用户编号
     * @param number 页码
     * @return 满足条件的用户列表
     */
    List<User> listUser(@Param("userName") String userName, @Param("userCode") String userCode,@Param("number") int number);

    /**
     * 用户记录数
     * @param userName 用户姓名
     * @param roleName 用户角色
     * @param userCode 用户编号
     * @return 满足条件的用户记录数
     */
    int countPage(@Param("userName") String userName,@Param("roleName") String roleName,@Param("userCode") String userCode);

    /**
     * 根据用户编号删除一个用户的信息
     * @param userId 用户ID
     * @return 删除操作影响的数据库的行数
     */
    int deleteUser(int userId);

    /**
     * 根据组织获取userId
     * @param orgId
     * @return
     */
    List<User> getUserByOrg(String orgId);
    /**
     * 更新用户信息
     * @param user 需要更新的用户信息
     * @return 更新操作影响的数据库的行数
     */
    int updateUser(User user);

    /**
     * 验证用户的token是否正确
     * @param token 令牌
     * @return  该token是否存在,存在则取出该用户名
     */
    User checkToken(String token);

    /**
     * 提供发送邮件用户信息
     * @param userName
     * @return
     */
    List<User> mailUser(@Param("userName") String userName);
    /**
     * 查看个人信息
     * @param token 令牌
     * @return
     */
    User checkUser(String token);

    /**
     * 用户注册后登录查看个人信息
     * @param token
     * @return
     */
    User checkInitUser(String token);
    /**
     * 销毁token
     * @param token 令牌
     * @return 删除操作影响的数据库行数
     */
    int deleteToken(String token);

    /**
     * 根据userLoginName获得token
     * @param userLoginName 登录名
     * @return 用户所对应的令牌
     */
    String getToken(String userLoginName);

}
