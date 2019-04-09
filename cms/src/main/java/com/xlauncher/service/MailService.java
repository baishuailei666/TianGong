package com.xlauncher.service;

import com.xlauncher.entity.MailR;
import com.xlauncher.entity.MailS;

import java.util.List;
import java.util.Map;

public interface MailService {

    /**
     * 编写邮件
     * @param mailS 邮件信息
     * @return 添加成功为1，失败为0
     */
    int insertMail(Map<String,Object> mailInfo,String token);

    /**
     * 删除邮件
     * @param mailId 邮件编号
     * @return 删除成功为1，失败为0
     */
    int deleteMailS(int mailId,String token);
    int deleteMailR(int mailId,String token);

    /**
     * 获取
     * @param mailId
     * @return
     */
    MailS queryMailS(int mailId,String token);

    MailR queryMailR(int mailId,String token);

    List<MailR> queryMailR(String token, int page);
    int countMailR(String token);

    List<MailS> queryMailS(String token, int page);
    int countMailS(String token);
    /**通过noticeDao的添加详情和获取详情*/

    int unread(String token);

    List<MailR> overview(String token);
}
