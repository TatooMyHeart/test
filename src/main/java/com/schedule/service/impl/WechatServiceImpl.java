package com.schedule.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.schedule.entity.Users;
import com.schedule.entity.Wechat;
import com.schedule.paramterBody.EncryptedDataBean;
import com.schedule.paramterBody.WechatBean;
import com.schedule.repository.UserRepository;
import com.schedule.repository.WechatRepository;
import com.schedule.service.WechatService;
import com.schedule.state.WechatStates;
import com.schedule.tool.wechat.AES;
import com.schedule.tool.wechat.WxPKCS7Encoder;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.*;
import java.util.Date;

/**
 * Created by dell on 2017/7/29.
 */
@Service
public class WechatServiceImpl implements WechatService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    WechatRepository wechatRepository;

    public WechatStates wechatRegister(Users users,HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session.getAttribute("tel")==null){return WechatStates.ERROR;}
        String tel=session.getAttribute("tel").toString();
        Wechat wechat = (Wechat)session.getAttribute("wechat");
        //检查用户是否为空
        if (null == users) {
            return WechatStates.ERROR;
        }
        //检查学号是否存在
        if (userRepository.findAllByStudentid(users.getName()) != null) {
            return WechatStates.STUDENT_ID_EXIST;
        }
        //检查用户名是否重复
        if (userRepository.findAllByName(users.getName()) != null) {
            return WechatStates.NAME_EXIST;
        }

        //检查unionId是否重复
        if(userRepository.findAllByWechatid(wechat.getUnionId())!=null)
        {
            return WechatStates.ERROR;
        }
                MessageDigest sha256 = null;
                try {
                    sha256 = MessageDigest.getInstance("SHA-256");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                BASE64Encoder base64Encoder = new BASE64Encoder();
                users.setPassword(base64Encoder.encode(sha256.digest(users.getPassword().getBytes())));
                users.setWechatid(wechat.getUnionId());
                users.setTel(tel);
                users.setNickname(wechat.getNickname());
                userRepository.save(users);

        if(wechatRepository.findAllByThirdsession(wechat.getThirdsession())!=null)
        {
            Wechat wechat1=wechatRepository.findAllByThirdsession(wechat.getThirdsession());
            if(wechat1.getUnionId()==wechat.getUnionId())
            {
                return WechatStates.ERROR;
            }
        }
                wechatRepository.save(wechat);
                session.setAttribute("users",users);
                return WechatStates.SUCCESS;
    }


    public WechatStates wechatTel(String tel,String param, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session.getAttribute("wechat")==null){return WechatStates.ERROR;}
        Wechat wechat = (Wechat)session.getAttribute("wechat");
        System.out.println("unionid"+wechat.getUnionId());
        System.out.println("3rdsession"+wechat.getThirdsession());
        Users users = userRepository.findAllByTel(tel);
        if (users == null) {
            session.setAttribute("tel",tel);
            return WechatStates.NOT_REGISTER;
        } else {
            if(wechatRepository.findAllByThirdsession(wechat.getThirdsession())!=null)
            {
                if(wechatRepository.findAllByThirdsession(wechat.getThirdsession()).getUnionId()==wechat.getUnionId())
                {
                    return WechatStates.ERROR;
                }
            }
           if(users.getWechatid()==null)
           {
               if(userRepository.findAllByWechatid(wechat.getUnionId())!=null)
               {
                   return WechatStates.ERROR;
               }
               userRepository.updateUnionIdByUserid(wechat.getUnionId(),users.getUserid());
               users = userRepository.findAllByUserid(users.getUserid());
           }
            wechatRepository.save(wechat);
            Date date = new Date();
            long paramGetTime = date.getTime();
            if (param.equals(session.getAttribute("param"))) {
                if (paramGetTime - (long) session.getAttribute("paramSetTime") <= 180000) {
                    session.setAttribute("users", users);
                    return WechatStates.SUCCESS;
                } else {
                    session.removeAttribute("param");
                    session.removeAttribute("paramSetTime");
                    return WechatStates.PARAM_ERROR;
                }
            }else
            {
                return WechatStates.PARAM_ERROR;
            }
        }
    }

    public String getThirdSession(String code, HttpServletRequest request, HttpServletResponse response) {

        String appid = "wx392f4df971214976";
        String secret = "ff570e29bb03624ac30a9807d28a5940";
        String apiurl = "https://api.weixin.qq.com/sns/jscode2session";
        String cType = "application/json";
        Gson gson = new Gson();
        StringBuffer stringBuffer = new StringBuffer();
        CloseableHttpClient client = HttpClients.createDefault();
        String url = stringBuffer.append(apiurl)
                .append("?appid=").append(appid).append("&secret=").append(secret)
                .append("&js_code=").append(code).append("&grant_type=authorization_code").toString();
        //String timestamp = DateUtil.dateToStr(new Date(),DateUtil.DATE_TIME_NO_SLASH);
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Accept", cType);
        httppost.setHeader("Content-Type", cType + ";charset=utf-8");
        HttpResponse httpResponse = null;
        try {
            httpResponse = client.execute(httppost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int responseCode = httpResponse.getStatusLine().getStatusCode();
        if (responseCode == HttpStatus.SC_OK) {
            MessageDigest sha256 = null;
            try {
                sha256 = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            BASE64Encoder base64Encoder = new BASE64Encoder();
            HttpEntity httpEntity = httpResponse.getEntity();
            WechatBean wechatBean = null;
            try {
                wechatBean = gson.fromJson(EntityUtils.toString(httpEntity), WechatBean.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (wechatBean.getSession_key() != null && wechatBean.getOpenid() != null) {
                String temp = wechatBean.getOpenid() + wechatBean.getSession_key();
                String third_session = base64Encoder.encode(sha256.digest(temp.getBytes()));


                HttpSession session = request.getSession();
                session.setAttribute("thirdsession",third_session);
                session.setAttribute("sessionkey",wechatBean.getSession_key());
                session.setAttribute("openid",wechatBean.getOpenid());
//                if(wechatRepository.findAllByThirdsession(third_session)!=null)
//                {
//                    return "third_session aleardy exist";
//                }
//
//                Wechat wechat = new Wechat();
//                wechat.setThirdsession(third_session);
//                wechat.setOpenid(wechatBean.getOpenid());
//                wechat.setSessionkey(wechatBean.getSession_key());
//                wechatRepository.save(wechat);
                return third_session;
            } else if (wechatBean.getErrmsg() != null) {
                return wechatBean.getErrmsg();
            } else {
                return "unknowError";
            }
        }else {
            return "unknowError";
        }
    }



    public WechatStates checkThirdSession(String thirdSession, HttpServletResponse response, HttpServletRequest request)
    {
        Wechat wechat = wechatRepository.findAllByThirdsession(thirdSession);
        if(wechat!=null){
            if(wechat.getUnionId()==null){return WechatStates.ERROR;}
           Users users = userRepository.findAllByWechatid(wechat.getUnionId());
            HttpSession session = request.getSession();
            session.setAttribute("users",users);
            return WechatStates.SUCCESS;
        }
        else
        {
            return WechatStates.OUT_OF_TIME;
        }
    }

    public String getWechatInfo(WechatBean wechatBean,HttpServletRequest request,HttpServletResponse response)
    {
//        System.out.println(wechatBean.getThirdsession());
//        Wechat wechat=wechatRepository.findAllByThirdsession(wechatBean.getThirdsession());
//        System.out.println(wechat.getThirdsession());
//        if(wechat==null){return WechatStates.OUT_OF_TIME;}
//        String sessionkey=wechat.getSessionkey();
        HttpSession session = request.getSession();
//        session.setAttribute("thirdsession","Ez47wkGE4J0vYTuhwpq6VrDmYaPgubmpouCNOoFikaM=");
//        session.setAttribute("sessionkey","mWpcKk/I7JyLGb6WvDAM9g==");
//        session.setAttribute("openid","oiHfw0IYoekwNYvxD7AjTM2dl3Ow");
        if(session.getAttribute("sessionkey")==null)
        {
            return "timeout";
        }
        String sessionkey=session.getAttribute("sessionkey").toString();
        AES aes = new AES();
        try {
            byte[] resultByte=aes.decrypt(Base64.decodeBase64(wechatBean.getEncryptedData()),
                    Base64.decodeBase64(sessionkey),Base64.decodeBase64(wechatBean.getIv()));
            if(null!=resultByte&&resultByte.length>0)
            {
                String userInfo=new String(WxPKCS7Encoder.decode(resultByte));
                EncryptedDataBean encryptedDataBean = new EncryptedDataBean();
                Gson gson= new GsonBuilder().disableInnerClassSerialization().create();
                encryptedDataBean=gson.fromJson(userInfo,EncryptedDataBean.class);
                if(encryptedDataBean.getUnionId()==null)
                {
                    return "No unionID in encryptedData";
                }

                Wechat wechat = new Wechat();
                wechat.setNickname(encryptedDataBean.getNickName());
                wechat.setUnionId(encryptedDataBean.getUnionId());
                wechat.setIv(wechatBean.getIv());
                wechat.setEncryptedData(wechatBean.getEncryptedData());
                wechat.setThirdsession(session.getAttribute("thirdsession").toString());
                wechat.setSessionkey(sessionkey);
                wechat.setOpenid(session.getAttribute("openid").toString());

                if(wechatBean.getSignature()==null){
                    wechat.setSignature(null);
                }else{
                    wechat.setSignature(wechatBean.getSignature());
                }

                if(wechatBean.getRawDate()==null){
                    wechat.setRawDate(null);
                }else{
                    wechat.setRawDate(wechatBean.getRawDate());
                }
                session.setAttribute("wechat",wechat);
//                wechatRepository.updateWechatInfoByThirdsession(wechat.getEncryptedData(),wechat.getIv(),wechat.getNickname(),
//                        wechat.getRawDate(),wechat.getSignature(),wechat.getUnionId(),wechat.getThirdsession());


             }
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

        Wechat wechat = (Wechat)session.getAttribute("wechat");
        if(wechat.getUnionId()==null){return null; }
       return wechat.getUnionId();
    }


    public WechatStates wechatOut(String unionId,HttpServletRequest request)
    {
            Wechat wechat = wechatRepository.findAllByUnionId(unionId);
            if(wechat!=null) {
                wechatRepository.delete(wechat);
            }
            return WechatStates.SUCCESS;
    }
}
