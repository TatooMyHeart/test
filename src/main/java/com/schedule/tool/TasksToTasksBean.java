package com.schedule.tool;

import com.schedule.entity.Tasks;
import com.schedule.paramterBody.TasksBean;

/**
 * Created by dell on 2017/7/28.
 */
public class TasksToTasksBean {
    public TasksBean turn(Tasks tasks)
    {
        TasksBean tasksBean = new TasksBean();
        tasksBean.setContext(tasks.getContext());
        tasksBean.setDeadline(tasks.getDeadline());
        tasksBean.setFeedback(tasks.getFeedback());
        tasksBean.setFeedbacktype(tasks.getFeedbacktype());
        tasksBean.setPublishtime(tasks.getPublishtime());
        tasksBean.setState(tasks.getState());
        tasksBean.setTaskid(tasks.getTaskid());
        tasksBean.setUrgency(tasks.getUrgency());
        tasksBean.setTitle(tasks.getTitle());
        return tasksBean;
    }
}
