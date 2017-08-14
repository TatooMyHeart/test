package com.schedule.tool.sms;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by dell on 2017/7/19.
 */
public class HttpRequst {
    public static final String accountSid="6629441b8dca00888b912fec04733c53";
    public static final String authToken="7ffb590429125fd8f28e47258b82e667";
    public static final String apiurl="https://api.ucpaas.com";
    public static final String appId="192f3bfc860d4c99920959d7ecc01d49";
    public static final String templateId="104064";
    public static final String version="2014-06-30";



    //发送短信验证码
    public String templateSMS(String to) {
        String result = "";
        String param=generateWord();
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            //MD5加密
            Encrypt encrypt = new Encrypt();
            // 构造请求URL内容
            //时间戳
            String timestamp = DateUtil.dateToStr(new Date(),DateUtil.DATE_TIME_NO_SLASH);
            //签名
            String signature =getSignature(timestamp,encrypt);
            //URL
            StringBuffer stringBuffer = new StringBuffer();
            String url = stringBuffer.append(apiurl).append("/").append(version)
                    .append("/Accounts/").append(accountSid)
                    .append("/Messages/templateSMS")
                    .append("?sig=").append(signature).toString();
            //Json
            PostBody postBody=new PostBody();
            postBody.setAppId(appId);
            postBody.setTemplateId(templateId);
            postBody.setTo(to);
            postBody.setParam(param);
            Gson gson = new Gson();
            String body = gson.toJson(postBody);
            body="{\"templateSMS\":"+body+"}";
            //log4j
            //logger.info(body);
            HttpResponse response=post("application/json",timestamp,url,client,encrypt,body);
            int stateCode=response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, "UTF-8");
            }
            if(!result.toString().contains("000000")){
                return null;
            }
            EntityUtils.consume(entity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            // 关闭连接
            client.getConnectionManager().shutdown();
        }
        return param;
    }

    public HttpResponse post(String cType,String timestamp,String url,CloseableHttpClient client,Encrypt encrypt,String body) throws Exception{
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Accept", cType);
        httppost.setHeader("Content-Type", cType+";charset=utf-8");

        /**
         * Authorization是包头验证信息：
         1、使用Base64编码（账户Id + 冒号 + 时间戳）
         2、冒号为英文冒号
         3、时间戳是当前系统时间（24小时制），格式“yyyyMMddHHmmss”，需与SigParameter中时间戳相同
         */

        String src = accountSid + ":" + timestamp;
        String auth = encrypt.base64Encoder(src);
        httppost.setHeader("Authorization", auth);
        BasicHttpEntity requestBody = new BasicHttpEntity();
        requestBody.setContent(new ByteArrayInputStream(body.getBytes("UTF-8")));
        requestBody.setContentLength(body.getBytes("UTF-8").length);
        httppost.setEntity(requestBody);
        // 执行客户端请求
        HttpResponse response = client.execute(httppost);
        return response;
    }
    /**
     * SigParameter是REST API 验证参数：
     1、URL后必须带有sig参数，sig= MD5（账户Id + 账户授权令牌 + 时间戳），共32位(注:转成大写)
     2、使用MD5加密（账户Id + 账户授权令牌 + 时间戳），共32位
     3、时间戳是当前系统时间（24小时制），格式“yyyyMMddHHmmss”。时间戳有效时间为50分钟
     */
    public String getSignature(String timestamp,Encrypt encrypt) throws Exception {
        String sig = accountSid + authToken + timestamp;
        String signature = encrypt.md5Digest(sig);
        return signature;
    }


    //获得验证码
    private static String generateWord() {
        int length = 6;
        String[] beforeShuffle = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        List list = Arrays.asList(beforeShuffle);
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        String afterShuffle = sb.toString();
        return afterShuffle.substring(2, 2 + length);
    }

}
