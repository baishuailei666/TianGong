package com.xlauncher.dao;

import com.xlauncher.entity.Question;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestionDao {

    /**
     * 用户填写密保问题
     * @param question 密保问题填写者的问题与答案
     * @return
     */
    int insertQuestion(Question question);

    /**
     * 用户填写密保问题
     * @param question 密保问题填写者的问题与答案
     * @return
     */
    int updateQuestion(Question question);

    /**
     * 删除用户的密保问题（系统管理员重置密码时调用）
     * @param questionUserId 需要删除的密保问题所属的用户编号
     * @return 删除操作影响的数据库行数
     */
    int deleteQuestionByUser(int questionUserId);

    /**
     * 删除用户的某一个密保问题
     * @param questionId 需要删除的密保问题在数据库中的编号
     * @return 删除操作影响的数据库行数
     */
    int deleteQuestion(int questionId);

    /**
     * 查看某个用户的所有密保问题和答案
     * @param questionUserId 用户的编号
     * @return 某用户的所有（一般为3个）密保问题和答案
     */
    List<Question> listQuestion(int questionUserId);

    /**
     *
     * @param questionId
     * @return
     */
    Question getQuestionById(int questionId);
}
