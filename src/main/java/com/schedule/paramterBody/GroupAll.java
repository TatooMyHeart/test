package com.schedule.paramterBody;

import com.google.gson.annotations.Expose;
import com.schedule.entity.Users;

import java.util.List;

/**
 * Created by dell on 2017/8/2.
 * 测试使用，上线后删除
 */
public class GroupAll {

    private List<UsersBean> usersBeanList;
    private String groupid;
    private String name;

    public List<UsersBean> getUsersBeanList() {
        return usersBeanList;
    }

    public void setUsersBeanList(List<UsersBean> usersBeanList) {
        this.usersBeanList = usersBeanList;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
