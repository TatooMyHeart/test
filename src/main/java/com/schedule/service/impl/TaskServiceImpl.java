package com.schedule.service.impl;

import com.schedule.entity.Tasks;
import com.schedule.entity.Users;
import com.schedule.paramterBody.Taskwithfeedback;
import com.schedule.repository.TaskRepository;
import com.schedule.repository.UserRepository;
import com.schedule.service.TaskService;
import com.schedule.state.TaskStates;
import com.schedule.tool.RandomId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.HTMLDocument;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by dell on 2017/7/22.
 */
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public TaskStates createTask(List<Tasks> tasksList, HttpServletRequest request) {

        TaskStates taskStates = TaskStates.SUCCESS;
        if(tasksList==null){return TaskStates.ERROR;}
        HttpSession session = request.getSession();
        Users users = new Users();
        Date date = new Date();
        RandomId randomId = new RandomId();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        users = (Users)session.getAttribute("users");
        String time = simpleDateFormat.format(date);
        String taskid = randomId.randomNumForId();
        for(int i=0;i<tasksList.size();i++)
        {
            tasksList.get(i).setPublishtime(time);
            tasksList.get(i).setState(TaskStates.LAUNCHED.getStates());
            tasksList.get(i).setTaskid(taskid);
            tasksList.get(i).setUsers(users);
        }
        taskRepository.save(tasksList);
        return taskStates;
    }

    @Override
    public List<Tasks> getRecievedTsaks(HttpServletRequest request) {
        List<Tasks> tasksList = new ArrayList<Tasks>();
        HttpSession session = request.getSession();
        Users users =new Users();
        Date datenow = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        users = (Users) session.getAttribute("users");
        Integer executorid = users.getUserid();
        tasksList = taskRepository.findAllByExecutorid(executorid);
        for(int i=0;i<tasksList.size();i++)
        {
            if(tasksList.get(i).getState()==2) {
                try {
                    Date deadline = simpleDateFormat.parse(tasksList.get(i).getDeadline());
                    if (deadline.getTime() < datenow.getTime()) {
                        tasksList.get(i).setState(4);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }
        return tasksList;
    }

    @Override
    public List<Tasks> getSendTsaks(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Users users =new Users();
        users = (Users) session.getAttribute("users");
        List<Tasks> tasksList = new ArrayList<Tasks>();
        tasksList=taskRepository.findDistinctByUsers(users);
        return tasksList;
    }

    @Override
    public TaskStates finishTask(String task_id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        TaskStates taskStates = TaskStates.SUCCESS;
        Users users = (Users) session.getAttribute("users");
        Integer executorid = users.getUserid();
        Tasks tasks = taskRepository.findAllByExecutoridAndTaskid(users.getUserid(), task_id);
        if (tasks == null) {
            return TaskStates.ERROR;
        }
        taskRepository.updateStateByTaskidAndExecutorid(3, task_id, executorid);
        return taskStates;
    }



    @Override
    public TaskStates giveupTask(String task_id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        TaskStates taskStates = TaskStates.SUCCESS;
        Users users = (Users)session.getAttribute("users");
        Integer executorid = users.getUserid();
        Tasks tasks = taskRepository.findAllByExecutoridAndTaskid(users.getUserid(),task_id);
        if(tasks==null){return  TaskStates.ERROR;}
        taskRepository.updateStateByTaskidAndExecutorid(5,task_id,executorid);
        return taskStates;
    }

    @Override
    public TaskStates sendFeedback(String feedback,String taskid,HttpServletRequest request) {
        HttpSession session = request.getSession();
        Users users = new Users();
        users = (Users)session.getAttribute("users");
        Integer executorid = users.getUserid();
        Tasks tasks = taskRepository.findAllByExecutoridAndTaskid(executorid,taskid);
        if(tasks==null){return  TaskStates.ERROR;}
        taskRepository.updateFeedbackByTaskidAndExecutorid(feedback,taskid,executorid);

        return TaskStates.SUCCESS;
    }

    @Override
    public List<Taskwithfeedback> getFeedback(String task_id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<Taskwithfeedback> taskwithfeedbackList = new ArrayList<Taskwithfeedback>();
        Users users = new Users();
        Users users1 = new Users();
        String feedback="";
        users = (Users)session.getAttribute("users");
       List<Tasks> tasksList = new ArrayList<Tasks>();
        tasksList = taskRepository.findAllByTaskidAndUsers(task_id,users);
        if(tasksList==null){return null;}
        for(int i=0;i<tasksList.size();i++)
        {
            Taskwithfeedback taskwithfeedback = new Taskwithfeedback();
            users1=userRepository.findAllByUserid(tasksList.get(i).getExecutorid());
            feedback = tasksList.get(i).getFeedback();
            if(feedback==null){
                taskwithfeedback.setFeedback("未反馈");
            }
            else
            {
               //如果以后有文件类型的反馈，这里需要判断一下反馈类型
                taskwithfeedback.setFeedback(feedback);
            }

           taskwithfeedback.setNickname(users1.getNickname());
            taskwithfeedback.setStudentid(users1.getStudentid());
           taskwithfeedbackList.add(i,taskwithfeedback);
        }
        return taskwithfeedbackList;
    }

    @Override
    public TaskStates deleteTask(String task_id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Users users = new Users();
        users = (Users)session.getAttribute("users");
        taskRepository.deleteByTaskidAndUsersid(task_id,users.getUserid());
        return TaskStates.SUCCESS;
    }

    @Override
    public List<Tasks> getTask(String task_id,HttpServletRequest request) {
       List<Tasks> tasksList = new ArrayList<Tasks>();
        tasksList=taskRepository.findByTaskid(task_id);
        if(tasksList==null){return null;}
        return tasksList;
    }
}
