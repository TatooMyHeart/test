package com.schedule.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by dell on 2017/8/14.
 */
@Entity(name = "wechat")
public class Wechat {

    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "unionId")
    private String unionId;

    @Column(name = "thirdsession")
    private String thirdsession;

    @Column(name = "sessionkey")
    private String sessionkey;

    @Column(name = "openid")
    private String openid;

    @Column(name = "rawDate")
    private String rawDate;

    @Column(name = "signature")
    private String signature;

    @Column(name = "encryptedData")
    private String encryptedData;

    @Column(name = "iv")
    private String iv;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "param1")
    private String param1;

    @Column(name = "param2")
    private String param2;

    @Column(name = "param3")
    private String param3;

    @Column(name = "param4")
    private String param4;

    @Column(name = "param5")
    private String param5;

    @Column(name = "param6")
    private String param6;

    @Column(name = "param7")
    private String param7;

    @Column(name = "param8")
    private String param8;

    @Column(name = "param9")
    private String param9;

    @Column(name = "param10")
    private String param10;

    @Column(name = "timer")
    private Timestamp timer;

    public Timestamp getTimer() {
        return timer;
    }

    public void setTimer(Timestamp timer) {
        this.timer = timer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getThirdsession() {
        return thirdsession;
    }

    public void setThirdsession(String thirdsession) {
        this.thirdsession = thirdsession;
    }

    public String getSessionkey() {
        return sessionkey;
    }

    public void setSessionkey(String sessionkey) {
        this.sessionkey = sessionkey;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getRawDate() {
        return rawDate;
    }

    public void setRawDate(String rawDate) {
        this.rawDate = rawDate;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getParam3() {
        return param3;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }

    public String getParam4() {
        return param4;
    }

    public void setParam4(String param4) {
        this.param4 = param4;
    }

    public String getParam5() {
        return param5;
    }

    public void setParam5(String param5) {
        this.param5 = param5;
    }

    public String getParam6() {
        return param6;
    }

    public void setParam6(String param6) {
        this.param6 = param6;
    }

    public String getParam7() {
        return param7;
    }

    public void setParam7(String param7) {
        this.param7 = param7;
    }

    public String getParam8() {
        return param8;
    }

    public void setParam8(String param8) {
        this.param8 = param8;
    }

    public String getParam9() {
        return param9;
    }

    public void setParam9(String param9) {
        this.param9 = param9;
    }

    public String getParam10() {
        return param10;
    }

    public void setParam10(String param10) {
        this.param10 = param10;
    }
}
