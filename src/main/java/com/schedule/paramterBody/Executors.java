package com.schedule.paramterBody;

import com.google.gson.annotations.Expose;
import com.schedule.entity.Users;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by dell on 2017/7/23.
 */
public class Executors {
    @Expose
    private String title;
    @Expose
    private String context;
    @Expose
    private String deadline;
    @Expose
    private int urgency;
    @Expose
    private int feedbacktype;
    @Expose
    private List<Users> users;

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

    public int getUrgency() {
        return urgency;
    }

    public void setUrgency(int urgency) {
        this.urgency = urgency;
    }

    public int getFeedbacktype() {
        return feedbacktype;
    }

    public void setFeedbacktype(int feedbacktype) {
        this.feedbacktype = feedbacktype;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }
}
