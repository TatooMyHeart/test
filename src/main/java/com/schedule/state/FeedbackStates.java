package com.schedule.state;

/**
 * Created by dell on 2017/7/21.
 */
public enum FeedbackStates implements BaseStates {
    DATE(0),NONE(1);
    private int states;
    FeedbackStates(int states){this.states=states;}

    @Override
    public int getStates() {
        return states;
    }
}
