package com.xlauncher.util;

import com.xlauncher.dao.NoticeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckInfoCount {
    @Autowired
    NoticeDao noticeDao;

    public void checkInfoCount(int infoId){
        if (noticeDao.infoCount(infoId)==0){
            if (noticeDao.infoCountS(infoId)==0){
                noticeDao.deleteInfo(infoId);
            }
        }
    }
}
