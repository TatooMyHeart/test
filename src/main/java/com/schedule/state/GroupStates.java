package com.schedule.state;

/**
 * Created by dell on 2017/7/21.
 */
public enum GroupStates implements BaseStates {
    ERROR(0),SUCCESS(1);

    private int states;
    GroupStates(int states){this.states=states;}

    @Override
    public int getStates() {
        return states;
    }
}
