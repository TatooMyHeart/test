package com.schedule.paramterBody;

import com.google.gson.annotations.Expose;
import com.schedule.entity.Users;

import java.util.List;

/**
 * Created by dell on 2017/7/24.
 */
public class GroupsUser {
    @Expose
    private List<Users>  userslist;
    @Expose
    private String groupid;

    public List<Users> getUserslist() {
        return userslist;
    }

    public void setUserslist(List<Users> userslist) {
        this.userslist = userslist;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

}
