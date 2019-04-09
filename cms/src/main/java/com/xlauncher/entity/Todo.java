package com.xlauncher.entity;

public class Todo {

    /**待办事件编号*/
    private Integer todoId;
    /**待办事件用户编号*/
    private Integer todoUserId;
    /**用户信息*/
    private User user;
    /**待办事件描述*/
    private String todoNote;
    /**待办事件提交时间*/
    private String todoTime;
    /**待办事件状态*/
    private Integer todoStatus;
    /**事件处理这*/
    private String todoHandler;

    public Todo() {
    }

    public Todo(Integer todoUserId, User user, String todoNote, String todoTime, Integer todoStatus, String todoHandler) {
        this.todoUserId = todoUserId;
        this.user = user;
        this.todoNote = todoNote;
        this.todoTime = todoTime;
        this.todoStatus = todoStatus;
        this.todoHandler = todoHandler;
    }

    public Todo(Integer todoId, Integer todoUserId, User user, String todoNote, String todoTime, Integer todoStatus, String todoHandler) {
        this.todoId = todoId;
        this.todoUserId = todoUserId;
        this.user = user;
        this.todoNote = todoNote;
        this.todoTime = todoTime;
        this.todoStatus = todoStatus;
        this.todoHandler = todoHandler;
    }

    public Integer getTodoId() {
        return todoId;
    }

    public void setTodoId(Integer todoId) {
        this.todoId = todoId;
    }

    public Integer getTodoUserId() {
        return todoUserId;
    }

    public void setTodoUserId(Integer todoUserId) {
        this.todoUserId = todoUserId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTodoNote() {
        return todoNote;
    }

    public void setTodoNote(String todoNote) {
        this.todoNote = todoNote;
    }

    public String getTodoTime() {
        return todoTime;
    }

    public void setTodoTime(String todoTime) {
        this.todoTime = todoTime;
    }

    public Integer getTodoStatus() {
        return todoStatus;
    }

    public void setTodoStatus(Integer todoStatus) {
        this.todoStatus = todoStatus;
    }

    public String getTodoHandler() {
        return todoHandler;
    }

    public void setTodoHandler(String todoHandler) {
        this.todoHandler = todoHandler;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "todoId=" + todoId +
                ", todoUserId=" + todoUserId +
                ", user=" + user +
                ", todoNote='" + todoNote + '\'' +
                ", todoTime='" + todoTime + '\'' +
                ", todoStatus=" + todoStatus +
                ", todoHandler='" + todoHandler + '\'' +
                '}';
    }
}
