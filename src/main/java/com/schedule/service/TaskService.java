package com.schedule.service;

import com.schedule.entity.Tasks;
import com.schedule.paramterBody.Taskwithfeedback;
import com.schedule.state.TaskStates;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by dell on 2017/7/22.
 */
@Service
public interface TaskService {
    TaskStates createTask(List<Tasks> tasksList, HttpServletRequest request);
    List<Tasks> getRecievedTsaks(HttpServletRequest request);
    List<Tasks> getSendTsaks(HttpServletRequest request);
    TaskStates finishTask(String task_id, HttpServletRequest request);
    TaskStates giveupTask(String task_id, HttpServletRequest request);
    TaskStates sendFeedback(String feedback, String taskid, HttpServletRequest request);
    List<Taskwithfeedback> getFeedback(String task_id, HttpServletRequest request);
    TaskStates deleteTask(String task_id, HttpServletRequest request);

    List<Tasks> getTask(String task_id, HttpServletRequest request);
}
