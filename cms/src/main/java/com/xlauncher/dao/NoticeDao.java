package com.xlauncher.dao;

import com.xlauncher.entity.Info;
import com.xlauncher.entity.Notice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.xml.soap.Text;
import java.util.List;

@Service
public interface NoticeDao {

    int insertNotice(Notice notice);

    int deleteNotice(int noticeId);

    int deleteNoticeRole(int noticeId);

    Notice queryNotice(int noticeId);

    /**
     *
     * @param noticeId
     * @param roleId
     * @return
     */
    int insertNoticeRole(@Param("noticeId") int noticeId, @Param("roleId") int roleId);

    List<Integer> queryNoticeRole(int roleId);



    /**以下为mail和notice通用部分*/
    /**
     * 删除信息
     * @param infoId 详情编号
     * @return 删除成功为1，
     */
    int deleteInfo(int infoId);

    /**
     * 获取邮件或者公告的详细信息
     * @param infoId 详情编号
     * @return  成功获取网页代码
     */
    String queryNoticeDetail(int infoId);

    /**
     * 添加详情
     * @param info 详情
     * @return 添加成功为1，失败为0
     */
    int insertNoticeDetail(Info info);


    /**计算收件箱info的数量*/
    int infoCount(int infoId);
    /**计算发件箱info的数量*/
    int infoCountS(int infoId);

}
