package com.schedule.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by dell on 2017/7/15.
 */
@Entity(name = "tasks")
public class Tasks {
    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @Column(name = "taskid")
    private String taskid;//任务ID


    @Column(name = "executorid")
    private Integer executorid;//执行人ID，也是系统分配的用户ID


    @Column(name = "title")
    private String title;//标题


    @Column(name = "context")
    private String context;//文本


    @Column(name = "deadline",columnDefinition="DATETIME")
    private String deadline;//截止日期


    @Column(name = "publishtime",columnDefinition = "DATETIME")
    private String publishtime;//发布日期


    @Column(name = "urgency")
    private int urgency;//紧急度，TaskStates


    @Column(name = "feedback")
    private String feedback;//反馈信息



    @Column(name = "feedbacktype")
    private int feedbacktype;//反馈类型,FeedbackStates


    @Column(name = "state")
    private int state;//任务状态,TaskStates

    @ManyToOne
    private Users users;


    @Column(name = "param1")
    private String param1;

    @Column(name = "param2")
    private String param2;

    @Column(name = "param3")
    private String param3;

    @Column(name = "param4")
    private String param4;

    @Column(name = "param5")
    private String param5;

    @Column(name = "param6")
    private String param6;

    @Column(name = "param7")
    private String param7;

    @Column(name = "param8")
    private String param8;

    @Column(name = "param9")
    private String param9;

    @Column(name = "param10")
    private String param10;

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getParam3() {
        return param3;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }

    public String getParam4() {
        return param4;
    }

    public void setParam4(String param4) {
        this.param4 = param4;
    }

    public String getParam5() {
        return param5;
    }

    public void setParam5(String param5) {
        this.param5 = param5;
    }

    public String getParam6() {
        return param6;
    }

    public void setParam6(String param6) {
        this.param6 = param6;
    }

    public String getParam7() {
        return param7;
    }

    public void setParam7(String param7) {
        this.param7 = param7;
    }

    public String getParam8() {
        return param8;
    }

    public void setParam8(String param8) {
        this.param8 = param8;
    }

    public String getParam9() {
        return param9;
    }

    public void setParam9(String param9) {
        this.param9 = param9;
    }

    public String getParam10() {
        return param10;
    }

    public void setParam10(String param10) {
        this.param10 = param10;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public Integer getExecutorid() {
        return executorid;
    }

    public void setExecutorid(Integer executorid) {
        this.executorid = executorid;
    }

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

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
