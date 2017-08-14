package com.schedule.service;

import com.schedule.entity.Users;
import com.schedule.paramterBody.Groupsget;
import com.schedule.state.GroupStates;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by dell on 2017/7/22.
 */
@Service
public interface GroupService {
    List<Groupsget> getGroupList(HttpServletRequest request);
    String createGroup(String name, HttpServletRequest request);
    GroupStates addBlackListUser(Integer groupuserid, HttpServletRequest request);
    List<Users> getGroupUsers(String groupid, HttpServletRequest request);
    GroupStates addGroupUsers(List<Users> usersList, String groupid, HttpServletRequest request);
    GroupStates deleteGroupUser(String groupid, Integer groupuserid);
    GroupStates deleteGroup(String groupid, HttpServletRequest request);
}
