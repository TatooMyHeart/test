package com.schedule.state;

/**
 * Created by dell on 2017/7/21.
 */
public enum UserStates implements BaseStates {
    ERROE(0),SUCCESS(1),PASSWORD_ERROR(2),USERNAME_ERROR_OR_NOTEXIST(3),PARAM_ERROR(4),
    STUDENT_ID_EXIST(5),TEL_NOT_BOUND(6),NAME_EXIST(7),TEL_EXIST(8),ALREADY_LOGIN(9);

    private int states;
    UserStates(int states){this.states=states;}
    @Override
    public int getStates(){return states;}
}
