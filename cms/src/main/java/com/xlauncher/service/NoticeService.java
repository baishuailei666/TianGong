package com.xlauncher.service;

import com.xlauncher.entity.Notice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoticeService {

    int insertNotice(Notice notice,String token);

    int deleteNotice(Integer noticeId,String token);

    List<Notice> queryNotice(String token,int page);

    int countNotice(String token);

    Notice queryNoticeDetail(Integer noticeId,String token);

    List<Notice> overview(String token);
}
