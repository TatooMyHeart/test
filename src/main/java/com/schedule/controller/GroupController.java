package com.schedule.controller;

import com.schedule.entity.Users;
import com.schedule.paramterBody.GroupAll;
import com.schedule.paramterBody.GroupsUser;
import com.schedule.paramterBody.Groupsget;
import com.schedule.paramterBody.UsersBean;
import com.schedule.service.impl.GroupServiceImpl;
import com.schedule.service.impl.UserServiceImpl;
import com.schedule.state.GroupStates;
import com.schedule.tool.UsersToUsersBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/7/22.
 */
@RestController
public class GroupController {
    @Autowired
    GroupServiceImpl groupService;

    @Autowired
    UserServiceImpl userService;

    GroupStates groupStates;


    @RequestMapping(value = "getGroupList",method = RequestMethod.POST)
    public List<Groupsget> getGroupList(HttpServletRequest request, HttpServletResponse response)
    {
        List<Groupsget> groupsgetList = new ArrayList<Groupsget>();
       groupsgetList = groupService.getGroupList(request);
       if(groupsgetList==null){return null;}
        return groupsgetList;
    }

    @RequestMapping(value = "createGroup",method = RequestMethod.POST)
    public String createGroup(HttpServletRequest request,HttpServletResponse response)
    {
        String groupname=request.getParameter("groupname");
        String groupid=groupService.createGroup(groupname,request);
        return groupid;
    }

    @RequestMapping(value = "addBlackListUser",method = RequestMethod.POST)
    public int addBlackListUser(HttpServletRequest request,HttpServletResponse response)
    {
        Integer groupuserid=Integer.parseInt(request.getParameter("userid"));
         groupStates = groupService.addBlackListUser(groupuserid,request);
        return groupStates.getStates();
    }

    @RequestMapping(value = "getGroupUsers",method = RequestMethod.POST)
    public List<UsersBean> getGroupUsers(HttpServletRequest request, HttpServletResponse response)
    {
        List<Users> usersList = new ArrayList<Users>();
        List<UsersBean> usersBeanList = new ArrayList<UsersBean>();
        UsersToUsersBean usersToUsersBean = new UsersToUsersBean();
        String groupid = request.getParameter("groupid");
      usersList = groupService.getGroupUsers(groupid,request);
        if(usersList==null){return null;}
      for(int i=0;i<usersList.size();i++)
      {
          UsersBean usersBean = new UsersBean();
          usersBean=usersToUsersBean.turn(usersList.get(i));
          usersBeanList.add(usersBean);
      }
        return usersBeanList;
    }

    @RequestMapping(value = "addGroupUsers",method = RequestMethod.POST)
    public int addGroupUsers(@RequestBody GroupsUser Groupsuser, HttpServletRequest request)
    {
        List<Users> usersListget = new ArrayList<Users>();
       usersListget =Groupsuser.getUserslist();
       String groupid = Groupsuser.getGroupid();
        for(int j=0;j<usersListget.size();j++)
        {
            for(int k=usersListget.size()-1;k>j;k--)
            {
                if(usersListget.get(k).getUserid()==usersListget.get(j).getUserid())
                {
                    usersListget.remove(k);
                }
            }
        }
        groupStates = groupService.addGroupUsers(usersListget,groupid,request);
        return groupStates.getStates();
    }

    @RequestMapping(value = "deleteGroupUser",method = RequestMethod.POST)
    public int deleteGroupUser(HttpServletRequest request){
        Integer groupuserid = Integer.parseInt(request.getParameter("userid"));
        String groupid = request.getParameter("groupid");
        groupStates=groupService.deleteGroupUser(groupid,groupuserid);
        return groupStates.getStates();
    }

    @RequestMapping(value = "deleteGroup",method = RequestMethod.POST)
    public int deleteGroup(HttpServletRequest request)
    {
        String groupid = request.getParameter("groupid");
        groupStates = groupService.deleteGroup(groupid,request);
        return groupStates.getStates();
    }

    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    public List<GroupAll> getAll(HttpServletRequest request)
    {
        List<Groupsget> groupsgetList = new ArrayList<Groupsget>();
        List<GroupAll> groupAllList = new ArrayList<GroupAll>();
        groupsgetList=groupService.getGroupList(request);
        for(int i=0;i<groupsgetList.size();i++)
        {
            GroupAll groupAll = new GroupAll();
            List<Users> usersList = new ArrayList<Users>();
            List<UsersBean> usersBeanList = new ArrayList<UsersBean>();
            UsersToUsersBean usersToUsersBean = new UsersToUsersBean();
            groupAll.setGroupid(groupsgetList.get(i).getGroupid());
            groupAll.setName(groupsgetList.get(i).getName());
            usersList = groupService.getGroupUsers(groupsgetList.get(i).getGroupid(),request);
            if(usersList==null){groupAll.setUsersBeanList(null);}
            for(int j=0;j<usersList.size();j++)
            {
                UsersBean usersBean = new UsersBean();
                usersBean=usersToUsersBean.turn(usersList.get(j));
                usersBeanList.add(usersBean);
            }
           groupAll.setUsersBeanList(usersBeanList);
            groupAllList.add(groupAll);
        }
       return groupAllList;
    }



}
