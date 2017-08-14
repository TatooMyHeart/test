package com.schedule.state;

/**
 * Created by dell on 2017/7/29.
 */
public enum WechatStates implements BaseStates {
    ERROR(0),SUCCESS(1),NOT_REGISTER(2),PARAM_ERROR(3),STUDENT_ID_EXIST(4),NAME_EXIST(5),OUT_OF_TIME(6),
    KEY_LENGTH_NOT_RIGHT(7);

    private int states;
    WechatStates(int states){this.states=states;}

    @Override
    public int getStates() {
        return states;
    }
}
