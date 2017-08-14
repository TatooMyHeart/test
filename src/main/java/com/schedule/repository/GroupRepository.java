package com.schedule.repository;

import com.schedule.entity.Groups;
import com.schedule.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dell on 2017/7/21.
 */
@Repository
@Transactional
public interface GroupRepository extends JpaRepository<Groups,Integer> {
  List<Groups> findAllByUsersAndAndGroupUserid(Users users, Integer groupuserid);
  List<Groups> findAllByGroupid(String groupid);

  Groups findAllByGroupidAndGroupUserid(String groupid, Integer groupuserid);

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query(value = "delete from groups where groupid =?1 and groupUserid = ?2",nativeQuery = true)
  void deleteByGroupidAndGroupUserid(String groupid, Integer groupuserid);

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query(value = "delete from groups where groupid =?1 and users_userid=?2",nativeQuery = true)
  void deleteByGroupidAndUserid(String groupid, Integer userid);

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query(value = "insert into groups (groupid,users_userid,name,groupUserid) values (?1,?2,?3,?4)",nativeQuery = true)
  void insertAll(String groupid, Integer userid, String name, Integer groupuserid);

}
