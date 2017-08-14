package com.schedule.service.impl;

import com.schedule.entity.Groups;
import com.schedule.entity.Users;
import com.schedule.paramterBody.Groupsget;
import com.schedule.repository.GroupRepository;
import com.schedule.repository.UserRepository;
import com.schedule.service.GroupService;
import com.schedule.state.GroupStates;
import com.schedule.tool.RandomId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/7/22.
 */
@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Groupsget> getGroupList(HttpServletRequest request) {
        HttpSession session=request.getSession();
        Users users=(Users)session.getAttribute("users");
        List<Groupsget> groupsgetList = new ArrayList<Groupsget>();
        List<Groups> groupsList=groupRepository.findAllByUsersAndAndGroupUserid(users,null);
        for(int i = 0;i<groupsList.size();i++)
        {
            Groupsget groupsget = new Groupsget();
            groupsget.setGroupid(groupsList.get(i).getGroupid());
            groupsget.setName(groupsList.get(i).getName());
            groupsgetList.add(groupsget);
        }
        Groupsget groupsget = new Groupsget();
        groupsget.setGroupid("0000");
        groupsget.setName("黑名单");
        groupsgetList.add(groupsget);

        return groupsgetList;
    }

    @Override
    public String createGroup(String name,HttpServletRequest request) {
        HttpSession session = request.getSession();
        RandomId randomId = new RandomId();
        Groups groups = new Groups();
        Users users=(Users)session.getAttribute("users");
        groups.setUsers(users);
        groups.setName(name);
        groups.setGroupid(randomId.randomNumForId());
        groupRepository.insertAll(groups.getGroupid(),users.getUserid(),groups.getName(),null);
        return groups.getGroupid();
    }

    @Override
    public GroupStates addBlackListUser(Integer groupuserid, HttpServletRequest request) {
        GroupStates groupStates = GroupStates.SUCCESS;
        HttpSession session = request.getSession();
        Users usersNow = (Users)session.getAttribute("users");
        Groups groups = new Groups();
        groups.setGroupid("0000");
        groups.setName("黑名单");
        groups.setGroupUserid(groupuserid);
        groups.setUsers(usersNow);
        groupRepository.insertAll(groups.getGroupid(),usersNow.getUserid(),groups.getName(),groupuserid);
        return groupStates;
    }

    //查询次数太多，需要优化
    @Override
    public List<Users> getGroupUsers(String groupid, HttpServletRequest request) {
        List<Users> usersList = new ArrayList<Users>();
        int i=0;
        List<Groups> groupList = groupRepository.findAllByGroupid(groupid);
        for(i=0;i<groupList.size();i++)
        {
            if(groupList.get(i).getGroupUserid()!=null) {
                usersList.add(userRepository.findAllByUserid(groupList.get(i).getGroupUserid()));
            }
        }
        return usersList;
    }

    @Override
    public GroupStates addGroupUsers(List<Users> usersList, String groupid, HttpServletRequest request) {
        GroupStates groupStates = GroupStates.SUCCESS;
        HttpSession session = request.getSession();
        Users users = (Users)session.getAttribute("users");
        if(users==null){return GroupStates.ERROR;}
        Groups groups1 = groupRepository.findAllByGroupidAndGroupUserid(groupid,null);
        if(groups1==null){return GroupStates.ERROR;}
        String groupname = groups1.getName();
        for(int i=0;i<usersList.size();i++)
        {
            Groups groups = new Groups();
            groups.setGroupid(groupid);
            groups.setName(groupname);
            groups.setUsers(users);
            groups.setGroupUserid(usersList.get(i).getUserid());
            groupRepository.insertAll(groups.getGroupid(),users.getUserid(),groups.getName(),groups.getGroupUserid());
        }
        return groupStates;
    }

    @Override
    public GroupStates deleteGroupUser(String groupid, Integer groupuserid)
    {
        GroupStates groupStates = GroupStates.SUCCESS;
        groupRepository.deleteByGroupidAndGroupUserid(groupid,groupuserid);
        return groupStates;
    }

    @Override
    public GroupStates deleteGroup(String groupid, HttpServletRequest request) {
        GroupStates groupStates = GroupStates.SUCCESS;
        HttpSession session = request.getSession();
        Users users = (Users)session.getAttribute("users");
        groupRepository.deleteByGroupidAndUserid(groupid,users.getUserid());
        return groupStates;
    }
}
