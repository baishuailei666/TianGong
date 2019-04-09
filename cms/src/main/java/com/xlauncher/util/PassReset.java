package com.xlauncher.util;

import com.xlauncher.dao.UserDao;
import com.xlauncher.entity.User;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "passReset")
public class PassReset {

    @Autowired
    UserDao userDao;

    public int passwordReset(int userId){
        User user = userDao.getToDoUserById(userId);
        /**设定新密码的规则并MD5加密*/
        user.setUserPassword( MD5Util.getResult(PinyinUtil.convert(user.getUserName(), HanyuPinyinCaseType.LOWERCASE, false)+user.getUserCardId().substring(user.getUserCardId().length()-4,user.getUserCardId().length())));
        user.setUserQuestion(-1);
        return userDao.updateUser(user);
    }

}
