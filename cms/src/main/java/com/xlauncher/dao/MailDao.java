package com.xlauncher.dao;

import com.xlauncher.entity.MailR;
import com.xlauncher.entity.MailS;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public interface MailDao {

    /**
     * 编写邮件
     * @param mailS 邮件信息
     * @return 添加成功为1，失败为0
     */
    int insertMailS(MailS mailS);

    int insertMailR(MailR mailR);

    /**
     * 删除邮件
     * @param mailId 邮件编号
     * @return 删除成功为1，失败为0
     */
    int deleteMailS(int mailId);
    int deleteMailR(int mailId);

    /**
     * 获取
     * @param mailId
     * @return
     */
    MailS queryMailS(int mailId);

    MailR queryMailR(int mailId);

    List<MailR> queryMailRList(@Param("mailReceiver") String mailReceiver, @Param("page") int page);

    int countMailR(String mailReceiver);
    /**
     *
     * @param mailSender
     * @param page
     * @return
     */
    List<MailS> queryMailSList(@Param("mailSender") String mailSender, @Param("page") int page);
    /**通过noticeDao的添加详情和获取详情*/

    int updateMail(int mailId);

    int countMailS(String mailSender);

    int unread(String mailReceiver);

    List<MailR> overview(String mailReceiver);
}
