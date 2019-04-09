package com.xlauncher.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xlauncher.dao.UserDao;
import com.xlauncher.entity.User;

import javax.servlet.http.HttpServletResponse;

/**
 * 检验token的工具类
 * @author yemao
 * @date 2018-03-08
 */
@Component
public class CheckToken {
    @Autowired
    UserDao userDao;

    /**
     * 判断当前用户的token是否匹配
     *
     * @param token 用户登录的令牌
     * @return 该令牌是否有对应用户
     */
    public User checkToken(String token){
        User user = this.userDao.checkToken(token);
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }
}
