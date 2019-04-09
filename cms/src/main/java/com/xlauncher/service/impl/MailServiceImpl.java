package com.xlauncher.service.impl;

import com.xlauncher.dao.MailDao;
import com.xlauncher.dao.NoticeDao;
import com.xlauncher.entity.Info;
import com.xlauncher.entity.MailR;
import com.xlauncher.entity.MailS;
import com.xlauncher.service.MailService;
import com.xlauncher.util.CheckInfoCount;
import com.xlauncher.util.CheckToken;
import com.xlauncher.util.DatetimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class MailServiceImpl implements MailService{

    @Autowired
    MailDao mailDao;

    @Autowired
    NoticeDao noticeDao;

    @Autowired
    CheckToken checkToken;

    @Autowired
    CheckInfoCount checkInfoCount;

    @Override
    public int insertMail(Map<String,Object> mailInfo, String token) {
        if (checkToken.checkToken(token)!=null) {
            Info info = new Info();
            info.setInfoContent((String) mailInfo.get("infoContent"));
            noticeDao.insertNoticeDetail(info);
            MailS mailS = new MailS();
            MailR mailR = new MailR();
            mailS.setMailTitle((String) mailInfo.get("mailTitle"));
            mailR.setMailTitle((String) mailInfo.get("mailTitle"));
            mailS.setMailCreateTime(DatetimeUtil.getDate(System.currentTimeMillis()));
            mailR.setMailCreateTime(DatetimeUtil.getDate(System.currentTimeMillis()));
            mailS.setMailSender(checkToken.checkToken(token).getUserLoginName());
            mailR.setMailSender(checkToken.checkToken(token).getUserLoginName());
            mailS.setMailStatus(1);
            mailR.setMailStatus(-1);
            mailS.setMailInfoId(info.getInfoId());
            mailR.setMailInfoId(info.getInfoId());
            List<String> receiverList = (List<String>) mailInfo.get("mailReceiver");
            String mailReceivers ="";
            if (receiverList.size()!=0){
                for (String receiver:
                        receiverList) {
                    mailReceivers=mailReceivers+receiver+",";
                }
            }
            mailS.setMailReceiver(mailReceivers.substring(0, mailReceivers.length() - 1));
            mailR.setMailReceivers(mailReceivers.substring(0, mailReceivers.length() - 1));
            mailDao.insertMailS(mailS);
            for (String user:
                    (List<String>)mailInfo.get("mailReceiver")) {
                mailR.setMailReceiver(user);
                mailDao.insertMailR(mailR);
            }
            return 1;
        }
        return 0;
    }

    @Override
    public int deleteMailS(int mailId, String token) {
        MailS mailS = mailDao.queryMailS(mailId);
        mailDao.deleteMailS(mailId);
        checkInfoCount.checkInfoCount(mailS.getMailInfoId());
        return 1;
    }

    @Override
    public int deleteMailR(int mailId, String token) {
        MailR mailR = mailDao.queryMailR(mailId);
        mailDao.deleteMailR(mailId);
        checkInfoCount.checkInfoCount(mailR.getMailInfoId());
        return 0;
    }

    @Override
    public MailS queryMailS(int mailId, String token) {
        MailS mailS = mailDao.queryMailS(mailId);
        mailS.setMailContent(noticeDao.queryNoticeDetail(mailS.getMailInfoId()));
        return mailS;
    }

    @Override
    public MailR queryMailR(int mailId, String token) {
        MailR mailR = mailDao.queryMailR(mailId);
        mailR.setMailContent(noticeDao.queryNoticeDetail(mailR.getMailInfoId()));
        mailDao.updateMail(mailId);
        return mailR;
    }

    @Override
    public List<MailR> queryMailR(String token, int page) {
        return mailDao.queryMailRList(checkToken.checkToken(token).getUserLoginName(),(page-1)*10);
    }

    @Override
    public int countMailR(String token) {
        return mailDao.countMailR(checkToken.checkToken(token).getUserLoginName());
    }

    @Override
    public List<MailS> queryMailS(String token, int page) {
        return mailDao.queryMailSList(checkToken.checkToken(token).getUserLoginName(),(page-1)*10);
    }

    @Override
    public int countMailS(String token) {
        return mailDao.countMailS(checkToken.checkToken(token).getUserLoginName());
    }

    @Override
    public int unread(String token) {
        return mailDao.unread(checkToken.checkToken(token).getUserLoginName());
    }

    @Override
    public List<MailR> overview(String token) {
        return mailDao.overview(checkToken.checkToken(token).getUserLoginName());
    }
}
