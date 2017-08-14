package com.schedule.service;

import com.schedule.entity.Users;
import com.schedule.state.UserStates;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;


/**
 * Created by dell on 2017/7/21.
 */
@Service
public interface UserService {

    UserStates sendParam(String tel, HttpServletRequest request);
    UserStates register(Users users, HttpServletRequest request) throws NoSuchAlgorithmException;
    UserStates logIn(Users users, HttpServletRequest request,HttpServletResponse response) throws NoSuchAlgorithmException;
    UserStates boundTel(String tel, String param, HttpServletRequest request);
    Users getUserInformation(HttpServletRequest request);
    Users lookForUser(String tel);
    Users alterUserInformation(String college, String major, String param1, String nickname, HttpServletRequest request);
    UserStates deleteUser(String tel);
    void logout(HttpServletRequest request);
}
