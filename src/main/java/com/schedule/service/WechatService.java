package com.schedule.service;

import com.schedule.entity.Users;
import com.schedule.paramterBody.WechatBean;
import com.schedule.state.UserStates;
import com.schedule.state.WechatStates;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by dell on 2017/7/29.
 */
@Service
public interface WechatService {
    WechatStates wechatRegister(Users users, HttpServletRequest request);
    WechatStates wechatTel(String tel,String param, HttpServletRequest request);
    String getThirdSession(String code, HttpServletRequest request, HttpServletResponse response);
    WechatStates checkThirdSession(String thirdSession, HttpServletResponse response, HttpServletRequest request);
    String getWechatInfo(WechatBean wechatBean, HttpServletRequest request, HttpServletResponse response);
    WechatStates wechatOut(String unionId,HttpServletRequest request);

}
