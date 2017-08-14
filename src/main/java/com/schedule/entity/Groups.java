package com.schedule.entity;

import javax.persistence.*;

/**
 * Created by dell on 2017/7/15.
 */
@Entity(name="groups")
public class Groups {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="groupid")
    private String groupid;//组ID

    @Column(name = "name")
    private String name;//组名

    @Column(name = "groupUserid")
    private Integer groupUserid;//组成员ID


    @ManyToOne(cascade = CascadeType.ALL)
    private Users users;//创建组的用户

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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGroupUserid() {
        return groupUserid;
    }

    public void setGroupUserid(Integer groupUserid) {
        this.groupUserid = groupUserid;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
