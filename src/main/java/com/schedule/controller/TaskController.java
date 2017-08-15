package com.schedule.controller;

import com.google.gson.Gson;
import com.schedule.entity.Tasks;
import com.schedule.entity.Users;
import com.schedule.paramterBody.Executors;
import com.schedule.paramterBody.TasksBean;
import com.schedule.paramterBody.Taskwithfeedback;
import com.schedule.service.impl.TaskServiceImpl;
import com.schedule.service.impl.UserServiceImpl;
import com.schedule.state.TaskStates;
import com.schedule.tool.TasksToTasksBean;
import com.schedule.webSocket.SystemWebSocketHandler;
import com.schedule.webSocket.WebSocketConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by dell on 2017/7/22.
 */
@RestController
public class TaskController {
    @Autowired
    TaskServiceImpl taskService;

    @Autowired
    UserServiceImpl userService;

    @Bean
    public SystemWebSocketHandler systemWebSocketHandler(){
        return  new SystemWebSocketHandler();
    }

    @RequestMapping(value = "createTask",method = RequestMethod.POST)
    public int createTask(@RequestBody Executors Taskwithexecutors, HttpServletRequest request) {
        //验证日期格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setLenient(false);
        try {
            simpleDateFormat.parse(Taskwithexecutors.getDeadline());
        } catch (ParseException e) {
            return TaskStates.ERROR.getStates();
        }

        TaskStates taskStates = TaskStates.SUCCESS;
        List<Users> usersList=Taskwithexecutors.getUsers();
        List<Tasks> tasksList = new ArrayList<Tasks>();
        for(int i=0;i<usersList.size();i++)
        {
            Tasks tasksget = new Tasks();
            tasksget.setTitle(Taskwithexecutors.getTitle());
            tasksget.setContext(Taskwithexecutors.getContext());
            tasksget.setDeadline(Taskwithexecutors.getDeadline());
            tasksget.setUrgency(Taskwithexecutors.getUrgency());
            tasksget.setFeedbacktype(Taskwithexecutors.getFeedbacktype());
            tasksget.setExecutorid(usersList.get(i).getUserid());
            tasksList.add(tasksget);
        }
        taskStates = taskService.createTask(tasksList,request);
        return taskStates.getStates();
    }

    @RequestMapping(value = "sendMessage",method = RequestMethod.POST)
    public int sendMessage(HttpServletRequest request)
    {
        String taskid = request.getParameter("task_id");
        List<Tasks> tasksList = new ArrayList<Tasks>();
        tasksList=taskService.getTask(taskid,request);
        String taskinfo = tasksList.get(0).getTitle();
        for(int i=0;i<tasksList.size();i++)
        {
            boolean res=systemWebSocketHandler().sendMessageToExcutor(tasksList.get(i).getExecutorid(),new TextMessage(taskinfo));
            if(!res){return TaskStates.ERROR.getStates();}
        }
     return TaskStates.SUCCESS.getStates();
    }

    @RequestMapping(value = "getRecievedTasks",method = RequestMethod.POST)
    public List<TasksBean> getRecievedTsaks(HttpServletRequest request){
        List<Tasks> tasksList = new ArrayList<Tasks>();
        List<TasksBean> tasksBeanList = new ArrayList<TasksBean>();
        TasksToTasksBean tasksToTasksBean = new TasksToTasksBean();
        tasksList = taskService.getRecievedTsaks(request);
        if(tasksList==null){return null;}
        for(int i=0;i<tasksList.size();i++)
        {
            TasksBean tasksBean = new TasksBean();
            tasksBean=tasksToTasksBean.turn(tasksList.get(i));
            tasksBeanList.add(tasksBean);
        }
        return tasksBeanList;
    }

    @RequestMapping(value = "getSendTasks",method = RequestMethod.POST)
    public List<TasksBean> getSendTsaks(HttpServletRequest request){

        List<Tasks> tasksList = new ArrayList<Tasks>();
        List<TasksBean> tasksBeanList = new ArrayList<TasksBean>();
        TasksToTasksBean tasksToTasksBean = new TasksToTasksBean();
        tasksList = taskService.getSendTsaks(request);
        if(tasksList==null){return null;}
        for(int i=0;i<tasksList.size();i++)
        {
            TasksBean tasksBean = new TasksBean();
            tasksBean=tasksToTasksBean.turn(tasksList.get(i));
            tasksBeanList.add(tasksBean);
        }
        for(int j=0;j<tasksBeanList.size();j++)
        {
            for(int k=tasksBeanList.size()-1;k>j;k--)
            {
                if(tasksBeanList.get(k).getTaskid().equals(tasksBeanList.get(j).getTaskid()))
                {
                    tasksBeanList.remove(k);
                }
            }
        }
        return tasksBeanList;
    }

    @RequestMapping(value = "finishTask",method = RequestMethod.POST)
    public int finishTask(HttpServletRequest request){
        String taskid = request.getParameter("task_id");
        TaskStates taskStates = TaskStates.SUCCESS;
        taskStates = taskService.finishTask(taskid,request);
        return taskStates.getStates();
    }


    @RequestMapping(value = "giveupTask",method = RequestMethod.POST)
    public int giveupTask(HttpServletRequest request){
        String taskid = request.getParameter("task_id");
        TaskStates taskStates = TaskStates.SUCCESS;
        taskStates = taskService.giveupTask(taskid,request);
        return taskStates.getStates();
    }


    @RequestMapping(value = "sendFeedback",method = RequestMethod.POST)
    public int sendFeedback(HttpServletRequest request)
    {
        String feedback = request.getParameter("feedback");
        String taskid = request.getParameter("task_id");
        TaskStates taskStates = TaskStates.SUCCESS;
        taskStates = taskService.sendFeedback(feedback,taskid,request);
        return taskStates.getStates();
    }


    @RequestMapping(value = "getFeedback",method = RequestMethod.POST)
    public List<Taskwithfeedback> getFeedback(HttpServletRequest request){
        String taskid = request.getParameter("task_id");
        List<Taskwithfeedback> taskwithfeedbackList = new ArrayList<Taskwithfeedback>();
        taskwithfeedbackList = taskService.getFeedback(taskid,request);
        if(taskwithfeedbackList==null){return null;}
        return taskwithfeedbackList;
    }


    @RequestMapping(value = "deleteTask",method = RequestMethod.POST)
    public int deleteTask(HttpServletRequest request){
        String taskid = request.getParameter("task_id");
        TaskStates taskStates = TaskStates.SUCCESS;
        taskStates = taskService.deleteTask(taskid,request);
        return taskStates.getStates();
    }


}
