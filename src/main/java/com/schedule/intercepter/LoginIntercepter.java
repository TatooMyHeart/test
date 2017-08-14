package com.schedule.intercepter;

import com.schedule.entity.Users;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by dell on 2017/7/24.
 */
public class LoginIntercepter implements org.springframework.web.servlet.HandlerInterceptor{
    private static final String[] IGNORE_URL={
            "/login","/register","/sendParam","/deleteUser","/upload",
            "/getThirdSession","/checkThirdSession","/wechatRegister","/wechatTel","/getWechatInfo",
            "/schedule/login","/schedule/register","/schedule/sendParam", "/schedule/deleteUser",
            "/schedule/getThirdSession","/schedule/checkThirdSession","/schedule/wechatRegister",
            "/schedule/wechatTel","/schedule/getWechatInfo",};
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        boolean b= false;
        String url =httpServletRequest.getRequestURL().toString();
        System.out.println("url:"+url);
        for(String s:IGNORE_URL)
        {
            if(url.contains(s)) {
                b = true;
                break;
            }
        }
        if(!b) {
            HttpSession session = httpServletRequest.getSession();
            Users users = (Users) session.getAttribute("users");
            if (users == null) {
                System.out.println("用户未登录！");
                httpServletRequest.getRequestDispatcher("/index.jsp").forward(httpServletRequest, httpServletResponse);
            }
            else {
                System.out.println("userID：" + users.getUserid());
                b = true;
            }
        }
        return b;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
