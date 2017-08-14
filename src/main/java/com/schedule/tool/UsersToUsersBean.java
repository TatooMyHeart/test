package com.schedule.tool;

import com.schedule.entity.Users;
import com.schedule.paramterBody.UsersBean;

/**
 * Created by dell on 2017/7/28.
 */
public class UsersToUsersBean {
    public UsersBean turn(Users users)
    {
        UsersBean usersBean = new UsersBean();
        usersBean.setCollege(users.getCollege());
        usersBean.setMajor(users.getMajor());
        usersBean.setName(users.getName());
        usersBean.setNickname(users.getNickname());
        usersBean.setParam1(users.getParam1());
        usersBean.setPassword("已隐藏");
        usersBean.setStudentid(users.getStudentid());
        usersBean.setParam(users.getParam());
        usersBean.setTel(users.getTel());
        usersBean.setUserid(users.getUserid());
        return usersBean;
    }
}
