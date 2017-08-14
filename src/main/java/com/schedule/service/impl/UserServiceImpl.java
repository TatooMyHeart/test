package com.schedule.service.impl;

import com.google.gson.Gson;
import com.schedule.entity.Users;
import com.schedule.entity.Wechat;
import com.schedule.paramterBody.WechatBean;
import com.schedule.repository.UserRepository;
import com.schedule.repository.WechatRepository;
import com.schedule.service.UserService;
import com.schedule.state.UserStates;
import com.schedule.tool.sms.DateUtil;
import com.schedule.tool.sms.HttpRequst;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created by dell on 2017/7/21.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    //private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    WechatRepository wechatRepository;


    public UserStates sendParam(String tel, HttpServletRequest request) {
        UserStates userStates = UserStates.SUCCESS;
        HttpRequst httpRequst = new HttpRequst();
        String paramCode = httpRequst.templateSMS(tel);

        Date date = new Date();
        long paramSetTime = date.getTime();
        if (null == paramCode) {
            return UserStates.ERROE;
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("param", paramCode);
            session.setAttribute("paramSetTime", paramSetTime);
            return userStates;
        }

    }

    public UserStates register(Users users, HttpServletRequest request) {
        UserStates userStates = UserStates.SUCCESS;
        HttpSession session = request.getSession();
        //检查用户是否为空
        if (null == users) {
            return UserStates.ERROE;
        }
        //检查学号是否存在
        if (userRepository.findAllByStudentid(users.getName()) != null) {
            return UserStates.STUDENT_ID_EXIST;
        }
        //检查电话号是否重复
        if (userRepository.findAllByTel(users.getTel()) != null) {
            return UserStates.TEL_EXIST;
        }
        //检查用户名是否重复
        if (userRepository.findAllByName(users.getName()) != null) {
            return UserStates.NAME_EXIST;
        }
        Date date = new Date();
        long paramGetTime = date.getTime();
        if (users.getParam().equals(session.getAttribute("param"))) {
            if (paramGetTime - (long) session.getAttribute("paramSetTime") <= 180000) {
                    users.setNickname(users.getName());
                    MessageDigest sha256 = null;
                    try {
                        sha256 = MessageDigest.getInstance("SHA-256");
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    BASE64Encoder base64Encoder = new BASE64Encoder();
                    users.setPassword(base64Encoder.encode(sha256.digest(users.getPassword().getBytes())));
                    userRepository.save(users);
                    return userStates;
            } else {
                session.removeAttribute("param");
                session.removeAttribute("paramSetTime");
                return UserStates.PARAM_ERROR;
            }
        } else {
            return UserStates.PARAM_ERROR;
        }

    }

    public UserStates logIn(Users users, HttpServletRequest request,HttpServletResponse response) {
        UserStates userStates = UserStates.SUCCESS;
        if (users == null) {
            return UserStates.ERROE;
        }
        MessageDigest sha256 = null;
        try {
            sha256 = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String passwordin = base64Encoder.encode(sha256.digest(users.getPassword().getBytes()));
        //输入的用户名可能是用户名、学号、手机号，分别查询一次
        HttpSession session = request.getSession();
        Users usersByName;
        Users usersByStudentId;
        Users usersByTel;
        Users usersFind;
        usersByName = userRepository.findAllByName(users.getName());
        usersByStudentId = userRepository.findAllByStudentid(users.getName());
        usersByTel = userRepository.findAllByTel(users.getName());
        //判断到底是哪个,存入usersFind
        if (usersByName == null && usersByStudentId == null && usersByTel == null) {
            return UserStates.USERNAME_ERROR_OR_NOTEXIST;
        } else {
            if (usersByStudentId != null) {
                if (usersByStudentId.getTel() == null) {
                    if (passwordin.equals(usersByStudentId.getPassword())) {
                        session.setAttribute("users", usersByStudentId);
                        return UserStates.TEL_NOT_BOUND;
                    } else {
                        return UserStates.PASSWORD_ERROR;
                    }
                } else {
                    usersFind = usersByStudentId;
                }
            } else if (usersByName != null) {
                usersFind = usersByName;
            } else if (usersByTel != null) {
                usersFind = usersByTel;
            } else {
                return UserStates.ERROE;
            }
            if (passwordin.equals(usersFind.getPassword())) {
                //UDID防止手机端重复登陆
                //如果请求头中有UDID
                if(request.getHeader("UDID")!=null) {
                    String UDID=request.getHeader("UDID").toString();
                    System.out.println("UDID: "+UDID);
                    //检查user的属性中UDID是否为null，null表示，logout方式登出，否则可能是重复登陆或session失效再次登录
                    if(usersFind.getParam2()==null) {
                        //UDID为空，录入UDID并记录创建时间(毫秒（getTime))
                        Date date = new Date();
                        userRepository.updateUDIDAndTimeByUserid(UDID,date.getTime()+"",usersFind.getUserid());
                        usersFind=userRepository.findAllByUserid(usersFind.getUserid());
                        session.setAttribute("users", usersFind);
                        return userStates;
                    }else
                    {
                        //判断是否为session失效
                        Date date=new Date();
                        //session失效
                        if(date.getTime()-Long.valueOf(usersFind.getParam3())>36000000)
                        {
                            userRepository.updateUDIDAndTimeByUserid(UDID,date.getTime()+"",usersFind.getUserid());
                            usersFind=userRepository.findAllByUserid(usersFind.getUserid());
                            session.setAttribute("users", usersFind);
                            return userStates;
                        }else//session未失效，即重复登陆
                        {
                            return UserStates.ALREADY_LOGIN;
                        }

                    }
                }
                //如果请求头中无UDID，即不是手机端，或者测试阶段两种方式并行，直接通过
                else
                {
                    session.setAttribute("users", usersFind);
                    return userStates;
                }
            } else {
                return UserStates.PASSWORD_ERROR;
            }
        }
    }

    public UserStates boundTel(String tel, String param, HttpServletRequest request) {
        if (userRepository.findAllByTel(tel) != null) {
            return UserStates.ERROE;
        }
        Users users = new Users();
        HttpSession session = request.getSession();
        users = (Users) session.getAttribute("users");
        Date date = new Date();
        long paramGetTime = date.getTime();
        if (param.equals(session.getAttribute("param"))) {
            if (paramGetTime - (long) session.getAttribute("paramSetTime") <= 180000) {
                userRepository.updateTelByUserid(tel, users.getUserid());
                users = userRepository.findAllByUserid(users.getUserid());
                session.removeAttribute("users");
                session.setAttribute("users", users);
                return UserStates.SUCCESS;
            } else {
                session.removeAttribute("param");
                session.removeAttribute("paramSetTime");
                return UserStates.PARAM_ERROR;
            }
        } else {
            return UserStates.PARAM_ERROR;
        }
    }

    public Users alterUserInformation(String college, String major, String param1, String nickname, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Users users = (Users) session.getAttribute("users");
        if(college==null){college=users.getCollege();}
        if(major==null){major=users.getMajor();}
        if(param1==null){param1=users.getParam1();}
        if(nickname==null){nickname=users.getNickname();}
        userRepository.updateUserInformationByUserid(college, major, param1, nickname, users.getUserid());
        users = userRepository.findAllByUserid(users.getUserid());
        session.setAttribute("users",users);
        return users;
    }

    public Users lookForUser(String tel) {
        Users users = new Users();
        users = userRepository.findAllByTel(tel);
        return users;
    }

    public Users getUserInformation(HttpServletRequest request) {
        UserStates userStates = UserStates.SUCCESS;
        HttpSession session = request.getSession();
        Users users;
        users = (Users) session.getAttribute("users");
        return users;
    }


    public UserStates deleteUser (String tel)
    {
        Users users=userRepository.findAllByTel(tel);
        if(users!=null) {
            userRepository.delete(users.getUserid());
            return UserStates.SUCCESS;
        }else
        {return UserStates.ERROE;}
    }

    public void logout(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        Users users = (Users)session.getAttribute("users");
        userRepository.updateUDIDAndTimeByUserid(null,null,users.getUserid());
        if(users.getWechatid()!=null) {
            Wechat wechat = wechatRepository.findAllByUnionId(users.getWechatid());
            if(wechat!=null) {
                wechatRepository.delete(wechat);
            }
        }
    }

    public Users getGroupUsers(Integer user_id)
    {
        return userRepository.findAllByUserid(user_id);
    }



}
