package com.xlauncher.service.impl;

import com.xlauncher.dao.AssignmentDao;
import com.xlauncher.dao.NoticeDao;
import com.xlauncher.entity.Info;
import com.xlauncher.entity.Notice;
import com.xlauncher.entity.User;
import com.xlauncher.service.NoticeService;
import com.xlauncher.util.CheckToken;
import com.xlauncher.util.DatetimeUtil;
import com.xlauncher.util.DimUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    CheckToken checkToken;

    @Autowired
    NoticeDao noticeDao;

    @Autowired
    AssignmentDao assignmentDao;

    @Override
    public int insertNotice(Notice notice, String token) {
        if (checkToken.checkToken(token)!=null){
            notice.setNoticeAuthor(checkToken.checkToken(token).getUserLoginName());
            notice.setNoticeCreateTime(DatetimeUtil.getDate(System.currentTimeMillis()));
            notice.setNoticeCount(0);
            notice.setNoticeInfoId(1);
            Info info = new Info();
            info.setInfoContent(notice.getInfoContent());
            noticeDao.insertNoticeDetail(info);
            notice.setNoticeInfoId(info.getInfoId());
            noticeDao.insertNotice(notice);
            for (int roleId:
                 notice.getNoticeRole()) {
                noticeDao.insertNoticeRole(notice.getNoticeId(),roleId);
            }
            return 1;
        }
        return 0;
    }

    @Override
    public int deleteNotice(Integer noticeId, String token) {
        if (checkToken.checkToken(token)!=null) {
            noticeDao.deleteNoticeRole(noticeId);
            noticeDao.deleteInfo(noticeDao.queryNotice(noticeId).getNoticeInfoId());
            return noticeDao.deleteNotice(noticeId);
        }
        return 0;
    }

    @Override
    public List<Notice> queryNotice(String token,int page) {
        User user = checkToken.checkToken(token);
        if (user==null){
            return null;
        }
        List<Integer> list = assignmentDao.listAssign(user.getUserId());
        List<Integer> noticeList = new ArrayList<>();
        for (int roleId:
             list) {
            noticeList.addAll(noticeDao.queryNoticeRole(roleId));
        }
        HashSet hashSet = new HashSet(noticeList);
        noticeList.clear();
        noticeList.addAll(hashSet);
        Collections.sort(noticeList, new Comparator<Integer>() {
                    @Override
                    public int compare(Integer integer, Integer t1) {
                        return t1.compareTo(integer);
                    }
                });
        List < Notice > notices = new ArrayList<>();
        for (int i = (page-1)*10;i<page*10&&i<noticeList.size();i++){
            notices.add(noticeDao.queryNotice(noticeList.get(i)));
        }
        return notices;
    }

    @Override
    public int countNotice(String token) {
        User user = checkToken.checkToken(token);
        if (user==null){
            return 0;
        }
        List<Integer> list = assignmentDao.listAssign(user.getUserId());
        List<Integer> noticeList = new ArrayList<>();
        if (list.size()!=0) {
            for (int roleId :
                    list) {
                noticeList.addAll(noticeDao.queryNoticeRole(roleId));
            }
            HashSet hashSet = new HashSet(noticeList);
            noticeList.clear();
            noticeList.addAll(hashSet);
            return noticeList.size();
        }
        return 0;
    }

    @Override
    public List<Notice> overview(String token) {
        if (checkToken.checkToken(token)!=null){
            List<Integer> list = assignmentDao.listAssign(checkToken.checkToken(token).getUserId());
            List<Integer> noticeList = new ArrayList<>();
            for (int roleId:
                    list) {
                noticeList.addAll(noticeDao.queryNoticeRole(roleId));
            }
            HashSet hashSet = new HashSet(noticeList);
            noticeList.clear();
            noticeList.addAll(hashSet);
            Collections.sort(noticeList, new Comparator<Integer>() {
                @Override
                public int compare(Integer integer, Integer t1) {
                    return t1.compareTo(integer);
                }
            });
            List<Notice> notices = new ArrayList<>();
            for (int i = 0;i<4&&i<noticeList.size();i++){
                notices.add(noticeDao.queryNotice(noticeList.get(i)));
            }
            return notices;
        }
        return null;
    }

    @Override
    public Notice queryNoticeDetail(Integer noticeId, String token) {
        Notice notice = noticeDao.queryNotice(noticeId);
        notice.setInfoContent(noticeDao.queryNoticeDetail(notice.getNoticeInfoId()));
        notice.setNoticeCount(notice.getNoticeCount()+1);
        return notice;
    }
}
