package com.schedule.controller;

import com.schedule.entity.Users;
import com.schedule.paramterBody.TelBean;
import com.schedule.paramterBody.UsersBean;
import com.schedule.service.impl.UserServiceImpl;
import com.schedule.state.UserStates;
import com.schedule.tool.UsersToUsersBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * Created by dell on 2017/7/21.
 */
@RestController
public class UserController {
    @Autowired
    UserServiceImpl userService;

    UserStates userStates;


    @RequestMapping(value = "login",method = RequestMethod.POST)
    public int login(@RequestBody Users users, HttpServletRequest request, HttpServletResponse response)
    {
        userStates=userService.logIn(users,request,response);
        return userStates.getStates();
    }


    @RequestMapping(value = "sendParam",method = RequestMethod.POST)
    public int sendParam(@RequestBody TelBean Telbean, HttpServletRequest request, HttpServletResponse response)
    {
        userStates=userService.sendParam(Telbean.getTel(),request);
        return userStates.getStates();
    }

    @ResponseBody
    @RequestMapping(value = "register",method = RequestMethod.POST)
    public int register(@RequestBody Users users,HttpServletRequest request, HttpServletResponse response)
    {
        userStates=userService.register(users,request);
        return userStates.getStates();
    }


    @RequestMapping(value = "boundTel",method = RequestMethod.POST)
    public int boundTel(@RequestBody TelBean Telbean, HttpServletRequest request, HttpServletResponse response)
    {
        System.out.println(Telbean.getTel());
        System.out.println(Telbean.getParam());
        userStates=userService.boundTel(Telbean.getTel(),Telbean.getParam(),request);
        return userStates.getStates();
    }


    @RequestMapping(value = "lookForUser",method = RequestMethod.POST)
    public UsersBean lookForUser(HttpServletRequest request, HttpServletResponse response)
    {
        String telphone = request.getParameter("tel");
        Users users = new Users();
        users=userService.lookForUser(telphone);
        if(users==null){return null;}
        UsersBean usersBean = new UsersBean();
        UsersToUsersBean usersToUsersBean = new UsersToUsersBean();
        usersBean=usersToUsersBean.turn(users);
        return usersBean;
    }


    @RequestMapping(value = "getUserInformation",method = RequestMethod.POST)
    public UsersBean getUserInformation(HttpServletRequest request, HttpServletResponse response)
    {
        Users users=userService.getUserInformation(request);
        if(users==null){return null;}
        UsersBean usersBean = new UsersBean();
        UsersToUsersBean usersToUsersBean = new UsersToUsersBean();
        usersBean=usersToUsersBean.turn(users);
        return usersBean;
    }

    @RequestMapping(value = "uploadPhoto",method = RequestMethod.POST)
    public String changePhoto(@RequestParam(value = "photo",required = false)MultipartFile file, HttpServletRequest request,ModelMap model,HttpServletResponse response) {
        String path=request.getSession().getServletContext().getRealPath("upload");
        if(!file.isEmpty()) {
            Date date = new Date();
            String filename = file.getOriginalFilename();
            String newFileName=UUID.randomUUID()+filename;
            System.out.println(newFileName);
            File targetFile = new File(path, newFileName);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            try {
                file.transferTo(targetFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String photopath=request.getContextPath() + "/upload/" + newFileName;
            return photopath;
        }
        return "error";
    }

    @RequestMapping(value = "alterUserInformation",method = RequestMethod.POST)
    public UsersBean alterUserInformation(HttpServletRequest request, HttpServletResponse response)
    {
        String college=request.getParameter("college");
        String major=request.getParameter("major");
        String photopath=request.getParameter("photopath");
        String nickname=request.getParameter("nickname");
        Users users = userService.alterUserInformation(college,major,photopath,nickname,request);
        if(users==null){return null;}
        UsersBean usersBean = new UsersBean();
        UsersToUsersBean usersToUsersBean = new UsersToUsersBean();
        usersBean=usersToUsersBean.turn(users);
        return usersBean;
    }



    @RequestMapping(value = "logout",method = RequestMethod.POST)
    public int logout(HttpServletRequest request, HttpServletResponse response)
    {
        userService.logout(request);
        HttpSession session=request.getSession();
        session.invalidate();
        return 1;
    }

    @RequestMapping(value ="deleteUser",method = RequestMethod.POST)
    public int deleteUser(HttpServletRequest request,HttpServletResponse response)
    {
        String tel=request.getParameter("tel");
        userStates = userService.deleteUser(tel);

        return userStates.getStates();
    }

}
