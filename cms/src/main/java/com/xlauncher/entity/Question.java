package com.xlauncher.entity;

public class Question {

    private Integer questionId;

    private Integer questionUserId;

    private String questionName;

    private String questionAnswer;

    public Question() {
    }

    public Question(Integer questionUserId, String questionName, String questionAnswer) {
        this.questionUserId = questionUserId;
        this.questionName = questionName;
        this.questionAnswer = questionAnswer;
    }

    public Question(Integer questionId, Integer questionUserId, String questionName, String questionAnswer) {
        this.questionId = questionId;
        this.questionUserId = questionUserId;
        this.questionName = questionName;
        this.questionAnswer = questionAnswer;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getQuestionUserId() {
        return questionUserId;
    }

    public void setQuestionUserId(Integer questionUserId) {
        this.questionUserId = questionUserId;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", questionUserId=" + questionUserId +
                ", questionName='" + questionName + '\'' +
                ", questionAnswer='" + questionAnswer + '\'' +
                '}';
    }
}
