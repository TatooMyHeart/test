package com.schedule.controller;

import com.schedule.entity.Users;
import com.schedule.paramterBody.TelBean;
import com.schedule.paramterBody.WechatBean;
import com.schedule.service.impl.WechatServiceImpl;
import com.schedule.state.WechatStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by dell on 2017/7/29.
 */
@RestController
public class WechatController {

    @Autowired
    WechatServiceImpl wechatService;

    @RequestMapping(value = "getThirdSession")
    public String getThirdSession(HttpServletRequest request, HttpServletResponse response)
    {
        String code=request.getParameter("code");
        String third_session=wechatService.getThirdSession(code,request,response);
        return third_session;
    }

    @RequestMapping(value = "checkThirdSession")
    public int checkThirdSession(HttpServletRequest request,HttpServletResponse response)
    {
        String thirdSession=request.getParameter("3rd_session");
        return wechatService.checkThirdSession(thirdSession,response,request).getStates();
    }
    @RequestMapping(value = "wechatRegister")
    public int wechatRegister(@RequestBody Users users, HttpServletRequest request)
    {
        String thirdsession = request.getParameter("3rd_session");
        return wechatService.wechatRegister(users,thirdsession,request).getStates();
    }
    @RequestMapping(value = "wechatTel")
    public int wechatTel(@RequestBody TelBean telBean, HttpServletRequest request)
    {
        String thirdsession = request.getParameter("3rd_session");
        return wechatService.wechatTel(telBean.getTel(),telBean.getParam(),thirdsession,request).getStates();
    }

    @RequestMapping(value = "getWechatInfo")
    public int getWechatInfo(@RequestBody WechatBean wechatBean, HttpServletRequest request, HttpServletResponse response)
    {
      return wechatService.getWechatInfo(wechatBean,request,response).getStates();
    }

}
