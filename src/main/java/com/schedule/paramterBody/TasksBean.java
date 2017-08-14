package com.schedule.paramterBody;

import java.sql.Timestamp;

/**
 * Created by dell on 2017/7/28.
 */
public class TasksBean {
    private String taskid;//任务ID
    //private Integer executorid;//执行人ID，也是系统分配的用户ID
    private String title;//标题
    private String context;//文本
    private String deadline;//截止日期
    private String publishtime;//发布日期
    private int urgency;//紧急度，TaskStates
    private String feedback;//反馈信息
    private int feedbacktype;//反馈类型,FeedbackStates
    private int state;//任务状态,TaskStates

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

//    public Integer getExecutorid() {
//        return executorid;
//    }
//
//    public void setExecutorid(Integer executorid) {
//        this.executorid = executorid;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(String publishtime) {
        this.publishtime = publishtime;
    }

    public int getUrgency() {
        return urgency;
    }

    public void setUrgency(int urgency) {
        this.urgency = urgency;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public int getFeedbacktype() {
        return feedbacktype;
    }

    public void setFeedbacktype(int feedbacktype) {
        this.feedbacktype = feedbacktype;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
