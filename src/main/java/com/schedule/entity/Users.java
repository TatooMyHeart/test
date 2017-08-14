package com.schedule.entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by dell on 2017/7/15.
 */

@Entity(name="users")
public class Users {

    @Id
    @Column(name = "userid",nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userid;//系统分配的用户ID

    @Column(name="studentid")
    private String studentid;//学号

    @Column(name = "tel")
    private String tel;//手机号

    @Column(name="name")
    private String name;//用户名

    @Column(name="password")
    private String password;

    @Column(name = "college")
    private String college;

    @Column(name = "major")
    private String major;
    @Column(name = "wechatid")
    private String wechatid;
    @Column(name="param")
    private String param;

    @Column(name = "nickname")
    private String nickname;



    @OneToMany(mappedBy = "users",cascade = CascadeType.ALL)
    private List<Groups> groupsList =new ArrayList<Groups>();//用户拥有的组

    @OneToMany(mappedBy = "users",cascade = CascadeType.ALL)
    private List<Tasks> tasksList = new ArrayList<Tasks>();//用户拥有的任务（发布的和收到的）


    @Column(name = "param1") //头像路径
    private String param1;

    @Column(name = "param2")//UDID
    private String param2;

    @Column(name = "param3")//UDIDSetTime
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


    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer user_id) {
        this.userid = userid;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getTel() {
        return tel;
    }


    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getWechatid() {
        return wechatid;
    }

    public void setWechatid(String wechatid) {
        this.wechatid = wechatid;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public List<Groups> getGroupsList() {
        return groupsList;
    }

    public void setGroupsList(List<Groups> groupsList) {
        this.groupsList = groupsList;
    }

    public List<Tasks> getTasksList() {
        return tasksList;
    }

    public void setTasksList(List<Tasks> tasksList) {
        this.tasksList = tasksList;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
