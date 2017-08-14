package com.schedule.repository;

import com.schedule.entity.Tasks;
import com.schedule.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dell on 2017/7/22.
 */
@Repository
@Transactional
public interface TaskRepository extends JpaRepository<Tasks,Integer> {

    List<Tasks> findByTaskid(String taskid);
    List<Tasks> findAllByExecutorid(Integer executorid);
    List<Tasks> findAllByTaskidAndUsers(String taskid, Users users);
    Tasks findAllByExecutoridAndTaskid(Integer executorid, String taskid);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update tasks tasks0 set tasks0.state =?1 where tasks0.taskid=?2 and tasks0.executorid=?3",nativeQuery = true)
    void updateStateByTaskidAndExecutorid(int state, String taskid, Integer executorid);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update tasks tasks0 set tasks0.feedback =?1 where tasks0.taskid=?2 and tasks0.executorid=?3",nativeQuery = true)
    void updateFeedbackByTaskidAndExecutorid(String feedback, String taskid, Integer executorid);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from tasks where taskid =?1 and users_userid=?2",nativeQuery = true)
    void deleteByTaskidAndUsersid(String taskid, Integer usersid);


    List<Tasks> findDistinctByUsers(Users users);


}
