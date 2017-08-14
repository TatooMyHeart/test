package com.schedule.state;

/**
 * Created by dell on 2017/7/21.
 */
public enum TaskStates implements BaseStates {
    ERROR(0),SUCCESS(1),LAUNCHED(2),FINISH(3),END(4),GIVE_UP(5),NOT_URGENT(6),COMMON(7),URGENT(8);

    private int states;
    TaskStates(int states){this.states=states;}

    @Override
    public int getStates() {
        return states;
    }
}
